package com.proyecto.inscripcionplatform.inscripcion.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionRequest {

    private Long usuarioId;
    private List<Long> cursoIds;

}
