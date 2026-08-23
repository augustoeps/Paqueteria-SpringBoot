package com.example.paqueteria.infrastructure.adapter.out.persistence.usuario;

import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.*;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioJpaEntity toJpaEntity(Usuario usuario) {
        return new UsuarioJpaEntity(
                usuario.getId(),
                usuario.getUsuarioNombre().getNombre(),
                usuario.getUsuarioApellido().getApellido(),
                usuario.getUsuarioUsername().getValor(),
                usuario.getUsuarioContrasenaHash().getHash(),
                usuario.getUsuarioCorreo().getValor(),
                usuario.getUsuarioRol()
        );
    }

    public Usuario toDomain(UsuarioJpaEntity entity) {
        return new Usuario(
                entity.getId(),
                new UsuarioNombre(entity.getNombre()),
                new UsuarioApellido(entity.getApellido()),
                new UsuarioUsername(entity.getUsername()),
                new UsuarioContrasenaHash(entity.getContrasenaHash()),
                new UsuarioCorreo(entity.getCorreo()),
                entity.getRol()
        );
    }
}