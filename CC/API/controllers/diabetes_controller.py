import tensorflow as tf
import numpy as np

MODEL_PATH = './models/diabetes_model2.keras'
model = None

def load_model():
    global model
    try:
        model = tf.keras.models.load_model(MODEL_PATH)
        print("Model loaded successfully.")
    except Exception as e:
        print(f"Error loading model: {e}")

def predict_diabetes(data):
    if not model:
        raise RuntimeError("Model not loaded")

    features = [
        "age", "hypertension", "heart_disease", "bmi",
        "HbA1c_level", "blood_glucose_level", "gender_encoded", "smoking_history_encoded"
    ]
    input_data = [data[feature] for feature in features]
    input_array = np.array([input_data], dtype=np.float32)
    predictions = model.predict(input_array)
    predicted_class = np.argmax(predictions[0])

    results = [
        "Normal",
        "Pre-diabetes",
        "Controlled Diabetes",
        "Uncontrolled Diabetes",
    ]

    return {
        "input": data,
        "predictedClass": int(predicted_class),
        "description": results[predicted_class]
    }