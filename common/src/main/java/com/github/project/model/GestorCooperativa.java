package com.github.project.model;

import com.github.project.view.InicioGestorForm;

/**
 * Representa al gestor de la cooperativa.
 */
public class GestorCooperativa extends Usuario {

    private String idGestor;

    /**
     * Constructor para inicializar un GestorCooperativa.
     */
    public GestorCooperativa(
            String idUsuario,
            String nombre,
            String contrasena,
            String tipoUsuario,
            boolean activo,
            Cooperativa cooperativa,
            String idGestor) {

        super(idUsuario, nombre, contrasena, tipoUsuario, activo, cooperativa);
        this.idGestor = idGestor;
    }

    /**
     * 1. Como no es posible instanciar Usuario, y no se debe agregar atributos/métodos extra,
     *    la lógica completa de instanciación concreta (Conductor, Monitor, etc.) 
     *    queda reservada o simplemente no se implementa completamente aquí.
     *
     * @param nombre Nombre del usuario
     * @param contrasena Contraseña del usuario
     * @param tipoDeUsuario Tipo de rol
     */
    public void registrarUsuario(String nombre, String contrasena, String tipoDeUsuario) {
        String idUnico = "U-" + System.currentTimeMillis();
        Usuario nuevo = null;
        if ("GESTOR".equalsIgnoreCase(tipoDeUsuario)) {
            nuevo = new GestorCooperativa(idUnico, nombre, contrasena, "GESTOR", true, getCooperativa(), "G-" + idUnico);
        } else if ("MONITOR".equalsIgnoreCase(tipoDeUsuario)) {
            nuevo = new Monitor(idUnico, nombre, contrasena, "MONITOR", true, getCooperativa(), "M-" + idUnico);
        } else {
            nuevo = new Conductor(idUnico, nombre, contrasena, "CONDUCTOR", true, getCooperativa(), "LIC-" + idUnico, true);
        }
        agregarUsuarioACooperativa(nuevo);
    }

    /**
     * 1. (Funcionalidad limitada ya que el UML no establece una relación de asignación 
     *     dinámica en la firma de Vehiculo o Conductor directamente que podamos invocar).
     *
     * @param cedula Identificador (o licencia) del conductor
     * @param placa Placa del vehículo
     */
    public void asignarConductorAVehiculo(String cedula, String placa) {
        // En nuestro dominio la relación conductor-vehículo ocurre a través de Jornada.
    }

    /**
     * 1. Crea una instancia de Jornada (asumiendo que se construye con esos datos básicos).
     * 2. Añade la nueva jornada a la lista de jornadas activas en la cooperativa.
     * 3. Devuelve la instancia recién creada.
     *
     * @param placa Placa del vehículo
     * @param idRuta Identificador de la ruta
     * @return La nueva Jornada instanciada o null en ausencia de detalles de construcción
     */
    public Jornada iniciarJornada(String placa, String idRuta) {
        if (getCooperativa() != null) {
            Vehiculo vSeleccionado = null;
            for (Vehiculo v : getCooperativa().getFlotas()) {
                if (v.getPlaca().equals(placa)) {
                    vSeleccionado = v;
                    break;
                }
            }
            Ruta rSeleccionada = null;
            for (Ruta r : getCooperativa().getRutas()) {
                if (r.getIdRuta().equals(idRuta)) {
                    rSeleccionada = r;
                    break;
                }
            }
            Conductor cDisponible = null;
            for (Usuario u : getCooperativa().getUsuarios()) {
                if ("CONDUCTOR".equalsIgnoreCase(u.getTipoUsuario()) && ((Conductor)u).isDisponible()) {
                    cDisponible = (Conductor) u;
                    break;
                }
            }

            if (vSeleccionado != null && rSeleccionada != null && cDisponible != null) {
                String numJornada = "J0" + (getCooperativa().getJornadasActivas().size() + 1);
                Jornada j = new Jornada(numJornada, "Ahora", vSeleccionado, cDisponible, rSeleccionada);
                getCooperativa().getJornadasActivas().add(j);
                return j;
            }
        }
        return null;
    }

    /**
     * 1. Busca la jornada activa por idJornada iterando la lista.
     * 2. Llama a registrarHoraDeFin en dicha jornada.
     * 3. Retira la jornada de jornadasActivas y la añade a jornadasFinalizadas.
     *
     * @param idJornada Identificador de la jornada a cerrar
     */
    public void cerrarJornada(String idJornada) {
        if (getCooperativa() != null && getCooperativa().getJornadasActivas() != null && idJornada != null) {
            for (int i = 0; i < getCooperativa().getJornadasActivas().size(); i++) {
                Jornada j = getCooperativa().getJornadasActivas().get(i);
                // Se asume la existencia de getNumeroDeJornada como estándar
                if (idJornada.equals(j.getNumeroDeJornada())) {
                    j.registrarHoraDeFin();
                    getCooperativa().getJornadasActivas().remove(i);
                    if (getCooperativa().getJornadasFinalizadas() != null) {
                        getCooperativa().getJornadasFinalizadas().add(j);
                    }
                    break;
                }
            }
        }
    }

    /**
     * 1. Busca iterando a los usuarios en la cooperativa por idUsuario.
     * 2. Si coincide, establece setActivo en true.
     *
     * @param idUsuario Identificador del usuario a activar
     */
    public void activarUsuario(String idUsuario) {
        if (getCooperativa() != null && getCooperativa().getUsuarios() != null) {
            for (Usuario u : getCooperativa().getUsuarios()) {
                if (idUsuario.equals(u.getIdUsuario())) {
                    u.setActivo(true);
                    break;
                }
            }
        }
    }

    /**
     * 1. Busca iterando a los usuarios en la cooperativa por idUsuario.
     * 2. Si coincide, establece setActivo en false.
     *
     * @param idUsuario Identificador del usuario a desactivar
     */
    public void desactivarUsuario(String idUsuario) {
        if (getCooperativa() != null && getCooperativa().getUsuarios() != null) {
            for (Usuario u : getCooperativa().getUsuarios()) {
                if (idUsuario.equals(u.getIdUsuario())) {
                    u.setActivo(false);
                    break;
                }
            }
        }
    }

    /**
     * 1. Agrega directamente el usuario a la lista principal de usuarios en la cooperativa.
     *
     * @param usuario Instancia del usuario a agregar
     */
    public void agregarUsuarioACooperativa(Usuario usuario) {
        if (getCooperativa() != null && getCooperativa().getUsuarios() != null && usuario != null) {
            getCooperativa().getUsuarios().add(usuario);
        }
    }

    /**
     * 1. Agrega el vehículo provisto a la flota de la cooperativa.
     *
     * @param vehiculo El vehículo a agregar
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        if (getCooperativa() != null && getCooperativa().getFlotas() != null && vehiculo != null) {
            getCooperativa().getFlotas().add(vehiculo);
        }
    }

    /**
     * 1. Recorre iterativamente la flota de vehículos.
     * 2. Si se encuentra un vehículo con la misma placa (ignorando mayúsculas/minúsculas), se elimina.
     * 3. Devuelve true si la remoción fue exitosa, false de lo contrario.
     *
     * @param placa Placa del vehículo a remover
     * @return true si se removió el vehículo, false en caso contrario
     */
    public boolean removerVehiculo(String placa) {
        if (getCooperativa() != null && getCooperativa().getFlotas() != null && placa != null) {
            for (int i = 0; i < getCooperativa().getFlotas().size(); i++) {
                Vehiculo v = getCooperativa().getFlotas().get(i);
                if (placa.equalsIgnoreCase(v.getPlaca())) {
                    getCooperativa().getFlotas().remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 1. Instancia PanoramaGeneralForm, inyectando la instancia actual y la cooperativa.
     * 2. Llama al método show() para mostrar la pantalla principal del Gestor.
     * Esta es la frontera declarada con la capa de vista.
     */
    @Override
    public void mostrarMenuPropio() {
        new InicioGestorForm(this, getCooperativa()).show();
    }

    public String getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(String idGestor) {
        this.idGestor = idGestor;
    }
}
