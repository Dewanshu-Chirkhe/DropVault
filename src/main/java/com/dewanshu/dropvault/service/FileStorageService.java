package com.dewanshu.dropvault.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final S3Client s3Client;

    @Value("${aws.bucket}")
    private String bucket;

    public String saveFile(MultipartFile file, String storedFilename) throws IOException {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(storedFilename)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromInputStream(
                        file.getInputStream(),
                        file.getSize()
                )
        );

        return storedFilename;
    }

    public InputStreamResource loadFile(String storedFilename) {

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(storedFilename)
                .build();

        return new InputStreamResource(
                s3Client.getObject(request)
        );
    }

    public void deleteFile(String storedFilename) {

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(storedFilename)
                .build();

        s3Client.deleteObject(request);
    }
}