package com.example.paqueteria.infrastructure.adapter.in.web.tarifa;

import com.example.paqueteria.application.port.in.tarifa.*;
import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.PaquetePeso;
import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;
import com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    private final CreateTarifaUseCase createTarifaUseCase;
    private final FindByIdTarifaUseCase findByIdTarifaUseCase;
    private final FindAllTarifaUseCase findAllTarifaUseCase;
    private final FindTarifaByOrigenAndDestinoUseCase findTarifaByOrigenAndDestinoUseCase;
    private final UpdateTarifaUseCase updateTarifaUseCase;
    private final DeleteTarifaUseCase deleteTarifaUseCase;
    private final CotizarEnvioUseCase cotizarEnvioUseCase;

    public TarifaController(CreateTarifaUseCase createTarifaUseCase,
                            FindByIdTarifaUseCase findByIdTarifaUseCase,
                            FindAllTarifaUseCase findAllTarifaUseCase,
                            FindTarifaByOrigenAndDestinoUseCase findTarifaByOrigenAndDestinoUseCase,
                            UpdateTarifaUseCase updateTarifaUseCase,
                            DeleteTarifaUseCase deleteTarifaUseCase,
                            CotizarEnvioUseCase cotizarEnvioUseCase) {
        this.createTarifaUseCase = createTarifaUseCase;
        this.findByIdTarifaUseCase = findByIdTarifaUseCase;
        this.findAllTarifaUseCase = findAllTarifaUseCase;
        this.findTarifaByOrigenAndDestinoUseCase = findTarifaByOrigenAndDestinoUseCase;
        this.updateTarifaUseCase = updateTarifaUseCase;
        this.deleteTarifaUseCase = deleteTarifaUseCase;
        this.cotizarEnvioUseCase = cotizarEnvioUseCase;
    }

    @PostMapping
    public ResponseEntity<TarifaResponse> crear(@Valid @RequestBody CrearTarifaRequest request) {
        TarifaPrecioPorKilogramo precio = new TarifaPrecioPorKilogramo(request.precioPorKilogramo());

        Tarifa tarifa = createTarifaUseCase.create(
                request.provinciaOrigenId(),
                request.provinciaDestinoId(),
                precio
        );

        return ResponseEntity.ok(TarifaResponse.desde(tarifa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaResponse> buscarPorId(@PathVariable UUID id) {
        Optional<Tarifa> resultado = findByIdTarifaUseCase.findById(id);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(TarifaResponse.desde(resultado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TarifaResponse>> findAll() {
        List<Tarifa> tarifas = findAllTarifaUseCase.findAll();
        List<TarifaResponse> response = tarifas.stream()
                .map(TarifaResponse::desde)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<TarifaResponse> buscarPorRuta(
            @RequestParam UUID provinciaOrigenId,
            @RequestParam UUID provinciaDestinoId) {

        Optional<Tarifa> resultado = findTarifaByOrigenAndDestinoUseCase.findByOrigenAndDestino(
                provinciaOrigenId, provinciaDestinoId
        );

        if (resultado.isPresent()) {
            return ResponseEntity.ok(TarifaResponse.desde(resultado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaResponse> actualizar(@PathVariable UUID id, @Valid @RequestBody UpdateTarifaRequest request) {
        TarifaPrecioPorKilogramo precio = new TarifaPrecioPorKilogramo(request.precioPorKilogramo());
        Tarifa tarifa = updateTarifaUseCase.update(id, precio);
        return ResponseEntity.ok(TarifaResponse.desde(tarifa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = deleteTarifaUseCase.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cotizar")
    public ResponseEntity<BigDecimal> cotizar(@Valid @RequestBody CotizarEnvioRequest request) {
        PaquetePeso peso = new PaquetePeso(request.peso());

        BigDecimal precio = cotizarEnvioUseCase.cotizar(
                request.provinciaOrigenId(),
                request.provinciaDestinoId(),
                peso
        );

        return ResponseEntity.ok(precio);
    }
}