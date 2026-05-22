package com.proyecto.inscripcionplatform.inscripcion.model;

import java.time.LocalDate;
import java.util.List;

import com.proyecto.inscripcionplatform.curso.model.CursoEntity;
import com.proyecto.inscripcionplatform.usuario.model.UsuarioEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inscripcion")
public class InscripcionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToMany
    @JoinTable(
        name = "inscripcion_curso", //Nombre tabla intermedia
        joinColumns = @JoinColumn(name = "inscripcion_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )

    private List<CursoEntity> cursos;
    private LocalDate fechaInscripcion;
    private double valorTotal;

    
}
