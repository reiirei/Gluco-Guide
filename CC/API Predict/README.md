# Welcome to the GlucoGuide API Repository!
This repository serves as the backend for GlucoGuide, an application designed to assist users in managing their health by providing features such as user authentication, profile management, health history tracking, and diabetes prediction using machine learning models.

## Key Features:
- Diabetes Prediction: Utilize machine learning to predict the user's current health condition based on key health metrics.
- Health History: Store past health checkup records.

## API Documentation:
Explore the detailed API documentation for each endpoint, including request formats, responses, and example payloads. The endpoints are structured to ensure scalability and security for the best user experience.

Feel free to dive into the code and contribute to improving the application. Let’s make health management smarter and more accessible!

## Installation
1. Clone the Repository
Clone this repository to your local machine:
```
git clone <URL_REPO>
cd <PROJECT_FOLDER>
```
2. Install Dependencies
Install the required dependencies using pip:
```
pip install -r requirements.txt
```
## Running the Application
To run the application, simply execute the app.py file:
```
python app.py
```
Once the server is running, you will see output like this in the terminal:

```
Running on http://127.0.0.1:5000/ (Press CTRL+C to quit)
```

## Endpoint API
**URL**: http://127.0.0.1:5000/api/predict

### Predict Diabetes
#### Description: predict user current health condition using machine learning model.
- Endpoint: api/predict
- Method: POST
- Request Body:
  - Content-Type: application/json
- Body (JSON):
```
{
    "name": "jakikenjeran",
    "age": 20,
    "hypertension": "no",
    "heart_disease": "no",
    "bmi": 28.4,
    "HbA1c_level": 5.4,
    "blood_glucose_level": 100,
    "gender_encoded": "male",
    "smoking_history_encoded": "never"
}
```
- Success Response:
```
{
    "description": "Pra-diabetes",
    "input": {
        "HbA1c_level": 5.4,
        "age": 20,
        "blood_glucose_level": 100,
        "bmi": 28.4,
        "gender_encoded": "male",
        "heart_disease": "no",
        "hypertension": "no",
        "name": "jakikenjeran",
        "smoking_history_encoded": "never"
    }
}
```
- Failure Response
```
Return the error message that occurred in the code.
```
## Notes
- Make sure you have installed all dependencies correctly and the Flask server is running before testing the API.
- If you encounter errors related to TensorFlow or the model, ensure that the .keras model file is in the correct folder and compatible with the TensorFlow version in use.
