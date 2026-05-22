package com.proyecto.inscripcionplatform.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoMiniResponse {
    private Long id;
    private String nombre;
    private double valor;
}
