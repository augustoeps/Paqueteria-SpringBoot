package com.example.paqueteria.application.service.usuario;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.usuario.DeleteUsuarioUseCase;
import com.example.paqueteria.application.port.out.usuario.UsuarioRepositoryPort;
import com.example.paqueteria.domain.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class DeleteUsuarioService implements DeleteUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public DeleteUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }


    @Override
    public boolean delete(UUID id) {
        Optional<Usuario> usuario = this.usuarioRepositoryPort.findById(id);
        if(usuario.isEmpty()){
            throw new RecursoNoEncontradoException("El usuario a eliminar no existe");
        }
        return this.usuarioRepositoryPort.delete(id);
    }
}
