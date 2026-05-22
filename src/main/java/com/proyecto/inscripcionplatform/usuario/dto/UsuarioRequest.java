package com.proyecto.inscripcionplatform.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UsuarioRequest {

    @NotBlank(message = "El nombre del curso no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre del curso debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email no puede estar vacío")
    @Size(min = 5, max = 100, message = "El email debe tener entre 5 y 100 caracteres")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "La clave no puede estar vacía")
    @Size(min = 6, max = 100, message = "La clave debe tener entre 6 y 100 caracteres")
    private String clave;

    @NotBlank(message = "El rol no puede estar vacío")
    @Size(min = 4, max = 20, message = "El rol debe tener entre 4 y 20 caracteres")
    private String rol;
}
