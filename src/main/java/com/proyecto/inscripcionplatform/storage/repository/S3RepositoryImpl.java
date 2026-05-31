package com.proyecto.inscripcionplatform.storage.repository;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Repository
public class S3RepositoryImpl implements S3Repository {

    private static final Logger log = LoggerFactory.getLogger(S3RepositoryImpl.class);

    private final AmazonS3 s3Client;

    public S3RepositoryImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(String bucketName, String fileName, File fileObj) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "Archivo subido: " + fileName;
    }

    @Override
    public S3ObjectInputStream getObject(String bucketName, String fileName) throws IOException {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            log.error("Bucket no encontrado: {}", bucketName);
            return null;
        }
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        return s3Object.getObjectContent();
    }

    @Override
    public byte[] downloadFile(String bucketName, String fileName) throws IOException {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void moveObject(String bucketName, String fileKey, String destinationFileKey) {
        CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucketName, fileKey, bucketName, destinationFileKey);
        s3Client.copyObject(copyObjRequest);
        deleteObject(bucketName, fileKey);
    }

    @Override
    public void deleteObject(String bucketName, String fileKey) {
        s3Client.deleteObject(bucketName, fileKey);
    }
}

