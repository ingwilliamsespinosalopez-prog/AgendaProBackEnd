package org.example.controller;

import io.javalin.http.Handler;
import org.example.model.Cita;
import org.example.service.CitaService;
import java.util.Map;

public class CitaController {

    private static final CitaService service = new CitaService();

    public static Handler crear = ctx -> {
        Cita cita = ctx.bodyAsClass(Cita.class);

        Integer idGenerado = service.crearCita(cita);

        if (idGenerado == null || idGenerado <= 0) {
            ctx.json(Map.of("error", "Error al crear cita"));
            return;
        }

        ctx.json(Map.of(
                "msg", "Cita creada exitosamente",
                "idCita", idGenerado
        ));
    };

    public static Handler listarPorCliente = ctx -> {
        int idCliente = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(service.obtenerPorCliente(idCliente));
    };

    public static Handler obtenerPorId = ctx -> {
        int idCita = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(service.obtenerPorId(idCita));
    };

    public static Handler actualizarEstado = ctx -> {
        int idCita = Integer.parseInt(ctx.pathParam("id"));
        Cita body = ctx.bodyAsClass(Cita.class);

        boolean ok = service.actualizarEstado(idCita, body.getIdEstado());
        ctx.json(ok ? Map.of("msg", "Estado actualizado") : Map.of("error", "Error"));
    };

    public static Handler marcarPagado = ctx -> {
        int idCita = Integer.parseInt(ctx.pathParam("id"));
        boolean ok = service.marcarPagado(idCita);
        ctx.json(ok ? Map.of("msg", "Pago registrado") : Map.of("error", "Error"));
    };

    public static Handler cambiarEstado = ctx -> {
        int idCita = Integer.parseInt(ctx.pathParam("id"));
        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        String accion = body.get("accion").toString();

        String mensaje = service.cambiarEstado(idCita, accion);

        ctx.json(Map.of("status", "success", "message", mensaje));
    };

    public static Handler asignarAsesor = ctx -> {
        int idCita = Integer.parseInt(ctx.pathParam("id"));
        Map<String, Object> body = ctx.bodyAsClass(Map.class);

        int idAsesor = Integer.parseInt(body.get("idAsesor").toString());

        String mensaje = service.asignarAsesor(idCita, idAsesor);

        ctx.json(Map.of("status", "success", "message", mensaje));
    };
}
