package com.example.paqueteria.infrastructure.adapter.in.web.historialEstado;

import com.example.paqueteria.application.port.in.historialEstado.FindHistorialByIdUseCase;
import com.example.paqueteria.application.port.in.historialEstado.FindHistorialByPaqueteIdUseCase;
import com.example.paqueteria.domain.entity.HistorialEstado;
import com.example.paqueteria.infrastructure.adapter.in.web.historialEstado.dto.HistorialEstadoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/historial-estados")
public class HistorialEstadoController {

    private final FindHistorialByIdUseCase findHistorialByIdUseCase;
    private final FindHistorialByPaqueteIdUseCase findHistorialByPaqueteIdUseCase;

    public HistorialEstadoController(FindHistorialByIdUseCase findHistorialByIdUseCase,
                                     FindHistorialByPaqueteIdUseCase findHistorialByPaqueteIdUseCase) {
        this.findHistorialByIdUseCase = findHistorialByIdUseCase;
        this.findHistorialByPaqueteIdUseCase = findHistorialByPaqueteIdUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEstadoResponse> buscarPorId(@PathVariable UUID id) {
        Optional<HistorialEstado> resultado = findHistorialByIdUseCase.findById(id);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(HistorialEstadoResponse.desde(resultado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paquete/{paqueteId}")
    public ResponseEntity<List<HistorialEstadoResponse>> buscarPorPaqueteId(@PathVariable UUID paqueteId) {
        List<HistorialEstado> historial = findHistorialByPaqueteIdUseCase.findByPaqueteId(paqueteId);

        List<HistorialEstadoResponse> response = historial.stream()
                .map(HistorialEstadoResponse::desde)
                .toList();

        return ResponseEntity.ok(response);
    }
}