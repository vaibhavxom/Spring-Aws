# **Spring-Aws**

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
   ``` bash
   git clone https://github.com/yourusername/spring-aws.git
   cd spring-aws
     ```
#
2. **Add AWS SDK Dependency**
    Add the following dependency to your pom.xml file:
``` xml
   <dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk</artifactId>
    <version>1.11.133</version><!--use latest verison -->
    </dependency>

     <dependency>
    <groupId>io.awspring.cloud</groupId>
    <artifactId>spring-cloud-aws-starter
    </artifactId>
    <version>3.1.1</version><!--use latest verison -->
    </dependency>
```
#

3. **Configure AWS Credentials** Create an application.properties file in the src/main/resources directory with the following content:
   ``` Propertie
   server.port=8081    //optional you can use default port
   aws.accessKeyId= YOUR_ACCESS_KEY
   aws.secretKey= YOUR_SECRET_KEY
   aws.region=us-east-1
   ```
#
4.Usage
API Endpoints
Upload File

Endpoint:

`POST` ```localhost:8081/api/s3/upload```

Request: Form-data with key file (type: File)
Response: Returns the URL of the uploaded file.

#

5.Usage 
API Endpoints
Download 

Endpoint:

`GET` ```http://localhost:8081/api/s3/download?keyName=FileName.jpg&downloadPath=DownloadPath/FileName.jpg```
#

   
