package com.github.project;

/**
 * Representa un punto de control dentro de una ruta.
 */
public class PuntoControl {

    /**
     * Identificador único del punto de control.
     */
    private String idControl;

    /**
     * Ubicación física del punto de control.
     */
    private String ubicacion;

    /**
     * Hora programada para pasar por el punto.
     */
    private String horaProgramada;

    /**
     * Hora real en la que se pasó por el punto.
     */
    private String horaRealDePaso;

    /**
     * Indica si el punto fue superado.
     */
    private boolean superado;

    /**
     * Monitor responsable del punto de control.
     */
    private Monitor monitorACargo;

    /**
     * Constructor de la clase PuntoControl.
     */
    public PuntoControl(String idControl,
                        String ubicacion,
                        String horaProgramada,
                        Monitor monitorACargo) {

        this.idControl = idControl;
        this.ubicacion = ubicacion;
        this.horaProgramada = horaProgramada;
        this.monitorACargo = monitorACargo;
        this.superado = false;
    }

    /**
     * Muestra toda la información del punto de control.
     *
     * @return Información del punto.
     */
    public String mostrarInformacionDePuntoDeControl() {

        return "ID: " + idControl +
               "\nUbicación: " + ubicacion +
               "\nHora Programada: " + horaProgramada +
               "\nHora Real: " + horaRealDePaso +
               "\nSuperado: " + superado;
    }

    public String getIdControl() {
        return idControl;
    }

    public String getHoraProgramada() {
        return horaProgramada;
    }

    public String getHoraRealDePaso() {
        return horaRealDePaso;
    }

    public void setHoraRealDePaso(String horaRealDePaso) {
        this.horaRealDePaso = horaRealDePaso;
    }

    public boolean isSuperado() {
        return superado;
    }

    public void setSuperado(boolean superado) {
        this.superado = superado;
    }
}