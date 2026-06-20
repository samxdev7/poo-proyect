package com.github.project;

public class GestorCooperativa extends Usuario {

    // ==========================
    // Atributos
    // ==========================

    private String idGestor;

    // ==========================
    // Constructores
    // ==========================

    public GestorCooperativa(
            String idUsuario,
            String nombre,
            String contrasena,
            String tipoUsuario,
            boolean activo,
            Cooperativa cooperativa,
            String idGestor) {

        super(
                idUsuario,
                nombre,
                contrasena,
                tipoUsuario,
                activo,
                cooperativa
        );

        this.idGestor = idGestor;
    }

    // ==========================
    // Getters y Setters
    // ==========================

    public String getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(String idGestor) {
        this.idGestor = idGestor;
    }

    // ==========================
    // Métodos
    // ==========================

    public void registrarUsuario(String nombre,
                                 String contrasena,
                                 String tipoDeUsuario) {
        // TODO
    }

    public void asignarConductorAVehiculo(String cedula,
                                          String placa) {
        // TODO
    }

    public Jornada iniciarJornada(String placa,
                                  String idRuta) {
        return null;
    }

    public void cerrarJornada(String idJornada) {
        // TODO
    }

    public void activarUsuario(String idUsuario) {
        // TODO
    }

    public void desactivarUsuario(String idUsuario) {
        // TODO
    }

    public void agregarUsuarioACooperativa(Usuario usuario) {
        // TODO
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        // TODO
    }

    public boolean removerVehiculo(String placa) {
        return false;
    }

}