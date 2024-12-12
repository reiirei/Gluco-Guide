# Cloud Computing
<div align="center">
  <img src="https://github.com/reiirei/Gluco-Guide/blob/main/CC/github_assets_cc/cloud%20computing.png" alt="Cloud Computing Logo" width="600">
</div>

---
This repository showcases a comprehensive cloud architecture design aimed at providing scalable, secure, and efficient solutions for modern applications. It includes detailed diagrams, implementation guides, and best practices to help you build robust cloud infrastructures tailored to your needs.

## URL
- Backend auth URL : https://glucoguide-auth-276770190589.asia-southeast2.run.app
- Backend model URL : https://glucoguide-predict-276770190589.asia-southeast2.run.app

## Deploy
- Clone this repository
- You need to make database in cloud sql 
  - mysql configuration :
    - create database
    - ```
      CREATE DATABASE glucoguide;
      ```
    - use database
    - ```
      USE DATABASE glucoguide;
      ```
    - create table users
    - ```
      CREATE TABLE users (
        id INT(11) AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) UNIQUE NOT NULL, 
        email VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL
      );
      ```
    - create table histories
    - ```
      CREATE TABLE histories (
        id INT(11) AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255),
        age INT(11),
        hypertension VARCHAR(255) NULL,
        heart_disease VARCHAR(255) NULL,
        body_mass_index FLOAT(11) NULL,
        HbA1c_level FLOAT(11) NULL,
        blood_glucose_level INT(11) NULL,
        gender VARCHAR(255) NULL,
        smoking_history VARCHAR(255) NULL, 
        diabetes_category VARCHAR(255) NULL, 
        check_date DATETIME NULL, 
        FOREIGN KEY (name) REFERENCES users(name)
            ON DELETE CASCADE
            ON UPDATE CASCADE
      );
      ```
- use this Dockerfile
```
# Use Node.js as the base image
FROM node:18

# Set working directory
WORKDIR /usr/src/app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire project code
COPY . .

# Expose the port used by the application
EXPOSE 3000

# Run the application
CMD ["node", "app.js"]
```
- Don't forget to create a .dockerignore file
```
node_modules
.env
Dockerfile
.git
.gitignore
npm-debug.log
```
- Create a repository in Artifact Registry
``` gcloud cli
gcloud artifacts repositories create glucoguide-auth \
    --repository-format=docker \
    --location=[REGION] \
    --description="Docker repository for Glucoguide Auth"

```
- Tag the Docker image you created
```
docker build -t glucoguide-auth:1.0 .
```
- Tag the Docker image for Artifact Registry
```
docker tag glucoguide-auth:1.0 [REGION]-docker.pkg.dev/[PROJECT_ID]/glucoguide-auth/glucoguide-auth:1.0
```
- Login to Artifact Registry
```
gcloud auth configure-docker [REGION]-docker.pkg.dev
```
- Push the image to Artifact Registry
```
docker push [REGION]-docker.pkg.dev/[PROJECT_ID]/glucoguide-auth/glucoguide-auth:1.0
```
## Endpoint
### 1. Register
#### Description : Register a new user.
- Description: Register a new user.
- Endpoint: /api/register
- Method: POST
- Request Body:
  - Content-Type: application/json
```
username as string
email as string
password as string

```
- Body (JSON)
```
{
  "name": "karakter-lebih-dari-8",
  "email": "fromat-email@example.com",
  "password": "karakter-lebih-dari-8"
}
```
- Success Response:
```
{
    "status": "success",
    "message": "User Created"
}
```

- Failure Response (Invalid Email):
```
{
    "status": "error",
    "message": "Invalid email format"
}
```
- Failure Response (Username Less Than 8 Characters):
```
{
    "status": "error",
    "message": "Username must have a minimum of 8 characters"
}
```
- Failure Response (Password Less Than 8 Characters):
```
{
    "status": "error",
    "message": "Password must have a minimum of 8 characters"
}
```
- Failure Response (Email Already Registered):
```
{
    "status": "error",
    "message": "Username or email is already registered."
}
```
- Failure Response (Username Already Taken):
```
{
    "status": "error",
    "message": "Username is already taken."
}
```

### 2. Login
#### Description: User login to obtain a JWT token.
- Endpoint: /api/login
- Method: POST
- Request Body:
  - Content-Type: application/json
```
email as string
password as string

```
- Body (JSON):
```
{
    "email": "jaki@example.com",
    "password": "password123"
}
```
- Success Response:
```
{
    "status": "success",
    "message": "Login successful",
    "loginResult": {
        "userId": 2,
        "name": "jakikenjeran",
        "token": <JWT TOKEN>
    }
}
```
- Failure Response (Invalid Email or Password):
```
{
    "status": "error",
    "message": "Invalid email or password"
}
```

### 3. Show User Profile
#### Description: Get the current user profile.
- Endpoint: api/profile
- Method: GET
- Request Body:
  - Authorization: Bearer <JWT_TOKEN>

- Success Response:
```
{
    "status": "success",
    "user": {
        "name": "jakikenjeran",
        "email": "jaki@ex.com"
    }
}
```
- Failure Response (Invalid/Missing Token):
```
{
    "status": "error",
    "message": "Invalid token"
}
{
    "status": "error",
    "message": "Token missing"
}
```


### 4. Update User Profile
#### Description: Update the user profile.
- Endpoint: api/profile
- Method: PUT
- Request Body:
  - Content-Type: application/json
  - Authorization: Bearer <JWT_TOKEN>
  
- Body (JSON):
```
{
  "name": "jakiUpdated326",
  "password": "newpassword123"
}

```
- Success Response:
```
{
    "status": "success",
    "message": "Profile updated successfully"
}
```
- Failure Response (Username or Password Not Provided):
```
{
    "status": "error",
    "message": "Username must be provided for update"
}
{
    "status": "error",
    "message": "Password must be provided for update"
}
```
- Failure Response (Username Less Than 8 Characters):
```
{
    "status": "error",
    "message": "Username must be at least 8 characters long"
}
```
- Failure Response (Password Less Than 8 Characters):
```
{
    "status": "error",
    "message": "Password must be at least 8 characters long"
}
```
- Failure Response (Username or Password same from the current values)
```
{
    "status": "error",
    "message": "New username and password must be different from the current values"
}
```
- Failure Response (Invalid/Missing Token):
```
{
    "status": "error",
    "message": "Invalid token"
}
{
    "status": "error",
    "message": "Token missing"
}
```

### 5. Show User History
#### Description: Get the current user's checkup history.
- Endpoint: api/histories
- Method: GET
- Request Body:
  - Content-Type: application/json
  - Authorization: Bearer <JWT_TOKEN>
- Success Response:
```
{
    "error": false,
    "histories": [
        {
            "keluhan": "Gula darah 200",
            "diagnosa": "Diabetes",
            "tanggal_cek": "2024-12-04 23:31:52"
        }
    ]
}
```
- Failure Response (User Has No History):
```
{
    "status": "error",
    "message": "No history found"
}
```
- Failure Response (Invalid/Missing Token):
```
{
    "status": "error",
    "message": "Invalid token"
}
{
    "status": "error",
    "message": "Token missing"
}
```
### 6. Predict Diabetes
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
