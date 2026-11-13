package com.example.paqueteria.testingExamples.unittesting;

import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.valueobjects.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

// @Tag: Mi intencion aqui es usar esta etiqueta para ejecutar solo los test de tipo regression:
//   mvnw test -Dgroups=regression
@Tag("regression")
class OficinaRegresionTest {

    private final OficinaCodigo codigo = new OficinaCodigo("MAD01");
    private final OficinaNombre nombre = new OficinaNombre("Oficina Central Madrid");
    private final OficinaDireccion direccion =
            new OficinaDireccion("28013", "Madrid", "Gran Vía", "28");
    private final OficinaLatitud latitud = new OficinaLatitud(40.4203);
    private final OficinaLongitud longitud = new OficinaLongitud(-3.7018);


    @Test
    @DisplayName("Regresión: el constructor de creación aceptaba una oficina sin provincia")
    void elConstructorDeCreacionDebeRechazarProvinciaNula() {
        // ACT + ASSERT: crear una oficina con provincia null debe fallar
        NullPointerException error = assertThrows(NullPointerException.class,
                () -> new Oficina(codigo, nombre, direccion, latitud, longitud, null));


        assertEquals("La provincia es obligatoria", error.getMessage());
    }
}
