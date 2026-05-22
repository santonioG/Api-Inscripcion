package com.proyecto.inscripcionplatform.inscripcion.dto;

import java.time.LocalDate;
import java.util.List;

import com.proyecto.inscripcionplatform.curso.dto.CursoMiniResponse;
import com.proyecto.inscripcionplatform.usuario.dto.UsuarioMiniResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionResponse {
    
    private Long id;
    private List<CursoMiniResponse> cursos;
    private UsuarioMiniResponse usuario;
    private LocalDate fechaInscripcion;
    private double valorTotal;

}

