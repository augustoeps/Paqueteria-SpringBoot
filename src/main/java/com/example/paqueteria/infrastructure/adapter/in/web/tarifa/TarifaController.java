package com.example.paqueteria.infrastructure.adapter.in.web.tarifa;

import com.example.paqueteria.application.port.in.tarifa.*;

public class TarifaController {


    private final CreateTarifaUseCase createTarifaUseCase;
    private final DeleteTarifaUseCase deleteTarifaUseCase;
    private final FindTarifaByOrigenAndDestinoUseCase findTarifaByOrigenAndDestinoUseCase;
    private final FindByIdTarifaUseCase findByIdTarifaUseCase;

    public TarifaController(CreateTarifaUseCase createTarifaUseCase, DeleteTarifaUseCase deleteTarifaUseCase, FindTarifaByOrigenAndDestinoUseCase findTarifaByOrigenAndDestinoUseCase, FindByIdTarifaUseCase findByIdTarifaUseCase) {
        this.createTarifaUseCase = createTarifaUseCase;
        this.deleteTarifaUseCase = deleteTarifaUseCase;
        this.findTarifaByOrigenAndDestinoUseCase = findTarifaByOrigenAndDestinoUseCase;
        this.findByIdTarifaUseCase = findByIdTarifaUseCase;
    }

}
