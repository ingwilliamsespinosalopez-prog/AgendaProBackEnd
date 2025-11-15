package org.example.model;

public class Cita {
    private int idCita;
    private int idCliente;
    private int idAsesor;
    private int idServicio;
    private int idEstado;
    private int idModalidad;
    private String fechaCita;
    private String horaCita;
    private boolean pagado;
    private String notas;

    public Cita() {}

    public Cita(int idCita, int idCliente, int idAsesor, int idServicio, int idEstado,
                int idModalidad, String fechaCita, String horaCita,
                boolean pagado, String notas) {

        this.idCita = idCita;
        this.idCliente = idCliente;
        this.idAsesor = idAsesor;
        this.idServicio = idServicio;
        this.idEstado = idEstado;
        this.idModalidad = idModalidad;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.pagado = pagado;
        this.notas = notas;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdAsesor() {
        return idAsesor;
    }

    public void setIdAsesor(int idAsesor) {
        this.idAsesor = idAsesor;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
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

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
