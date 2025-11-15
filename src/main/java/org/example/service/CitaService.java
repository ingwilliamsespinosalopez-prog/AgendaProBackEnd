package org.example.service;

import org.example.model.Cita;
import org.example.repository.CitaRepository;
import java.util.List;

public class CitaService {

    private final CitaRepository repository = new CitaRepository();

    public Integer crearCita(Cita c) {

        boolean ocupado = repository.existeEmpalme(
                c.getIdAsesor(),
                c.getFechaCita(),
                c.getHoraCita()
        );

        if (ocupado) return null;

        return repository.crear(c);
    }

    public List<Cita> obtenerPorCliente(int idCliente) {
        return repository.listarPorCliente(idCliente);
    }

    public Cita obtenerPorId(int idCita) {
        return repository.obtenerPorId(idCita);
    }

    public boolean actualizarEstado(int idCita, int nuevoEstado) {
        try {
            return repository.actualizarEstado(idCita, nuevoEstado) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean marcarPagado(int idCita) {
        return repository.marcarPagado(idCita);
    }

    public String cambiarEstado(int idCita, String accion) {
        return repository.cambiarEstado(idCita, accion);
    }

    public String asignarAsesor(int idCita, int idAsesor) {
        boolean ok = repository.asignarAsesor(idCita, idAsesor);
        return ok ? "Asesor asignado correctamente" : "No se pudo asignar";
    }
}
