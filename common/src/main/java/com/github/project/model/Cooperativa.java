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
        StringBuilder sb = new StringBuilder();
        sb.append("--- REPORTE DETALLADO DE OPERACIONES ---\n\n");
        sb.append("COOPERATIVA: ").append(nombre).append("\n\n");
        
        sb.append("=== PERSONAL REGISTRADO ===\n");
        if (usuarios.isEmpty()) sb.append("Sin personal.\n");
        for (Usuario u : usuarios) {
            sb.append("- ").append(u.getNombre()).append(" | Rol: ").append(u.getTipoUsuario());
            sb.append(" | Estado: ").append(u.isActivo() ? "ACTIVO" : "INACTIVO").append("\n");
        }
        sb.append("\n");

        sb.append("=== FLOTA VEHICULAR ===\n");
        if (flotas.isEmpty()) sb.append("Sin vehículos.\n");
        for (Vehiculo v : flotas) {
            sb.append("- Placa: ").append(v.getPlaca()).append(" | Marca: ").append(v.getMarca());
            sb.append(" | Tipo: ").append(v.getTipoCombustible()).append("\n");
            if (v instanceof VehiculoElectrico) {
                VehiculoElectrico ve = (VehiculoElectrico) v;
                sb.append("  [Batería: ").append(ve.getCapacidadBateria()).append(" kWh | Carga: ").append(ve.getNivelCarga()).append("%");
                sb.append(" | Autonomía real: ").append(ve.calcularAutonomiaRestante()).append(" kWh]\n");
            } else if (v instanceof VehiculoDiesel) {
                VehiculoDiesel vd = (VehiculoDiesel) v;
                sb.append("  [Tanque: ").append(vd.getCapacidadDeTanque()).append(" L | Combustible: ").append(vd.getNivelDeCombustible()).append(" L");
                sb.append(" | Autonomía real: ").append(vd.calcularAutonomiaRestante()).append(" Litros]\n");
            }
        }
        sb.append("\n");

        sb.append("=== RUTAS ===\n");
        if (rutas.isEmpty()) sb.append("Sin rutas.\n");
        for (Ruta r : rutas) {
            sb.append("- ").append(r.getNombreDeRuta()).append(" (").append(r.getPuntoDeSalida()).append(" -> ").append(r.getPuntoDeLlegada()).append(")\n");
            sb.append("  Horario: ").append(r.getHoraDeSalida()).append(" a ").append(r.getHoraDeLlegada()).append(" | Puntos: ").append(r.getPuntosDeControl().size()).append("\n");
        }
        sb.append("\n");

        sb.append("=== JORNADAS ACTIVAS ===\n");
        if (jornadasActivas.isEmpty()) sb.append("No hay jornadas en curso.\n");
        for (Jornada j : jornadasActivas) {
            sb.append("- Jornada #").append(j.getNumeroDeJornada()).append(" | Inicio: ").append(j.getFecha()).append("\n");
            sb.append("  Conductor: ").append(j.getConductor().getNombre()).append(" | Vehículo: ").append(j.getVehiculo().getPlaca()).append("\n");
            sb.append("  Ruta: ").append(j.getRuta().getNombreDeRuta()).append("\n");
        }

        return sb.toString();
    }

    /**
     * 1. Devuelve un texto con número de registro, nombre y dirección.
     */
    public String mostrarInformacionDeCooperativa() {
        return "Cooperativa: " + nombre
                + "\nRegistro Operativo N°: " + numeroDeRegistro
                + "\nSede Central: " + direccion
                + "\nEmpleados Totales: " + usuarios.size()
                + "\nFlota Total: " + flotas.size() + " vehículos";
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
