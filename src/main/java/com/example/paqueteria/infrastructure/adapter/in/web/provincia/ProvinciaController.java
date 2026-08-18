package com.example.paqueteria.infrastructure.adapter.in.web.provincia;

import com.example.paqueteria.application.port.in.provincia.CreateProvinciaUseCase;
import com.example.paqueteria.application.port.in.provincia.DeleteProvinciaUseCase;
import com.example.paqueteria.application.port.in.provincia.FindAllProvinciaUseCase;
import com.example.paqueteria.application.port.in.provincia.FindByIdProvinciaUseCase;
import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.valueobjects.ProvinciaNombre;
import com.example.paqueteria.infrastructure.adapter.in.web.provincia.dto.CrearProvinciaRequest;
import com.example.paqueteria.infrastructure.adapter.in.web.provincia.dto.ProvinciaResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/provincias")
public class ProvinciaController {

    private final CreateProvinciaUseCase createProvinciaUseCase;
    private final FindByIdProvinciaUseCase findByIdProvinciaUseCase;
    private final FindAllProvinciaUseCase findAllProvinciaUseCase;
    private final DeleteProvinciaUseCase deleteProvinciaUseCase;

    public ProvinciaController(CreateProvinciaUseCase createProvinciaUseCase,
                               FindByIdProvinciaUseCase findByIdProvinciaUseCase,
                               FindAllProvinciaUseCase findAllProvinciaUseCase,
                               DeleteProvinciaUseCase deleteProvinciaUseCase) {
        this.createProvinciaUseCase = createProvinciaUseCase;
        this.findByIdProvinciaUseCase = findByIdProvinciaUseCase;
        this.findAllProvinciaUseCase = findAllProvinciaUseCase;
        this.deleteProvinciaUseCase = deleteProvinciaUseCase;
    }

    @PostMapping
    public ResponseEntity<ProvinciaResponse> create(@Valid @RequestBody CrearProvinciaRequest request) {

        ProvinciaNombre nombre = new ProvinciaNombre(request.nombre());

        Provincia provincia = createProvinciaUseCase.create(nombre);

        return ResponseEntity.ok(ProvinciaResponse.desde(provincia));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinciaResponse> findById(@PathVariable UUID id) {

        Optional<Provincia> resultado = findByIdProvinciaUseCase.findById(id);

        if (resultado.isPresent()) {
            Provincia provincia = resultado.get();
            ProvinciaResponse response = ProvinciaResponse.desde(provincia);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<ProvinciaResponse>> findAll() {
        List<Provincia> provincias = findAllProvinciaUseCase.getAll();

        List<ProvinciaResponse> response = provincias.stream()
                .map(ProvinciaResponse::desde)
                .toList();

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = deleteProvinciaUseCase.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}