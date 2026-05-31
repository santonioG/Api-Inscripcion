package com.proyecto.inscripcionplatform.storage.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.inscripcionplatform.storage.repository.S3Repository;

@Service
public class AwsServiceImpl implements AwsService {

    private static final Logger log = LoggerFactory.getLogger(AwsServiceImpl.class);

    private final S3Repository s3Repository;

    public AwsServiceImpl(S3Repository s3Repository) {
        this.s3Repository = s3Repository;
    }

    /**
     * Sube el resumen de inscripción al bucket S3.
     * El archivo se almacena dentro de una carpeta cuyo nombre corresponde al ID de inscripción.
     * Ruta final: inscripcion-{inscripcionId}/{fileName}
     */
    @Override
    public String uploadResumen(String bucketName, Long inscripcionId, MultipartFile file) {
        File fileObj = convertMultipartFileToFile(file);
        String originalName = file.getOriginalFilename();
        String s3Key = "inscripcion-" + inscripcionId + "/" + originalName;
        String result = s3Repository.uploadFile(bucketName, s3Key, fileObj);
        log.info("Resumen subido a S3 en ruta: {}", s3Key);
        return result;
    }

    /**
     * Descarga el resumen desde S3 dado el ID de inscripción y el nombre del archivo.
     */
    @Override
    public byte[] downloadResumen(String bucketName, Long inscripcionId, String fileName) throws IOException {
        String s3Key = "inscripcion-" + inscripcionId + "/" + fileName;
        log.info("Descargando archivo desde S3: {}", s3Key);
        return s3Repository.downloadFile(bucketName, s3Key);
    }

    /**
     * Reemplaza (actualiza) el resumen existente en S3.
     * Elimina el archivo anterior y sube el nuevo conservando la carpeta del ID de inscripción.
     */
    @Override
    public String replaceResumen(String bucketName, Long inscripcionId, String oldFileName, MultipartFile newFile) {
        // Eliminar archivo anterior
        String oldKey = "inscripcion-" + inscripcionId + "/" + oldFileName;
        s3Repository.deleteObject(bucketName, oldKey);
        log.info("Archivo anterior eliminado: {}", oldKey);

        // Subir nuevo archivo
        File fileObj = convertMultipartFileToFile(newFile);
        String newFileName = newFile.getOriginalFilename();
        String newKey = "inscripcion-" + inscripcionId + "/" + newFileName;
        String result = s3Repository.uploadFile(bucketName, newKey, fileObj);
        log.info("Nuevo resumen subido en: {}", newKey);
        return result;
    }

    /**
     * Elimina el resumen de inscripción desde S3.
     */
    @Override
    public String deleteResumen(String bucketName, Long inscripcionId, String fileName) {
        String s3Key = "inscripcion-" + inscripcionId + "/" + fileName;
        s3Repository.deleteObject(bucketName, s3Key);
        log.info("Archivo eliminado de S3: {}", s3Key);
        return "Archivo eliminado: " + s3Key;
    }

    // Convierte MultipartFile a File temporal para enviarlo a S3
    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error al convertir MultipartFile a File", e);
        }
        return convertedFile;
    }
}
