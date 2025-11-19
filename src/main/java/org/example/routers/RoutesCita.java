package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.CitaController;
import org.example.config.AuthMiddleware;

public class RoutesCita {

    private final CitaController controller;

    public RoutesCita(CitaController controller) {
        this.controller = controller;
    }

    public void register(Javalin app) {
        app.before("/admin/cita/*", AuthMiddleware.requireAdmin);
        app.get("/admin/cita/listar", controller::listarTodas);

        app.before("/cliente/cita/*", AuthMiddleware.requireCliente);
        app.get("/cliente/cita/mias", controller::listarMisCitas);
        
        app.get("/cita/estado/{estado}", controller::listarPorEstado);
        app.get("/cita/asesor/{idAsesor}", controller::listarPorAsesor);
        app.get("/cita/cliente/{idCliente}", controller::listarPorCliente);
    }
}