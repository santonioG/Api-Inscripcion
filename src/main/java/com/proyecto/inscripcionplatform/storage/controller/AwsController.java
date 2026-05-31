package com.proyecto.inscripcionplatform.storage.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.inscripcionplatform.storage.service.AwsService;

@RestController
@RequestMapping("/api/s3")
public class AwsController {

    private final AwsService awsService;

    public AwsController(AwsService awsService) {
        this.awsService = awsService;
    }

    /**
     * POST /api/s3/resumen/{inscripcionId}
     * Sube el resumen de inscripción al bucket S3.
     * El archivo queda guardado en la carpeta: inscripcion-{inscripcionId}/
     *
     * Params:
     *   - bucketName: nombre del bucket S3
     *   - file: archivo a subir (form-data)
     */
    @PostMapping("/resumen/{inscripcionId}")
    public ResponseEntity<String> uploadResumen(
            @PathVariable Long inscripcionId,
            @RequestParam String bucketName,
            @RequestParam MultipartFile file) {

        String result = awsService.uploadResumen(bucketName, inscripcionId, file);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/s3/resumen/{inscripcionId}/download
     * Descarga el resumen de inscripción desde S3.
     *
     * Params:
     *   - bucketName: nombre del bucket S3
     *   - fileName: nombre del archivo dentro de la carpeta de inscripción
     */
    @GetMapping("/resumen/{inscripcionId}/download")
    public ResponseEntity<ByteArrayResource> downloadResumen(
            @PathVariable Long inscripcionId,
            @RequestParam String bucketName,
            @RequestParam String fileName) {

        try {
            byte[] data = awsService.downloadResumen(bucketName, inscripcionId, fileName);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok()
                    .contentLength(data.length)
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/s3/resumen/{inscripcionId}
     * Reemplaza (actualiza) el resumen existente en S3.
     * Elimina el archivo anterior y sube el nuevo en la misma carpeta de inscripción.
     *
     * Params:
     *   - bucketName: nombre del bucket S3
     *   - oldFileName: nombre del archivo actual a reemplazar
     *   - file: nuevo archivo (form-data)
     */
    @PutMapping("/resumen/{inscripcionId}")
    public ResponseEntity<String> replaceResumen(
            @PathVariable Long inscripcionId,
            @RequestParam String bucketName,
            @RequestParam String oldFileName,
            @RequestParam MultipartFile file) {

        String result = awsService.replaceResumen(bucketName, inscripcionId, oldFileName, file);
        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/s3/resumen/{inscripcionId}
     * Elimina el resumen de inscripción del bucket S3.
     *
     * Params:
     *   - bucketName: nombre del bucket S3
     *   - fileName: nombre del archivo a eliminar
     */
    @DeleteMapping("/resumen/{inscripcionId}")
    public ResponseEntity<String> deleteResumen(
            @PathVariable Long inscripcionId,
            @RequestParam String bucketName,
            @RequestParam String fileName) {

        String result = awsService.deleteResumen(bucketName, inscripcionId, fileName);
        return ResponseEntity.ok(result);
    }
}

