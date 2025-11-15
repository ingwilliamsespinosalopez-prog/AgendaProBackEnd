package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.CitaController;

public class RoutesCita {

    public static void register(Javalin app) {

        app.post("/cita", CitaController.crear);
        app.get("/cita/cliente/{id}", CitaController.listarPorCliente);
        app.get("/cita/{id}", CitaController.obtenerPorId);
        app.put("/cita/estado/{id}", CitaController.actualizarEstado);
        app.put("/cita/pago/{id}", CitaController.marcarPagado);
        app.put("/cita/cambiarEstado/{id}", CitaController.cambiarEstado);
        app.put("/cita/asignar/{id}", CitaController.asignarAsesor);
    }
}
