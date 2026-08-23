package com.example.paqueteria.infrastructure.adapter.out.persistence.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, UUID> {

    Optional<UsuarioJpaEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByCorreo(String correo);
}