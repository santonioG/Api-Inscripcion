package com.proyecto.inscripcionplatform.curso.mapper;

import com.proyecto.inscripcionplatform.curso.dto.CursoMiniResponse;
import com.proyecto.inscripcionplatform.curso.dto.CursoRequest;
import com.proyecto.inscripcionplatform.curso.dto.CursoResponse;
import com.proyecto.inscripcionplatform.curso.dto.ProfesorResponse;
import com.proyecto.inscripcionplatform.curso.model.CursoEntity;
import com.proyecto.inscripcionplatform.usuario.model.UsuarioEntity;


public class CursoMapper {

    // Metodo para convertir un CursoRequest a CursoEntity, incluyendo la referencia al profesor por su ID
    public static CursoEntity toEntity(CursoRequest request) {
        if (request == null) return null;

        return CursoEntity.builder()
                .nombre(request.getNombre())
                .duracionMinutos(request.getDuracionMinutos())
                .valor(request.getValor())
                .profesor(
                    UsuarioEntity.builder()
                        .id(request.getProfesorId())
                        .build()
                )
                .build();
    }
    // Método para actualizar una entidad existente con los datos de un request
    public static void updateEntity(CursoRequest request, CursoEntity bd) {
        if (request == null || bd == null) return;

        bd.setNombre(request.getNombre());
        bd.setDuracionMinutos(request.getDuracionMinutos());
        bd.setValor(request.getValor());

        if (request.getProfesorId() != null) {
            bd.setProfesor(
                UsuarioEntity.builder()
                    .id(request.getProfesorId())
                    .build()
            );
        }
    }

    // Método para convertir una entidad a un response, incluyendo el ID del profesor
    public static CursoResponse toResponse(CursoEntity entity) {
        if (entity == null) return null;
        return CursoResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .duracionMinutos(entity.getDuracionMinutos())
                .valor(entity.getValor())
                .profesor(
                    entity.getProfesor() != null
                    ? ProfesorResponse.builder()
                        .id(entity.getProfesor().getId())
                        .nombre(entity.getProfesor().getNombre())
                        .build()
                    : null
                )
                .build();
    }

    // Método para convertir una entidad a un response mini, con solo los campos esenciales
    public static CursoMiniResponse toMiniResponse(CursoEntity curso) {
    if (curso == null) return null;

    return CursoMiniResponse.builder()
            .id(curso.getId())
            .nombre(curso.getNombre())
            .valor(curso.getValor())
            .build();
}
}
