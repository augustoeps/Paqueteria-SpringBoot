package com.example.paqueteria.application.port.in.paquete;

import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.valueobjects.DatosContacto;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;
import com.example.paqueteria.domain.valueobjects.PaquetePeso;

import java.util.UUID;

public interface CreatePaqueteUseCase {
    Paquete create( PaquetePeso peso,
                   UUID oficinaOrigenId, UUID oficinaDestinoId,
                   DatosContacto remitente, DatosContacto destinatario);
}
