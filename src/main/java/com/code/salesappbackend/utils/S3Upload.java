package com.code.salesappbackend.utils;

import com.amazonaws.s3.model.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.CompletedFileUpload;
import software.amazon.awssdk.transfer.s3.model.FileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Upload {
    private final S3TransferManager s3TransferManager;

    @Value("${aws-s3.bucket-name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("temp", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        String key = generateUniqueKey(file.getOriginalFilename());
        UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                .putObjectRequest(b -> b.bucket(bucketName).key(key))
                .source(tempFile)
                .build();
        FileUpload fileUpload = s3TransferManager.uploadFile(uploadFileRequest);
        fileUpload.completionFuture().join();
        return "https://" + bucketName + ".s3." + "ap-southeast-1" + ".amazonaws.com/" + key;
    }

    private String generateUniqueKey(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }
}
