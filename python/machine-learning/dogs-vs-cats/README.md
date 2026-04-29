
# ⭐ Dogs vs Cats — MobileNetV2 Classifier (Google Colab)

`🐍 Python` `🧠 TensorFlow` `📦 TFLite` `☁️ Google Colab`

Google Colab script that trains a binary image classifier (cat vs dog) using transfer learning with MobileNetV2, then exports the model to TFLite format ready to be used in Android with ML Kit or LiteRT.

## ✨ Features

- Transfer learning on MobileNetV2 (pretrained on ImageNet)
- Dataset: `cats_vs_dogs` from TensorFlow Datasets (~23 000 images)
- Training: 10 epochs with Adam optimizer
- Export: SavedModel → TFLite with default quantization optimization
- Output: `cats_dogs_model.tflite` + `labels.txt`
- Works offline on Android (no internet needed for inference)

## 📚 Dependencies

* tensorflow >= 2.15
* tensorflow-datasets

```bash
pip install tensorflow tensorflow-datasets
```

## 🚀 How to run

https://github.com/dacape-dev/examples/tree/main/python#-how-to-run

## 📤 Output files

| File | Description |
|---|---|
| `cats_dogs_model.tflite` | Quantized TFLite model (~8 MB) |
| `labels.txt` | Class labels (`cat`, `dog`) |

> These files are downloaded automatically at the end of the Colab run. Copy them to the Android project's `assets/` folder.
