
package com.mycompany.avancespoo;


public class Vehiculo {

    // Atributos
    private String placa;
    private String marca;
    private String modelo;
    private String tipoCombustible;
    private String estadoDeVehiculo;

    // Constructor
    public Vehiculo(String placa, String marca, String modelo,
            String tipoCombustible, String estadoDeVehiculo) {

        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoCombustible = tipoCombustible;
        this.estadoDeVehiculo = estadoDeVehiculo;
    }

    // Getters

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public String getEstadoDeVehiculo() {
        return estadoDeVehiculo;
    }

    // Setters

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public void setEstadoDeVehiculo(String estadoDeVehiculo) {
        this.estadoDeVehiculo = estadoDeVehiculo;
    }
}
    

