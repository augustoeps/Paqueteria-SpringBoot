package com.example.paqueteria.domain;

import com.example.paqueteria.domain.valueobjects.ProvinciaNombre;
import com.example.paqueteria.domain.entity.Provincia;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProvinciaTest {

    @Test
    void debeCrearUnaProvinciaConNombreValido() {
        ProvinciaNombre nombre =
                new ProvinciaNombre("Las Palmas");

        Provincia provincia = new Provincia(nombre);

        assertNotNull(provincia);
        assertNotNull(provincia.getId());
        assertEquals(nombre, provincia.getNombre());
    }

    @Test
    void debeGenerarUnIdentificadorAutomaticamente() {
        Provincia provincia = new Provincia(
                new ProvinciaNombre("Las Palmas")
        );

        UUID identificador = provincia.getId();

        assertNotNull(identificador);
    }

    @Test
    void debeReconstruirUnaProvinciaConIdentificadorExistente() {
        UUID idExistente = UUID.randomUUID();

        Provincia provincia = new Provincia(
                idExistente,
                new ProvinciaNombre("Madrid")
        );

        assertEquals(idExistente, provincia.getId());
        assertEquals(
                new ProvinciaNombre("Madrid"),
                provincia.getNombre()
        );
    }

    @Test
    void noDebeCrearUnaProvinciaConNombreNulo() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> new Provincia(null)
        );

        assertEquals(
                "El nombre de la provincia es obligatorio",
                excepcion.getMessage()
        );
    }

    @Test
    void noDebeReconstruirUnaProvinciaConIdNulo() {
        ProvinciaNombre nombre =
                new ProvinciaNombre("Las Palmas");

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> new Provincia(null, nombre)
        );

        assertEquals(
                "El identificador de la provincia es obligatorio",
                excepcion.getMessage()
        );
    }

    @Test
    void debeCambiarElNombreDeLaProvincia() {
        Provincia provincia = new Provincia(
                new ProvinciaNombre("Las Palmas")
        );

        ProvinciaNombre nuevoNombre =
                new ProvinciaNombre("Santa Cruz de Tenerife");

        provincia.cambiarNombre(nuevoNombre);

        assertEquals(nuevoNombre, provincia.getNombre());
    }

    @Test
    void noDebeCambiarElNombrePorUnValorNulo() {
        Provincia provincia = new Provincia(
                new ProvinciaNombre("Las Palmas")
        );

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> provincia.cambiarNombre(null)
        );

        assertEquals(
                "El nombre de la provincia es obligatorio",
                excepcion.getMessage()
        );
    }

    @Test
    void dosProvinciasConElMismoIdDebenSerIguales() {
        UUID mismoId = UUID.randomUUID();

        Provincia primera = new Provincia(
                mismoId,
                new ProvinciaNombre("Las Palmas")
        );

        Provincia segunda = new Provincia(
                mismoId,
                new ProvinciaNombre("Madrid")
        );

        assertEquals(primera, segunda);
        assertEquals(
                primera.hashCode(),
                segunda.hashCode()
        );
    }

    @Test
    void dosProvinciasConDiferenteIdNoDebenSerIguales() {
        Provincia primera = new Provincia(
                UUID.randomUUID(),
                new ProvinciaNombre("Las Palmas")
        );

        Provincia segunda = new Provincia(
                UUID.randomUUID(),
                new ProvinciaNombre("Las Palmas")
        );

        assertNotEquals(primera, segunda);
    }

    @Test
    void unaProvinciaNoDebeSerIgualANull() {
        Provincia provincia = new Provincia(
                new ProvinciaNombre("Las Palmas")
        );

        assertNotEquals(null, provincia);
    }

    @Test
    void unaProvinciaNoDebeSerIgualAUnObjetoDeOtraClase() {
        Provincia provincia = new Provincia(
                new ProvinciaNombre("Las Palmas")
        );

        assertNotEquals("Las Palmas", provincia);
    }
}