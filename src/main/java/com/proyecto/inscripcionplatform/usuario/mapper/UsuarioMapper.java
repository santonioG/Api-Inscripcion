package com.proyecto.inscripcionplatform.usuario.mapper;

import com.proyecto.inscripcionplatform.inscripcion.model.Rol;
import com.proyecto.inscripcionplatform.usuario.dto.UsuarioRequest;
import com.proyecto.inscripcionplatform.usuario.dto.UsuarioResponse;
import com.proyecto.inscripcionplatform.usuario.model.UsuarioEntity;

public class UsuarioMapper {

    //Metodo para pasar de UsuarioRequest a UsuarioEntity
    public static UsuarioEntity toEntity(UsuarioRequest request) {
        if (request == null) return null;

        return UsuarioEntity.builder()
                .id(request.getId())
                .nombre(request.getNombre())
                .email(request.getEmail())
                .clave(request.getClave())
                .rol(
                    Rol.valueOf(request.getRol().toUpperCase())
                )
                .build();
    }
    
    // Método para actualizar una entidad existente con los datos de un request
    public static void updateEntity(UsuarioRequest request, UsuarioEntity bd) {
        if (request == null || bd == null) return;

        bd.setNombre(request.getNombre());
        bd.setEmail(request.getEmail());
        bd.setClave(request.getClave());
        bd.setRol(Rol.valueOf(request.getRol().toUpperCase()));
    }

    // Método para convertir una entidad a un response
    public static UsuarioResponse toResponse(UsuarioEntity entity) {
        if (entity == null) return null;

        return UsuarioResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .rol(entity.getRol())
                .build();
    }
}
