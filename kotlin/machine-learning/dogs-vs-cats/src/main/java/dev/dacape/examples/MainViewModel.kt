package dev.dacape.examples

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ClassificationUiState(
    val imageUrl: String = "https://images.dog.ceo/breeds/retriever-golden/n02099601_3004.jpg",
    val results: List<Pair<String, Float>> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val bitmap: Bitmap? = null
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(ClassificationUiState())
    val uiState: StateFlow<ClassificationUiState> = _uiState.asStateFlow()

    private val classifierHelper = ImageClassifierHelper(application)
    private val imageLoader = ImageLoader(application)

    fun onUrlChange(newUrl: String) {
        _uiState.update { it.copy(imageUrl = newUrl) }
    }

    fun classifyFromUrl() {
        val url = _uiState.value.imageUrl
        if (url.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, error = null, results = emptyList()) }
            
            try {
                val request = ImageRequest.Builder(getApplication())
                    .data(url)
                    .allowHardware(false) // Required to get a software bitmap for TFLite
                    .build()
                
                val result = imageLoader.execute(request)
                val drawable = result.drawable
                
                if (drawable is BitmapDrawable) {
                    val bitmap = drawable.bitmap
                    val classificationResults = classifierHelper.classify(bitmap)
                    
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            results = classificationResults,
                            bitmap = bitmap
                        ) 
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "No se pudo cargar la imagen") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error: ${e.message}") }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        classifierHelper.close()
    }
}
