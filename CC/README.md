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
