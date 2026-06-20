package com.github.project;

import java.util.ArrayList;
import java.util.List;


public class Ruta {

    // ==========================
    // Atributos
    // ==========================

    private String idRuta;
    private String nombreDeRuta;
    private String puntoDeSalida;
    private String puntoDeLlegada;
    private String horaDeSalida;
    private String horaDeLlegada;
    private int duracionEstimadaMinutos;
    private double distanciaTotalKm;
    private List<PuntoControl> puntosDeControl;

    // ==========================
    // Constructores
    // ==========================

    public Ruta(String idRuta,
                String nombreDeRuta,
                String puntoDeSalida,
                String puntoDeLlegada,
                String horaDeSalida,
                String horaDeLlegada,
                int duracionEstimadaMinutos,
                double distanciaTotalKm) {

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

    // ==========================
    // Getters y Setters
    // ==========================

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreDeRuta() {
        return nombreDeRuta;
    }

    public void setNombreDeRuta(String nombreDeRuta) {
        this.nombreDeRuta = nombreDeRuta;
    }

    public String getPuntoDeSalida() {
        return puntoDeSalida;
    }

    public void setPuntoDeSalida(String puntoDeSalida) {
        this.puntoDeSalida = puntoDeSalida;
    }

    public String getPuntoDeLlegada() {
        return puntoDeLlegada;
    }

    public void setPuntoDeLlegada(String puntoDeLlegada) {
        this.puntoDeLlegada = puntoDeLlegada;
    }

    public String getHoraDeSalida() {
        return horaDeSalida;
    }

    public void setHoraDeSalida(String horaDeSalida) {
        this.horaDeSalida = horaDeSalida;
    }

    public String getHoraDeLlegada() {
        return horaDeLlegada;
    }

    public void setHoraDeLlegada(String horaDeLlegada) {
        this.horaDeLlegada = horaDeLlegada;
    }

    public int getDuracionEstimadaMinutos() {
        return duracionEstimadaMinutos;
    }

    public void setDuracionEstimadaMinutos(int duracionEstimadaMinutos) {
        this.duracionEstimadaMinutos = duracionEstimadaMinutos;
    }

    public double getDistanciaTotalKm() {
        return distanciaTotalKm;
    }

    public void setDistanciaTotalKm(double distanciaTotalKm) {
        this.distanciaTotalKm = distanciaTotalKm;
    }

    public List<PuntoControl> getPuntosDeControl() {
        return puntosDeControl;
    }

    public void setPuntosDeControl(List<PuntoControl> puntosDeControl) {
        this.puntosDeControl = puntosDeControl;
    }

    // ==========================
    // Métodos
    // ==========================

    public void registrarPasoPorControl(String idControl,
                                        String horaReal) {

        for (PuntoControl control : puntosDeControl) {

            if (control.getIdControl().equals(idControl)) {
                control.setHoraRealDePaso(horaReal);
                control.setSuperado(true);
            }
        }
    }

    public int calcularRetraso(String idControl) {

        
        return 0;
    }

    public boolean todosLosControlesSuperados() {

        for (PuntoControl control : puntosDeControl) {

            if (!control.isSuperado()) {
                return false;
            }
        }

        return true;
    }

    public String obtenerItinerarioCompleto() {

        StringBuilder itinerario = new StringBuilder();

        itinerario.append("Ruta: ").append(nombreDeRuta)
                  .append("\nSalida: ").append(puntoDeSalida)
                  .append("\nLlegada: ").append(puntoDeLlegada)
                  .append("\nHora de salida: ").append(horaDeSalida)
                  .append("\nHora de llegada: ").append(horaDeLlegada)
                  .append("\nDistancia: ").append(distanciaTotalKm)
                  .append(" km");

        return itinerario.toString();
    }

}