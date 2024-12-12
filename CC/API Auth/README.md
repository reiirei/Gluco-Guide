# Welcome to the GlucoGuide Authentication API Repository!  

This repository provides the backend functionalities for the **GlucoGuide** application, focusing exclusively on user authentication and management. It includes features for secure user registration, login, and CRUD (Create, Read, Update, Delete) operations for user profiles.  

## Key Features:
- **User Registration**: Enable new users to create an account securely.  
- **User Login**: Authenticate users and issue JWT tokens for session management.  
- **User Profile Management**: Perform CRUD operations on user profiles, including updating usernames and passwords.  

## API Documentation:  
Comprehensive API documentation is available, detailing each authentication-related endpoint, including request formats, responses, and example payloads.  

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
Running on http://127.0.0.1:3000/ (Press CTRL+C to quit)
```

## Endpoint API
**URL**: http://127.0.0.1:3000

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

