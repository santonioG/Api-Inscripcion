package com.proyecto.inscripcionplatform.curso.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.inscripcionplatform.curso.dto.CursoResponse;
import com.proyecto.inscripcionplatform.curso.repository.CursoRepository;

@Service
public class CursoService {

    private final CursoRepository repo;
    public CursoService(CursoRepository CursoRepository) {
        this.repo = CursoRepository;
    }

    public List<CursoResponse> listarTodos(){
        return repo.findAll().stream().map(CursoMapper::toResponse).toList();
    }

}
