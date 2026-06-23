package com.github.project.model;

import java.util.ArrayList;
import java.util.List;

public class Cooperativa {
    
    private String numeroDeRegistro;
    private String nombre;
    private String direccion;

    private List<Vehiculo> flotas;
    private List<Conductor> conductores;
    private List<Jornada> jornadasActivas;
    private List<Jornada> jornadasFinalizadas;
    private List<Ruta> rutas;
    private List<Usuario> usuarios;

    public Cooperativa(String numeroDeRegistro, String nombre, String direccion) {
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

    /**
     * 1. Concatena la información general y contadores de las listas.
     * 2. Devuelve la cadena.
     */
    public String generarReporteGeneralDeActividad() {
        return "Cooperativa: " + nombre
                + "\nVehículos: " + flotas.size()
                + "\nConductores: " + conductores.size()
                + "\nJornadas activas: " + jornadasActivas.size()
                + "\nJornadas finalizadas: " + jornadasFinalizadas.size()
                + "\nRutas: " + rutas.size()
                + "\nUsuarios: " + usuarios.size();
    }

    /**
     * 1. Devuelve un texto con número de registro, nombre y dirección.
     */
    public String mostrarInformacionDeCooperativa() {
        return "Número de registro: " + numeroDeRegistro
                + "\nNombre: " + nombre
                + "\nDirección: " + direccion;
    }

    public String getNumeroDeRegistro() { return numeroDeRegistro; }
    public void setNumeroDeRegistro(String numeroDeRegistro) { this.numeroDeRegistro = numeroDeRegistro; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Vehiculo> getFlotas() { return flotas; }
    public void setFlotas(List<Vehiculo> flotas) { this.flotas = flotas; }

    public List<Conductor> getConductores() { return conductores; }
    public void setConductores(List<Conductor> conductores) { this.conductores = conductores; }

    public List<Jornada> getJornadasActivas() { return jornadasActivas; }
    public void setJornadasActivas(List<Jornada> jornadasActivas) { this.jornadasActivas = jornadasActivas; }

    public List<Jornada> getJornadasFinalizadas() { return jornadasFinalizadas; }
    public void setJornadasFinalizadas(List<Jornada> jornadasFinalizadas) { this.jornadasFinalizadas = jornadasFinalizadas; }

    public List<Ruta> getRutas() { return rutas; }
    public void setRutas(List<Ruta> rutas) { this.rutas = rutas; }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}
