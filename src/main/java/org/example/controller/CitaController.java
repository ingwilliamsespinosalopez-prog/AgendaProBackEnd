package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import org.example.model.Cita;
import org.example.service.CitaService;
import java.util.List;

public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    public void listarMisCitas(Context ctx) {
        try {
            Integer idCliente = ctx.attribute("usuarioId");
            if (idCliente == null) {
                throw new ForbiddenResponse("No autorizado");
            }
            List<Cita> citas = service.obtenerPorCliente(idCliente);
            ctx.json(citas);
        } catch (ForbiddenResponse e) {
            ctx.status(403).json(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).json("Error al listar citas: " + e.getMessage());
        }
    }

    public void listarTodas(Context ctx) {
        try {
            List<Cita> citas = service.listarTodas();
            ctx.json(citas);
        } catch (Exception e) {
            ctx.status(500).json("Error al listar citas: " + e.getMessage());
        }
    }

    public void listarPorEstado(Context ctx) {
        try {
            String estadoParam = ctx.pathParam("estado");
            int idEstado = Integer.parseInt(estadoParam);
            List<Cita> citas = service.listarPorEstado(idEstado);
            ctx.json(citas);
        } catch (NumberFormatException e) {
            ctx.status(400).json("Estado inválido");
        } catch (Exception e) {
            ctx.status(500).json("Error al listar citas por estado: " + e.getMessage());
        }
    }

    public void listarPorCliente(Context ctx) {
        try {
            String clienteParam = ctx.pathParam("idCliente");
            int idCliente = Integer.parseInt(clienteParam);
            List<Cita> citas = service.obtenerPorCliente(idCliente);
            ctx.json(citas);
        } catch (NumberFormatException e) {
            ctx.status(400).json("ID de cliente inválido");
        } catch (Exception e) {
            ctx.status(500).json("Error al listar citas del cliente: " + e.getMessage());
        }
    }

    public void listarPorAsesor(Context ctx) {
        try {
            String asesorParam = ctx.pathParam("idAsesor");
            int idAsesor = Integer.parseInt(asesorParam);
            List<Cita> citas = service.listarPorAsesor(idAsesor);
            ctx.json(citas);
        } catch (NumberFormatException e) {
            ctx.status(400).json("ID de asesor inválido");
        } catch (Exception e) {
            ctx.status(500).json("Error al listar citas del asesor: " + e.getMessage());
        }
    }
}