package com.github.project.model;

public class VehiculoDiesel extends Vehiculo {

    private String idVehiculoDiesel;
    private double capacidadDeTanque;
    private double nivelDeCombustible;

    /**
     * Constructor para inicializar un vehículo de motor diésel.
     *
     * @param placa El número de placa del vehículo.
     * @param marca La marca fabricante.
     * @param modelo El modelo del vehículo.
     * @param tipoCombustible El tipo de combustible.
     * @param estadoDeVehiculo El estado operativo actual.
     * @param idVehiculoDiesel El identificador específico del vehículo diésel.
     * @param capacidadDeTanque La capacidad máxima del tanque en litros.
     * @param nivelDeCombustible El nivel de combustible actual.
     */
    public VehiculoDiesel(String placa, String marca, String modelo,
            String tipoCombustible, String estadoDeVehiculo,
            String idVehiculoDiesel, double capacidadDeTanque, double nivelDeCombustible) {
        super(placa, marca, modelo, tipoCombustible, estadoDeVehiculo);
        this.idVehiculoDiesel = idVehiculoDiesel;
        this.capacidadDeTanque = capacidadDeTanque;
        this.nivelDeCombustible = nivelDeCombustible;
    }

    /**
     * Devuelve la cantidad exacta de combustible disponible como representación de su autonomía.
     * Al no existir una métrica de rendimiento (km/L) en el diseño UML, la autonomía
     * real funcional se mide por su capacidad restante neta en litros.
     *
     * @return La autonomía expresada en litros.
     */
    public double calcularAutonomiaRestante() {
        return nivelDeCombustible;
    }

    public String getIdVehiculoDiesel() { return idVehiculoDiesel; }
    public void setIdVehiculoDiesel(String idVehiculoDiesel) { this.idVehiculoDiesel = idVehiculoDiesel; }

    public double getCapacidadDeTanque() { return capacidadDeTanque; }
    public void setCapacidadDeTanque(double capacidadDeTanque) { this.capacidadDeTanque = capacidadDeTanque; }

    public double getNivelDeCombustible() { return nivelDeCombustible; }
    public void setNivelDeCombustible(double nivelDeCombustible) { this.nivelDeCombustible = nivelDeCombustible; }
}
