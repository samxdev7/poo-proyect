package com.github.project;
import java.util.ArrayList;
import java.util.List;

public class Cooperativa {
    
    // Atributos
    private String numeroDeRegistro;
    private String nombre;
    private String direccion;

    private List<Vehiculo> flotas;
    private List<Conductor> conductores;
    private List<Jornada> jornadasActivas;
    private List<Jornada> jornadasFinalizadas;
    private List<Ruta> rutas;
    private List<Usuario> usuarios;

    // Constructor
    public Cooperativa(String numeroDeRegistro,
                       String nombre,
                       String direccion) {

        this.numeroDeRegistro = numeroDeRegistro;
        this.nombre = nombre;
        this.direccion = direccion;

        this.flotas = new ArrayList<>();
        this.conductores = new ArrayList<>();
        this.jornadasActivas = new ArrayList<>();
        this.jornadasFinalizadas = new ArrayList<>();
        this.rutas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    // Métodos del diagrama

    public String generarReporteGeneralDeActividad() {
        return "Cooperativa: " + nombre
                + "\nVehículos: " + flotas.size()
                + "\nConductores: " + conductores.size()
                + "\nJornadas activas: " + jornadasActivas.size()
                + "\nJornadas finalizadas: " + jornadasFinalizadas.size()
                + "\nRutas: " + rutas.size()
                + "\nUsuarios: " + usuarios.size();
    }

    public String mostrarInformacionDeCooperativa() {
        return "Número de registro: " + numeroDeRegistro
                + "\nNombre: " + nombre
                + "\nDirección: " + direccion;
    }

    // Getters

    public String getNumeroDeRegistro() {
        return numeroDeRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Vehiculo> getFlotas() {
        return flotas;
    }

    public List<Conductor> getConductores() {
        return conductores;
    }

    public List<Jornada> getJornadasActivas() {
        return jornadasActivas;
    }

    public List<Jornada> getJornadasFinalizadas() {
        return jornadasFinalizadas;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // Setters

    public void setNumeroDeRegistro(String numeroDeRegistro) {
        this.numeroDeRegistro = numeroDeRegistro;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFlotas(List<Vehiculo> flotas) {
        this.flotas = flotas;
    }

    public void setConductores(List<Conductor> conductores) {
        this.conductores = conductores;
    }

    public void setJornadasActivas(List<Jornada> jornadasActivas) {
        this.jornadasActivas = jornadasActivas;
    }

    public void setJornadasFinalizadas(List<Jornada> jornadasFinalizadas) {
        this.jornadasFinalizadas = jornadasFinalizadas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
