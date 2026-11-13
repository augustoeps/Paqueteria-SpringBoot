package com.example.paqueteria.testingExamples.unittesting;
import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.application.port.out.provincia.ProvinciaRepositoryPort;
import com.example.paqueteria.application.service.oficina.CreateOficinaService;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.entity.Provincia;
import com.example.paqueteria.domain.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Conecta Mockito con JUnit.
// Antes de CADA test, Mockito crea los @Mock nuevos y los inyecta en el @InjectMocks.
@ExtendWith(MockitoExtension.class)
class CreateOficinaServiceTest {

    @Mock
    private ProvinciaRepositoryPort provinciaRepositoryPort;

    @Mock
    private OficinaRepositoryPort oficinaRepositoryPort;

    @InjectMocks
    private CreateOficinaService service;


    private final OficinaCodigo codigo = new OficinaCodigo("MAD01");
    private final OficinaNombre nombre = new OficinaNombre("Oficina Central Madrid");
    private final OficinaDireccion direccion =
            new OficinaDireccion("28013", "Madrid", "Gran Vía", "28");
    private final OficinaLatitud latitud = new OficinaLatitud(40.4203);
    private final OficinaLongitud longitud = new OficinaLongitud(-3.7018);
    // Id de la provincia que el servicio va a buscar en el repositorio (falso)
    private final UUID provinciaId = UUID.randomUUID();

    @Test
    void debeGuardarLaOficina_cuandoLaProvinciaExiste() {

        when(provinciaRepositoryPort.findById(provinciaId))
                .thenReturn(Optional.of(mock(Provincia.class)));

        when(oficinaRepositoryPort.save(any(Oficina.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Oficina resultado = service.create(codigo, nombre, direccion, latitud, longitud, provinciaId);

        // JUnit
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals(codigo, resultado.getCodigo());
        assertEquals(provinciaId, resultado.getProvinciaId());


        verify(oficinaRepositoryPort, times(1)).save(any(Oficina.class));

    }

    @Test
    void noDebeGuardarLaOficina_cuandoLaProvinciaNoExiste() {
        when(provinciaRepositoryPort.findById(provinciaId))
                .thenReturn(Optional.empty());


        assertThrows(RecursoNoEncontradoException.class,
                () -> service.create(codigo, nombre, direccion, latitud, longitud, provinciaId));

        verify(oficinaRepositoryPort, never()).save(any(Oficina.class));
    }

}
