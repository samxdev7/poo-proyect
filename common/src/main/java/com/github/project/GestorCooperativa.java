package com.github.project;

/**
 * Representa a un gestor de la cooperativa.
 * Hereda las características generales de un Usuario.
 */
public class GestorCooperativa extends Usuario {

    /**
     * Identificador único del gestor.
     */
    private String idGestor;

    /**
     * Constructor de la clase GestorCooperativa.
     *
     * @param idGestor Identificador del gestor.
     */
    public GestorCooperativa(String idGestor) {
        this.idGestor = idGestor;
    }

    /**
     * Obtiene el identificador del gestor.
     *
     * @return id del gestor.
     */
    public String getIdGestor() {
        return idGestor;
    }

    /**
     * Modifica el identificador del gestor.
     *
     * @param idGestor Nuevo identificador.
     */
    public void setIdGestor(String idGestor) {
        this.idGestor = idGestor;
    }

    /**
     * Registra un nuevo usuario dentro del sistema.
     *
     * @param nombre Nombre del usuario.
     * @param contrasena Contraseña del usuario.
     * @param tipoDeUsuario Tipo de usuario a crear.
     */
    public void registrarUsuario(String nombre, String contrasena, String tipoDeUsuario) {
        // TODO: Implementar lógica de registro.
    }

    /**
     * Asigna un conductor a un vehículo.
     *
     * @param cedula Identificación del conductor.
     * @param placa Placa del vehículo.
     */
    public void asignarConductorAVehiculo(String cedula, String placa) {
        // TODO: Implementar asignación.
    }

    /**
     * Inicia una nueva jornada de trabajo.
     *
     * @param placa Placa del vehículo.
     * @param idRuta Ruta asignada.
     * @return Jornada creada.
     */
    public Jornada iniciarJornada(String placa, String idRuta) {
        return null;
    }

    /**
     * Finaliza una jornada activa.
     *
     * @param idJornada Identificador de la jornada.
     */
    public void cerrarJornada(String idJornada) {
        // TODO: Implementar cierre.
    }

    /**
     * Activa un usuario del sistema.
     *
     * @param idUsuario Identificador del usuario.
     */
    public void activarUsuario(String idUsuario) {
        // TODO: Implementar activación.
    }

    /**
     * Desactiva un usuario del sistema.
     *
     * @param idUsuario Identificador del usuario.
     */
    public void desactivarUsuario(String idUsuario) {
        // TODO: Implementar desactivación.
    }

    /**
     * Agrega un usuario a la cooperativa.
     *
     * @param usuario Usuario a agregar.
     */
    public void agregarUsuarioACooperativa(Usuario usuario) {
        // TODO: Implementar agregado.
    }

    /**
     * Agrega un vehículo a la cooperativa.
     *
     * @param vehiculo Vehículo a registrar.
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        // TODO: Implementar agregado.
    }

    /**
     * Elimina un vehículo de la cooperativa.
     *
     * @param placa Placa del vehículo.
     * @return true si se eliminó correctamente.
     */
    public boolean removerVehiculo(String placa) {
        return false;
    }
}