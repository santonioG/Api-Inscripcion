package com.proyecto.inscripcionplatform.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponse {

    private Long id;
    private String nombre;
    private double duracion;
    private double valor;
    private ProfesorResponse profesor;
}   
