package com.github.project;


public class Jornada {

    // ==========================
    // Atributos
    // ==========================

    private String numeroDeJornada;
    private String fecha;
    private String horaDeInicioRegistrado;
    private String horaDeFinRegistrado;
    private String estadoDeJornada;

    private Vehiculo vehiculo;
    private Conductor conductor;
    private Ruta ruta;

    private double kilometrosRecorridos;
    private int pasajerosTransportados;
    private int cantidadDeViajes;

    // ==========================
    // Constructores
    // ==========================

    public Jornada() {

    }

    public Jornada(String numeroDeJornada,
                   String fecha,
                   Vehiculo vehiculo,
                   Conductor conductor,
                   Ruta ruta) {

        this.numeroDeJornada = numeroDeJornada;
        this.fecha = fecha;
        this.vehiculo = vehiculo;
        this.conductor = conductor;
        this.ruta = ruta;
    }

    // ==========================
    // Getters y Setters
    // ==========================

    public String getNumeroDeJornada() {
        return numeroDeJornada;
    }

    public void setNumeroDeJornada(String numeroDeJornada) {
        this.numeroDeJornada = numeroDeJornada;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraDeInicioRegistrado() {
        return horaDeInicioRegistrado;
    }

    public void setHoraDeInicioRegistrado(String horaDeInicioRegistrado) {
        this.horaDeInicioRegistrado = horaDeInicioRegistrado;
    }

    public String getHoraDeFinRegistrado() {
        return horaDeFinRegistrado;
    }

    public void setHoraDeFinRegistrado(String horaDeFinRegistrado) {
        this.horaDeFinRegistrado = horaDeFinRegistrado;
    }

    public String getEstadoDeJornada() {
        return estadoDeJornada;
    }

    public void setEstadoDeJornada(String estadoDeJornada) {
        this.estadoDeJornada = estadoDeJornada;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public double getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(double kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public int getPasajerosTransportados() {
        return pasajerosTransportados;
    }

    public void setPasajerosTransportados(int pasajerosTransportados) {
        this.pasajerosTransportados = pasajerosTransportados;
    }

    public int getCantidadDeViajes() {
        return cantidadDeViajes;
    }

    public void setCantidadDeViajes(int cantidadDeViajes) {
        this.cantidadDeViajes = cantidadDeViajes;
    }

    // ==========================
    // Métodos
    // ==========================

    public void registrarDatosDeJornada(double kmRecorridos,
                                        int pasajerosTransportados) {

        this.kilometrosRecorridos = kmRecorridos;
        this.pasajerosTransportados = pasajerosTransportados;
    }

    public String generarReporteDeJornada() {

        return "Jornada: " + numeroDeJornada
                + "\nFecha: " + fecha
                + "\nEstado: " + estadoDeJornada
                + "\nKilómetros recorridos: " + kilometrosRecorridos
                + "\nPasajeros transportados: " + pasajerosTransportados
                + "\nCantidad de viajes: " + cantidadDeViajes;
    }

    public void registrarHoraDeInicio() {
        horaDeInicioRegistrado = "Hora registrada";
    }

    public void registrarHoraDeFin() {
        horaDeFinRegistrado = "Hora registrada";
    }

}