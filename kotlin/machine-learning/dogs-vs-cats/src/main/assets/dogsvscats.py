# 🐱🐶 Clasificador Cats vs Dogs
# Modelo MobileNetV2 -> Exportación TFLite compatible con ML Kit Local
# ✅ Funciona offline
# ✅ TensorFlow 2.15+

!pip install -q tensorflow tensorflow-datasets

import tensorflow as tf, tensorflow_datasets as tfds, os

# 1️⃣ Load dataset
(train, val), info = tfds.load(
    "cats_vs_dogs",
    split=["train[:80%]", "train[80%:]"],
    with_info=True,
    as_supervised=True
)

IMG, BATCH = 224, 32
N_CLASSES = info.features["label"].num_classes
LABELS = info.features["label"].names

def prep(img, label):
    img = tf.image.resize(img, (IMG, IMG))
    img = tf.cast(img, tf.float32) / 255.0
    return img, label

train = train.map(prep).shuffle(1000).batch(BATCH).prefetch(tf.data.AUTOTUNE)
val   = val.map(prep).batch(BATCH).prefetch(tf.data.AUTOTUNE)

# 2️⃣ Create base model
base = tf.keras.applications.MobileNetV2(
    input_shape=(IMG, IMG, 3),
    include_top=False,
    weights="imagenet"
)
base.trainable = False

model = tf.keras.Sequential([
    base,
    tf.keras.layers.GlobalAveragePooling2D(),
    tf.keras.layers.Dense(N_CLASSES, activation="softmax")
])

model.compile(
    optimizer="adam",
    loss="sparse_categorical_crossentropy",
    metrics=["acc"]
)

# 3️⃣ Train (10 epochs)
history = model.fit(train, validation_data=val, epochs=10)

# 4️⃣ Export model
model.export("cats_dogs_model")

# 5️⃣ Convert to TFLite
converter = tf.lite.TFLiteConverter.from_saved_model("cats_dogs_model")
converter.optimizations = [tf.lite.Optimize.DEFAULT]
tflite = converter.convert()

with open("cats_dogs_model.tflite","wb") as f:
    f.write(tflite)

with open("labels.txt","w") as f:
    f.write("\n".join(LABELS))

# 6️⃣ Info
print(f"✅ Modelo listo con {N_CLASSES} clases")
print(f"Tamaño final: {os.path.getsize('cats_dogs_model.tflite')/1024/1024:.2f} MB")

# 7️⃣ Download files
from google.colab import files
files.download("cats_dogs_model.tflite")
files.download("labels.txt")