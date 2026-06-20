package com.github.project;

import java.util.ArrayList;
import java.util.List;

public class Conductor extends Usuario {

    
    // Atributos
    

    private String licenciaDeConducir;
    private boolean disponible;

    private List<Jornada> jornadasRealizadas;
    private List<Infraccion> infracciones;

   
    // Constructores
    

    public Conductor(
            String idUsuario,
            String nombre,
            String contrasena,
            String tipoUsuario,
            boolean activo,
            Cooperativa cooperativa,
            String licenciaDeConducir,
            boolean disponible) {

        super(idUsuario, nombre, contrasena, tipoUsuario, activo, cooperativa);

        this.licenciaDeConducir = licenciaDeConducir;
        this.disponible = disponible;
        this.jornadasRealizadas = new ArrayList<>();
        this.infracciones = new ArrayList<>();
    }

    
    // Getters y Setters
    

    public String getLicenciaDeConducir() {
        return licenciaDeConducir;
    }

    public void setLicenciaDeConducir(String licenciaDeConducir) {
        this.licenciaDeConducir = licenciaDeConducir;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Jornada> getJornadasRealizadas() {
        return jornadasRealizadas;
    }

    public void setJornadasRealizadas(List<Jornada> jornadasRealizadas) {
        this.jornadasRealizadas = jornadasRealizadas;
    }

    public List<Infraccion> getInfracciones() {
        return infracciones;
    }

    public void setInfracciones(List<Infraccion> infracciones) {
        this.infracciones = infracciones;
    }

    
    // Métodos
    

    public void reportarViajeCompletado(String idJornada,
                                        double km,
                                        int pasajeros) {
        // TODO
    }

    public void registrarCantidadDeViajesRealizadosEnJornada() {
        // TODO
    }

    public boolean tieneInfraccionesGraves() {

        for (Infraccion infraccion : infracciones) {

            if (infraccion.getNivel().equalsIgnoreCase("GRAVE")) {
                return true;
            }
        }

        return false;
    }

    public String mostrarInfoDeConductor() {

        return "Licencia: " + licenciaDeConducir +
               "\nDisponible: " + disponible +
               "\nJornadas realizadas: " + jornadasRealizadas.size() +
               "\nInfracciones registradas: " + infracciones.size();
    }

}