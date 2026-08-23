package com.example.paqueteria.application.port.in.usuario;

import com.example.paqueteria.domain.entity.Usuario;

import java.util.List;

public interface FindAllUsuarioUseCase {

    List<Usuario> findAll();
}
