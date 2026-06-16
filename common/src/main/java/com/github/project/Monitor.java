package com.github.project;

import java.util.ArrayList;
import java.util.List;


public class Monitor extends Usuario {

    // ==========================
    // Atributos
    // ==========================

    private String idMonitor;
    private List<PuntoControl> puntosDeControlAsignados;
    private List<PuntoControl> puntosDeControlMarcados;

    // ==========================
    // Constructores
    // ==========================

    public Monitor(
            String idUsuario,
            String nombre,
            String contrasena,
            String tipoUsuario,
            boolean activo,
            Cooperativa cooperativa,
            String idMonitor) {

        super(
                idUsuario,
                nombre,
                contrasena,
                tipoUsuario,
                activo,
                cooperativa);

        this.idMonitor = idMonitor;
        this.puntosDeControlAsignados = new ArrayList<>();
        this.puntosDeControlMarcados = new ArrayList<>();
    }

    // ==========================
    // Getters y Setters
    // ==========================

    public String getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(String idMonitor) {
        this.idMonitor = idMonitor;
    }

    public List<PuntoControl> getPuntosDeControlAsignados() {
        return puntosDeControlAsignados;
    }

    public void setPuntosDeControlAsignados(
            List<PuntoControl> puntosDeControlAsignados) {
        this.puntosDeControlAsignados = puntosDeControlAsignados;
    }

    public List<PuntoControl> getPuntosDeControlMarcados() {
        return puntosDeControlMarcados;
    }

    public void setPuntosDeControlMarcados(
            List<PuntoControl> puntosDeControlMarcados) {
        this.puntosDeControlMarcados = puntosDeControlMarcados;
    }

    // ==========================
    // Métodos
    // ==========================

    public void reportarResultadoDePuntoDeControl(
            String idJornada,
            String idControl) {
        // TODO
    }

    public Infraccion registrarInfraccion(
            String licenciaDeConducir,
            String nivel,
            String descripcion,
            String idJornada,
            String idControl) {

        return null;
    }

    public void marcarPuntoDeControl(
            String idControl,
            String horaReal) {
        // TODO
    }

    public int calcularRetrasoDePuntoDeControlMinutos(
            String idControl) {
        return 0;
    }

}