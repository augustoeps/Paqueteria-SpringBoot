package com.example.paqueteria.application.port.in.usuario;

import java.util.UUID;

public interface DeleteUsuarioUseCase {

    boolean delete(UUID id);
}
