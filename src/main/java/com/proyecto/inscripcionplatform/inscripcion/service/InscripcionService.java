package com.proyecto.inscripcionplatform.inscripcion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.inscripcionplatform.curso.model.CursoEntity;
import com.proyecto.inscripcionplatform.curso.repository.CursoRepository;
import com.proyecto.inscripcionplatform.exception.NotFoundException;
import com.proyecto.inscripcionplatform.inscripcion.dto.InscripcionRequest;
import com.proyecto.inscripcionplatform.inscripcion.dto.InscripcionResponse;
import com.proyecto.inscripcionplatform.inscripcion.mapper.InscripcionMapper;
import com.proyecto.inscripcionplatform.inscripcion.model.InscripcionEntity;
import com.proyecto.inscripcionplatform.inscripcion.repository.InscripcionRepository;
import com.proyecto.inscripcionplatform.usuario.model.UsuarioEntity;
import com.proyecto.inscripcionplatform.usuario.repository.UsuarioRepository;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepo;
    private final UsuarioRepository usuarioRepo;
    private final CursoRepository cursoRepo;

    public InscripcionService(InscripcionRepository inscripcionRepo, UsuarioRepository usuarioRepo, CursoRepository cursoRepo) {
        this.inscripcionRepo = inscripcionRepo;
        this.usuarioRepo = usuarioRepo;
        this.cursoRepo = cursoRepo;
    }

    //Inscribirse a un curso
    public InscripcionResponse inscribir(InscripcionRequest request) {

        //buscar usuario
        UsuarioEntity usuario = usuarioRepo.findById(request.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + request.getUsuarioId()));

        //buscar curso
        List<CursoEntity> cursos = cursoRepo.findAllById(request.getCursoIds());

        //calcular total 
        double total = cursos.stream().mapToDouble(CursoEntity::getValor).sum();

        //crear inscripcion
        InscripcionEntity inscripcion = InscripcionEntity.builder()
                .usuario(usuario)
                .cursos(cursos)
                .fechaInscripcion(LocalDate.now())
                .valorTotal(total)
                .build();
    
        //Guardar en base de datos
        InscripcionEntity saved = inscripcionRepo.save(inscripcion);

        //retornar response
        return InscripcionMapper.toResponse(saved);
    }
}
