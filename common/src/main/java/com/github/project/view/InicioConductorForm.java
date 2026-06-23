package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.Button;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.Dialog;

import com.github.project.model.Conductor;
import com.github.project.model.Cooperativa;

public class InicioConductorForm extends Form {

    private Conductor conductor;
    private Cooperativa cooperativa;

    public InicioConductorForm(Conductor conductor, Cooperativa cooperativa) {
        super(new BorderLayout());
        this.conductor = conductor;
        this.cooperativa = cooperativa;
        this.setUIID("FormConductor"); // Vincula esta pantalla con tu bloque de css
        maquetarInterfazVisual();
    }

    private void maquetarInterfazVisual() {
        Toolbar tb = getToolbar();
        tb.setTitle("Conductor");
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
            new PantallaPerfilConductor().show();
        });
        
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            CoordinadorNavegacion.getInstancia().cerrarSesion();
        });

        Container contenedorCentral = new Container(BoxLayout.y());
        contenedorCentral.setScrollableY(true);

        java.util.List<com.github.project.model.Jornada> misJornadas = new java.util.ArrayList<>();
        for (com.github.project.model.Jornada j : cooperativa.getJornadasActivas()) {
            if (j.getConductor().equals(conductor)) {
                misJornadas.add(j);
            }
        }
        
        Container navInferior = new Container(BoxLayout.y());

        if (misJornadas.isEmpty()) {
            Label lblVacio = new Label("No tienes jornadas asignadas actualmente.");
            lblVacio.setUIID("SubtituloSeccion");
            contenedorCentral.add(lblVacio);
        } else {
            for (com.github.project.model.Jornada jRef : misJornadas) {
                Container tarjetaJornada = new Container(new TableLayout(3, 2));
                tarjetaJornada.setUIID("TarjetaContenedor");
                
                tarjetaJornada.add(new Label("Vehículo:")).add(new Label(jRef.getVehiculo().getPlaca()));
                tarjetaJornada.add(new Label("Ruta asignada:")).add(new Label(jRef.getRuta().getNombreDeRuta()));
                
                Label lblEstado = new Label("EN RUTA");
                lblEstado.setUIID("EstadoActivo");
                tarjetaJornada.add(new Label("Estado:")).add(lblEstado);

                contenedorCentral.add(tarjetaJornada);
            }
            
            com.github.project.model.Jornada jActual = misJornadas.get(0);

            Button btnViaje = new Button("Registrar Viaje Completo");
            btnViaje.setUIID("BotonLogin");
            btnViaje.addActionListener(e -> {
                conductor.reportarViajeCompleto(jActual.getNumeroDeJornada(), 15.5, 40);
                Dialog.show("Viaje Registrado", "Cantidad de viajes: " + jActual.getCantidadDeViajes(), "OK", null);
            });
            
            Button btnFin = new Button("Finalizar Jornada");
            btnFin.setUIID("BotonLogin");
            btnFin.addActionListener(e -> {
                for (com.github.project.model.PuntoControl pc : jActual.getRuta().getPuntosDeControl()) {
                    if (!pc.isSuperado()) {
                        pc.setHoraRealDePaso("NO MARCADO");
                    }
                }
                jActual.registrarHoraDeFin();
                cooperativa.getJornadasActivas().remove(jActual);
                cooperativa.getJornadasFinalizadas().add(jActual);
                Dialog.show("Finalizada", "Jornada terminada correctamente.", "OK", null);
                this.removeAll();
                maquetarInterfazVisual();
                this.revalidate();
            });
            
            navInferior.addAll(btnViaje, btnFin);
        }

        // Sección de Historial Integrado
        Label lblHistorial = new Label("Mi Historial de Infracciones:");
        lblHistorial.setUIID("SubtituloSeccion");
        contenedorCentral.add(lblHistorial);

        java.util.List<com.github.project.model.Infraccion> misInfracciones = conductor.getInfracciones();
        if (misInfracciones == null || misInfracciones.isEmpty()) {
            contenedorCentral.add(new Label("No tienes infracciones registradas. ¡Buen trabajo!"));
        } else {
            for (com.github.project.model.Infraccion inf : misInfracciones) {
                Container tarjetaInfraccion = new Container(BoxLayout.y());
                tarjetaInfraccion.setUIID("TarjetaInfraccion"); 

                Label infLine1 = new Label(inf.getFechaYHoraDeRegistro() + " - " + inf.getDescripcion());
                Label infLine2 = new Label("Nivel: " + inf.getNivelDeInfraccion());

                infLine1.setTickerEnabled(false);
                infLine2.setTickerEnabled(false);
                infLine1.setUIID("InfraccionLinea1");
                infLine2.setUIID("InfraccionLinea2");

                tarjetaInfraccion.addAll(infLine1, infLine2);
                contenedorCentral.add(tarjetaInfraccion);
            }
        }

        this.add(BorderLayout.CENTER, contenedorCentral);
        if (navInferior.getComponentCount() > 0) {
            this.add(BorderLayout.SOUTH, navInferior);
        }
    }
}