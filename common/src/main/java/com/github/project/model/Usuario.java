package com.github.project.model;

import java.util.Objects;

/**
 * Clase abstracta que representa a un usuario genérico del sistema.
 */
public abstract class Usuario {

    private String idUsuario;
    private String nombre;
    private String contrasena;
    private String tipoUsuario;
    private boolean activo;
    private Cooperativa cooperativa;

    /**
     * Constructor para inicializar un usuario.
     *
     * @param idUsuario   Identificador único del usuario
     * @param nombre      Nombre de usuario
     * @param contrasena  Contraseña del usuario
     * @param tipoUsuario Tipo de rol del usuario
     * @param activo      Estado activo o inactivo
     * @param cooperativa Referencia a la cooperativa principal
     */
    public Usuario(String idUsuario, String nombre, String contrasena,
                   String tipoUsuario, boolean activo, Cooperativa cooperativa) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.activo = activo;
        this.cooperativa = cooperativa;
    }

    /**
     * 1. Llama al método verificarUsuario pasando el nombre y la contraseña.
     * 2. Devuelve el resultado booleano de dicha verificación.
     *
     * @param nombre     Nombre de usuario intentando ingresar
     * @param contrasena Contraseña proveída
     * @return true si la sesión puede ser iniciada exitosamente
     */
    public boolean iniciarSesion(String nombre, String contrasena) {
        return verificarUsuario(nombre, contrasena);
    }

    /**
     * 1. Verifica que el nombre proporcionado coincida con el almacenado.
     * 2. Verifica que la contraseña proporcionada coincida con la almacenada.
     * 3. Verifica que la cuenta del usuario se encuentre activa.
     * 4. Retorna true si todas las condiciones se cumplen, de lo contrario false.
     *
     * @param nombre     Nombre a validar
     * @param contrasena Contraseña a validar
     * @return true si las credenciales son correctas y el usuario está activo
     */
    public boolean verificarUsuario(String nombre, String contrasena) {
        return this.nombre.equals(nombre)
                && this.contrasena.equals(contrasena)
                && this.activo;
    }

    /**
     * 1. Actualiza el atributo interno contrasena con el nuevo valor dado.
     *
     * @param contrasenaNueva El nuevo valor de la contraseña
     */
    public void cambiarContrasena(String contrasenaNueva) {
        this.contrasena = contrasenaNueva;
    }

    /**
     * 1. Devuelve el atributo tipoUsuario.
     *
     * @return Cadena que representa el tipo de usuario
     */
    public String mostrarTipoDeUsuario() {
        return tipoUsuario;
    }

    /**
     * Método abstracto que marca la frontera con la capa de vista.
     * 1. Las subclases deben implementar este método para construir e inicializar su propio formulario (Form).
     */
    public abstract void mostrarMenuPropio();

    /**
     * 1. Concatena un texto estático con el identificador de la jornada recibido.
     * 2. Retorna la cadena resultante.
     *
     * @param idJornada El identificador de la jornada a consultar
     * @return Una cadena representativa de la consulta
     */
    public String consultarReporteDeJornada(String idJornada) {
        return "Reporte de jornada: " + idJornada;
    }

    /**
     * 1. Verifica si la referencia a la cooperativa existe y si la placa no es nula.
     * 2. Itera sobre la lista de vehículos (flotas) obtenida de la cooperativa.
     * 3. Si encuentra un vehículo cuya placa coincida (ignorando mayúsculas), lo devuelve.
     * 4. Si termina la iteración sin encontrarlo, retorna null.
     *
     * @param placa La placa del vehículo a buscar
     * @return La instancia del Vehiculo encontrado, o null en su defecto
     */
    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null || cooperativa == null || cooperativa.getFlotas() == null) {
            return null;
        }
        for (Vehiculo vehiculo : cooperativa.getFlotas()) {
            if (placa.equalsIgnoreCase(vehiculo.getPlaca())) {
                return vehiculo;
            }
        }
        return null;
    }

    /**
     * 1. Verifica si la referencia a la cooperativa existe y si el parámetro no es nulo.
     * 2. Itera sobre la lista de conductores de la cooperativa.
     * 3. Asumiendo que el parámetro placa en la firma puede corresponder a la licencia de conducir
     *    del conductor en este contexto de búsqueda, lo compara.
     * 4. Retorna el conductor si coincide, si no, retorna null.
     *
     * @param placa El identificador a buscar (usualmente la licencia del conductor)
     * @return El Conductor encontrado o null
     */
    public Conductor buscarConductor(String placa) {
        if (placa == null || cooperativa == null || cooperativa.getConductores() == null) {
            return null;
        }
        for (Conductor conductor : cooperativa.getConductores()) {
            if (placa.equalsIgnoreCase(conductor.getLicenciaDeConducir())) {
                return conductor;
            }
        }
        return null;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(idUsuario, usuario.idUsuario);
    }
}
