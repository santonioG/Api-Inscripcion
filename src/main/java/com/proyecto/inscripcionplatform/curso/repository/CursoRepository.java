package com.proyecto.inscripcionplatform.curso.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.inscripcionplatform.curso.model.CursoEntity;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long> {

}
