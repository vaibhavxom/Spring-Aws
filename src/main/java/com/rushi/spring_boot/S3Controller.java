package com.rushi.spring_boot;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/s3")
@CrossOrigin("*")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    // Uploads a file to AWS S3 and returns the URL of the uploaded file
    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile file) throws IOException {
        String keyName = file.getOriginalFilename();
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        
        // Upload the file to S3
        s3Service.uploadFile(keyName, tempFile.getAbsolutePath());

        // Assuming the uploaded file URL can be constructed like this
        String fileUrl = s3Service.getFileUrl(keyName);
        return "File uploaded successfully. File URL: " + fileUrl;
    }
    //get list of all uploaded files
    @GetMapping("/list")
    public List<String> listFiles() {
        return s3Service.listAllFiles();
    }

    // Downloads a file from AWS S3 to a specific location on the server
    @GetMapping("/download")
    public String downloadFile(@RequestParam String keyName,
                               @RequestParam String downloadPath) {
        s3Service.downloadFile(keyName, downloadPath);
        return "File downloaded successfully.";
    }
    
    // New: Client-side download method for direct browser download
    @GetMapping("/download-direct")
    public ResponseEntity<byte[]> downloadFileToClient(@RequestParam String keyName) {
        byte[] data = s3Service.getFileBytes(keyName);

        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + keyName + "\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(data);
    }
}
