package com.example.paqueteria.application.port.in.oficina;

import com.example.paqueteria.domain.entity.Oficina;
import com.example.paqueteria.domain.valueobjects.OficinaCodigo;
import com.example.paqueteria.domain.valueobjects.OficinaDireccion;
import com.example.paqueteria.domain.valueobjects.OficinaNombre;

import java.util.UUID;

public interface CreateOficinaUseCase {

    Oficina create(OficinaCodigo codigo, OficinaNombre nombre, OficinaDireccion oficinaDireccion, UUID provinciaId);
}
