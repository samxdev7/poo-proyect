package com.github.project.model;

import com.github.project.view.InicioMonitorForm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monitor extends Usuario {

    private String idMonitor;
    private List<PuntoControl> puntosDeControlAsignados;
    private List<PuntoControl> puntosDeControlMarcados;

    public Monitor(
            String idUsuario,
            String nombre,
            String contrasena,
            String tipoUsuario,
            boolean activo,
            Cooperativa cooperativa,
            String idMonitor) {

        super(idUsuario, nombre, contrasena, tipoUsuario, activo, cooperativa);
        this.idMonitor = idMonitor;
        this.puntosDeControlAsignados = new ArrayList<>();
        this.puntosDeControlMarcados = new ArrayList<>();
    }

    /**
     * Reporta el resultado de la supervisión de un punto de control en una jornada.
     *
     * @param idJornada El identificador de la jornada supervisada.
     * @param idControl El identificador del punto de control.
     */
    public void reportarResultadoDeControl(String idJornada, String idControl) {
        if (getCooperativa() != null) {
            for (Jornada j : getCooperativa().getJornadasActivas()) {
                if (j.getNumeroDeJornada().equals(idJornada)) {
                    j.getRuta().registrarPasoPorControl(idControl, "Ahora");
                    break;
                }
            }
        }
    }

    /**
     * Registra una nueva infracción cometida por un conductor.
     *
     * @param licenciaDeConducir La licencia del conductor infractor.
     * @param nivel El nivel de gravedad de la infracción.
     * @param descripcion Detalles adicionales sobre el incidente.
     * @param idJornada El identificador de la jornada en la que ocurrió.
     * @param idControl El identificador del punto de control.
     * @return La instancia de la infracción generada.
     */
    public Infraccion registrarInfraccion(String licenciaDeConducir, String nivel, String descripcion, String idJornada, String idControl) {
        Infraccion inf = new Infraccion("I-" + System.currentTimeMillis(), descripcion, nivel, "Ahora", idJornada, idMonitor);
        if (getCooperativa() != null) {
            for (Conductor c : getCooperativa().getConductores()) {
                if (c.getLicenciaDeConducir().equals(licenciaDeConducir)) {
                    List<Infraccion> lista = new ArrayList<>(c.getInfracciones());
                    lista.add(inf);
                    c.setInfracciones(lista);
                    break;
                }
            }
        }
        return inf;
    }

    /**
     * Registra el paso de un vehículo por un punto de control y lo marca como superado.
     *
     * @param idControl El identificador del punto de control.
     * @param horaReal La hora real en la que el vehículo pasó por el control.
     */
    public void marcarPuntoDeControl(String idControl, String horaReal) {
        for (int i = 0; i < puntosDeControlAsignados.size(); i++) {
            PuntoControl pc = puntosDeControlAsignados.get(i);
            if (idControl.equals(pc.getIdControl())) {
                pc.setHoraRealDePaso(horaReal);
                pc.setSuperado(true);
                puntosDeControlMarcados.add(pc);
                puntosDeControlAsignados.remove(i);
                break;
            }
        }
    }

    /**
     * Calcula la diferencia en minutos entre la hora programada y la hora real de paso.
     *
     * @param idControl El identificador del punto de control.
     * @return El retraso calculado en minutos.
     */
    public int calcularRetrasoDeControl(String idControl) {
        for (PuntoControl pc : puntosDeControlMarcados) {
            if (pc.getIdControl().equals(idControl)) {
                String programada = pc.getHoraProgramada();
                String real = pc.getHoraRealDePaso();
                if (programada == null || real == null || real.equals("NO MARCADO")) {
                    return 0; 
                }
                try {
                    String[] pParts = programada.split(":");
                    String[] rParts = real.split(":");
                    int pMin = Integer.parseInt(pParts[0].trim()) * 60 + Integer.parseInt(pParts[1].trim());
                    int rMin = Integer.parseInt(rParts[0].trim()) * 60 + Integer.parseInt(rParts[1].trim());
                    return rMin - pMin;
                } catch (Exception e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * Muestra la pantalla específica para el perfil de Monitor.
     */
    @Override
    public void mostrarMenuPropio() {
        new InicioMonitorForm(this, getCooperativa()).show();
    }

    public String getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(String idMonitor) {
        this.idMonitor = idMonitor;
    }

    public List<PuntoControl> getPuntosDeControlAsignados() {
        return Collections.unmodifiableList(puntosDeControlAsignados);
    }

    public void setPuntosDeControlAsignados(List<PuntoControl> puntosDeControlAsignados) {
        this.puntosDeControlAsignados = new ArrayList<>(puntosDeControlAsignados);
    }

    public List<PuntoControl> getPuntosDeControlMarcados() {
        return Collections.unmodifiableList(puntosDeControlMarcados);
    }

    public void setPuntosDeControlMarcados(List<PuntoControl> puntosDeControlMarcados) {
        this.puntosDeControlMarcados = new ArrayList<>(puntosDeControlMarcados);
    }
}
