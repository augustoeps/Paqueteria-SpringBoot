package com.example.paqueteria.infrastructure.adapter.in.web.oficina;

import com.example.paqueteria.application.port.in.oficina.CreateOficinaUseCase;
import com.example.paqueteria.application.port.in.oficina.DeleteOficinaUseCase;
import com.example.paqueteria.application.port.in.oficina.FindAllOficinaUseCase;
import com.example.paqueteria.application.port.in.oficina.FindOficinaByIdUseCase;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.valueobjects.OficinaCodigo;
import com.example.paqueteria.domain.valueobjects.OficinaDireccion;
import com.example.paqueteria.domain.valueobjects.OficinaNombre;
import com.example.paqueteria.infrastructure.adapter.in.web.oficina.dto.CrearOficinaRequest;
import com.example.paqueteria.infrastructure.adapter.in.web.oficina.dto.OficinaResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/oficinas")
public class OficinaController {

    private final CreateOficinaUseCase createOficinaUseCase;
    private final FindOficinaByIdUseCase findOficinaByIdUseCase;
    private final FindAllOficinaUseCase findAllOficinaUseCase;
    private final DeleteOficinaUseCase deleteOficinaUseCase;

    public OficinaController(CreateOficinaUseCase createOficinaUseCase,
                             FindOficinaByIdUseCase findOficinaByIdUseCase,
                             FindAllOficinaUseCase findAllOficinaUseCase,
                             DeleteOficinaUseCase deleteOficinaUseCase) {
        this.createOficinaUseCase = createOficinaUseCase;
        this.findOficinaByIdUseCase = findOficinaByIdUseCase;
        this.findAllOficinaUseCase = findAllOficinaUseCase;
        this.deleteOficinaUseCase = deleteOficinaUseCase;
    }

    @PostMapping
    public ResponseEntity<OficinaResponse> crear(@Valid @RequestBody CrearOficinaRequest request) {
        OficinaCodigo codigo = new OficinaCodigo(request.codigo());
        OficinaNombre nombre = new OficinaNombre(request.nombre());
        OficinaDireccion direccion = new OficinaDireccion(
                request.codigoPostal(),
                request.ciudad(),
                request.calle(),
                request.numero()
        );

        Oficina oficina = createOficinaUseCase.create(codigo, nombre, direccion, request.provinciaId());

        return ResponseEntity.ok(OficinaResponse.desde(oficina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OficinaResponse> buscarPorId(@PathVariable UUID id) {
        Optional<Oficina> resultado = findOficinaByIdUseCase.findById(id);

        if (resultado.isPresent()) {
            Oficina oficina = resultado.get();
            OficinaResponse response = OficinaResponse.desde(oficina);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OficinaResponse>> findAll() {
        List<Oficina> oficinas = findAllOficinaUseCase.findAll();

        List<OficinaResponse> response = oficinas.stream()
                .map(OficinaResponse::desde)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = deleteOficinaUseCase.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}