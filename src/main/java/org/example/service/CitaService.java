package org.example.service;

import org.example.model.Cita;
import org.example.repository.CitaRepository;

import java.util.List;
import java.sql.SQLException;

public class CitaService {

    private final CitaRepository repository;

    public CitaService(CitaRepository repository) {
        this.repository = repository;
    }

    public Integer crearCita(Cita cita) {
        try {
            boolean ocupado = repository.existeEmpalme(
                    cita.getIdAsesor(),
                    cita.getFechaCita(),
                    cita.getHoraCita()
            );

            if (ocupado) return null;

            return repository.crear(cita);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cita> listarTodas() {
        try {
            return repository.listarTodas();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Cita> listarPorEstado(int idEstado) {
        try {
            return repository.listarPorEstado(idEstado);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Cita> listarPorAsesor(int idAsesor) {
        try {
            return repository.listarPorAsesor(idAsesor);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Cita> obtenerPorCliente(int idCliente) {
        try {
            return repository.listarPorCliente(idCliente);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Cita obtenerPorId(int idCita) {
        try {
            return repository.obtenerPorId(idCita);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean actualizarEstado(int idCita, int nuevoEstado) {
        try {
            return repository.actualizarEstado(idCita, nuevoEstado) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean marcarPagado(int idCita) {
        try {
            return repository.marcarPagado(idCita);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String cambiarEstado(int idCita, String accion) {
        try {
            return repository.cambiarEstado(idCita, accion);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al cambiar estado";
        }
    }

    public String asignarAsesor(int idCita, int idAsesor) {
        try {
            boolean ok = repository.asignarAsesor(idCita, idAsesor);
            return ok ? "Asesor asignado correctamente" : "No se pudo asignar";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la base de datos al asignar asesor";
        }
    }

    public List<Cita> listarCompletadasYCanceladas() {
        try {
            return repository.listarPorEstados(List.of(3, 4));
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
