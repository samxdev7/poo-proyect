package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout; 
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.Button;
import com.codename1.ui.TextField; 
import com.codename1.ui.Dialog;    
import com.codename1.ui.Command;   
import com.codename1.ui.Component; 
import com.codename1.ui.Font;
import com.codename1.components.MultiButton; 
import com.codename1.components.SpanButton;  

import com.github.project.model.Monitor;
import com.github.project.model.Cooperativa;

public class InicioMonitorForm extends Form {

    private Monitor monitor;
    private Cooperativa cooperativa;

    public InicioMonitorForm(Monitor monitor, Cooperativa cooperativa) {
        super(new BorderLayout());
        this.monitor = monitor;
        this.cooperativa = cooperativa;
        maquetarInterfazVisual();
    }

    private void maquetarInterfazVisual() {
        Toolbar tb = getToolbar();
        tb.setTitle("Punto de Control");
        
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
            new PantallaPerfilConductor().show();
        });
        
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            CoordinadorNavegacion.getInstancia().cerrarSesion();
        });

        // Contenedor central para scroll seguro de informacion de control y jornadas
        Container contenedorCentral = new Container(BoxLayout.y());
        contenedorCentral.setScrollableY(true); 


        java.util.List<com.github.project.model.PuntoControl> misPuntos = monitor.getPuntosDeControlAsignados();
        
        Label tituloTarjeta = new Label("Mis Puntos de Control Pendientes:");
        Container tarjetaControl = new Container(BoxLayout.y());
        tarjetaControl.setUIID("TarjetaContenedor");
        
        if (misPuntos.isEmpty()) {
            tarjetaControl.add(new Label("No tienes puntos asignados."));
        } else {
            for (com.github.project.model.PuntoControl pc : misPuntos) {
                Container fila = new Container(new BorderLayout());
                fila.add(BorderLayout.WEST, new Label(pc.getUbicacion() + " (" + pc.getHoraProgramada() + ")"));
                
                Button btnLlegada = new Button("Marcar");
                btnLlegada.setUIID("BotonLogin");
                btnLlegada.getAllStyles().setPadding(1, 1, 1, 1);
                btnLlegada.addActionListener(e -> {
                    monitor.marcarPuntoDeControl(pc.getIdControl(), "Ahora");
                    Dialog.show("Éxito", "Llegada marcada.", "OK", null);
                    this.removeAll();
                    maquetarInterfazVisual();
                    this.revalidate();
                });
                fila.add(BorderLayout.EAST, btnLlegada);
                tarjetaControl.add(fila);
            }
        }

        SpanButton btnInfraccion = new SpanButton("Reportar\nInfracción");
        btnInfraccion.setUIID("BotonLogin");
        btnInfraccion.getTextAllStyles().setAlignment(Component.CENTER); 
        
        // Reduccion de fuente y paddings identica para el segundo boton
        btnInfraccion.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        btnInfraccion.getAllStyles().setPaddingUnit(com.codename1.ui.plaf.Style.UNIT_TYPE_DIPS);
        btnInfraccion.getAllStyles().setPadding(3, 3, 2, 2);
        
        btnInfraccion.addActionListener(e -> {
            if (cooperativa.getJornadasActivas().isEmpty()) {
                Dialog.show("Información", "No hay jornadas activas circulando.", "OK", null);
                return;
            }

            java.util.Vector<String> choferes = new java.util.Vector<>();
            for (com.github.project.model.Jornada j : cooperativa.getJornadasActivas()) {
                choferes.add(j.getConductor().getNombre());
            }
            com.codename1.ui.ComboBox<String> cmbChofer = new com.codename1.ui.ComboBox<>(choferes);

            com.codename1.ui.ComboBox<String> cmbNivel = new com.codename1.ui.ComboBox<>("LEVE", "MODERADA", "GRAVE");

            com.codename1.ui.TextArea txtDetalle = new com.codename1.ui.TextArea(5, 20);
            txtDetalle.setHint("Ej: Exceso de velocidad");
            txtDetalle.setSingleLineTextArea(false);
            TextField txtFecha = new TextField("", "DD/MM/YYYY");
            TextField txtHora = new TextField("", "HH:MM (24h)");
            txtFecha.setUIID("CampoTexto");
            txtHora.setUIID("CampoTexto");
            
            Command cmdEnviar = new Command("Enviar");
            Command cmdCancelar = new Command("Cancelar");
            
            Command resultado = Dialog.show(
                "Nueva Infracción", 
                BoxLayout.encloseY(
                    new Label("Conductor:"), cmbChofer,
                    new Label("Nivel de Infracción:"), cmbNivel,
                    new Label("Fecha:"), txtFecha,
                    new Label("Hora:"), txtHora,
                    new Label("Detalle:"), txtDetalle
                ), 
                new Command[] { cmdEnviar, cmdCancelar }
            );
            
            if (resultado == cmdEnviar) {
                if (cmbChofer.getSelectedItem() == null || txtDetalle.getText().trim().isEmpty() || txtHora.getText().trim().isEmpty()) {
                    Dialog.show("Error", "Debe llenar todos los campos requeridos.", "OK", null);
                    return;
                }
                com.github.project.model.Conductor infractor = null;
                for (com.github.project.model.Usuario u : cooperativa.getUsuarios()) {
                     if (cmbChofer.getSelectedItem().equals(u.getNombre())) {
                          infractor = (com.github.project.model.Conductor) u;
                          break;
                     }
                }
                if (infractor != null) {
                     java.util.List<com.github.project.model.Infraccion> lista = new java.util.ArrayList<>(infractor.getInfracciones());
                     lista.add(new com.github.project.model.Infraccion(
                        "I-" + System.currentTimeMillis(), 
                        txtDetalle.getText(), 
                        cmbNivel.getSelectedItem(), 
                        txtFecha.getText() + " " + txtHora.getText(), 
                        "N/A", 
                        monitor.getNombre()
                     ));
                     infractor.setInfracciones(lista);
                     Dialog.show("Reportado", "Infracción registrada.", "OK", null);
                } else {
                     Dialog.show("Error", "Conductor no encontrado.", "OK", null);
                }
            }
        });
        contenedorCentral.addAll(
            tituloTarjeta, tarjetaControl, 
            btnInfraccion
        );
        
        this.add(BorderLayout.CENTER, contenedorCentral);
    }
}