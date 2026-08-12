package com.example.paqueteria.domain.entity;

import com.example.paqueteria.domain.enums.EstadoPaquete;
import com.example.paqueteria.domain.valueobjects.DatosContacto;
import com.example.paqueteria.domain.valueobjects.PaqueteCodigoSeguimiento;
import com.example.paqueteria.domain.valueobjects.PaquetePeso;
import com.example.paqueteria.domain.valueobjects.PaqueteTarifaAplicada;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Paquete {

    private final UUID id;
    private final PaqueteCodigoSeguimiento codigoSeguimiento;
    private final PaquetePeso peso;
    private EstadoPaquete estadoPaquete;
    private final UUID oficinaOrigenId;
    private final UUID oficinaDestinoId;
    private final DatosContacto remitente;
    private final DatosContacto destinatario;
    private final PaqueteTarifaAplicada tarifaAplicada;
    private final LocalDateTime fechaCreacion;

    public Paquete(PaqueteCodigoSeguimiento codigoSeguimiento, PaquetePeso peso, UUID oficinaOrigenId, UUID oficinaDestinoId, DatosContacto remitente, DatosContacto destinatario, PaqueteTarifaAplicada tarifaAplicada) {
        validacion(codigoSeguimiento, peso, oficinaOrigenId, oficinaDestinoId, remitente, destinatario, tarifaAplicada);

        this.id = UUID.randomUUID();
        this.codigoSeguimiento = codigoSeguimiento;
        this.peso = peso;
        this.estadoPaquete = EstadoPaquete.CREADO;
        this.oficinaOrigenId = oficinaOrigenId;
        this.oficinaDestinoId = oficinaDestinoId;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.tarifaAplicada = tarifaAplicada;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Paquete(UUID id, PaqueteCodigoSeguimiento codigoSeguimiento, PaquetePeso peso, EstadoPaquete estadoPaquete, UUID oficinaOrigenId, UUID oficinaDestinoId, DatosContacto remitente, DatosContacto destinatario, PaqueteTarifaAplicada tarifaAplicada, LocalDateTime fechaCreacion) {
        validacionId(id);
        validacion(codigoSeguimiento, peso, oficinaOrigenId, oficinaDestinoId, remitente, destinatario, tarifaAplicada);
        validacionEstadoYFecha(estadoPaquete, fechaCreacion);

        this.id = id;
        this.codigoSeguimiento = codigoSeguimiento;
        this.peso = peso;
        this.estadoPaquete = estadoPaquete;
        this.oficinaOrigenId = oficinaOrigenId;
        this.oficinaDestinoId = oficinaDestinoId;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.tarifaAplicada = tarifaAplicada;
        this.fechaCreacion = fechaCreacion;
    }

    private void validacionId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El identificador del paquete es obligatorio"
            );
        }
    }

    private void validacion(PaqueteCodigoSeguimiento codigoSeguimiento, PaquetePeso peso, UUID oficinaOrigenId, UUID oficinaDestinoId, DatosContacto remitente, DatosContacto destinatario, PaqueteTarifaAplicada tarifaAplicada) {
        if (codigoSeguimiento == null) {
            throw new IllegalArgumentException(
                    "El código de seguimiento es obligatorio"
            );
        }

        if (peso == null) {
            throw new IllegalArgumentException(
                    "El peso del paquete es obligatorio"
            );
        }

        if (oficinaOrigenId == null) {
            throw new IllegalArgumentException(
                    "La oficina de origen es obligatoria"
            );
        }

        if (oficinaDestinoId == null) {
            throw new IllegalArgumentException(
                    "La oficina de destino es obligatoria"
            );
        }

        if (remitente == null) {
            throw new IllegalArgumentException(
                    "Los datos del remitente son obligatorios"
            );
        }

        if (destinatario == null) {
            throw new IllegalArgumentException(
                    "Los datos del destinatario son obligatorios"
            );
        }

        if (tarifaAplicada == null) {
            throw new IllegalArgumentException(
                    "La tarifa aplicada es obligatoria"
            );
        }
    }

    private void validacionEstadoYFecha(EstadoPaquete estadoPaquete, LocalDateTime fechaCreacion) {
        if (estadoPaquete == null) {
            throw new IllegalArgumentException(
                    "El estado del paquete es obligatorio"
            );
        }

        if (fechaCreacion == null) {
            throw new IllegalArgumentException(
                    "La fecha de creación es obligatoria"
            );
        }
    }

    public UUID getId() {
        return id;
    }

    public PaqueteCodigoSeguimiento getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public PaquetePeso getPeso() {
        return peso;
    }

    public EstadoPaquete getEstadoPaquete() {
        return estadoPaquete;
    }

    public UUID getOficinaOrigenId() {
        return oficinaOrigenId;
    }

    public UUID getOficinaDestinoId() {
        return oficinaDestinoId;
    }

    public DatosContacto getRemitente() {
        return remitente;
    }

    public DatosContacto getDestinatario() {
        return destinatario;
    }

    public PaqueteTarifaAplicada getTarifaAplicada() {
        return tarifaAplicada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        Paquete otroPaquete = (Paquete) objeto;

        return Objects.equals(id, otroPaquete.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //LOGICA DE PAQUETE

    public HistorialEstado crearHistorialInicial(UUID oficinaId) {
        return new HistorialEstado(
                id,
                EstadoPaquete.CREADO,
                fechaCreacion,
                oficinaId
        );
    }

    public HistorialEstado ponerEnTransito(UUID oficinaId) {
        if (
                estadoPaquete != EstadoPaquete.CREADO
                        && estadoPaquete != EstadoPaquete.EN_TRANSITO
        ) {
            throw new IllegalStateException(
                    "El paquete no puede ponerse en tránsito desde el estado "
                            + estadoPaquete
            );
        }

        return cambiarEstado(
                EstadoPaquete.EN_TRANSITO,
                oficinaId
        );
    }

    public HistorialEstado registrarLlegadaDestino(UUID oficinaId) {
        if (estadoPaquete != EstadoPaquete.EN_TRANSITO) {
            throw new IllegalStateException(
                    "Solo un paquete en tránsito puede llegar a la oficina de destino"
            );
        }

        if (oficinaId == null) {
            throw new IllegalArgumentException(
                    "La oficina de destino es obligatoria"
            );
        }

        return cambiarEstado(
                EstadoPaquete.EN_OFICINA_DESTINO,
                oficinaId
        );
    }

    public HistorialEstado entregar(UUID oficinaId) {
        if (estadoPaquete != EstadoPaquete.EN_OFICINA_DESTINO) {
            throw new IllegalStateException(
                    "Solo un paquete que está en la oficina de destino puede entregarse"
            );
        }

        return cambiarEstado(
                EstadoPaquete.ENTREGADO,
                oficinaId
        );
    }

    public HistorialEstado cancelar(UUID oficinaId) {
        if (estadoPaquete == EstadoPaquete.ENTREGADO) {
            throw new IllegalStateException(
                    "Un paquete entregado no puede cancelarse"
            );
        }

        if (estadoPaquete == EstadoPaquete.CANCELADO) {
            throw new IllegalStateException(
                    "El paquete ya está cancelado"
            );
        }

        return cambiarEstado(
                EstadoPaquete.CANCELADO,
                oficinaId
        );
    }

    private HistorialEstado cambiarEstado(EstadoPaquete nuevoEstado, UUID oficinaId) {
        this.estadoPaquete = nuevoEstado;

        return new HistorialEstado(
                id, // Paquete.id → se guarda como paqueteId
                nuevoEstado,
                LocalDateTime.now(),
                oficinaId
        );
    }




}