package com.example.paqueteria.infrastructure.adapter.in.web.paquete;

import com.example.paqueteria.application.port.in.paquete.*;
import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.valueobjects.DatosContacto;
import com.example.paqueteria.domain.valueobjects.DatosContactoDireccion;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;
import com.example.paqueteria.domain.valueobjects.PaquetePeso;
import com.example.paqueteria.infrastructure.adapter.in.web.paquete.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/paquetes")
public class PaqueteController {

    private final CreatePaqueteUseCase createPaqueteUseCase;
    private final FindPaqueteByIdUseCase findPaqueteByIdUseCase;
    private final FindPaqueteByCodigoSeguimientoUseCase findPaqueteByCodigoSeguimientoUseCase;
    private final FindAllPaqueteUseCase findAllPaqueteUseCase;
    private final PonerEnTransitoUseCase ponerEnTransitoUseCase;
    private final RegistrarLlegadaDestinoUseCase registrarLlegadaDestinoUseCase;
    private final EntregarPaqueteUseCase entregarPaqueteUseCase;
    private final CancelarPaqueteUseCase cancelarPaqueteUseCase;

    public PaqueteController(CreatePaqueteUseCase createPaqueteUseCase,
                             FindPaqueteByIdUseCase findPaqueteByIdUseCase,
                             FindPaqueteByCodigoSeguimientoUseCase findPaqueteByCodigoSeguimientoUseCase,
                             FindAllPaqueteUseCase findAllPaqueteUseCase,
                             PonerEnTransitoUseCase ponerEnTransitoUseCase,
                             RegistrarLlegadaDestinoUseCase registrarLlegadaDestinoUseCase,
                             EntregarPaqueteUseCase entregarPaqueteUseCase,
                             CancelarPaqueteUseCase cancelarPaqueteUseCase) {
        this.createPaqueteUseCase = createPaqueteUseCase;
        this.findPaqueteByIdUseCase = findPaqueteByIdUseCase;
        this.findPaqueteByCodigoSeguimientoUseCase = findPaqueteByCodigoSeguimientoUseCase;
        this.findAllPaqueteUseCase = findAllPaqueteUseCase;
        this.ponerEnTransitoUseCase = ponerEnTransitoUseCase;
        this.registrarLlegadaDestinoUseCase = registrarLlegadaDestinoUseCase;
        this.entregarPaqueteUseCase = entregarPaqueteUseCase;
        this.cancelarPaqueteUseCase = cancelarPaqueteUseCase;
    }

    @PostMapping
    public ResponseEntity<PaqueteResponse> crear(@Valid @RequestBody CrearPaqueteRequest request) {
        PaquetePeso peso = new PaquetePeso(request.peso());
        DatosContacto remitente = toDatosContacto(request.remitente());
        DatosContacto destinatario = toDatosContacto(request.destinatario());

        Paquete paquete = createPaqueteUseCase.create(
                peso,
                request.oficinaOrigenId(), request.oficinaDestinoId(),
                remitente, destinatario
        );

        return ResponseEntity.ok(PaqueteResponse.desde(paquete));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaqueteResponse> buscarPorId(@PathVariable UUID id) {
        Optional<Paquete> resultado = findPaqueteByIdUseCase.FindById(id);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(PaqueteResponse.desde(resultado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/codigo/{codigoSeguimiento}")
    public ResponseEntity<PaqueteResponse> buscarPorCodigo(@PathVariable String codigoSeguimiento) {
        PaqueteCodigoSeguimiento codigo = new PaqueteCodigoSeguimiento(codigoSeguimiento);
        Optional<Paquete> resultado = findPaqueteByCodigoSeguimientoUseCase.findByCodigoSeguimiento(codigo);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(PaqueteResponse.desde(resultado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PaqueteResponse>> findAll() {
        List<Paquete> paquetes = findAllPaqueteUseCase.findAll();
        List<PaqueteResponse> response = paquetes.stream()
                .map(PaqueteResponse::desde)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/poner-en-transito")
    public ResponseEntity<PaqueteResponse> ponerEnTransito(@PathVariable UUID id, @Valid @RequestBody CambiarEstadoPaqueteRequest request) {
        Paquete paquete = ponerEnTransitoUseCase.ponerEnTransito(id, request.oficinaId());
        return ResponseEntity.ok(PaqueteResponse.desde(paquete));
    }

    @PatchMapping("/{id}/registrar-llegada-destino")
    public ResponseEntity<PaqueteResponse> registrarLlegadaDestino(@PathVariable UUID id, @Valid @RequestBody CambiarEstadoPaqueteRequest request) {
        Paquete paquete = registrarLlegadaDestinoUseCase.registrarLlegadaDestino(id, request.oficinaId());
        return ResponseEntity.ok(PaqueteResponse.desde(paquete));
    }

    @PatchMapping("/{id}/entregar")
    public ResponseEntity<PaqueteResponse> entregar(@PathVariable UUID id, @Valid @RequestBody CambiarEstadoPaqueteRequest request) {
        Paquete paquete = entregarPaqueteUseCase.entregar(id, request.oficinaId());
        return ResponseEntity.ok(PaqueteResponse.desde(paquete));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PaqueteResponse> cancelar(@PathVariable UUID id, @Valid @RequestBody CambiarEstadoPaqueteRequest request) {
        Paquete paquete = cancelarPaqueteUseCase.cancelar(id, request.oficinaId());
        return ResponseEntity.ok(PaqueteResponse.desde(paquete));
    }

    private DatosContacto toDatosContacto(DatosContactoRequest request) {
        DatosContactoDireccion direccion = new DatosContactoDireccion(
                request.direccion().calle(),
                request.direccion().ciudad(),
                request.direccion().provincia(),
                request.direccion().codigoPostal()
        );
        return new DatosContacto(request.nombre(), request.telefono(), request.email(), direccion);
    }
}