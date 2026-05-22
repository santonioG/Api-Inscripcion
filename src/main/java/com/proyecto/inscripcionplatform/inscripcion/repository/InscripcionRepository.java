package com.proyecto.inscripcionplatform.inscripcion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.inscripcionplatform.inscripcion.model.InscripcionEntity;

@Repository
public interface InscripcionRepository extends JpaRepository<InscripcionEntity, Long> {

}
