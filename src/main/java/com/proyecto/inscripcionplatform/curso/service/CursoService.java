package com.proyecto.inscripcionplatform.curso.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.inscripcionplatform.curso.dto.CursoRequest;
import com.proyecto.inscripcionplatform.curso.dto.CursoResponse;
import com.proyecto.inscripcionplatform.curso.mapper.CursoMapper;
import com.proyecto.inscripcionplatform.curso.model.CursoEntity;
import com.proyecto.inscripcionplatform.curso.repository.CursoRepository;
import com.proyecto.inscripcionplatform.exception.NotFoundException;
import com.proyecto.inscripcionplatform.usuario.model.UsuarioEntity;
import com.proyecto.inscripcionplatform.usuario.repository.UsuarioRepository;

@Service
public class CursoService {

    private final CursoRepository repo;
    private final UsuarioRepository usuarioRepo;

    public CursoService(CursoRepository CursoRepository, UsuarioRepository usuarioRepo) {
        this.repo = CursoRepository;
        this.usuarioRepo = usuarioRepo;
    }

    //obtener todos los Cursos
    public List<CursoResponse> findAll() { 
        return repo.findAll().stream().map(CursoMapper::toResponse).toList();
    }
    //crear un nuevo Curso
    public CursoResponse create(CursoRequest request) {
        CursoEntity entity = CursoMapper.toEntity(request);
        UsuarioEntity profesor = usuarioRepo.findById(request.getProfesorId()).orElseThrow(() -> new NotFoundException("Profesor no encontrado con id: " + request.getProfesorId()));
        entity.setProfesor(profesor);
        CursoEntity saved = repo.save(entity);
        return CursoMapper.toResponse(saved);
    }
    //actualizar un Curso existente
    public CursoResponse update(Long id, CursoRequest request) {
        CursoEntity entity = repo.findById(id).orElseThrow(() -> new NotFoundException("Curso no encontrado con id: " + id));
        CursoMapper.updateEntity(request, entity);
        CursoEntity updated = repo.save(entity);
        return CursoMapper.toResponse(updated);
    }
    //eliminar un Curso por id
    public void delete(Long id) {
        CursoEntity entity = repo.findById(id).orElseThrow(() -> new NotFoundException("Curso no encontrado con id: " + id));
        repo.delete(entity);
    }
}