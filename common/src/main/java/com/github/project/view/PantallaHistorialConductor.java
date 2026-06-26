package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.github.project.model.Usuario;
import com.github.project.model.Conductor;
import com.github.project.model.Jornada;
import com.github.project.model.PuntoControl;
import java.util.List;

public class PantallaHistorialConductor extends Form {
    public PantallaHistorialConductor() {
        super("Historial de Jornadas", BoxLayout.y());
        Toolbar tb = getToolbar();
        
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> {
            CoordinadorNavegacion.getInstancia().despacharPantallaRaiz();
        });

        Usuario u = CoordinadorNavegacion.getInstancia().getUsuarioAutenticado();
        if (!(u instanceof Conductor)) {
            this.add(new Label("No eres un conductor."));
            return;
        }

        Conductor conductor = (Conductor) u;
        List<Jornada> jornadas = conductor.getJornadasRealizadas();

        if (jornadas == null || jornadas.isEmpty()) {
            this.add(new Label("Aún no tienes jornadas finalizadas."));
            return;
        }

        for (Jornada j : jornadas) {
            Container itemHistorial = new Container(BoxLayout.y());
            itemHistorial.setUIID("MultiButton"); 
            itemHistorial.setLeadComponent(null); 

            Label linea1 = new Label("Jornada #" + j.getNumeroDeJornada() + " - " + j.getFecha());
            Label linea2 = new Label("Vehículo: " + j.getVehiculo().getPlaca() + " | Ruta: " + j.getRuta().getNombreDeRuta());
            
            linea1.setUIID("MultiButtonLine1");
            linea2.setUIID("MultiButtonLine2");
            linea1.setTickerEnabled(false);
            linea2.setTickerEnabled(false);

            itemHistorial.addAll(linea1, linea2);
            
            itemHistorial.addPointerReleasedListener(e -> {
                StringBuilder sb = new StringBuilder();
                sb.append("Rendimiento: ").append(j.getKilometrosRecorridos()).append(" km, ")
                  .append(j.getPasajerosTransportados()).append(" pasajeros.\n\n");
                
                sb.append("--- PUNTOS DE CONTROL ---\n");
                boolean hayNoMarcados = false;
                
                Form detalleForm = new Form("Detalle de Jornada", BoxLayout.y());
                detalleForm.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, evt -> this.showBack());
                
                TextArea txtCabecera = new TextArea(sb.toString());
                txtCabecera.setEditable(false);
                txtCabecera.setUIID("Label");
                detalleForm.add(txtCabecera);

                for (PuntoControl pc : j.getRuta().getPuntosDeControl()) {
                    Label lblPunto = new Label(pc.getUbicacion() + " (" + pc.getHoraProgramada() + ")");
                    if (pc.isSuperado()) {
                        lblPunto.setText(lblPunto.getText() + " - MARCADO");
                    } else {
                        lblPunto.setText(lblPunto.getText() + " - NO MARCADO");
                        lblPunto.getAllStyles().setFgColor(0xFF0000); // Color Rojo
                    }
                    detalleForm.add(lblPunto);
                }

                detalleForm.show();
            });

            this.add(itemHistorial);
        }
    }
}