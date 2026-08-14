package com.example.paqueteria.application.service.tarifa;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.tarifa.CotizarEnvioUseCase;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.application.port.out.tarifa.TarifaRepositoryPort;
import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.PaquetePeso;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class CotizarTarifaService  implements CotizarEnvioUseCase {

    private final TarifaRepositoryPort tarifaRepositoryPort;
    private final ProvinciaRepositoryPort provinciaRepositoryPort;

    public CotizarTarifaService(TarifaRepositoryPort tarifaRepositoryPort, ProvinciaRepositoryPort provinciaRepositoryPort){
        this.tarifaRepositoryPort = tarifaRepositoryPort;
        this.provinciaRepositoryPort = provinciaRepositoryPort;
    }

    @Override
    public BigDecimal cotizar(UUID provinciaOrigenId, UUID provinciaDestinoId, PaquetePeso peso) {

        Optional<Provincia> provinciaOrigen = this.provinciaRepositoryPort.findById(provinciaOrigenId);
        Optional<Provincia> provinciaDestino = this.provinciaRepositoryPort.findById(provinciaDestinoId);

        if(provinciaDestino.isEmpty() || provinciaOrigen.isEmpty()) {
            throw new RecursoNoEncontradoException("La provincia origen o destino no existe");
        }

        Optional<Tarifa> tarifa = this.tarifaRepositoryPort.findByOrigenAndDestino(provinciaOrigenId, provinciaDestinoId);

        if (tarifa.isEmpty()) {
            throw new RecursoNoEncontradoException("No existe una tarifa configurada para esta ruta");
        }

        return tarifa.get().calcularPrecio(peso);
    }
}
