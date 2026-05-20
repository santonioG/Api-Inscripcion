package com.proyecto.inscripcionplatform.curso.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.inscripcionplatform.curso.dto.CursoRequest;
import com.proyecto.inscripcionplatform.curso.dto.CursoResponse;
import com.proyecto.inscripcionplatform.curso.service.CursoService;

import jakarta.validation.Valid;
import lombok.NonNull;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;
    public CursoController(CursoService CursoService) {
        this.service = CursoService;
    }

    //obtener todos los Cursos
    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAllCursos() {
        return ResponseEntity.ok(service.findAll());
    }
    
    //crear un nuevo Curso
    @PostMapping
    public ResponseEntity<CursoResponse> createCurso(@Valid @RequestBody CursoRequest request) {
        CursoResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //actualizar un Curso existente
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> update(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        CursoResponse updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    //eliminar un Curso por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
