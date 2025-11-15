package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Pago;
import org.example.service.PagoService;

public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    public void registrar(Context ctx) {
        try {
            Pago pago = ctx.bodyAsClass(Pago.class);

            Pago pagoCreado = pagoService.registrarPago(pago);

            ctx.json(pagoCreado);
            ctx.status(201);

        } catch (Exception e) {
            ctx.status(500).json("Error al registrar pago: " + e.getMessage());
        }
    }
}
