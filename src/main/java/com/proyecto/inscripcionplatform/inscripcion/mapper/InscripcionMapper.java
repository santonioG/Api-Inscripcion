package com.proyecto.inscripcionplatform.inscripcion.mapper;

import com.proyecto.inscripcionplatform.curso.mapper.CursoMapper;
import com.proyecto.inscripcionplatform.curso.model.CursoEntity;
import com.proyecto.inscripcionplatform.inscripcion.dto.InscripcionRequest;
import com.proyecto.inscripcionplatform.inscripcion.dto.InscripcionResponse;
import com.proyecto.inscripcionplatform.inscripcion.model.InscripcionEntity;
import com.proyecto.inscripcionplatform.usuario.mapper.UsuarioMapper;
import com.proyecto.inscripcionplatform.usuario.model.UsuarioEntity;

public class InscripcionMapper {
    
    //Metodo para convertir de request a entity
    public static InscripcionEntity toEntity(InscripcionRequest request) {
        if (request == null) return null;

        return InscripcionEntity.builder()
                .usuario(
                    UsuarioEntity.builder()
                        .id(request.getUsuarioId())
                        .build()
                )
                .cursos(
                    request.getCursoIds().stream()
                    .map(id -> CursoEntity.builder()
                        .id(id)
                        .build()
                )
                .toList()
            )
            .build();
    }

    //Metodo para convertir de entity a response
    public static InscripcionResponse toResponse(InscripcionEntity entity) {
        if (entity == null) return null;

        return InscripcionResponse.builder()
                .id(entity.getId())
                .valorTotal(entity.getValorTotal())
                .fechaInscripcion(entity.getFechaInscripcion())
                .usuario(
                    UsuarioMapper.toMiniResponse(entity.getUsuario())
                )
                .cursos(
                    entity.getCursos().stream()
                    .map(CursoMapper::toMiniResponse)
                    .toList()
                )
                .build();
    }
}