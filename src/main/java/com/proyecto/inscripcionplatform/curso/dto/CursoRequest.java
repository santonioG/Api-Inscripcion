package com.proyecto.inscripcionplatform.curso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoRequest {
    
    @NotBlank(message = "El nombre del curso no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre del curso debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El ID del profesor es obligatorio")
    private Long profesorId;

    @NotNull(message = "La duración del curso es obligatoria")
    @Positive(message = "La duración del curso debe ser un número positivo")
    private double duracionMinutos;

    @NotNull(message = "El valor del curso es obligatorio")
    @Positive(message = "El valor del curso debe ser un número positivo")
    private double valor;
}
