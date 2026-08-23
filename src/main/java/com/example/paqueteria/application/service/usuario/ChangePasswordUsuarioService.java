package com.example.paqueteria.application.service.usuario;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.in.usuario.ChangePasswordUsuarioUseCase;
import com.example.paqueteria.application.port.out.usuario.UsuarioRepositoryPort;
import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.UsuarioContrasena;
import com.example.paqueteria.domain.valueobjects.UsuarioContrasenaHash;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class ChangePasswordUsuarioService implements ChangePasswordUsuarioUseCase {


    private final UsuarioRepositoryPort repositoryPort;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordUsuarioService(UsuarioRepositoryPort repositoryPort, PasswordEncoder passwordEncoder) {
        this.repositoryPort = repositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario changePassword(UUID usuarioId, UsuarioContrasena nuevaContrasena) {

        Optional<Usuario> usuarioExistente = this.repositoryPort.findById(usuarioId);
        if (usuarioExistente.isEmpty()) {
            throw new RecursoNoEncontradoException("El usuario no existe");
        }

        Usuario usuario = usuarioExistente.get();

        String hashGenerado = passwordEncoder.encode(nuevaContrasena.getValor());
        UsuarioContrasenaHash nuevoHash = new UsuarioContrasenaHash(hashGenerado);

        usuario.cambiarContrasena(nuevoHash); // ← muta el objeto existente, no reconstruyes nada

        return this.repositoryPort.update(usuario);
    }
}
