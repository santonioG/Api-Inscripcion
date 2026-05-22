package com.proyecto.inscripcionplatform.inscripcion.controller;

import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<InscripcionResponse> createInscripcion(@Valid @RequestBody InscripcionRequest request) {
        InscripcionResponse response = service.inscribir(request);
        return ResponseEntity.ok(response);
    }
}
