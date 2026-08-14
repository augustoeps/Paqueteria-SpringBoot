package com.example.paqueteria.application.service.paquete;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.paquete.CreatePaqueteUseCase;
import com.example.paqueteria.application.port.out.historialEstado.HistorialEstadoRepositoryPort;
import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.application.port.out.paquete.PaqueteRepositoryPort;
import com.example.paqueteria.application.port.out.tarifa.TarifaRepositoryPort;
import com.example.paqueteria.domain.entity.HistorialEstado;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.DatosContacto;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;
import com.example.paqueteria.domain.valueobjects.PaquetePeso;
import com.example.paqueteria.domain.valueobjects.PaqueteTarifaAplicada;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
@Service
public class CreatePaqueteService implements CreatePaqueteUseCase {

    private final PaqueteRepositoryPort paqueteRepositoryPort;
    private final OficinaRepositoryPort oficinaRepositoryPort;
    private final TarifaRepositoryPort tarifaRepositoryPort;
    private final HistorialEstadoRepositoryPort historialEstadoRepositoryPort;

    public CreatePaqueteService(PaqueteRepositoryPort paqueteRepositoryPort,
                                OficinaRepositoryPort oficinaRepositoryPort,
                                TarifaRepositoryPort tarifaRepositoryPort,
                                HistorialEstadoRepositoryPort historialEstadoRepositoryPort) {
        this.paqueteRepositoryPort = paqueteRepositoryPort;
        this.oficinaRepositoryPort = oficinaRepositoryPort;
        this.tarifaRepositoryPort = tarifaRepositoryPort;
        this.historialEstadoRepositoryPort = historialEstadoRepositoryPort;
    }

    @Override
    public Paquete create(PaqueteCodigoSeguimiento codigoSeguimiento, PaquetePeso peso,
                          UUID oficinaOrigenId, UUID oficinaDestinoId,
                          DatosContacto remitente, DatosContacto destinatario) {

        Optional<Oficina> oficinaOrigen = this.oficinaRepositoryPort.findById(oficinaOrigenId);
        Optional<Oficina> oficinaDestino = this.oficinaRepositoryPort.findById(oficinaDestinoId);

        if (oficinaOrigen.isEmpty() || oficinaDestino.isEmpty()) {
            throw new RecursoNoEncontradoException("La oficina origen o destino no existe");
        }

        Optional<Tarifa> tarifa = tarifaRepositoryPort.findByOrigenAndDestino(
                oficinaOrigen.get().getProvinciaId(),
                oficinaDestino.get().getProvinciaId()
        );

        if (tarifa.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe una tarifa configurada para esta ruta");
        }

        BigDecimal precioAplicado = tarifa.get().calcularPrecio(peso);
        PaqueteTarifaAplicada paqueteTarifaAplicada = new PaqueteTarifaAplicada(precioAplicado);

        Paquete paquete = new Paquete(codigoSeguimiento, peso, oficinaOrigenId, oficinaDestinoId,
                remitente, destinatario, paqueteTarifaAplicada);

        Paquete paqueteGuardado = this.paqueteRepositoryPort.save(paquete);

        HistorialEstado historialInicial = paqueteGuardado.crearHistorialInicial(oficinaOrigenId);
        historialEstadoRepositoryPort.save(historialInicial);

        return paqueteGuardado;
    }
}

