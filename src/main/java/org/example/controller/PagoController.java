package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Pago;
import org.example.service.PagoService;
import java.util.List;

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

    public void listarTodos(Context ctx) {
        try {
            List<Pago> pagos = pagoService.listarTodos();
            ctx.json(pagos);
        } catch (Exception e) {
            System.err.println("Error en listarPagos: " + e.getMessage());
            ctx.status(500).json("Error al listar pagos: " + e.getMessage());
        }
    }
}