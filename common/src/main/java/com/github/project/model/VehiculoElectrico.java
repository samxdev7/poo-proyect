package com.github.project.model;

public class VehiculoElectrico extends Vehiculo {
    
    private String idVehiculoElectrico;
    private double capacidadBateria;
    private double nivelCarga;
    private int ciclosDeCarga;

    /**
     * Constructor para inicializar un vehículo de motor eléctrico.
     *
     * @param placa El número de placa del vehículo.
     * @param marca La marca fabricante.
     * @param modelo El modelo del vehículo.
     * @param tipoCombustible El tipo de energía o combustible.
     * @param estadoDeVehiculo El estado operativo actual.
     * @param idVehiculoElectrico El identificador específico del vehículo eléctrico.
     * @param capacidadBateria La capacidad total de la batería.
     * @param nivelCarga El nivel de carga actual.
     * @param ciclosDeCarga La cantidad de ciclos de carga completados.
     */
    public VehiculoElectrico(String placa, String marca, String modelo,
            String tipoCombustible, String estadoDeVehiculo,
            String idVehiculoElectrico, double capacidadBateria, double nivelCarga, int ciclosDeCarga) {
        super(placa, marca, modelo, tipoCombustible, estadoDeVehiculo);
        this.idVehiculoElectrico = idVehiculoElectrico;
        this.capacidadBateria = capacidadBateria;
        this.nivelCarga = nivelCarga;
        this.ciclosDeCarga = ciclosDeCarga;
    }

    /**
     * Estima la autonomía restante basándose en la capacidad de la batería y el nivel actual de carga.
     *
     * @return La estimación de autonomía restante.
     */
    public double calcularAutonomiaRestante() {
        return (capacidadBateria * nivelCarga) / 100;
    }

    public String getIdVehiculoElectrico() { return idVehiculoElectrico; }
    public void setIdVehiculoElectrico(String idVehiculoElectrico) { this.idVehiculoElectrico = idVehiculoElectrico; }

    public double getCapacidadBateria() { return capacidadBateria; }
    public void setCapacidadBateria(double capacidadBateria) { this.capacidadBateria = capacidadBateria; }

    public double getNivelCarga() { return nivelCarga; }
    public void setNivelCarga(double nivelCarga) { this.nivelCarga = nivelCarga; }

    public int getCiclosDeCarga() { return ciclosDeCarga; }
    public void setCiclosDeCarga(int ciclosDeCarga) { this.ciclosDeCarga = ciclosDeCarga; }
}
