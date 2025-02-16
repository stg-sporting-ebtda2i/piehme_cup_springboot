package com.stgsporting.piehmecup.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

@Service
public class S3Service {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.key}")
    private String accessKey;
    @Value("${aws.secret}")
    private String secretKey;

    @Value("${aws.s3.bucket.region}")
    private String region;

    @Value("${aws.s3.directory}")
    private String directory;

    private S3Client s3Client;

    private S3Client getS3Client() {
        if (this.s3Client != null) {
            return this.s3Client;
        }

        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return this.s3Client = S3Client
                .builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public String uploadFile(MultipartFile file) {
        String key = getKey(file.getOriginalFilename());

        try {
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

            getS3Client().putObject(
                    request -> request.bucket(bucketName).key(key).ifNoneMatch("*"),
                    requestBody
            );
        }catch (IOException e){
            return null;
        }

        return key;
    }

    public void deleteFile(String key) {
        getS3Client()
                .deleteObject(request -> request.bucket(bucketName).key(key));
    }

    private String getKey(String filename) {
        return directory + "/" + generateUniqueString() + "." + FilenameUtils.getExtension(filename);
    }

    private String generateUniqueString() {
        long millis = System.currentTimeMillis();
        String datetime = new Date().toString();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");
        String rndchars = RandomStringUtils.randomAlphanumeric(16);

        return rndchars + "_" + datetime + "_" + millis;
    }
}
