package com.proyecto.inscripcionplatform.storage.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AwsService {

    String uploadResumen(String bucketName, Long inscripcionId, MultipartFile file);

    byte[] downloadResumen(String bucketName, Long inscripcionId, String fileName) throws IOException;

    String replaceResumen(String bucketName, Long inscripcionId, String oldFileName, MultipartFile newFile);

    String deleteResumen(String bucketName, Long inscripcionId, String fileName);
}
