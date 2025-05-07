# **Spring-AWS S3 Upload Application**

## 📄 Description
This is a Spring Boot application that provides RESTful APIs to upload, download, and delete files in an Amazon S3 bucket. It also includes a simple web interface (HTML/CSS/JS) to interact with these APIs.

---

## ✅ Prerequisites

- Java 17 or higher
- Maven
- AWS Account
- AWS IAM User with S3 permissions
- An S3 bucket already created in your AWS account

---

## ⚙️ Installation Instructions

### 1. **Clone the Repository**
```bash
git clone https://github.com/yourusername/spring-aws.git
cd spring-aws
```

---

### 2. **Dependencies**

Add the following dependencies to your `pom.xml`:

```xml
<dependencies>
    <!-- Spring Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>commons-logging</groupId>
                <artifactId>commons-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <!-- Spring Cloud AWS -->
    <dependency>
        <groupId>io.awspring.cloud</groupId>
        <artifactId>spring-cloud-aws-starter</artifactId>
        <version>3.1.1</version>
    </dependency>

    <!-- AWS SDK v2 for S3 -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>s3</artifactId>
        <version>2.30.2</version>
    </dependency>

    <!-- Spring DevTools (Optional) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

### 3. **Configure AWS Credentials**

Create `application.properties` inside `src/main/resources/`:

```properties
# Server config (optional)
server.port=8081

# AWS credentials (consider using environment variables or AWS profiles for production)
aws.accessKeyId=YOUR_ACCESS_KEY
aws.secretKey=YOUR_SECRET_KEY
aws.region=us-east-1

# AWS S3 bucket name
aws.s3.bucket-name=my-bucket-3026
```

---

## 💻 Frontend Web UI

Place the following files inside `src/main/resources/templates/`:

### 🔹 index.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>S3 File Upload</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h2>Upload File to AWS S3</h2>
    <form id="uploadForm">
        <input type="file" id="fileInput" name="file" required />
        <button type="submit">Upload</button>
    </form>
    <p id="response"></p>

    <script src="script.js"></script>
</body>
</html>
```

### 🔹 styles.css

```css
body {
    font-family: Arial, sans-serif;
    text-align: center;
    margin-top: 50px;
    background-color: #f3f3f3;
}

form {
    display: inline-block;
    padding: 20px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

button {
    margin-top: 10px;
    padding: 10px 20px;
    border: none;
    background: #007BFF;
    color: white;
    cursor: pointer;
    border-radius: 4px;
}

button:hover {
    background: #0056b3;
}
```

### 🔹 script.js

```javascript
document.getElementById('uploadForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];

    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch('/api/s3/upload', {
            method: 'POST',
            body: formData
        });

        const result = await response.json();
        document.getElementById('response').innerText = result.url || JSON.stringify(result);
    } catch (err) {
        console.error(err);
        document.getElementById('response').innerText = 'Upload failed!';
    }
});
```

---

## 📡 API Usage

### 1. **Upload a File**

- **Endpoint:**
  ```http
  POST /api/s3/upload
  ```

- **Request:**
  - Content-Type: `multipart/form-data`
  - Form-data key: `file`

- **Response:**
  - JSON with the S3 file URL.

---

### 2. **Download a File**

- **Endpoint:**
  ```http
  GET /api/s3/download?keyName=FileName.jpg&downloadPath=/path/to/save/FileName.jpg
  ```

- **Response:**
  - Downloads the specified file from S3 to a local path (server-side).

---

### 3. **Delete a File**

- **Endpoint:**
  ```http
  DELETE /api/s3/delete?fileName=FileName.jpg
  ```

- **Response:**
  - Success or error message based on the file deletion outcome.

---

## 🛠 Troubleshooting

### 🔸 Commons Logging Warning
If you see:
```
Standard Commons Logging discovery in action with spring-jcl: please remove commons-logging.jar from classpath in order to avoid potential conflicts
```
You're already handling it correctly with this exclusion:
```xml
<exclusion>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
</exclusion>
```
Ensure no other library brings `commons-logging` transitively into your classpath.

---

## 🚀 Run the Application
```bash
mvn spring-boot:run
```

Then open the browser and go to:

```
http://localhost:8081/index.html
```

---

## 🙌 Author

**Rushikesh Mithagare**  
Royal Education Society College of Computer Science and Information Technologies, Latur  
Swami Ramanand Teerth Marathwada University, Nanded  
Graduating: April 2025

---
