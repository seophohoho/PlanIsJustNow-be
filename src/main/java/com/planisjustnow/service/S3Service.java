package com.planisjustnow.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class S3Service {
    private final AmazonS3 amazonS3;
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    public String saveFile(MultipartFile multipartFile,String path) throws IOException{
        String originalFilename = multipartFile.getOriginalFilename();
        String filePath = path+originalFilename;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, filePath, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, filePath).toString();
    }
}
