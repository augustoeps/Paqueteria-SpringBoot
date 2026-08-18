package com.example.paqueteria.infrastructure.adapter.in.web.tarifa;

import com.example.paqueteria.application.port.in.tarifa.*;
import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;
import com.example.paqueteria.infrastructure.adapter.in.web.oficina.dto.OficinaResponse;
import com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto.CrearTarifaRequest;
import com.example.paqueteria.infrastructure.adapter.in.web.tarifa.dto.TarifaResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class TarifaController {


    private final CreateTarifaUseCase createTarifaUseCase;
    private final DeleteTarifaUseCase deleteTarifaUseCase;
    private final FindTarifaByOrigenAndDestinoUseCase findTarifaByOrigenAndDestinoUseCase;
    private final FindByIdTarifaUseCase findByIdTarifaUseCase;
    private final FindAllTarifaUseCase findAllTarifaUseCase;

    public TarifaController(CreateTarifaUseCase createTarifaUseCase, DeleteTarifaUseCase deleteTarifaUseCase, FindTarifaByOrigenAndDestinoUseCase findTarifaByOrigenAndDestinoUseCase, FindByIdTarifaUseCase findByIdTarifaUseCase, FindAllTarifaUseCase findAllTarifaUseCase) {
        this.createTarifaUseCase = createTarifaUseCase;
        this.deleteTarifaUseCase = deleteTarifaUseCase;
        this.findTarifaByOrigenAndDestinoUseCase = findTarifaByOrigenAndDestinoUseCase;
        this.findByIdTarifaUseCase = findByIdTarifaUseCase;
        this.findAllTarifaUseCase = findAllTarifaUseCase;
    }

    @PostMapping
    public ResponseEntity<TarifaResponse> create(@Valid @RequestBody CrearTarifaRequest request){

        TarifaPrecioPorKilogramo tarifaPrecioPorKilogramo = new TarifaPrecioPorKilogramo(request.precioPorKilogramo());

        Tarifa tarifa = this.createTarifaUseCase.create(request.provinciaOrigenId(),request.provinciaDestinoId(), tarifaPrecioPorKilogramo);

        return ResponseEntity.ok(TarifaResponse.desde(tarifa));
    }

    @GetMapping("/id")
    public ResponseEntity<TarifaResponse> findById(UUID id){
        Optional<Tarifa> tarifa = this.findByIdTarifaUseCase.findById(id);
        if(tarifa.isPresent()){
            return ResponseEntity.ok(TarifaResponse.desde(tarifa.get()));
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<TarifaResponse>> findAll(){
        List<Tarifa> tarifas = this.findAllTarifaUseCase.findAll();

        List<TarifaResponse> response = tarifas.stream()
                .map(TarifaResponse::desde)
                .toList();

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        boolean eliminado = deleteTarifaUseCase.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<TarifaResponse> findByOrigenAndDestino(@RequestParam UUID provinciaOrigenId, @RequestParam UUID provinciaDestinoId){

        this.findTarifaByOrigenAndDestinoUseCase()
    }





}
