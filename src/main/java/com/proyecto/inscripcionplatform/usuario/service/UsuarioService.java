package com.proyecto.inscripcionplatform.usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.inscripcionplatform.exception.NotFoundException;
import com.proyecto.inscripcionplatform.usuario.dto.UsuarioRequest;
import com.proyecto.inscripcionplatform.usuario.dto.UsuarioResponse;
import com.proyecto.inscripcionplatform.usuario.mapper.UsuarioMapper;
import com.proyecto.inscripcionplatform.usuario.model.UsuarioEntity;
import com.proyecto.inscripcionplatform.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    public UsuarioService(UsuarioRepository UsuarioRepository) {
        this.repo = UsuarioRepository;
    }

    //obtener todos los Usuarios
    public List<UsuarioResponse> findAll() {
        return repo.findAll().stream().map(UsuarioMapper::toResponse).toList();
    }

    //crear un nuevo Usuario
    public UsuarioResponse create(UsuarioRequest request) {
        UsuarioEntity entity = UsuarioMapper.toEntity(request);
        UsuarioEntity saved = repo.save(entity);
        return UsuarioMapper.toResponse(saved);
    }

    //actualizar un Usuario existente 
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        UsuarioEntity entity = repo.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));
        UsuarioMapper.updateEntity(request, entity);
        UsuarioEntity updated = repo.save(entity);
        return UsuarioMapper.toResponse(updated);
    }

    //eliminar un Usuario por id
    public void delete(Long id) {
        UsuarioEntity entity = repo.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));
        repo.delete(entity);
    }
}