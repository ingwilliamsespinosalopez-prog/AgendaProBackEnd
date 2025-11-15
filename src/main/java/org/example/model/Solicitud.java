package org.example.model;

public class Solicitud {
    public int idCita;
    public String cliente;
    public String servicio;
    public String fechaCita;
    public String horaCita;
    public String modalidad;
    public String estado;
    public String asesor;
    public String notas;

    public Solicitud(int idCita, String cliente, String servicio, String fechaCita, String horaCita, String modalidad, String asesor, String notas) {
        this.idCita = idCita;
        this.cliente = cliente;
        this.servicio = servicio;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.modalidad = modalidad;
        this.asesor = asesor;
        this.notas = notas;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
