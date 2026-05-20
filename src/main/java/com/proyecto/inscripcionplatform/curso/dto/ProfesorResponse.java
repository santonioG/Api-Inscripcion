package com.proyecto.inscripcionplatform.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor
@Builder @NoArgsConstructor
public class ProfesorResponse {
    private Long id;
    private String nombre;
}
