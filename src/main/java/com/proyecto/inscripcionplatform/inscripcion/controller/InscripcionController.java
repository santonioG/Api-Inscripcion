package com.proyecto.inscripcionplatform.inscripcion.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.inscripcionplatform.inscripcion.dto.InscripcionRequest;
import com.proyecto.inscripcionplatform.inscripcion.dto.InscripcionResponse;
import com.proyecto.inscripcionplatform.inscripcion.service.InscripcionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    // POST /api/inscripciones - Crear nueva inscripción
    @PostMapping
    public ResponseEntity<InscripcionResponse> createInscripcion(@Valid @RequestBody InscripcionRequest request) {
        InscripcionResponse response = service.inscribir(request);
        return ResponseEntity.ok(response);
    }

    // GET /api/inscripciones - Ver todas las inscripciones
    @GetMapping
    public ResponseEntity<?> getAllInscripciones() {
        return ResponseEntity.ok(service.getAllInscripciones());
    }

    /**
     * GET /api/inscripciones/{id}/resumen
     * Genera y descarga el resumen de inscripción como archivo .txt
     * Este archivo físico puede guardarse directamente en el computador.
     */
    @GetMapping("/{id}/resumen")
    public ResponseEntity<ByteArrayResource> descargarResumen(@PathVariable Long id) {
        ByteArrayResource resource = service.generarResumen(id);
        String fileName = "resumen_inscripcion_" + id + ".txt";
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
