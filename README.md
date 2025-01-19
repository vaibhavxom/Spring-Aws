# AWS Spring Boot File Upload

## Description
This project is a Spring Boot application that allows users to upload images and files to an Amazon S3 bucket. It provides RESTful API endpoints for uploading and deleting files, making it easy to manage file storage in the cloud.

## Prerequisites
- Java 11 or higher
- Maven
- AWS Account
- AWS SDK for Java
- An S3 bucket created in your AWS account

## Installation Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/aws-spring-file-upload.git
   cd aws-spring-file-upload
2. **Add AWS SDK Dependency**
    Add the following dependency to your pom.xml file:
   ```xml
   <dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk</artifactId>
    <version>1.11.133</version>
    </dependency>
3. **Configure AWS Credentials** Create an application.properties file in the src/main/resources directory with the following content:
   ```propertie
   aws.accessKeyId= YOUR_ACCESS_KEY
   aws.secretKey= YOUR_SECRET_KEY
   aws.region=us-east-1
4.Usage
API Endpoints
Upload File

Endpoint: POST /storage/uploadFile
Request: Form-data with key file (type: File)
Response: Returns the URL of the uploaded file.
   
