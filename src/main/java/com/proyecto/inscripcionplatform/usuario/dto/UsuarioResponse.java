package com.proyecto.inscripcionplatform.usuario.dto;

import com.proyecto.inscripcionplatform.inscripcion.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
}
