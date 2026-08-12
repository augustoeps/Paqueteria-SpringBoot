package com.example.paqueteria.domain;

import com.example.paqueteria.domain.entity.Tarifa;
import com.example.paqueteria.domain.valueobjects.PaquetePeso;
import com.example.paqueteria.domain.valueobjects.TarifaPrecioPorKilogramo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TarifaTest {

    @Test
    void debeCrearUnaTarifaValida() {
        UUID origenId = UUID.randomUUID();
        UUID destinoId = UUID.randomUUID();

        TarifaPrecioPorKilogramo precio =
                new TarifaPrecioPorKilogramo(new BigDecimal("8.00"));

        Tarifa tarifa = new Tarifa(origenId, destinoId, precio);

        assertNotNull(tarifa.getId());
        assertEquals(origenId, tarifa.getProvinciaOrigenId());
        assertEquals(destinoId, tarifa.getProvinciaDestinoId());
        assertEquals(precio, tarifa.getPrecioPorKilogramo());
    }

    @Test
    void debeCalcularElPrecioDelPaquete() {
        Tarifa tarifa = new Tarifa(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new TarifaPrecioPorKilogramo(new BigDecimal("8.00"))
        );

        PaquetePeso peso = new PaquetePeso(new BigDecimal("3.000"));

        BigDecimal resultado = tarifa.calcularPrecio(peso);

        assertEquals(new BigDecimal("24.00"), resultado);
    }

    @Test
    void noDebeCrearTarifaSinProvinciaOrigen() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Tarifa(
                        null,
                        UUID.randomUUID(),
                        new TarifaPrecioPorKilogramo(new BigDecimal("8.00"))
                )
        );
    }

    @Test
    void noDebeCrearTarifaSinProvinciaDestino() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Tarifa(
                        UUID.randomUUID(),
                        null,
                        new TarifaPrecioPorKilogramo(new BigDecimal("8.00"))
                )
        );
    }

    @Test
    void noDebeCrearTarifaSinPrecio() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Tarifa(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null
                )
        );
    }
}