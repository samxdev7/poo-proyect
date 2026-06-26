package com.github.project.model;

import com.github.project.view.InicioConductorForm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa a un conductor dentro del sistema de la cooperativa.
 */
public class Conductor extends Usuario {

    private String licenciaDeConducir;
    private boolean disponible;
    private List<Jornada> jornadasRealizadas;
    private List<Infraccion> infracciones;

    /**
     * Constructor para inicializar un Conductor.
     *
     * @param idUsuario          Identificador único del usuario
     * @param nombre             Nombre del conductor
     * @param contrasena         Contraseña del conductor
     * @param tipoUsuario        Tipo de usuario
     * @param activo             Estado del conductor
     * @param cooperativa        Referencia a la cooperativa
     * @param licenciaDeConducir Licencia de conducir (identificador único del conductor)
     * @param disponible         Indica si el conductor está disponible
     */
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

    /**
     * 1. Delega a la cooperativa la búsqueda de la jornada activa correspondiente.
     * 2. Si se encuentra la jornada, llama a registrarDatosDeJornada en dicha jornada.
     *
     * @param idJornada Identificador de la jornada
     * @param km        Kilómetros recorridos
     * @param pasajeros Pasajeros transportados
     */
    public void reportarViajeCompleto(String idJornada, double km, int pasajeros) {
        if (getCooperativa() != null && getCooperativa().getJornadasActivas() != null) {
            for (Jornada j : getCooperativa().getJornadasActivas()) {
                // Se asume la existencia de getNumeroDeJornada como estándar de encapsulamiento
                if (idJornada.equals(j.getNumeroDeJornada())) {
                    j.registrarDatosDeJornada(km, pasajeros);
                    break;
                }
            }
        }
    }

    /**
     * 1. Delega a la jornada la actualización de la cantidad de viajes.
     *
     * @param idJornada El identificador de la jornada
     * @param cantidad  La cantidad de viajes a registrar
     */
    public void registrarCantidadDeViajes(String idJornada, int cantidad) {
        if (getCooperativa() != null && getCooperativa().getJornadasActivas() != null) {
            for (Jornada j : getCooperativa().getJornadasActivas()) {
                if (idJornada.equals(j.getNumeroDeJornada())) {
                    j.setCantidadDeViajes(cantidad);
                    break;
                }
            }
        }
    }

    /**
     * 1. Itera sobre las infracciones registradas en la lista interna.
     * 2. Usa mostrarNivelDeInfraccion para verificar si alguna es "GRAVE".
     * 3. Retorna true si encuentra al menos una, false de lo contrario.
     *
     * @return true si el conductor posee infracciones graves
     */
    public boolean tieneInfraccionesGraves() {
        for (Infraccion infraccion : infracciones) {
            if ("GRAVE".equalsIgnoreCase(infraccion.mostrarNivelDeInfraccion())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 1. Retorna una cadena formateada con la licencia, disponibilidad,
     *    y la cantidad de jornadas e infracciones del conductor.
     *
     * @return Cadena con la información del conductor
     */
    public String mostrarInfoDeConductor() {
        return "Licencia: " + licenciaDeConducir +
               "\nDisponible: " + disponible +
               "\nJornadas realizadas: " + jornadasRealizadas.size() +
               "\nInfracciones registradas: " + infracciones.size();
    }

    /**
     * 1. Instancia la clase InicioConductorForm, inyectando la instancia actual y la cooperativa.
     * 2. Llama al método show() para mostrar la pantalla en Codename One.
     * (Esta es la única frontera declarada entre el modelo y la vista en Conductor).
     */
    @Override
    public void mostrarMenuPropio() {
        new InicioConductorForm(this, getCooperativa()).show();
    }

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
        return Collections.unmodifiableList(jornadasRealizadas);
    }

    public void setJornadasRealizadas(List<Jornada> jornadasRealizadas) {
        this.jornadasRealizadas = new ArrayList<>(jornadasRealizadas);
    }

    public List<Infraccion> getInfracciones() {
        return Collections.unmodifiableList(infracciones);
    }

    public void setInfracciones(List<Infraccion> infracciones) {
        this.infracciones = new ArrayList<>(infracciones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Conductor conductor = (Conductor) o;
        return Objects.equals(licenciaDeConducir, conductor.licenciaDeConducir);
    }
}
