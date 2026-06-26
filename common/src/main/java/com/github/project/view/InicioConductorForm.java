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
import com.codename1.ui.Component;
import java.util.List;
import java.util.ArrayList;
import com.github.project.model.Infraccion;
import com.github.project.model.Jornada;
import com.github.project.model.Conductor;
import com.github.project.model.Cooperativa;
import com.github.project.model.PuntoControl;

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
        this.removeAllCommands();
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

        List<Jornada> misJornadas = new ArrayList<>();
        for (Jornada j : cooperativa.getJornadasActivas()) {
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
            for (Jornada jRef : misJornadas) {
                Container tarjetaJornada = new Container(new TableLayout(3, 2));
                tarjetaJornada.setUIID("TarjetaContenedor");
                
                tarjetaJornada.add(new Label("Vehículo:")).add(new Label(jRef.getVehiculo().getPlaca()));
                tarjetaJornada.add(new Label("Ruta asignada:")).add(new Label(jRef.getRuta().getNombreDeRuta()));
                
                Label lblViajesInfo = new Label(jRef.getCantidadDeViajes() + " completados");
                tarjetaJornada.add(new Label("Viajes:")).add(lblViajesInfo);
                
                Label lblEstado = new Label("EN RUTA");
                lblEstado.setUIID("EstadoActivo");
                tarjetaJornada.add(new Label("Estado:")).add(lblEstado);

                contenedorCentral.add(tarjetaJornada);
            }
            
            Jornada jActual = misJornadas.get(0);

            Button btnViaje = new Button("Registrar Viaje Completo");
            btnViaje.setUIID("BotonLogin");
            btnViaje.addActionListener(e -> {
                Form fViaje = new Form("Datos del Viaje", new BorderLayout());
                fViaje.setUIID("FormConductor");
                fViaje.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, evt -> this.showBack());

                com.codename1.ui.TextField txtKm = new com.codename1.ui.TextField("", "Kilómetros recorridos");
                com.codename1.ui.TextField txtPasajeros = new com.codename1.ui.TextField("", "Cantidad de pasajeros");
                
                txtKm.setConstraint(com.codename1.ui.TextField.DECIMAL);
                txtPasajeros.setConstraint(com.codename1.ui.TextField.NUMERIC);
                
                txtKm.setUIID("CampoTexto");
                txtPasajeros.setUIID("CampoTexto");

                Container bodyViaje = BoxLayout.encloseY(
                    new Label("Kilómetros (km):"), txtKm,
                    new Label("Pasajeros Transportados:"), txtPasajeros
                );
                bodyViaje.setScrollableY(true);
                
                Button btnGuardarViaje = new Button("Guardar Viaje");
                btnGuardarViaje.setUIID("BotonLogin");
                btnGuardarViaje.addActionListener(evt -> {
                    if (txtKm.getText().trim().isEmpty() || txtPasajeros.getText().trim().isEmpty()) {
                        Dialog.show("Error", "Debe llenar ambos campos.", "OK", null);
                        return;
                    }
                    try {
                        double km = Double.parseDouble(txtKm.getText().trim());
                        int pasajeros = Integer.parseInt(txtPasajeros.getText().trim());
                        
                        jActual.setCantidadDeViajes(jActual.getCantidadDeViajes() + 1);
                        conductor.reportarViajeCompleto(jActual.getNumeroDeJornada(), km, pasajeros);
                        
                        this.removeAll();
                        maquetarInterfazVisual();
                        this.revalidate();
                        
                        this.showBack();
                        Dialog.show("Viaje Registrado", "Cantidad total de viajes: " + jActual.getCantidadDeViajes(), "OK", null);
                    } catch (NumberFormatException ex) {
                        Dialog.show("Error", "Ingrese valores numéricos válidos.", "OK", null);
                    }
                });

                fViaje.add(BorderLayout.CENTER, bodyViaje);
                fViaje.add(BorderLayout.SOUTH, btnGuardarViaje);
                fViaje.show();
            });
            
            Button btnFin = new Button("Finalizar Jornada");
            btnFin.setUIID("BotonLogin");
            btnFin.addActionListener(e -> {
                for (PuntoControl pc : jActual.getRuta().getPuntosDeControl()) {
                    if (!pc.isSuperado()) {
                        pc.setHoraRealDePaso("NO MARCADO");
                    }
                }
                jActual.registrarHoraDeFin();
                cooperativa.getJornadasActivas().remove(jActual);
                cooperativa.getJornadasFinalizadas().add(jActual);
                
                // Add the journey to the conductor's personal history
                List<Jornada> historial = new ArrayList<>(conductor.getJornadasRealizadas());
                historial.add(jActual);
                conductor.setJornadasRealizadas(historial);
                
                Dialog.show("Finalizada", "Jornada terminada correctamente.", "OK", null);
                this.removeAll();
                maquetarInterfazVisual();
                this.revalidate();
            });
            
            navInferior.addAll(btnViaje, btnFin);
        }

        // Botón para acceder al historial de jornadas
        Button btnHistorialJornadas = new Button("Ver Historial de Jornadas");
        btnHistorialJornadas.setUIID("BotonLogin");
        btnHistorialJornadas.addActionListener(e -> {
            new PantallaHistorialConductor().show();
        });
        contenedorCentral.add(btnHistorialJornadas);

        // Sección de Historial Integrado
        Label lblHistorial = new Label("Mi Historial de Infracciones:");
        lblHistorial.setUIID("SubtituloSeccion");
        contenedorCentral.add(lblHistorial);

        List<Infraccion> misInfracciones = conductor.getInfracciones();
        if (misInfracciones == null || misInfracciones.isEmpty()) {
            contenedorCentral.add(new Label("No tienes infracciones registradas. ¡Buen trabajo!"));
        } else {
            for (Infraccion inf : misInfracciones) {
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