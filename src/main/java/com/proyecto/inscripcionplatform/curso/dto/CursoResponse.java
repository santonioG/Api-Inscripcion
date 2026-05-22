package com.proyecto.inscripcionplatform.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponse {

    private Long id;
    private String nombre;
    private double duracionMinutos;
    private double valor;
    private ProfesorResponse profesor;
}   
