package com.example.paqueteria.testingExamples.integrationtesting;
import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.valueobjects.*;
import com.example.paqueteria.infrastructure.adapter.out.persistence.oficina.OficinaMapper;
import com.example.paqueteria.infrastructure.adapter.out.persistence.oficina.OficinaRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

// Levanta SOLO la capa JPA (entidades, JpaRepository, conexión a la BD).
// "create-drop": Hibernate crea las tablas al arrancar y las borra al terminar.
// Lo indicamos aquí porque application.properties no está en Git (en Jenkins no existe).
@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")

// El adaptador y el mapper NO forman parte de la "rebanada" JPA: los añadimos a mano.
@Import({OficinaRepositoryAdapter.class, OficinaMapper.class})

// Activa Testcontainers: arranca los @Container antes de los tests y los para al final.
@Testcontainers
class OficinaRepositoryAdapterIT {

    // El PostgreSQL de verdad, en Docker.
    // static: se arranca UNA vez para todos los tests de la clase (más rápido).
    // @ServiceConnection: Spring lee su URL, usuario y contraseña y configura la conexión solo.
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    // El adaptador REAL, creado por Spring (no es un mock).
    @Autowired
    private OficinaRepositoryAdapter adapter;

    // Herramienta de Spring para controlar JPA en los tests (flush, clear...)
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void elContextoArrancaYElContenedorEstaEnMarcha() {
        assertTrue(postgres.isRunning());
        assertNotNull(adapter);
    }
    @Test
    void debeGuardarYRecuperarTodosLosCampos() {
        // ARRANGE: una oficina de dominio con datos conocidos
        UUID provinciaId = UUID.randomUUID();
        Oficina oficina = new Oficina(
                new OficinaCodigo("MAD01"),
                new OficinaNombre("Oficina Central Madrid"),
                new OficinaDireccion("28013", "Madrid", "Gran Vía", "28"),
                new OficinaLatitud(40.4203),
                new OficinaLongitud(-3.7018),
                provinciaId);

        // ACT 1: guardar con el adaptador REAL (mapper + JPA)
        adapter.save(oficina);

        // Forzamos el INSERT real y vaciamos la caché de JPA,
        // para que el findById vaya DE VERDAD a PostgreSQL
        entityManager.flush();
        entityManager.clear();

        // ACT 2: leer desde la base de datos
        Optional<Oficina> encontrada = adapter.findById(oficina.getId());

        // ASSERT: existe y TODOS los campos vuelven iguales
        assertTrue(encontrada.isPresent());
        Oficina leida = encontrada.get();

        assertEquals("MAD01", leida.getCodigo().getCodigo());
        assertEquals("Oficina Central Madrid", leida.getNombre().getNombre());
        assertEquals("28013", leida.getDireccion().getCodigoPostal());
        assertEquals("Madrid", leida.getDireccion().getCiudad());
        assertEquals("Gran Vía", leida.getDireccion().getCalle());
        assertEquals("28", leida.getDireccion().getNumero());
        assertEquals(40.4203, leida.getLatitud().getValor());
        assertEquals(-3.7018, leida.getLongitud().getValor());
        assertEquals(provinciaId, leida.getProvinciaId());
    }


}