
package com.mycompany.avancespoo;


public class VehiculoElectrico extends Vehiculo {
    
    // Atributos
    private String idVehiculoElectrico;
    private double capacidadBateria;
    private double nivelCarga;
    private int ciclosDeCarga;

    // Constructor
    public VehiculoElectrico(String placa, String marca, String modelo,
            String tipoCombustible, String estadoDeVehiculo,
            String idVehiculoElectrico,
            double capacidadBateria,
            double nivelCarga,
            int ciclosDeCarga) {

        super(placa, marca, modelo, tipoCombustible, estadoDeVehiculo);

        this.idVehiculoElectrico = idVehiculoElectrico;
        this.capacidadBateria = capacidadBateria;
        this.nivelCarga = nivelCarga;
        this.ciclosDeCarga = ciclosDeCarga;
    }

    // Método del diagrama
    public double calcularAutonomiaRestante() {
        return (capacidadBateria * nivelCarga) / 100;
    }

    // Getters

    public String getIdVehiculoElectrico() {
        return idVehiculoElectrico;
    }

    public double getCapacidadBateria() {
        return capacidadBateria;
    }

    public double getNivelCarga() {
        return nivelCarga;
    }

    public int getCiclosDeCarga() {
        return ciclosDeCarga;
    }

    // Setters

    public void setIdVehiculoElectrico(String idVehiculoElectrico) {
        this.idVehiculoElectrico = idVehiculoElectrico;
    }

    public void setCapacidadBateria(double capacidadBateria) {
        this.capacidadBateria = capacidadBateria;
    }

    public void setNivelCarga(double nivelCarga) {
        this.nivelCarga = nivelCarga;
    }

    public void setCiclosDeCarga(int ciclosDeCarga) {
        this.ciclosDeCarga = ciclosDeCarga;
    }
}
