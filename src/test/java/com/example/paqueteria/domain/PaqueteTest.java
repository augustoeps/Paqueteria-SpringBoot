package com.example.paqueteria.domain;

import com.example.paqueteria.domain.entity.HistorialEstado;
import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.enums.EstadoPaquete;
import com.example.paqueteria.domain.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaqueteTest {

    @Test
    void unPaqueteNuevoDebeCrearseEnEstadoCreado() {
        Paquete paquete = crearPaquete();

        assertNotNull(paquete.getId());
        assertNotNull(paquete.getFechaCreacion());
        assertEquals(EstadoPaquete.CREADO, paquete.getEstadoPaquete());
    }

    @Test
    void debeCrearElHistorialInicial() {
        Paquete paquete = crearPaquete();
        UUID oficinaId = paquete.getOficinaOrigenId();

        HistorialEstado historial =
                paquete.crearHistorialInicial(oficinaId);

        assertNotNull(historial.getId());
        assertEquals(paquete.getId(), historial.getPaqueteId());
        assertEquals(EstadoPaquete.CREADO, historial.getEstadoPaquete());
        assertEquals(oficinaId, historial.getOficinaId());
        assertEquals(paquete.getFechaCreacion(), historial.getFecha());
    }

    @Test
    void debePonerUnPaqueteCreadoEnTransito() {
        Paquete paquete = crearPaquete();
        UUID oficinaId = paquete.getOficinaOrigenId();

        HistorialEstado historial =
                paquete.ponerEnTransito(oficinaId);

        assertEquals(EstadoPaquete.EN_TRANSITO, paquete.getEstadoPaquete());
        assertEquals(EstadoPaquete.EN_TRANSITO, historial.getEstadoPaquete());
        assertEquals(paquete.getId(), historial.getPaqueteId());
        assertEquals(oficinaId, historial.getOficinaId());
    }

    @Test
    void debePermitirVariosEventosEnTransito() {
        Paquete paquete = crearPaquete();

        UUID centroOrigenId = UUID.randomUUID();
        UUID centroDestinoId = UUID.randomUUID();

        HistorialEstado primero =
                paquete.ponerEnTransito(centroOrigenId);

        HistorialEstado segundo =
                paquete.ponerEnTransito(centroDestinoId);

        assertEquals(EstadoPaquete.EN_TRANSITO, paquete.getEstadoPaquete());
        assertEquals(centroOrigenId, primero.getOficinaId());
        assertEquals(centroDestinoId, segundo.getOficinaId());
        assertNotEquals(primero.getId(), segundo.getId());
    }

    @Test
    void debeRegistrarLaLlegadaALaOficinaDestino() {
        Paquete paquete = crearPaquete();

        paquete.ponerEnTransito(paquete.getOficinaOrigenId());

        HistorialEstado historial =
                paquete.registrarLlegadaDestino(
                        paquete.getOficinaDestinoId()
                );

        assertEquals(
                EstadoPaquete.EN_OFICINA_DESTINO,
                paquete.getEstadoPaquete()
        );

        assertEquals(
                EstadoPaquete.EN_OFICINA_DESTINO,
                historial.getEstadoPaquete()
        );

        assertEquals(
                paquete.getOficinaDestinoId(),
                historial.getOficinaId()
        );
    }

    @Test
    void debeEntregarUnPaqueteQueEstaEnLaOficinaDestino() {
        Paquete paquete = crearPaquete();

        paquete.ponerEnTransito(paquete.getOficinaOrigenId());
        paquete.registrarLlegadaDestino(paquete.getOficinaDestinoId());

        HistorialEstado historial =
                paquete.entregar(paquete.getOficinaDestinoId());

        assertEquals(EstadoPaquete.ENTREGADO, paquete.getEstadoPaquete());
        assertEquals(EstadoPaquete.ENTREGADO, historial.getEstadoPaquete());
    }

    @Test
    void noDebeEntregarUnPaqueteRecienCreado() {
        Paquete paquete = crearPaquete();

        assertThrows(
                IllegalStateException.class,
                () -> paquete.entregar(paquete.getOficinaDestinoId())
        );

        assertEquals(EstadoPaquete.CREADO, paquete.getEstadoPaquete());
    }

    @Test
    void noDebeRegistrarLlegadaDestinoSiNoEstaEnTransito() {
        Paquete paquete = crearPaquete();

        assertThrows(
                IllegalStateException.class,
                () -> paquete.registrarLlegadaDestino(
                        paquete.getOficinaDestinoId()
                )
        );
    }

    @Test
    void debeCancelarUnPaqueteCreado() {
        Paquete paquete = crearPaquete();

        HistorialEstado historial =
                paquete.cancelar(paquete.getOficinaOrigenId());

        assertEquals(EstadoPaquete.CANCELADO, paquete.getEstadoPaquete());
        assertEquals(EstadoPaquete.CANCELADO, historial.getEstadoPaquete());
    }

    @Test
    void noDebeCancelarUnPaqueteEntregado() {
        Paquete paquete = crearPaquete();

        paquete.ponerEnTransito(paquete.getOficinaOrigenId());
        paquete.registrarLlegadaDestino(paquete.getOficinaDestinoId());
        paquete.entregar(paquete.getOficinaDestinoId());

        assertThrows(
                IllegalStateException.class,
                () -> paquete.cancelar(paquete.getOficinaDestinoId())
        );

        assertEquals(EstadoPaquete.ENTREGADO, paquete.getEstadoPaquete());
    }

    @Test
    void noDebeCancelarDosVecesElMismoPaquete() {
        Paquete paquete = crearPaquete();

        paquete.cancelar(paquete.getOficinaOrigenId());

        assertThrows(
                IllegalStateException.class,
                () -> paquete.cancelar(paquete.getOficinaOrigenId())
        );
    }

    @Test
    void cadaHistorialDebeTenerSuPropioIdentificador() {
        Paquete paquete = crearPaquete();

        HistorialEstado inicial =
                paquete.crearHistorialInicial(paquete.getOficinaOrigenId());

        HistorialEstado transito =
                paquete.ponerEnTransito(paquete.getOficinaOrigenId());

        assertNotEquals(inicial.getId(), transito.getId());
        assertEquals(paquete.getId(), inicial.getPaqueteId());
        assertEquals(paquete.getId(), transito.getPaqueteId());
    }

    private Paquete crearPaquete() {
        UUID oficinaOrigenId = UUID.randomUUID();
        UUID oficinaDestinoId = UUID.randomUUID();

        PaqueteCodigoSeguimiento codigo =
                PaqueteCodigoSeguimiento.generar();

        PaquetePeso peso =
                new PaquetePeso(new BigDecimal("3.000"));

        DatosContacto remitente =
                crearDatosContacto(
                        "Ana García",
                        "600111222",
                        "ana@email.com",
                        "Las Palmas"
                );

        DatosContacto destinatario =
                crearDatosContacto(
                        "Carlos Pérez",
                        "600333444",
                        "carlos@email.com",
                        "Madrid"
                );

        PaqueteTarifaAplicada tarifa =
                new PaqueteTarifaAplicada(
                        new BigDecimal("24.00")
                );

        return new Paquete(
                codigo,
                peso,
                oficinaOrigenId,
                oficinaDestinoId,
                remitente,
                destinatario,
                tarifa
        );
    }

    private DatosContacto crearDatosContacto(String nombre, String telefono, String email, String provincia) {
        DatosContactoDireccion direccion =
                new DatosContactoDireccion(
                        "Calle Mayor 10",
                        "Las Palmas de Gran Canaria",
                        provincia,
                        "35001"
                );

        return new DatosContacto(
                nombre,
                telefono,
                email,
                direccion
        );
    }
}