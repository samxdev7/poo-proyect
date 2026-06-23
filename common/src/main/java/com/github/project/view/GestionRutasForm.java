package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.Button;
import com.codename1.ui.TextField;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Command;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import com.github.project.model.Cooperativa;
import com.github.project.model.GestorCooperativa;
import com.github.project.model.Monitor;
import com.github.project.model.PuntoControl;
import com.github.project.model.Ruta;
import com.github.project.model.Usuario;

public class GestionRutasForm extends Form {

    private GestorCooperativa gestor;
    private Cooperativa cooperativa;
    private java.util.List<PuntoControl> puntosControlDisponibles;

    public GestionRutasForm(GestorCooperativa gestor, Cooperativa cooperativa) {
        super(new BorderLayout());
        this.gestor = gestor;
        this.cooperativa = cooperativa;
        this.puntosControlDisponibles = new java.util.ArrayList<>();
        this.setUIID("FormGestor");
        maquetarVisual();
    }

    private void maquetarVisual() {
        Toolbar tb = getToolbar();
        tb.setTitle("Rutas y Controles");
        
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> {
            new InicioGestorForm(gestor, cooperativa).showBack();
        });

        Container centro = new Container(BoxLayout.y());
        centro.setScrollableY(true);

        Label lblRutas = new Label("Creación de Rutas");
        lblRutas.setUIID("SubtituloSeccion");
        
        Button btnNuevaRuta = new Button("Crear Nueva Ruta");
        btnNuevaRuta.setUIID("BotonLogin");
        btnNuevaRuta.addActionListener(e -> ejecutarCrearRuta());

        Label lblPuntos = new Label("Puntos de Control");
        lblPuntos.setUIID("SubtituloSeccion");

        Button btnNuevoPunto = new Button("Crear Punto de Control");
        btnNuevoPunto.setUIID("BotonLogin");
        btnNuevoPunto.addActionListener(e -> ejecutarCrearPuntoControl());

        Button btnVincular = new Button("Asignar Punto a Ruta");
        btnVincular.setUIID("BotonLogin");
        btnVincular.addActionListener(e -> ejecutarVincularPuntoARuta());

        centro.addAll(lblRutas, btnNuevaRuta, lblPuntos, btnNuevoPunto, btnVincular);
        this.add(BorderLayout.CENTER, centro);
    }
    
    private void ejecutarCrearRuta() {
        TextField txtNombre = new TextField("", "Ej: Ruta Universitaria");
        TextField txtOrigen = new TextField("", "Punto de salida");
        TextField txtDestino = new TextField("", "Punto de llegada");
        TextField txtDistancia = new TextField("", "Distancia (Km)");
        
        txtNombre.setUIID("CampoTexto");
        txtOrigen.setUIID("CampoTexto");
        txtDestino.setUIID("CampoTexto");
        txtDistancia.setUIID("CampoTexto");

        Command cmdGuardar = new Command("Guardar");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado = Dialog.show(
            "Nueva Ruta", 
            BoxLayout.encloseY(
                new Label("Nombre de la ruta:"), txtNombre,
                new Label("Origen:"), txtOrigen,
                new Label("Destino:"), txtDestino,
                new Label("Distancia Total (km):"), txtDistancia
            ), 
            new Command[] { cmdGuardar, cmdCancelar }
        );
        
        if (resultado == cmdGuardar && !txtNombre.getText().trim().isEmpty()) {
            double km = 0.0;
            try {
                km = Double.parseDouble(txtDistancia.getText().trim());
            } catch (Exception ex) {}
            
            String id = "R0" + (cooperativa.getRutas().size() + 1);
            Ruta nueva = new Ruta(id, txtNombre.getText(), txtOrigen.getText(), txtDestino.getText(), "06:00", "22:00", 120, km);
            cooperativa.getRutas().add(nueva);
            Dialog.show("Éxito", "Ruta " + txtNombre.getText() + " creada correctamente.", "OK", null);
        }
    }
    
    private void ejecutarCrearPuntoControl() {
        java.util.Vector<String> listaMonitores = new java.util.Vector<>();
        for (Usuario u : cooperativa.getUsuarios()) {
            if ("MONITOR".equalsIgnoreCase(u.getTipoUsuario()) && u.isActivo() && u instanceof Monitor) {
                listaMonitores.add(u.getNombre() + " (" + ((Monitor)u).getIdMonitor() + ")");
            }
        }
        boolean hayMonitores = !listaMonitores.isEmpty();
        ComboBox<String> cmbMonitor = new ComboBox<>(listaMonitores);
        
        if (!hayMonitores) {
            Dialog.show("Error", "No hay monitores activos para asignar.", "OK", null);
            return;
        }

        TextField txtUbicacion = new TextField("", "Lugar físico");
        TextField txtHora = new TextField("", "Ej: 14:00");
        txtUbicacion.setUIID("CampoTexto");
        txtHora.setUIID("CampoTexto");

        Command cmdGuardar = new Command("Guardar");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado = Dialog.show(
            "Nuevo Punto de Control", 
            BoxLayout.encloseY(
                new Label("Ubicación:"), txtUbicacion,
                new Label("Hora Programada:"), txtHora,
                new Label("Monitor a Cargo:"), cmbMonitor
            ), 
            new Command[] { cmdGuardar, cmdCancelar }
        );
        
        if (resultado == cmdGuardar && !txtUbicacion.getText().trim().isEmpty()) {
            Monitor monitorAsignado = null;
            for (Usuario u : cooperativa.getUsuarios()) {
                if (u instanceof Monitor) {
                    Monitor m = (Monitor) u;
                    if (cmbMonitor.getSelectedItem().equals(m.getNombre() + " (" + m.getIdMonitor() + ")")) {
                        monitorAsignado = m;
                        break;
                    }
                }
            }
            
            String id = "PC" + System.currentTimeMillis();
            PuntoControl pc = new PuntoControl(id, txtUbicacion.getText(), txtHora.getText(), monitorAsignado);
            puntosControlDisponibles.add(pc); 
            
            if (monitorAsignado != null) {
                java.util.List<PuntoControl> lista = new java.util.ArrayList<>(monitorAsignado.getPuntosDeControlAsignados());
                lista.add(pc);
                monitorAsignado.setPuntosDeControlAsignados(lista);
            }
            
            Dialog.show("Éxito", "Punto asignado a " + (monitorAsignado!=null?monitorAsignado.getNombre():""), "OK", null);
        }
    }

    private void ejecutarVincularPuntoARuta() {
        if (cooperativa.getRutas().isEmpty() || puntosControlDisponibles.isEmpty()) {
            Dialog.show("Atención", "Debe tener al menos una ruta y un punto de control creados.", "OK", null);
            return;
        }
        
        java.util.Vector<String> listaRutas = new java.util.Vector<>();
        for (Ruta r : cooperativa.getRutas()) {
            listaRutas.add(r.getNombreDeRuta());
        }
        ComboBox<String> cmbRutas = new ComboBox<>(listaRutas);
        
        java.util.Vector<String> listaPuntos = new java.util.Vector<>();
        for (PuntoControl pc : puntosControlDisponibles) {
            listaPuntos.add(pc.getUbicacion() + " - " + pc.getHoraProgramada());
        }
        ComboBox<String> cmbPuntos = new ComboBox<>(listaPuntos);
        
        Command cmdGuardar = new Command("Asignar");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado = Dialog.show(
            "Vincular Punto a Ruta", 
            BoxLayout.encloseY(
                new Label("Seleccione Ruta:"), cmbRutas,
                new Label("Seleccione Punto:"), cmbPuntos
            ), 
            new Command[] { cmdGuardar, cmdCancelar }
        );
        
        if (resultado == cmdGuardar) {
            Ruta rutaElegida = null;
            for (Ruta r : cooperativa.getRutas()) {
                if (cmbRutas.getSelectedItem().equals(r.getNombreDeRuta())) {
                    rutaElegida = r;
                    break;
                }
            }
            
            PuntoControl puntoElegido = null;
            for (PuntoControl pc : puntosControlDisponibles) {
                if (cmbPuntos.getSelectedItem().equals(pc.getUbicacion() + " - " + pc.getHoraProgramada())) {
                    puntoElegido = pc;
                    break;
                }
            }
            
            if (rutaElegida != null && puntoElegido != null) {
                rutaElegida.getPuntosDeControl().add(puntoElegido);
                Dialog.show("Vinculado", "Punto asignado a la ruta exitosamente.", "OK", null);
            }
        }
    }
}
