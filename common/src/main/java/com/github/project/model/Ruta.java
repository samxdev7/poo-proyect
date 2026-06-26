package com.github.project.model;

import java.util.ArrayList;
import java.util.List;

public class Ruta {

    private String idRuta;
    private String nombreDeRuta;
    private String puntoDeSalida;
    private String puntoDeLlegada;
    private String horaDeSalida;
    private String horaDeLlegada;
    private int duracionEstimadaMinutos;
    private double distanciaTotalKm;
    private List<PuntoControl> puntosDeControl;

    public Ruta(String idRuta, String nombreDeRuta, String puntoDeSalida, String puntoDeLlegada,
                String horaDeSalida, String horaDeLlegada, int duracionEstimadaMinutos, double distanciaTotalKm) {
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
     * 1. Itera sobre los puntos de control asignados.
     * 2. Si coincide el id, actualiza la hora y marca superado.
     */
    public void registrarPasoPorControl(String idControl, String horaReal) {
        for (int i = 0; i < puntosDeControl.size(); i++) {
            PuntoControl control = puntosDeControl.get(i);
            if (control.getIdControl().equals(idControl)) {
                control.setHoraRealDePaso(horaReal);
                control.setSuperado(true);
                break;
            }
        }
    }

    /**
     * 1. Simula el cálculo del retraso comparando la hora.
     */
    public int calcularRetraso(String idControl) {
        for (PuntoControl pc : puntosDeControl) {
            if (pc.getIdControl().equals(idControl)) {
                String programada = pc.getHoraProgramada();
                String real = pc.getHoraRealDePaso();
                if (programada == null || real == null || real.equals("NO MARCADO")) {
                    return 0; 
                }
                try {
                    String[] pParts = programada.split(":");
                    String[] rParts = real.split(":");
                    int pMin = Integer.parseInt(pParts[0].trim()) * 60 + Integer.parseInt(pParts[1].trim());
                    int rMin = Integer.parseInt(rParts[0].trim()) * 60 + Integer.parseInt(rParts[1].trim());
                    return rMin - pMin;
                } catch (Exception e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * 1. Itera sobre los puntos de control asignados.
     * 2. Si encuentra alguno que no esté superado, retorna false.
     * 3. Si termina el ciclo, retorna true.
     */
    public boolean todosLosControlesSuperados() {
        for (int i = 0; i < puntosDeControl.size(); i++) {
            if (!puntosDeControl.get(i).isSuperado()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 1. Retorna el itinerario concatenado de la ruta.
     */
    public String obtenerItinerarioCompleto() {
        return "Ruta: " + nombreDeRuta
             + "\nSalida: " + puntoDeSalida
             + "\nLlegada: " + puntoDeLlegada
             + "\nHora de salida: " + horaDeSalida
             + "\nHora de llegada: " + horaDeLlegada
             + "\nDistancia: " + distanciaTotalKm + " km";
    }

    public String getIdRuta() { return idRuta; }
    public void setIdRuta(String idRuta) { this.idRuta = idRuta; }

    public String getNombreDeRuta() { return nombreDeRuta; }
    public void setNombreDeRuta(String nombreDeRuta) { this.nombreDeRuta = nombreDeRuta; }

    public String getPuntoDeSalida() { return puntoDeSalida; }
    public void setPuntoDeSalida(String puntoDeSalida) { this.puntoDeSalida = puntoDeSalida; }

    public String getPuntoDeLlegada() { return puntoDeLlegada; }
    public void setPuntoDeLlegada(String puntoDeLlegada) { this.puntoDeLlegada = puntoDeLlegada; }

    public String getHoraDeSalida() { return horaDeSalida; }
    public void setHoraDeSalida(String horaDeSalida) { this.horaDeSalida = horaDeSalida; }

    public String getHoraDeLlegada() { return horaDeLlegada; }
    public void setHoraDeLlegada(String horaDeLlegada) { this.horaDeLlegada = horaDeLlegada; }

    public int getDuracionEstimadaMinutos() { return duracionEstimadaMinutos; }
    public void setDuracionEstimadaMinutos(int duracionEstimadaMinutos) { this.duracionEstimadaMinutos = duracionEstimadaMinutos; }

    public double getDistanciaTotalKm() { return distanciaTotalKm; }
    public void setDistanciaTotalKm(double distanciaTotalKm) { this.distanciaTotalKm = distanciaTotalKm; }

    public List<PuntoControl> getPuntosDeControl() { return puntosDeControl; }
    public void setPuntosDeControl(List<PuntoControl> puntosDeControl) { this.puntosDeControl = puntosDeControl; }
}
