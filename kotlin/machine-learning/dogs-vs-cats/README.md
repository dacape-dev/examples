
# ⭐ Dogs vs Cats — Android Image Classifier

`🤖 Android` `📖 Kotlin` `🚀 Jetpack Compose` `🖌️ Material Design 3` `🧠 TFLite (LiteRT)`

Android application that classifies an image (provided as a URL) as a cat or a dog. The model runs fully on-device using LiteRT (TensorFlow Lite), with no internet connection needed for inference. The TFLite model is trained with the companion [Python script](../../../../python/machine-learning/dogs-vs-cats/).

## ✨ Features

- Enter any image URL to classify it as cat or dog with confidence score
- On-device inference — works offline after first image load
- MVVM architecture with `StateFlow`
- Image loading with Coil

## 📚 Dependencies

* litert_version = '1.0.1'
* coil = '2.7.0'
* compose-bom = '2024.09.00'

```groovy
// Coil for image loading
implementation "io.coil-kt:coil-compose:2.7.0"

// LiteRT (TensorFlow Lite)
implementation "com.google.ai.edge.litert:litert:1.0.1"

implementation platform('androidx.compose:compose-bom:2024.09.00')
implementation 'androidx.compose.material3:material3'
```

## 🚀 Build and run

https://github.com/dacape-dev/examples/tree/main/kotlin#-build-and-run
