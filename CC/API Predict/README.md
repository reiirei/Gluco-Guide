# GlucoGuide Predict API

GlucoGuide Predict API is a Flask-based application that utilizes a TensorFlow model to predict diabetes risk levels based on user input. This API receives user health data through an endpoint and provides a classification on whether someone is at risk of diabetes or not.

## Features

- **Input**: Users can submit data in the form of medical features such as age, BMI, HbA1c level, and more.
- **Prediction**: The API returns predictions about the user's diabetes status (e.g., "uncontrolled diabetes", "at risk", etc.).
- **Model**: A TensorFlow model is used to process the input and provide prediction results.

## Instalation

1. **Clone Repository**  
   Clone this repository to your local machine:
   ```
   git clone <URL_REPO>
   cd <FOLDER_PROJECT>
2. **Install Dependency**
   Install the required dependencies using pip:
   ```
   pip install -r requirements.txt

## Running the Application
To run the application, simply execute the app.py file:
```
python app.py
```
Once the server is running, you will see output like this in the terminal:
```
Running on http://127.0.0.1:3000/ (Press CTRL+C to quit)
```
## Endpoint API
**URL**: http://127.0.0.1:3000/api/predict

**Method**: **POST**

**Body Request (JSON)**: Send JSON data containing the user's medical information for prediction. Here is an example of the data that can be sent:
```
{
    "age": 45,
    "hypertension": 1,
    "heart_disease": 0,
    "bmi": 28.5,
    "HbA1c_level": 6.0,
    "blood_glucose_level": 120,
    "gender_encoded": 1,
    "smoking_history_encoded": 0
}
```
**Response**: The API will return the prediction result in JSON format. Example response:
```
{
    "prediction": "uncontrolled diabetes"
}
```
## Notes
- Ensure all dependencies are installed correctly and the Flask server is running before testing the API.
- If you encounter errors related to TensorFlow or the model, ensure that the `.keras` model file is in the correct folder and compatible with the TensorFlow version being used.
