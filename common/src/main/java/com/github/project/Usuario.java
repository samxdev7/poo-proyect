
package com.mycompany.avancespoo;


public class Usuario {
    
   

    // Atributos
    private String idUsuario;
    private String nombre;
    private String contrasena;
    private String tipoUsuario;
    private boolean activo;
    private Cooperativa cooperativa;

    // Constructor
    public Usuario(String idUsuario, String nombre, String contrasena,
            String tipoUsuario, boolean activo, Cooperativa cooperativa) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.activo = activo;
        this.cooperativa = cooperativa;
    }

    // Métodos

    public boolean iniciarSesion(String nombre, String contrasena) {
        return verificarUsuario(nombre, contrasena);
    }

    public boolean verificarUsuario(String nombre, String contrasena) {
        return this.nombre.equals(nombre)
                && this.contrasena.equals(contrasena)
                && this.activo;
    }

    public void cambiarContrasena(String contrasenaNueva) {
        this.contrasena = contrasenaNueva;
    }

    public String mostrarTipoDelUsuario() {
        return tipoUsuario;
    }

    public void mostrarMenuPropio() {
        System.out.println("Mostrando menú del usuario...");
    }

    public String consultarReporteDeJornada(String idJornada) {
        return "Reporte de jornada: " + idJornada;
    }

    public Vehiculo buscarVehiculo(String placa) {
        return null;
    }

    public Conductor buscarConductor(String placa) {
        return null;
    }

    // Getters y Setters

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Cooperativa getCooperativa() {
        return cooperativa;
    }

    public void setCooperativa(Cooperativa cooperativa) {
        this.cooperativa = cooperativa;
    }
}

