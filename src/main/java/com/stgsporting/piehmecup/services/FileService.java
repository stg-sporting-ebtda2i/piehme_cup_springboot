package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.exceptions.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities;
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;
import software.amazon.awssdk.services.s3.S3Client;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class FileService {

    @Value("${aws.key}")
    private String accessKey;
    @Value("${aws.secret}")
    private String secretKey;

    @Value("${aws.s3.bucket.region}")
    private String region;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.s3.directory}")
    private String directory;

    @Value("${aws.cloudfront.domain}")
    private String domain;
    @Value("${aws.cloudfront.key.id}")
    private String keyId;
    @Value("${aws.cloudfront.key.path}")
    private String keyPath;

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public String generateSignedUrl(String key) {
        CloudFrontUtilities cloudFrontUtilities = CloudFrontUtilities.create();
        Instant expirationTime = Instant.now().plus(5, ChronoUnit.HOURS);

        System.out.println(Paths.get(keyPath));
        CannedSignerRequest request = null;
        try {
            request = CannedSignerRequest.builder()
                    .resourceUrl("https://" + domain + "/" + key)
                    .privateKey(Paths.get(ResourceUtils.getFile("classpath:" + keyPath).getPath()))
                    .keyPairId(keyId)
                    .expirationDate(expirationTime)
                    .build();
        } catch (Exception e) {
            return "";
        }

        SignedUrl signedUrl = cloudFrontUtilities.getSignedUrlWithCannedPolicy(request);

        return signedUrl.url();
    }

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

        try(InputStream inputStream = file.getInputStream()) {
            RequestBody requestBody = RequestBody.fromInputStream(inputStream, file.getSize());

            getS3Client().putObject(
                    request -> request.bucket(bucketName).key(key).ifNoneMatch("*"),
                    requestBody
            );
        }catch (IOException e) {
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
        String rndchars = RandomStringUtils.random(16, 32, 126, true, true, null, null);

        return rndchars + "_" + millis;
    }
}
