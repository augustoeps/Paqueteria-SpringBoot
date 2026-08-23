package com.example.paqueteria.application.service.usuario;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.usuario.ChangeRolUsuarioUseCase;
import com.example.paqueteria.application.port.out.usuario.UsuarioRepositoryPort;
import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.enums.UsuarioRol;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class ChangeRolUsuarioService implements ChangeRolUsuarioUseCase {

    private final UsuarioRepositoryPort repositoryPort;

    public ChangeRolUsuarioService(UsuarioRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }


    @Override
    public Usuario changeRol(UUID usuarioId, UsuarioRol nuevoRol) {

        Optional<Usuario> usuarioGuardado = this.repositoryPort.findById(usuarioId);
        if(usuarioGuardado.isEmpty()){
            throw new RecursoNoEncontradoException("Usuario no encontrado");
        }

        Usuario usuarioActualizado = usuarioGuardado.get();

        usuarioActualizado.cambiarRol(nuevoRol);
        return this.repositoryPort.update(usuarioActualizado);
    }
}
