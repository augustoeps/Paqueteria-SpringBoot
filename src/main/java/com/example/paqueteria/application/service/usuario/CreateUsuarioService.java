package com.example.paqueteria.application.service.usuario;

import com.example.paqueteria.application.port.in.usuario.CreateUsuarioUseCase;
import com.example.paqueteria.application.port.out.usuario.UsuarioRepositoryPort;
import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.enums.UsuarioRol;
import com.example.paqueteria.domain.valueobjects.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUsuarioService implements CreateUsuarioUseCase {


    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoder passwordEncoder;


    public CreateUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoder passwordEncoder) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario create(UsuarioNombre usuarioNombre, UsuarioApellido usuarioApellido, UsuarioUsername usuarioUsername, UsuarioContrasena usuarioContrasena, UsuarioCorreo usuarioCorreo, UsuarioRol usuarioRol) {

        boolean existe = this.usuarioRepositoryPort.existsByUsername(usuarioUsername);
        boolean correo = this.usuarioRepositoryPort.existsByEmail(usuarioCorreo);
        if(existe||correo){
            throw new IllegalArgumentException("Ese nombre de usuario o correo ya existe");
        }

        String hashGenerado = passwordEncoder.encode(usuarioContrasena.getValor());
        UsuarioContrasenaHash usuarioContrasenaHash = new UsuarioContrasenaHash(hashGenerado);


        Usuario usuario = new Usuario(usuarioNombre,usuarioApellido,usuarioUsername,usuarioContrasenaHash,
                usuarioCorreo, usuarioRol);

        return this.usuarioRepositoryPort.save(usuario);
    }
}
