package com.github.project;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una ruta de transporte dentro de la cooperativa.
 * Contiene información sobre horarios, distancia y puntos de control.
 */
public class Ruta {

    /**
     * Identificador único de la ruta.
     */
    private String idRuta;

    /**
     * Nombre de la ruta.
     */
    private String nombreDeRuta;

    /**
     * Punto donde inicia la ruta.
     */
    private String puntoDeSalida;

    /**
     * Punto donde finaliza la ruta.
     */
    private String puntoDeLlegada;

    /**
     * Hora programada de salida.
     */
    private String horaDeSalida;

    /**
     * Hora programada de llegada.
     */
    private String horaDeLlegada;

    /**
     * Duración estimada del recorrido en minutos.
     */
    private int duracionEstimadaMinutos;

    /**
     * Distancia total de la ruta en kilómetros.
     */
    private double distanciaTotalKm;

    /**
     * Lista de puntos de control asociados a la ruta.
     */
    private List<PuntoControl> puntosDeControl;

    /**
     * Constructor de la clase Ruta.
     */
    public Ruta(String idRuta, String nombreDeRuta,
                String puntoDeSalida, String puntoDeLlegada,
                String horaDeSalida, String horaDeLlegada,
                int duracionEstimadaMinutos, double distanciaTotalKm) {

        this.idRuta = idRuta;
        this.nombreDeRuta = nombreDeRuta;
        this.puntoDeSalida = puntoDeSalida;
        this.puntoDeLlegada = puntoDeLlegada;
        this.horaDeSalida = horaDeSalida;
        this.horaDeLlegada = horaDeLlegada;
        this.duracionEstimadaMinutos = duracionEstimadaMinutos;
        this.distanciaTotalKm = distanciaTotalKm;
        this.puntosDeControl = new ArrayList<>();
    }

    /**
     * Registra la hora real de paso por un punto de control.
     *
     * @param idControl Identificador del punto de control.
     * @param horaReal Hora real de paso.
     */
    public void registrarPasoPorControl(String idControl, String horaReal) {

        for (PuntoControl control : puntosDeControl) {

            if (control.getIdControl().equals(idControl)) {
                control.setHoraRealDePaso(horaReal);
                control.setSuperado(true);
            }
        }
    }

    /**
     * Calcula el retraso de un punto de control.
     *
     * @param idControl Identificador del control.
     * @return Minutos de retraso.
     */
    public int calcularRetraso(String idControl) {

        // Implementación pendiente.
        return 0;
    }

    /**
     * Verifica si todos los puntos de control fueron superados.
     *
     * @return true si todos fueron completados.
     */
    public boolean todosLosControlesSuperados() {

        for (PuntoControl control : puntosDeControl) {

            if (!control.isSuperado()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Devuelve toda la información de la ruta.
     *
     * @return Información completa de la ruta.
     */
    public String obtenerItinerarioCompleto() {

        StringBuilder itinerario = new StringBuilder();

        itinerario.append("Ruta: ").append(nombreDeRuta)
                  .append("\nSalida: ").append(puntoDeSalida)
                  .append("\nLlegada: ").append(puntoDeLlegada)
                  .append("\nHora de salida: ").append(horaDeSalida)
                  .append("\nHora de llegada: ").append(horaDeLlegada)
                  .append("\nDistancia: ").append(distanciaTotalKm).append(" km");

        return itinerario.toString();
    }

    public String getIdRuta() {
        return idRuta;
    }

    public List<PuntoControl> getPuntosDeControl() {
        return puntosDeControl;
    }
}