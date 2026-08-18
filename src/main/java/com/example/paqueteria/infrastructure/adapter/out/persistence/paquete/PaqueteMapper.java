package com.example.paqueteria.infrastructure.adapter.out.persistence.paquete;

import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.valueobjects.DatosContacto;
import com.example.paqueteria.domain.valueobjects.DatosContactoDireccion;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;
import com.example.paqueteria.domain.valueobjects.PaquetePeso;
import com.example.paqueteria.domain.valueobjects.PaqueteTarifaAplicada;
import org.springframework.stereotype.Component;

@Component
public class PaqueteMapper {

    public PaqueteJpaEntity toJpaEntity(Paquete paquete) {
        DatosContacto remitente = paquete.getRemitente();
        DatosContacto destinatario = paquete.getDestinatario();
        DatosContactoDireccion direccionRemitente = remitente.getDireccion();
        DatosContactoDireccion direccionDestinatario = destinatario.getDireccion();

        return new PaqueteJpaEntity(
                paquete.getId(),
                paquete.getCodigoSeguimiento().getCodigo(),
                paquete.getPeso().getKilogramos(),
                paquete.getEstadoPaquete(),
                paquete.getOficinaOrigenId(),
                paquete.getOficinaDestinoId(),
                remitente.getNombre(),
                remitente.getTelefono(),
                remitente.getEmail(),
                direccionRemitente.getCalle(),
                direccionRemitente.getCiudad(),
                direccionRemitente.getProvincia(),
                direccionRemitente.getCodigoPostal(),
                destinatario.getNombre(),
                destinatario.getTelefono(),
                destinatario.getEmail(),
                direccionDestinatario.getCalle(),
                direccionDestinatario.getCiudad(),
                direccionDestinatario.getProvincia(),
                direccionDestinatario.getCodigoPostal(),
                paquete.getTarifaAplicada().getMonto(),
                paquete.getFechaCreacion()
        );
    }

    public Paquete toDomain(PaqueteJpaEntity entity) {
        DatosContactoDireccion direccionRemitente = new DatosContactoDireccion(
                entity.getRemitenteCalle(),
                entity.getRemitenteCiudad(),
                entity.getRemitenteProvincia(),
                entity.getRemitenteCodigoPostal()
        );

        DatosContacto remitente = new DatosContacto(
                entity.getRemitenteNombre(),
                entity.getRemitenteTelefono(),
                entity.getRemitenteEmail(),
                direccionRemitente
        );

        DatosContactoDireccion direccionDestinatario = new DatosContactoDireccion(
                entity.getDestinatarioCalle(),
                entity.getDestinatarioCiudad(),
                entity.getDestinatarioProvincia(),
                entity.getDestinatarioCodigoPostal()
        );

        DatosContacto destinatario = new DatosContacto(
                entity.getDestinatarioNombre(),
                entity.getDestinatarioTelefono(),
                entity.getDestinatarioEmail(),
                direccionDestinatario
        );

        return new Paquete(
                entity.getId(),
                new PaqueteCodigoSeguimiento(entity.getCodigoSeguimiento()),
                new PaquetePeso(entity.getPeso()),
                entity.getEstadoPaquete(),
                entity.getOficinaOrigenId(),
                entity.getOficinaDestinoId(),
                remitente,
                destinatario,
                new PaqueteTarifaAplicada(entity.getTarifaAplicada()),
                entity.getFechaCreacion()
        );
    }
}