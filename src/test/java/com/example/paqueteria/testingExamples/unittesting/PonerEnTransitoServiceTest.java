package com.example.paqueteria.testingExamples.unittesting;


import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.out.historialEstado.HistorialEstadoRepositoryPort;
import com.example.paqueteria.application.port.out.notifications.EventPublisherPort;
import com.example.paqueteria.application.port.out.oficina.OficinaRepositoryPort;
import com.example.paqueteria.application.port.out.paquete.PaqueteRepositoryPort;
import com.example.paqueteria.application.service.paquete.PonerEnTransitoService;
import com.example.paqueteria.domain.entity.HistorialEstado;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.entity.Paquete;
import com.example.paqueteria.domain.enums.EstadoPaquete;
import com.example.paqueteria.domain.valueobjects.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PonerEnTransitoServiceTest {



    @Mock
    private PaqueteRepositoryPort paqueteRepositoryPort;

    @Mock
    private OficinaRepositoryPort oficinaRepositoryPort;
    @Mock
    private HistorialEstadoRepositoryPort historialEstadoRepositoryPort;
    @Mock
    private EventPublisherPort eventPublisherPort;

    private final UUID paqueteId = UUID.randomUUID();
    private final UUID oficinaId = UUID.randomUUID();

    // ---------- Datos de prueba para crear un Paquete real ----------
    private final UUID oficinaOrigenId = UUID.randomUUID();
    private final UUID oficinaDestinoId = UUID.randomUUID();

    private final PaqueteCodigoSeguimiento codigo = PaqueteCodigoSeguimiento.generar();
    private final PaquetePeso peso = new PaquetePeso(new BigDecimal("3.000"));
    private final PaqueteTarifaAplicada tarifa = new PaqueteTarifaAplicada(new BigDecimal("24.00"));

    private final DatosContacto remitente =
            crearDatosContacto("Ana García", "600111222", "ana@email.com", "Las Palmas");
    private final DatosContacto destinatario =
            crearDatosContacto("Carlos Pérez", "600333444", "carlos@email.com", "Madrid");


    UUID provinciaOrigenId = UUID.randomUUID();
    UUID provinciaDestinoId = UUID.randomUUID();

    Oficina oficinaOrigen = new Oficina(oficinaOrigenId,
            new OficinaCodigo("LPA01"),
            new OficinaNombre("Oficina Las Palmas"),
            new OficinaDireccion("35001", "Las Palmas", "Calle Triana", "10"),
            new OficinaLatitud(28.1235),
            new OficinaLongitud(-15.4363),
            provinciaOrigenId);

    Oficina oficinaDestino = new Oficina(oficinaDestinoId,
            new OficinaCodigo("MAD01"),
            new OficinaNombre("Oficina Central Madrid"),
            new OficinaDireccion("28013", "Madrid", "Gran Vía", "28"),
            new OficinaLatitud(40.4203),
            new OficinaLongitud(-3.7018),
            provinciaDestinoId);


    @InjectMocks
    private PonerEnTransitoService ponerEnTransitoService;



    @Test
    void debeLanzarExcepcion_cuandoElPaqueteNoExiste(){

        when(paqueteRepositoryPort.findById(paqueteId))
                .thenReturn(Optional.empty());
        when(oficinaRepositoryPort.findById(oficinaId))
                .thenReturn(Optional.of(mock(Oficina.class)));

        assertThrows(RecursoNoEncontradoException.class,
                () -> ponerEnTransitoService.ponerEnTransito(paqueteId, oficinaId));

    }

    @Test
    void debeLanzarExcepcion_cuandoLaOficinaNoExiste(){

        when(paqueteRepositoryPort.findById(paqueteId))
                .thenReturn(Optional.of(mock(Paquete.class)));

        when(oficinaRepositoryPort.findById(oficinaId))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> ponerEnTransitoService.ponerEnTransito(paqueteId,oficinaId));

    }

    @Test
    void debeLanzarExcepcion_cuandoElPaqueteYaEstaEntregado(){

        Paquete paqueteEntregado = new Paquete(paqueteId, codigo, peso, EstadoPaquete.ENTREGADO,
                oficinaOrigenId, oficinaDestinoId, remitente, destinatario, tarifa, LocalDateTime.now());


        when(paqueteRepositoryPort.findById(paqueteId))
                .thenReturn(Optional.of(paqueteEntregado));
        when(oficinaRepositoryPort.findById(oficinaId))
                .thenReturn(Optional.of(mock(Oficina.class)));


         Exception error = assertThrows(IllegalStateException.class,
                () -> ponerEnTransitoService.ponerEnTransito(paqueteId,oficinaId ));


         assertEquals(error.getMessage(), "El paquete no puede ponerse en tránsito desde el estado ENTREGADO");

        verify(paqueteRepositoryPort, never()).save(any());          // no se guardó el paquete
        verify(historialEstadoRepositoryPort, never()).save(any());  // no se guardó el historial
        verify(eventPublisherPort, never()).publicarEmail(any());    // no se envió el correo
    }

    @Test
    void debePonerEnTransito_cuandoElPaqueteEstaCreado() {
        Paquete paqueteCreado = new Paquete(paqueteId, codigo, peso, EstadoPaquete.CREADO,
                oficinaOrigenId, oficinaDestinoId, remitente, destinatario, tarifa, LocalDateTime.now());


        when(paqueteRepositoryPort.findById(paqueteId))
                .thenReturn(Optional.of(paqueteCreado));
        when(oficinaRepositoryPort.findById(oficinaId))
                .thenReturn(Optional.of(oficinaOrigen));

        when(paqueteRepositoryPort.save(any(Paquete.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        when(oficinaRepositoryPort.findById(paqueteCreado.getOficinaOrigenId()))
                .thenReturn(Optional.of(oficinaOrigen));

        when(oficinaRepositoryPort.findById(paqueteCreado.getOficinaDestinoId()))
                .thenReturn(Optional.of(oficinaDestino));


        Paquete resultado = ponerEnTransitoService.ponerEnTransito(paqueteId, oficinaId);


        assertNotNull(resultado);
        assertSame(paqueteCreado, resultado);
        assertEquals(paqueteCreado.getId(),resultado.getId());
        assertEquals(EstadoPaquete.EN_TRANSITO, resultado.getEstadoPaquete());


    }

    // Crea unos datos de contacto válidos (copiado de PaqueteTest)
    private DatosContacto crearDatosContacto(String nombre, String telefono, String email, String provincia) {
        DatosContactoDireccion direccion = new DatosContactoDireccion(
                "Calle Mayor 10",
                "Las Palmas de Gran Canaria",
                provincia,
                "35001");

        return new DatosContacto(nombre, telefono, email, direccion);
    }

}
