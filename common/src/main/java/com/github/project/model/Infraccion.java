package com.github.project.model;

public class Infraccion {

    private String idInfraccion;
    private String descripcion;
    private String nivelDeInfraccion;
    private String fechaYHoraDeRegistro;
    private String idJornada;
    private String monitorQueReporto;

    public Infraccion(String idInfraccion, String descripcion, String nivelDeInfraccion,
                      String fechaYHoraDeRegistro, String idJornada, String monitorQueReporto) {
        this.idInfraccion = idInfraccion;
        this.descripcion = descripcion;
        this.nivelDeInfraccion = nivelDeInfraccion;
        this.fechaYHoraDeRegistro = fechaYHoraDeRegistro;
        this.idJornada = idJornada;
        this.monitorQueReporto = monitorQueReporto;
    }

    /**
     * 1. Retorna el nivel de la infracción registrado.
     */
    public String mostrarNivelDeInfraccion() {
        return nivelDeInfraccion;
    }

    /**
     * 1. Retorna los detalles formateados de la infracción.
     */
    public String mostrarDetallesDeInfraccion() {
        return "ID: " + idInfraccion +
               "\nDescripción: " + descripcion +
               "\nNivel: " + nivelDeInfraccion +
               "\nFecha y Hora: " + fechaYHoraDeRegistro +
               "\nJornada: " + idJornada +
               "\nMonitor: " + monitorQueReporto;
    }

    public String getIdInfraccion() { return idInfraccion; }
    public void setIdInfraccion(String idInfraccion) { this.idInfraccion = idInfraccion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getNivelDeInfraccion() { return nivelDeInfraccion; }
    public void setNivelDeInfraccion(String nivelDeInfraccion) { this.nivelDeInfraccion = nivelDeInfraccion; }

    public String getFechaYHoraDeRegistro() { return fechaYHoraDeRegistro; }
    public void setFechaYHoraDeRegistro(String fechaYHoraDeRegistro) { this.fechaYHoraDeRegistro = fechaYHoraDeRegistro; }

    public String getIdJornada() { return idJornada; }
    public void setIdJornada(String idJornada) { this.idJornada = idJornada; }

    public String getMonitorQueReporto() { return monitorQueReporto; }
    public void setMonitorQueReporto(String monitorQueReporto) { this.monitorQueReporto = monitorQueReporto; }
}
