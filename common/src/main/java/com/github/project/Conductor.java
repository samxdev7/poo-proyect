package com.github.project;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un conductor de la cooperativa.
 */
public class Conductor extends Usuario {

    /**
     * Número de licencia del conductor.
     * Actúa como identificador.
     */
    private String licenciaDeConducir;

    /**
     * Indica si el conductor está disponible.
     */
    private boolean disponible;

    /**
     * Historial de jornadas realizadas.
     */
    private List<Jornada> jornadasRealizadas;

    /**
     * Lista de infracciones registradas.
     */
    private List<Infraccion> infracciones;

    /**
     * Constructor de la clase Conductor.
     *
     * @param licenciaDeConducir Licencia del conductor.
     * @param disponible Estado de disponibilidad.
     */
    public Conductor(String licenciaDeConducir, boolean disponible) {
        this.licenciaDeConducir = licenciaDeConducir;
        this.disponible = disponible;
        this.jornadasRealizadas = new ArrayList<>();
        this.infracciones = new ArrayList<>();
    }

    /**
     * Reporta la finalización de un viaje.
     *
     * @param idJornada Jornada asociada.
     * @param km Kilómetros recorridos.
     * @param pasajeros Cantidad de pasajeros transportados.
     */
    public void reportarViajeCompletado(String idJornada, double km, int pasajeros) {
        // TODO
    }

    /**
     * Registra la cantidad de viajes realizados
     * durante una jornada.
     */
    public void registrarCantidadDeViajesRealizadosEnJornada() {
        // TODO
    }

    /**
     * Verifica si el conductor posee
     * infracciones de nivel grave.
     *
     * @return true si tiene al menos una infracción grave.
     */
    public boolean tieneInfraccionesGraves() {

        for (Infraccion infraccion : infracciones) {

            if (infraccion.getNivel().equalsIgnoreCase("GRAVE")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Genera un resumen de la información
     * principal del conductor.
     *
     * @return Información del conductor.
     */
    public String mostrarInfoDeConductor() {

        return "Licencia: " + licenciaDeConducir +
               "\nDisponible: " + disponible +
               "\nJornadas realizadas: " + jornadasRealizadas.size() +
               "\nInfracciones registradas: " + infracciones.size();
    }
}