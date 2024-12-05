# Cloud Computing
-

## URL
Backend URL : http://34.101.216.232:3000

## Deploy
- Clone this repository
- You need to prepare two services: cloud storage & cloud SQL
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
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
      ```
    - create table histories
    - ```
      CREATE TABLE histories (
      id INT(11) AUTO_INCREMENT PRIMARY KEY,
      user_id INT(11),
      complaint_disease TEXT NULL, 
      check_result TEXT NULL, 
      check_date DATETIME NULL, 
      FOREIGN KEY (user_id) REFERENCES users(id)
          ON DELETE CASCADE
          ON UPDATE CASCADE
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
      ```
- Don't forget to configure auth in GCP

## Endpoint
### Register
#### Description : Register a new user.
- Description: Register a new user.
- Endpoint: /auth/register
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
  "name": "karakter-lebih-dari-12",
  "email": "fromat-email@example.com",
  "password": "karakter-lebih-dari-8"
}
```
- Success Response:
```
{
  "error": false,
  "message": "User Created"
}
```

- Failure Response (Invalid Email):
```
{
    "error": true,
    "message": "Invalid email"
}
```
- Failure Response (Username Less Than 12 Characters And Password Less Than 8 Characters):
```
{
    "error": true,
    "message": "Username must have a minimum of 8 characters"
}
```
- Failure Response (Password Less Than 8 Characters):
```
{
    "error": true,
    "message": "Password must have a minimum of 8 characters"
}
```
- Failure Response (Email Already Registered):
```
{
    "error": true,
    "message": "Email is already registered."
}
```

### Login
#### Description: User login to obtain a JWT token.
- Endpoint: /auth/login
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
    "error": false,
    "message": "success",
    "loginResult": {
        "userId": 1,
        "name": "jakikenjeran326",
        "token": "<JWT_TOKEN>"
    }
}
```
- Failure Response (Invalid Email or Password):
```
{
    "error": true,
    "message": "Invalid email or password"
}
```

### Show User Profile
#### Description: Get the current user profile.
- Endpoint: /profile
- Method: GET
- Request Body:
  - Authorization: Bearer <JWT_TOKEN>

- Success Response:
```
{
    "error": false,
    "user": {
        "name": "jakikenjeran326",
        "email": "jaki@example.com"
    }
}
```
- Failure Response (Invalid/Missing Token):
```
{
    "error": true,
    "message": "Invalid token"
}
{
    "error": true,
    "message": "Token missing"
}
```


### Update User Profile
#### Description: Update the user profile.
- Endpoint: /profile
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
  "error": false,
  "message": "Profile updated successfully"
}
```
- Failure Response (Name or Password Not Provided):
```
{
    "error": true,
    "message": "Name or password must be provided for update"
}
```
- Failure Response (Username Less Than 8 Characters):
```
{
    "error": true,
    "message": "Name must be at least 12 characters long"
}
```
- Failure Response (Password Less Than 8 Characters):
```
{
    "error": true,
    "message": "Password must be at least 8 characters long"
}
```

### Show User History
#### Description: Get the current user's checkup history.
- Endpoint: /histories
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
- Failure Response (Invalid/Missing Token):
```
{
    "error": true,
    "message": "Invalid token"
}
{
    "error": true,
    "message": "Token missing"
}
```
- Failure Response (User Has No History):
```
{
    "error": true,
    "message": "No history found"
}
```


