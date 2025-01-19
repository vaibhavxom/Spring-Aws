package com.rushi.spring_boot;
  
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service 
public class S3Service {
	

	@Autowired
	private S3Client s3Client;

	private final String bucketName = "myawsbucket3026";

	/**
	 * Uploads a file to the S3 bucket.
	 */
	public void uploadFile(String keyName, String filePath)
	{
		PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();
		s3Client.putObject(putObjectRequest, RequestBody.fromFile(Paths.get(filePath)));
	}

	/**
	 * Downloads a file from the S3 bucket
	 */
	public void downloadFile(String keyName, String downloadPath)
	{
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
		s3Client.getObject(getObjectRequest, Paths.get(downloadPath));
	}
  
}