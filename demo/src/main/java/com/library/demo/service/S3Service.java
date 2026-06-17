package com.library.demo.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;

@Service
public class S3Service {

    private final S3Template s3Template;
    private final String bucketName;

    public S3Service(S3Template s3Template, @Value("${YOUR_BUCKET_NAME_PROPERTY}") String bucketName) {
        this.s3Template = s3Template;
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Uploaded file must have a valid filename.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = ObjectMetadata.builder()
                    .contentType(file.getContentType())
                    .build();
            s3Template.upload(bucketName, key, inputStream, metadata);
        }

        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
    }
}
