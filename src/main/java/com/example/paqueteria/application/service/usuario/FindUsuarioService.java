package com.example.paqueteria.application.service.usuario;

import com.example.paqueteria.application.port.in.usuario.FindAllUsuarioUseCase;
import com.example.paqueteria.application.port.in.usuario.FindByIdUsuarioUseCase;
import com.example.paqueteria.application.port.in.usuario.FindByUsernameUsuarioUseCase;
import com.example.paqueteria.application.port.out.usuario.UsuarioRepositoryPort;
import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.UsuarioUsername;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class FindUsuarioService implements FindByUsernameUsuarioUseCase, FindAllUsuarioUseCase, FindByIdUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public FindUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }


    @Override
    public List<Usuario> findAll() {
        return this.usuarioRepositoryPort.findAll();
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return this.usuarioRepositoryPort.findById(id);
    }

    @Override
    public Optional<Usuario> findByUsername(UsuarioUsername username) {
        return this.usuarioRepositoryPort.findByUsername(username);
    }
}
