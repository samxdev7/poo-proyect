package com.github.project.model;

public abstract class Vehiculo {

    private String placa;
    private String marca;
    private String modelo;
    private String tipoCombustible;
    private String estadoDeVehiculo;

    /**
     * Constructor principal para inicializar los datos base de un vehículo.
     *
     * @param placa El número de placa del vehículo.
     * @param marca La marca fabricante.
     * @param modelo El modelo del vehículo.
     * @param tipoCombustible El tipo de combustible que utiliza.
     * @param estadoDeVehiculo El estado actual.
     */
    public Vehiculo(String placa, String marca, String modelo,
            String tipoCombustible, String estadoDeVehiculo) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoCombustible = tipoCombustible;
        this.estadoDeVehiculo = estadoDeVehiculo;
    }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTipoCombustible() { return tipoCombustible; }
    public void setTipoCombustible(String tipoCombustible) { this.tipoCombustible = tipoCombustible; }

    public String getEstadoDeVehiculo() { return estadoDeVehiculo; }
    public void setEstadoDeVehiculo(String estadoDeVehiculo) { this.estadoDeVehiculo = estadoDeVehiculo; }
}
