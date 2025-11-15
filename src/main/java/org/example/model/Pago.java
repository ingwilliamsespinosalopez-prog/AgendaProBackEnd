package org.example.model;


public class Pago {
    private int idPago;
    private int idCita;
    private double monto;
    private String idTransaccion;
    private int idEstadoPago;
    private int idMetodoPago;
    private String fechaPago;

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(String idTransaccion) { this.idTransaccion = idTransaccion; }

    public int getIdEstadoPago() { return idEstadoPago; }
    public void setIdEstadoPago(int idEstadoPago) { this.idEstadoPago = idEstadoPago; }

    public int getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(int idMetodoPago) { this.idMetodoPago = idMetodoPago; }

    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String fechaPago) { this.fechaPago = fechaPago; }
}

