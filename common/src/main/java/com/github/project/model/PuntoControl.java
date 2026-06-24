package com.github.project.model;

public class PuntoControl {

    private String idControl;
    private String ubicacion;
    private String horaProgramada;
    private String horaRealDePaso;
    private boolean superado;
    private Monitor monitorACargo;

    public PuntoControl(String idControl, String ubicacion, String horaProgramada, Monitor monitorACargo) {
        this.idControl = idControl;
        this.ubicacion = ubicacion;
        this.horaProgramada = horaProgramada;
        this.monitorACargo = monitorACargo;
        this.superado = false;
    }

    /**
     * 1. Retorna los detalles informativos del punto de control.
     */
    public String mostrarInformacionDePuntoDeControl() {
        return "ID: " + idControl +
               "\nUbicación: " + ubicacion +
               "\nHora Programada: " + horaProgramada +
               "\nHora Real: " + horaRealDePaso +
               "\nSuperado: " + superado;
    }

    public String getIdControl() { return idControl; }
    public void setIdControl(String idControl) { this.idControl = idControl; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getHoraProgramada() { return horaProgramada; }
    public void setHoraProgramada(String horaProgramada) { this.horaProgramada = horaProgramada; }

    public String getHoraRealDePaso() { return horaRealDePaso; }
    public void setHoraRealDePaso(String horaRealDePaso) { this.horaRealDePaso = horaRealDePaso; }

    public boolean isSuperado() { return superado; }
    public void setSuperado(boolean superado) { this.superado = superado; }

    public Monitor getMonitorACargo() { return monitorACargo; }
    public void setMonitorACargo(Monitor monitorACargo) { this.monitorACargo = monitorACargo; }
}
