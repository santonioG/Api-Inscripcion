package com.proyecto.inscripcionplatform.inscripcion.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.proyecto.inscripcionplatform.curso.model.CursoEntity;
import com.proyecto.inscripcionplatform.curso.repository.CursoRepository;
import com.proyecto.inscripcionplatform.exception.NotFoundException;
import com.proyecto.inscripcionplatform.inscripcion.dto.InscripcionRequest;
import com.proyecto.inscripcionplatform.inscripcion.dto.InscripcionResponse;
import com.proyecto.inscripcionplatform.inscripcion.mapper.InscripcionMapper;
import com.proyecto.inscripcionplatform.inscripcion.model.InscripcionEntity;
import com.proyecto.inscripcionplatform.inscripcion.repository.InscripcionRepository;
import com.proyecto.inscripcionplatform.usuario.model.UsuarioEntity;
import com.proyecto.inscripcionplatform.usuario.repository.UsuarioRepository;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepo;
    private final UsuarioRepository usuarioRepo;
    private final CursoRepository cursoRepo;

    public InscripcionService(InscripcionRepository inscripcionRepo, UsuarioRepository usuarioRepo, CursoRepository cursoRepo) {
        this.inscripcionRepo = inscripcionRepo;
        this.usuarioRepo = usuarioRepo;
        this.cursoRepo = cursoRepo;
    }

    // Inscribirse a un curso
    public InscripcionResponse inscribir(InscripcionRequest request) {

        // Buscar usuario
        UsuarioEntity usuario = usuarioRepo.findById(request.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + request.getUsuarioId()));

        // Buscar cursos
        List<CursoEntity> cursos = cursoRepo.findAllById(request.getCursoIds());

        // Calcular total
        double total = cursos.stream().mapToDouble(CursoEntity::getValor).sum();

        // Crear inscripcion
        InscripcionEntity inscripcion = InscripcionEntity.builder()
                .usuario(usuario)
                .cursos(cursos)
                .fechaInscripcion(LocalDate.now())
                .valorTotal(total)
                .build();

        // Guardar en base de datos
        InscripcionEntity saved = inscripcionRepo.save(inscripcion);

        // Retornar response
        return InscripcionMapper.toResponse(saved);
    }

    // Obtener todas las inscripciones
    public List<InscripcionResponse> getAllInscripciones() {
        List<InscripcionEntity> inscripciones = inscripcionRepo.findAll();
        return inscripciones.stream().map(InscripcionMapper::toResponse).toList();
    }

    /**
     * Genera un archivo físico con el resumen de la inscripción.
     * El archivo se puede descargar directamente desde el endpoint.
     */
    public ByteArrayResource generarResumen(Long inscripcionId) {
        InscripcionEntity inscripcion = inscripcionRepo.findById(inscripcionId)
                .orElseThrow(() -> new NotFoundException("Inscripción no encontrada con id: " + inscripcionId));

        InscripcionResponse response = InscripcionMapper.toResponse(inscripcion);

        StringBuilder sb = new StringBuilder();
        sb.append("===========================================\n");
        sb.append("       RESUMEN DE INSCRIPCIÓN\n");
        sb.append("===========================================\n");
        sb.append("ID Inscripción  : ").append(response.getId()).append("\n");
        sb.append("Fecha           : ").append(response.getFechaInscripcion()).append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("ALUMNO\n");
        sb.append("  ID            : ").append(response.getUsuario().getId()).append("\n");
        sb.append("  Nombre        : ").append(response.getUsuario().getNombre()).append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("CURSOS INSCRITOS\n");
        response.getCursos().forEach(c -> {
            sb.append("  - ").append(c.getNombre())
              .append(" (ID: ").append(c.getId()).append(")")
              .append(" | $").append(c.getValor()).append("\n");
        });
        sb.append("-------------------------------------------\n");
        sb.append("TOTAL A PAGAR   : $").append(response.getValorTotal()).append("\n");
        sb.append("===========================================\n");

        byte[] content = sb.toString().getBytes();
        return new ByteArrayResource(content);
    }
}
