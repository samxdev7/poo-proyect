package com.github.project;
/**
 * Representa una infracción registrada durante una jornada.
 */
public class Infraccion {

    /**
     * Identificador único de la infracción.
     */
    private String idInfraccion;

    /**
     * Descripción detallada de la infracción.
     */
    private String descripcion;

    /**
     * Nivel de gravedad de la infracción.
     */
    private String nivelDeInfraccion;

    /**
     * Fecha y hora en que fue registrada.
     */
    private String fechaYHoraDeRegistro;

    /**
     * Jornada relacionada con la infracción.
     */
    private String idJornada;

    /**
     * Monitor que reportó la infracción.
     */
    private String monitorQueReporto;

    /**
     * Constructor de la clase Infraccion.
     */
    public Infraccion(String idInfraccion,
                      String descripcion,
                      String nivelDeInfraccion,
                      String fechaYHoraDeRegistro,
                      String idJornada,
                      String monitorQueReporto) {

        this.idInfraccion = idInfraccion;
        this.descripcion = descripcion;
        this.nivelDeInfraccion = nivelDeInfraccion;
        this.fechaYHoraDeRegistro = fechaYHoraDeRegistro;
        this.idJornada = idJornada;
        this.monitorQueReporto = monitorQueReporto;
    }

    /**
     * Devuelve el nivel de gravedad de la infracción.
     *
     * @return Nivel de infracción.
     */
    public String mostrarNivelDeInfraccion() {
        return nivelDeInfraccion;
    }

    /**
     * Devuelve todos los detalles de la infracción.
     *
     * @return Información completa de la infracción.
     */
    public String mostrarDetallesDeInfraccion() {

        return "ID: " + idInfraccion +
               "\nDescripción: " + descripcion +
               "\nNivel: " + nivelDeInfraccion +
               "\nFecha y Hora: " + fechaYHoraDeRegistro +
               "\nJornada: " + idJornada +
               "\nMonitor: " + monitorQueReporto;
    }

    public String getNivel() {
        return nivelDeInfraccion;
    }

    public String getIdInfraccion() {
        return idInfraccion;
    }
}