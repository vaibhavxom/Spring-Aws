package com.rushi.spring_boot;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.S3Client;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;

import org.springframework.stereotype.Service;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName = "myawsbucket3026";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(String keyName, String filePath) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build(),
                    RequestBody.fromFile(Path.of(filePath))
            );
        } catch (S3Exception e) {
            System.err.println("Error uploading file: " + e.getMessage());
        }
    }
    
    public List<String> listAllFiles() {
        ListObjectsV2Request listReq = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        return s3Client.listObjectsV2(listReq)
                .contents()
                .stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    public String getFileUrl(String keyName) {
        // This will generate a publicly accessible URL, adjust according to your access settings
        return "https://" + bucketName + ".s3.amazonaws.com/" + keyName;
    }

    // Method to download a file
    public void downloadFile(String keyName, String downloadPath) {
        try {
            s3Client.getObject(
                    request -> request.bucket(bucketName).key(keyName),
                    Path.of(downloadPath)
            );
        } catch (S3Exception e) {
            System.err.println("Error downloading file: " + e.getMessage());
        }
    }
    public byte[] getFileBytes(String keyName) {
        try {
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(
                GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build()
            );
            return objectBytes.asByteArray();
        } catch (S3Exception e) {
            System.err.println("Error fetching file bytes: " + e.getMessage());
            return null;
        }
    }
}
