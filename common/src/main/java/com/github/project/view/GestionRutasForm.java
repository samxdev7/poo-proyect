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
    public GestionRutasForm(GestorCooperativa gestor, Cooperativa cooperativa) {
        super(new BorderLayout());
        this.gestor = gestor;
        this.cooperativa = cooperativa;
        this.setUIID("FormGestor");
        maquetarVisual();
    }

    private Container wrapForDialog(Container body) {
        Container wrapper = new Container(new BorderLayout());
        wrapper.add(BorderLayout.CENTER, body);
        
        Label spacer = new Label();
        spacer.getAllStyles().setPaddingUnit(com.codename1.ui.plaf.Style.UNIT_TYPE_PIXELS);
        spacer.getAllStyles().setPaddingLeft(com.codename1.ui.Display.getInstance().getDisplayWidth() - 80);
        wrapper.add(BorderLayout.NORTH, spacer);
        
        return wrapper;
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
        
        Button btnVerTodos = new Button("Consultar Puntos Registrados");
        btnVerTodos.setUIID("BotonLogin");
        btnVerTodos.addActionListener(e -> ejecutarVerTodosLosPuntos());

        centro.addAll(lblRutas, btnNuevaRuta, lblPuntos, btnNuevoPunto, btnVincular, btnVerTodos);
        this.add(BorderLayout.CENTER, centro);
    }
    
    private boolean isFormatoHoraValido(String hora) {
        if (hora == null) return false;
        int colonIdx = hora.indexOf(':');
        if (colonIdx < 1 || colonIdx > 2 || colonIdx == hora.length() - 1) return false;
        String hStr = hora.substring(0, colonIdx);
        String mStr = hora.substring(colonIdx + 1);
        if (mStr.length() != 2) return false;
        try {
            int h = Integer.parseInt(hStr);
            int m = Integer.parseInt(mStr);
            return h >= 0 && h <= 23 && m >= 0 && m <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private java.util.List<PuntoControl> getPuntosGlobales() {
        java.util.List<PuntoControl> todos = new java.util.ArrayList<>();
        for (Usuario u : cooperativa.getUsuarios()) {
            if (u instanceof Monitor) {
                todos.addAll(((Monitor)u).getPuntosDeControlAsignados());
            }
        }
        return todos;
    }
    
    private void ejecutarVerTodosLosPuntos() {
        java.util.List<PuntoControl> todos = getPuntosGlobales();
        if (todos.isEmpty()) {
            Dialog.show("Información", "No existen puntos de control registrados.", "OK", null);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (PuntoControl pc : todos) {
            sb.append("- ").append(pc.getUbicacion()).append(" | Hora: ").append(pc.getHoraProgramada())
              .append("\n  Monitor: ").append(pc.getMonitorACargo() != null ? pc.getMonitorACargo().getNombre() : "Ninguno")
              .append("\n\n");
        }
        com.codename1.ui.TextArea ta = new com.codename1.ui.TextArea(sb.toString());
        ta.setEditable(false);
        ta.setUIID("Label");
        ta.getAllStyles().setBgTransparency(0);
        Dialog.show("Todos los Puntos Globales", ta, new Command("Cerrar"));
    }
    
    private void ejecutarCrearRuta() {
        Form f1 = new Form("Nueva Ruta", new BorderLayout());
        f1.setUIID("FormGestor");
        f1.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());
        
        TextField txtNombre = new TextField("", "Ej: Ruta Universitaria");
        TextField txtOrigen = new TextField("", "Punto de salida");
        
        com.codename1.ui.TextArea txtDestino = new com.codename1.ui.TextArea(4, 20);
        txtDestino.setHint("Punto de llegada");
        txtDestino.setSingleLineTextArea(false);
        
        TextField txtDistancia = new TextField("", "Distancia (Km)");
        
        TextField txtHoraInicio = new TextField("", "Ej: 06:00");
        TextField txtHoraFin = new TextField("", "Ej: 22:00");
        
        txtNombre.setUIID("CampoTexto");
        txtOrigen.setUIID("CampoTexto");
        txtDestino.setUIID("CampoTexto");
        txtDistancia.setUIID("CampoTexto");
        txtHoraInicio.setUIID("CampoTexto");
        txtHoraFin.setUIID("CampoTexto");

        Container bodyCrearRuta = BoxLayout.encloseY(
            new Label("Nombre de la ruta:"), txtNombre,
            new Label("Origen:"), txtOrigen,
            new Label("Destino:"), txtDestino,
            new Label("Distancia Total (km):"), txtDistancia,
            new Label("Hora de Inicio:"), txtHoraInicio,
            new Label("Hora de Fin:"), txtHoraFin
        );
        bodyCrearRuta.setScrollableY(true);
        
        Button btnGuardar = new Button("Guardar Ruta");
        btnGuardar.setUIID("BotonLogin");
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()) {
                Dialog.show("Error", "El nombre de la ruta es obligatorio.", "OK", null);
                return;
            }
            if (!txtHoraInicio.getText().trim().isEmpty() && !isFormatoHoraValido(txtHoraInicio.getText().trim())) {
                Dialog.show("Error", "La hora de inicio debe tener formato 24h (HH:MM).", "OK", null);
                return;
            }
            if (!txtHoraFin.getText().trim().isEmpty() && !isFormatoHoraValido(txtHoraFin.getText().trim())) {
                Dialog.show("Error", "La hora de fin debe tener formato 24h (HH:MM).", "OK", null);
                return;
            }
            String nombreIngresado = txtNombre.getText().trim();
            for (Ruta r : cooperativa.getRutas()) {
                if (r.getNombreDeRuta().equalsIgnoreCase(nombreIngresado)) {
                    Dialog.show("Error", "Ya existe una ruta con ese nombre.", "OK", null);
                    return;
                }
            }
            
            double km = 0.0;
            try {
                km = Double.parseDouble(txtDistancia.getText().trim());
            } catch (Exception ex) {}
            
            String id = "R0" + (cooperativa.getRutas().size() + 1);
            String hInicio = txtHoraInicio.getText().trim().isEmpty() ? "06:00" : txtHoraInicio.getText();
            String hFin = txtHoraFin.getText().trim().isEmpty() ? "22:00" : txtHoraFin.getText();
            
            Ruta nueva = new Ruta(id, txtNombre.getText(), txtOrigen.getText(), txtDestino.getText(), hInicio, hFin, 120, km);
            cooperativa.getRutas().add(nueva);
            
            this.showBack();
            Dialog.show("Éxito", "Ruta " + txtNombre.getText() + " creada correctamente.", "OK", null);
        });
        
        f1.add(BorderLayout.CENTER, bodyCrearRuta);
        f1.add(BorderLayout.SOUTH, btnGuardar);
        f1.show();
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

        Form f1 = new Form("Nuevo Punto de Control", new BorderLayout());
        f1.setUIID("FormGestor");
        f1.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());

        TextField txtUbicacion = new TextField("", "Lugar físico");
        TextField txtHora = new TextField("", "Ej: 14:00");
        txtUbicacion.setUIID("CampoTexto");
        txtHora.setUIID("CampoTexto");

        Container bodyCrearPunto = BoxLayout.encloseY(
            new Label("Ubicación:"), txtUbicacion,
            new Label("Hora Programada:"), txtHora,
            new Label("Monitor a Cargo:"), cmbMonitor
        );
        bodyCrearPunto.setScrollableY(true);
        
        Button btnGuardar = new Button("Guardar Punto");
        btnGuardar.setUIID("BotonLogin");
        btnGuardar.addActionListener(e -> {
            if (txtUbicacion.getText().trim().isEmpty()) {
                Dialog.show("Error", "La ubicación es requerida.", "OK", null);
                return;
            }
            if (!isFormatoHoraValido(txtHora.getText().trim())) {
                Dialog.show("Error", "La hora debe tener formato 24h (HH:MM).", "OK", null);
                return;
            }
            // Verificar duplicidad del punto de control
            for (PuntoControl pc : getPuntosGlobales()) {
                if (pc.getUbicacion().equalsIgnoreCase(txtUbicacion.getText().trim()) && pc.getHoraProgramada().equalsIgnoreCase(txtHora.getText().trim())) {
                    Dialog.show("Error", "Ya existe un Punto de Control en esa ubicación y hora.", "OK", null);
                    return;
                }
            }
            
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
            
            if (monitorAsignado != null) {
                java.util.List<PuntoControl> lista = new java.util.ArrayList<>(monitorAsignado.getPuntosDeControlAsignados());
                lista.add(pc);
                monitorAsignado.setPuntosDeControlAsignados(lista);
            }
            
            this.showBack();
            Dialog.show("Éxito", "Punto asignado a " + (monitorAsignado!=null?monitorAsignado.getNombre():""), "OK", null);
        });
        
        f1.add(BorderLayout.CENTER, bodyCrearPunto);
        f1.add(BorderLayout.SOUTH, btnGuardar);
        f1.show();
    }

    private void ejecutarVincularPuntoARuta() {
        if (cooperativa.getRutas().isEmpty() || getPuntosGlobales().isEmpty()) {
            Dialog.show("Atención", "Debe tener al menos una ruta y un punto de control creados.", "OK", null);
            return;
        }
        
        Form f1 = new Form("Vincular Punto a Ruta", new BorderLayout());
        f1.setUIID("FormGestor");
        f1.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());
        
        java.util.Vector<String> listaRutas = new java.util.Vector<>();
        for (Ruta r : cooperativa.getRutas()) {
            listaRutas.add(r.getNombreDeRuta());
        }
        ComboBox<String> cmbRutas = new ComboBox<>(listaRutas);
        
        java.util.Vector<String> listaPuntos = new java.util.Vector<>();
        for (PuntoControl pc : getPuntosGlobales()) {
            listaPuntos.add(pc.getUbicacion() + " - " + pc.getHoraProgramada());
        }
        ComboBox<String> cmbPuntos = new ComboBox<>(listaPuntos);
        
        Container bodyVincular = BoxLayout.encloseY(
            new Label("Seleccione Ruta:"), cmbRutas,
            new Label("Seleccione Punto:"), cmbPuntos
        );
        bodyVincular.setScrollableY(true);
        
        Button btnGuardar = new Button("Asignar");
        btnGuardar.setUIID("BotonLogin");
        btnGuardar.addActionListener(e -> {
            Ruta rutaElegida = null;
            for (Ruta r : cooperativa.getRutas()) {
                if (cmbRutas.getSelectedItem().equals(r.getNombreDeRuta())) {
                    rutaElegida = r;
                    break;
                }
            }
            
            PuntoControl puntoElegido = null;
            for (PuntoControl pc : getPuntosGlobales()) {
                if (cmbPuntos.getSelectedItem().equals(pc.getUbicacion() + " - " + pc.getHoraProgramada())) {
                    puntoElegido = pc;
                    break;
                }
            }
            
            if (rutaElegida != null && puntoElegido != null) {
                rutaElegida.getPuntosDeControl().add(puntoElegido);
                this.showBack();
                Dialog.show("Vinculado", "Punto asignado a la ruta exitosamente.", "OK", null);
            }
        });
        
        f1.add(BorderLayout.CENTER, bodyVincular);
        f1.add(BorderLayout.SOUTH, btnGuardar);
        f1.show();
    }
}
