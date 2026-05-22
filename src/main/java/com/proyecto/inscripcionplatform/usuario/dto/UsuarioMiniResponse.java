package com.proyecto.inscripcionplatform.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioMiniResponse {
    private Long id;
    private String nombre;
}
