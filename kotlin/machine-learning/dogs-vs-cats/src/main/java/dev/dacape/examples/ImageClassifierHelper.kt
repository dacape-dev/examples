package dev.dacape.examples

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class ImageClassifierHelper(
    private val context: Context,
    private val modelPath: String = "cats_dogs_model.tflite",
    private val labelsPath: String = "labels.txt"
) {
    private var interpreter: Interpreter? = null
    private val labels: List<String> by lazy {
        context.assets.open(labelsPath).bufferedReader().readLines()
    }

    private val inputSize = 224 // As per dogsvscats.py

    init {
        setupInterpreter()
    }

    private fun setupInterpreter() {
        try {
            val assetFileDescriptor = context.assets.openFd(modelPath)
            val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
            val fileChannel = fileInputStream.channel
            val startOffset = assetFileDescriptor.startOffset
            val declaredLength = assetFileDescriptor.declaredLength
            val modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            
            val options = Interpreter.Options()
            interpreter = Interpreter(modelBuffer, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun classify(bitmap: Bitmap): List<Pair<String, Float>> {
        val interpreter = interpreter ?: return emptyList()

        // Pre-process the image
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
        val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)

        // Output buffer: [1, numLabels]
        val outputBuffer = Array(1) { FloatArray(labels.size) }

        // Run inference
        interpreter.run(inputBuffer, outputBuffer)

        // Post-process results
        return labels.mapIndexed { index, label ->
            label to outputBuffer[0][index]
        }.sortedByDescending { it.second }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        // 4 bytes per float, 3 channels (RGB)
        val byteBuffer = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(inputSize * inputSize)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixelValue in intValues) {
            // Normalize to [0, 1] as per dogsvscats.py prep function
            byteBuffer.putFloat((pixelValue shr 16 and 0xFF) / 255.0f)
            byteBuffer.putFloat((pixelValue shr 8 and 0xFF) / 255.0f)
            byteBuffer.putFloat((pixelValue and 0xFF) / 255.0f)
        }
        return byteBuffer
    }

    fun close() {
        interpreter?.close()
        interpreter = null
    }
}
