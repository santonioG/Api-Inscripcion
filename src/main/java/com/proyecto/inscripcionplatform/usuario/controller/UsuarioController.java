package com.proyecto.inscripcionplatform.usuario.controller;

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

import com.proyecto.inscripcionplatform.usuario.dto.UsuarioRequest;
import com.proyecto.inscripcionplatform.usuario.dto.UsuarioResponse;
import com.proyecto.inscripcionplatform.usuario.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.NonNull;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    public UsuarioController(UsuarioService UsuarioService) {
        this.service = UsuarioService;
    }

    //obtener todos los Usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAllUsuarios() {
        return ResponseEntity.ok(service.findAll());
    }

    //crear un nuevo Usuario
    @PostMapping
    public ResponseEntity<UsuarioResponse> createUsuario(@Valid @RequestBody UsuarioRequest request){
        UsuarioResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //actualizar un Usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    //eliminar un Usuario por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
