package org.example.service;

import org.example.model.Pago;
import org.example.repository.PagoRepository;

public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Pago registrarPago(Pago pago) throws Exception {
        return pagoRepository.registrarPago(pago);
    }
}
