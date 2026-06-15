package com.github.project;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un monitor encargado de supervisar
 * puntos de control en una jornada.
 */
public class Monitor extends Usuario {

    /**
     * Identificador único del monitor.
     */
    private String idMonitor;

    /**
     * Lista de puntos de control asignados.
     */
    private List<PuntoControl> puntosDeControlAsignados;

    /**
     * Lista de puntos de control ya marcados.
     */
    private List<PuntoControl> puntosDeControlMarcados;

    /**
     * Constructor de la clase Monitor.
     *
     * @param idMonitor Identificador del monitor.
     */
    public Monitor(String idMonitor) {
        this.idMonitor = idMonitor;
        this.puntosDeControlAsignados = new ArrayList<>();
        this.puntosDeControlMarcados = new ArrayList<>();
    }

    /**
     * Obtiene el identificador del monitor.
     *
     * @return id del monitor.
     */
    public String getIdMonitor() {
        return idMonitor;
    }

    /**
     * Modifica el identificador del monitor.
     *
     * @param idMonitor Nuevo identificador.
     */
    public void setIdMonitor(String idMonitor) {
        this.idMonitor = idMonitor;
    }

    /**
     * Reporta el resultado de un punto de control.
     *
     * @param idJornada Jornada evaluada.
     * @param idControl Punto de control evaluado.
     */
    public void reportarResultadoDePuntoDeControl(String idJornada, String idControl) {
        // TODO
    }

    /**
     * Registra una infracción cometida por un conductor.
     *
     * @param licenciaDeConducir Licencia del conductor.
     * @param nivel Nivel de la infracción.
     * @param descripcion Descripción.
     * @param idJornada Jornada donde ocurrió.
     * @param idControl Punto de control relacionado.
     * @return Infracción registrada.
     */
    public Infraccion registrarInfraccion(
            String licenciaDeConducir,
            String nivel,
            String descripcion,
            String idJornada,
            String idControl) {

        return null;
    }

    /**
     * Marca un punto de control como visitado.
     *
     * @param idControl Identificador del punto.
     * @param horaReal Hora real de llegada.
     */
    public void marcarPuntoDeControl(String idControl, String horaReal) {
        // TODO
    }

    /**
     * Calcula el retraso de llegada a un punto de control.
     *
     * @param idControl Punto de control.
     * @return Minutos de retraso.
     */
    public int calcularRetrasoDePuntoDeControlMinutos(String idControl) {
        return 0;
    }
}