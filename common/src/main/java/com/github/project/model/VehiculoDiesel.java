package com.github.project;


public class VehiculoDiesel extends Vehiculo {

     // Atributos
    private String idVehiculoDiesel;
    private double capacidadDeTanque;
    private double nivelDeCombustible;

    // Constructor
    public VehiculoDiesel(String placa, String marca, String modelo,
            String tipoCombustible, String estadoDeVehiculo,
            String idVehiculoDiesel,
            double capacidadDeTanque,
            double nivelDeCombustible) {

        super(placa, marca, modelo, tipoCombustible, estadoDeVehiculo);

        this.idVehiculoDiesel = idVehiculoDiesel;
        this.capacidadDeTanque = capacidadDeTanque;
        this.nivelDeCombustible = nivelDeCombustible;
    }

    // Getters

    public String getIdVehiculoDiesel() {
        return idVehiculoDiesel;
    }

    public double getCapacidadDeTanque() {
        return capacidadDeTanque;
    }

    public double getNivelDeCombustible() {
        return nivelDeCombustible;
    }

    // Setters

    public void setIdVehiculoDiesel(String idVehiculoDiesel) {
        this.idVehiculoDiesel = idVehiculoDiesel;
    }

    public void setCapacidadDeTanque(double capacidadDeTanque) {
        this.capacidadDeTanque = capacidadDeTanque;
    }

    public void setNivelDeCombustible(double nivelDeCombustible) {
        this.nivelDeCombustible = nivelDeCombustible;
    }
}
