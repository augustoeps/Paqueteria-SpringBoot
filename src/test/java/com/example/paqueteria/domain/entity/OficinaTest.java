package com.example.paqueteria.domain.entity;

import com.example.paqueteria.domain.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OficinaTest {

    private final OficinaCodigo codigo = new OficinaCodigo("MAD01");
    private final OficinaNombre nombre = new OficinaNombre("Oficina Central Madrid");
    private final OficinaDireccion direccion =
            new OficinaDireccion("28013", "Madrid", "Gran Vía", "28");
    private final OficinaLatitud latitud = new OficinaLatitud(40.4203);
    private final OficinaLongitud longitud = new OficinaLongitud(-3.7018);
    private final UUID provinciaId = UUID.randomUUID();

    @Test
    void debeCrearUnaOficinaNuevaConIdentificador() {
        // Act
        Oficina oficina = new Oficina(codigo, nombre, direccion, latitud, longitud, provinciaId);

        // Assert
        assertNotNull(oficina.getId());
        assertEquals(codigo, oficina.getCodigo());
        assertEquals(provinciaId, oficina.getProvinciaId());
    }

    @Test
    void debeCambiarElNombreDeLaOficina() {
        // Arrange
        Oficina oficina = new Oficina(codigo, nombre, direccion, latitud, longitud, provinciaId);
        OficinaNombre nuevoNombre = new OficinaNombre("Oficina Gran Vía");

        // Act
        oficina.cambiarNombre(nuevoNombre);

        // Assert
        assertEquals(nuevoNombre, oficina.getNombre());
    }

    @Test
    void noDebeReconstruirUnaOficinaSinCodigo() {
        NullPointerException excepcion = assertThrows(
                NullPointerException.class,
                () -> new Oficina(UUID.randomUUID(), null, nombre, direccion, latitud, longitud, provinciaId)
        );

        assertEquals("El código es obligatorio", excepcion.getMessage());
    }

    @Test
    void noDebeCrearUnaOficinaNuevaSinCodigo() {
        NullPointerException excepcion = assertThrows(
                NullPointerException.class,
                () -> new Oficina(null, nombre, direccion, latitud, longitud, provinciaId)
        );

        assertEquals("El código es obligatorio", excepcion.getMessage());
    }
}