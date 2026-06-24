package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;      
import com.codename1.ui.TextField;   
import com.codename1.ui.Dialog;      
import com.codename1.ui.Command;     
import com.codename1.ui.ComboBox;

import com.github.project.model.Cooperativa;
import com.github.project.model.GestorCooperativa;
import com.github.project.model.Usuario;
import com.github.project.model.Monitor;
import com.github.project.model.Conductor;

public class InicioGestorForm extends Form {

    private GestorCooperativa gestor;
    private Cooperativa cooperativa;
    private boolean menuConstruido = false;

    public InicioGestorForm(GestorCooperativa gestor, Cooperativa cooperativa) {
        super(new BorderLayout());
        this.gestor = gestor;
        this.cooperativa = cooperativa;
        this.setUIID("FormGestor"); 
        maquetarVisual();
    }

    private Container wrapForDialog(Container body) {
        Container wrapper = new Container(new BorderLayout());
        wrapper.add(BorderLayout.CENTER, body);
        
        // Espaciador invisible para FORZAR el ancho del diálogo
        Label spacer = new Label();
        spacer.getAllStyles().setPaddingUnit(com.codename1.ui.plaf.Style.UNIT_TYPE_PIXELS);
        spacer.getAllStyles().setPaddingLeft(com.codename1.ui.Display.getInstance().getDisplayWidth() - 80);
        wrapper.add(BorderLayout.NORTH, spacer);
        
        return wrapper;
    }

    private void maquetarVisual() {
        this.removeAllCommands();
        Toolbar tb = getToolbar();
        tb.setUIID("ToolbarGestor"); 
        tb.setTitle("Cooperativa");
        
        if (!menuConstruido) {
            tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
                new PantallaPerfilConductor().show();
            });
            
            tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_EXIT_TO_APP, e -> {
                CoordinadorNavegacion.getInstancia().cerrarSesion();
            });
        }

        Container centro = new Container(BoxLayout.y());
        centro.setScrollableY(true);

        Label lblTitulo = new Label("Resumen de Control Operativo:");
        lblTitulo.setUIID("SubtituloSeccion");

        MultiButton jActivas = new MultiButton("Jornadas en Curso " + cooperativa.getJornadasActivas().size());
        jActivas.setTextLine2("Flota total: " + cooperativa.getFlotas().size() + " | Empleados: " + cooperativa.getUsuarios().size());
        jActivas.addActionListener(e -> {
            com.codename1.ui.TextArea ta = new com.codename1.ui.TextArea(cooperativa.generarReporteGeneralDeActividad());
            ta.setEditable(false);
            ta.setUIID("Label");
            ta.getAllStyles().setBgTransparency(0);
            Command btnCerrar = new Command("Cerrar");
            Dialog.show("Reporte Operativo", ta, btnCerrar);
        });



        Label lblGestion = new Label("Panel de Decisiones Administrativas");
        lblGestion.setUIID("SubtituloSeccion"); 
        
        Button btnRegistrar = new Button("Registrar Nuevo Personal");
        btnRegistrar.setUIID("BotonLogin"); 
        btnRegistrar.addActionListener(e -> ejecutarRegistrarUsuario());
        
        Button btnModificar = new Button("Modificar Personal");
        btnModificar.setUIID("BotonLogin");
        btnModificar.addActionListener(e -> ejecutarModificarUsuario());
        
        Button btnJornada = new Button("Asignar Nueva Jornada");
        btnJornada.setUIID("BotonLogin");
        btnJornada.addActionListener(e -> ejecutarGestionJornada());

        Button btnFlota = new Button("Control Operativo de Flota");
        btnFlota.setUIID("BotonLogin");
        btnFlota.addActionListener(e -> ejecutarGestionVehiculo());

        Button btnRutas = new Button("Gestión de Rutas y Controles");
        btnRutas.setUIID("BotonLogin");
        btnRutas.addActionListener(e -> new GestionRutasForm(gestor, cooperativa).show());

        Button btnConsultar = new Button("Consultar Personal Registrado");
        btnConsultar.setUIID("BotonLogin");
        btnConsultar.addActionListener(e -> new ConsultarUsuariosForm(gestor, cooperativa).show());

        centro.addAll(lblTitulo, jActivas, lblGestion, btnRegistrar, btnModificar, btnConsultar, btnJornada, btnFlota, btnRutas);
        this.add(BorderLayout.CENTER, centro);

        if (!menuConstruido) {
            tb.addMaterialCommandToSideMenu("Registrar Usuario", 
                FontImage.MATERIAL_PERSON_ADD, e -> ejecutarRegistrarUsuario());
            tb.addMaterialCommandToSideMenu("Modificar Usuario", 
                FontImage.MATERIAL_EDIT, e -> ejecutarModificarUsuario());
            tb.addMaterialCommandToSideMenu("Gestionar Flota", 
                FontImage.MATERIAL_DIRECTIONS_BUS, e -> ejecutarGestionVehiculo());
            menuConstruido = true;
        }
    }

    private void ejecutarRegistrarUsuario() {
        Form f1 = new Form("Registrar Usuario (Paso 1)", new BorderLayout());
        f1.setUIID("FormGestor");
        f1.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());
        
        TextField txtNombre = new TextField("", "Nombre completo");
        txtNombre.setUIID("CampoTexto");
        
        TextField txtPassword = new TextField("", "Ingrese una contraseña segura");
        txtPassword.setUIID("CampoTexto");
        txtPassword.setConstraint(TextField.PASSWORD);
        
        ComboBox<String> cmbRol = new ComboBox<>("Conductor", "Monitor", "Gestor");
        cmbRol.setUIID("CampoTexto"); 
        
        Container body1 = BoxLayout.encloseY(
            new Label("Nombre completo:"), txtNombre, 
            new Label("Contraseña provisoria:"), txtPassword, 
            new Label("Rol operativo:"), cmbRol
        );
        body1.setScrollableY(true);
        
        Button btnSiguiente = new Button("Siguiente");
        btnSiguiente.setUIID("BotonLogin");
        btnSiguiente.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
                Dialog.show("Error", "Nombre y contraseña son obligatorios.", "OK", null);
                return;
            }
            
            String rolSeleccionado = cmbRol.getSelectedItem();
            String etiquetaEspecial = "CONDUCTOR".equalsIgnoreCase(rolSeleccionado) ? "Licencia de Conducir:" : "ID de Empleado:";
            
            Form f2 = new Form("Datos de " + rolSeleccionado, new BorderLayout());
            f2.setUIID("FormGestor");
            f2.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> f1.showBack());
            
            TextField txtIdEspecial = new TextField("", etiquetaEspecial);
            txtIdEspecial.setUIID("CampoTexto");
            
            Container body2 = BoxLayout.encloseY(
                new Label(etiquetaEspecial), txtIdEspecial
            );
            body2.setScrollableY(true);
            
            Button btnGuardar = new Button("Guardar");
            btnGuardar.setUIID("BotonLogin");
            btnGuardar.addActionListener(e2 -> {
                if (txtIdEspecial.getText().trim().isEmpty()) {
                    Dialog.show("Error", "El dato especial es obligatorio.", "OK", null);
                    return;
                }
                
                Usuario nuevo;
                String idUnico = "U-" + System.currentTimeMillis();
                if ("GESTOR".equalsIgnoreCase(rolSeleccionado)) {
                    nuevo = new GestorCooperativa(idUnico, txtNombre.getText(), txtPassword.getText(), "GESTOR", true, cooperativa, txtIdEspecial.getText());
                } else if ("MONITOR".equalsIgnoreCase(rolSeleccionado)) {
                    nuevo = new Monitor(idUnico, txtNombre.getText(), txtPassword.getText(), "MONITOR", true, cooperativa, txtIdEspecial.getText());
                } else {
                    nuevo = new Conductor(idUnico, txtNombre.getText(), txtPassword.getText(), "CONDUCTOR", true, cooperativa, txtIdEspecial.getText(), true);
                }
                gestor.agregarUsuarioACooperativa(nuevo);
                
                this.removeAll();
                maquetarVisual();
                this.revalidate();
                
                this.showBack();
                Dialog.show("Éxito", "Usuario '" + txtNombre.getText() + "' registrado correctamente.", "OK", null);
            });
            
            f2.add(BorderLayout.CENTER, body2);
            f2.add(BorderLayout.SOUTH, btnGuardar);
            f2.show();
        });
        
        f1.add(BorderLayout.CENTER, body1);
        f1.add(BorderLayout.SOUTH, btnSiguiente);
        f1.show();
    }

    private void ejecutarModificarUsuario() {
        if (cooperativa.getUsuarios().isEmpty()) {
            Dialog.show("Atención", "No hay usuarios registrados para modificar.", "OK", null);
            return;
        }
        
        java.util.Vector<String> listaUsuarios = new java.util.Vector<>();
        for (Usuario u : cooperativa.getUsuarios()) {
            listaUsuarios.add(u.getNombre());
        }
        ComboBox<String> cmbUsuario = new ComboBox<>(listaUsuarios);

        Form f1 = new Form("Modificar Personal", new BorderLayout());
        f1.setUIID("FormGestor");
        f1.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());
        
        Container bodyMod1 = BoxLayout.encloseY(new Label("Seleccionar empleado:"), cmbUsuario);
        bodyMod1.setScrollableY(true);
        
        Button btnSeleccionar = new Button("Seleccionar");
        btnSeleccionar.setUIID("BotonLogin");
        btnSeleccionar.addActionListener(e -> {
            if (cmbUsuario.getSelectedItem() == null) return;
            String nombreBuscado = cmbUsuario.getSelectedItem();
            Usuario usuarioSeleccionado = null;
            for (Usuario u : cooperativa.getUsuarios()) {
                if (u.getNombre().equals(nombreBuscado)) {
                    usuarioSeleccionado = u;
                    break;
                }
            }
            
            if (usuarioSeleccionado != null) {
                Form f2 = new Form("Modificando a " + usuarioSeleccionado.getNombre(), new BorderLayout());
                f2.setUIID("FormGestor");
                f2.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> f1.showBack());
                
                TextField txtNuevaPassword = new TextField(usuarioSeleccionado.getContrasena(), "Nueva contraseña");
                txtNuevaPassword.setConstraint(TextField.PASSWORD);
                txtNuevaPassword.setUIID("CampoTexto");
                
                ComboBox<String> cmbEstado = new ComboBox<>("ACTIVO", "INACTIVO");
                cmbEstado.setSelectedItem(usuarioSeleccionado.isActivo() ? "ACTIVO" : "INACTIVO");
                cmbEstado.setUIID("CampoTexto");
                
                Container bodyMod2 = BoxLayout.encloseY(
                    new Label("Contraseña:"), txtNuevaPassword,
                    new Label("Estado Operativo:"), cmbEstado
                );
                bodyMod2.setScrollableY(true);
                
                Button btnGuardar = new Button("Guardar Cambios");
                btnGuardar.setUIID("BotonLogin");
                
                Usuario finalUsuarioSeleccionado = usuarioSeleccionado;
                btnGuardar.addActionListener(e3 -> {
                    if (txtNuevaPassword.getText().trim().isEmpty()) {
                        Dialog.show("Error", "La contraseña no puede estar vacía.", "OK", null);
                        return;
                    }
                    
                    finalUsuarioSeleccionado.cambiarContrasena(txtNuevaPassword.getText());
                    boolean estadoNuevo = "ACTIVO".equals(cmbEstado.getSelectedItem());
                    
                    if (estadoNuevo != finalUsuarioSeleccionado.isActivo()) {
                        if (estadoNuevo) {
                            gestor.activarUsuario(finalUsuarioSeleccionado.getIdUsuario());
                        } else {
                            gestor.desactivarUsuario(finalUsuarioSeleccionado.getIdUsuario());
                        }
                    }
                    
                    this.showBack();
                    Dialog.show("Éxito", "Usuario " + finalUsuarioSeleccionado.getNombre() + " actualizado correctamente.", "OK", null);
                });
                
                f2.add(BorderLayout.CENTER, bodyMod2);
                f2.add(BorderLayout.SOUTH, btnGuardar);
                f2.show();
            }
        });
        
        f1.add(BorderLayout.CENTER, bodyMod1);
        f1.add(BorderLayout.SOUTH, btnSeleccionar);
        f1.show();
    }

    private void ejecutarGestionJornada() {
        if (cooperativa.getFlotas().isEmpty() || cooperativa.getUsuarios().isEmpty()) {
            Dialog.show("Error", "Debe registrar al menos un vehículo y un conductor primero.", "OK", null);
            return;
        }

        java.util.Vector<String> listaChoferes = new java.util.Vector<>();
        for (Usuario u : cooperativa.getUsuarios()) {
            if ("CONDUCTOR".equalsIgnoreCase(u.getTipoUsuario())) {
                listaChoferes.add(u.getNombre());
            }
        }
        ComboBox<String> cmbChofer = new ComboBox<>(listaChoferes);
        
        java.util.Vector<String> listaVehiculos = new java.util.Vector<>();
        for (com.github.project.model.Vehiculo v : cooperativa.getFlotas()) {
            listaVehiculos.add(v.getPlaca());
        }
        ComboBox<String> cmbVehiculo = new ComboBox<>(listaVehiculos);
        
        java.util.Vector<String> listaRutas = new java.util.Vector<>();
        for (com.github.project.model.Ruta r : cooperativa.getRutas()) {
            listaRutas.add(r.getNombreDeRuta());
        }
        ComboBox<String> cmbRuta = new ComboBox<>(listaRutas);

        if (cooperativa.getRutas().isEmpty()) {
            Dialog.show("Atención", "Debe crear al menos una ruta en 'Gestión de Rutas' antes de asignar una jornada.", "OK", null);
            return;
        }

        Form f1 = new Form("Iniciar Jornada", new BorderLayout());
        f1.setUIID("FormGestor");
        f1.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());

        TextField txtFecha = new TextField("", "Ej: 15/05/2026");
        txtFecha.setUIID("CampoTexto");
        
        TextField txtHora = new TextField("", "Ej: 06:00 (HH:MM)");
        txtHora.setUIID("CampoTexto");
        
        if (!cooperativa.getRutas().isEmpty()) {
            txtHora.setText(cooperativa.getRutas().get(0).getHoraDeSalida());
        }
        
        cmbRuta.addActionListener(evt -> {
            for (com.github.project.model.Ruta r : cooperativa.getRutas()) {
                if (r.getNombreDeRuta().equals(cmbRuta.getSelectedItem())) {
                    txtHora.setText(r.getHoraDeSalida());
                    break;
                }
            }
        });

        Container bodyJornada = BoxLayout.encloseY(
            new Label("Conductor Disponible:"), cmbChofer, 
            new Label("Vehículo de la Flota:"), cmbVehiculo,
            new Label("Ruta Asignada:"), cmbRuta,
            new Label("Horario de Inicio:"), txtHora,
            new Label("Fecha de Inicio:"), txtFecha
        );
        bodyJornada.setScrollableY(true);
        
        Button btnAsignar = new Button("Asignar Jornada");
        btnAsignar.setUIID("BotonLogin");
        btnAsignar.addActionListener(e -> {
            if (cmbChofer.getSelectedItem() == null || cmbVehiculo.getSelectedItem() == null || cmbRuta.getSelectedItem() == null) {
                Dialog.show("Error", "Debe seleccionar opciones válidas.", "OK", null);
                return;
            }
            
            Conductor conductorSeleccionado = null;
            for (Usuario u : cooperativa.getUsuarios()) {
                if (cmbChofer.getSelectedItem().equals(u.getNombre())) {
                    conductorSeleccionado = (Conductor) u;
                    break;
                }
            }
            
            com.github.project.model.Vehiculo vehiculoSeleccionado = null;
            for (com.github.project.model.Vehiculo v : cooperativa.getFlotas()) {
                if (cmbVehiculo.getSelectedItem().equals(v.getPlaca())) {
                    vehiculoSeleccionado = v;
                    break;
                }
            }

            com.github.project.model.Ruta rutaSeleccionada = null;
            for (com.github.project.model.Ruta r : cooperativa.getRutas()) {
                if (cmbRuta.getSelectedItem().equals(r.getNombreDeRuta())) {
                    rutaSeleccionada = r;
                    break;
                }
            }

            if (conductorSeleccionado != null && vehiculoSeleccionado != null && rutaSeleccionada != null) {
                for (com.github.project.model.Jornada j : cooperativa.getJornadasActivas()) {
                    if (j.getConductor().equals(conductorSeleccionado) && j.getFecha().equalsIgnoreCase(txtFecha.getText())) {
                        Dialog.show("Error", "Este conductor ya tiene una jornada asignada en esa misma fecha.", "OK", null);
                        return;
                    }
                }

                String numJornada = "J0" + (cooperativa.getJornadasActivas().size() + cooperativa.getJornadasFinalizadas().size() + 1);
                String horaElegida = txtHora.getText().trim().isEmpty() ? rutaSeleccionada.getHoraDeSalida() : txtHora.getText().trim();
                String fechaHoraStr = txtFecha.getText().isEmpty() ? "Ahora" : txtFecha.getText() + " " + horaElegida;
                com.github.project.model.Jornada nuevaJornada = new com.github.project.model.Jornada(
                        numJornada, fechaHoraStr, vehiculoSeleccionado, conductorSeleccionado, rutaSeleccionada);
                
                cooperativa.getJornadasActivas().add(nuevaJornada);
                
                this.removeAll();
                maquetarVisual();
                this.revalidate();
                
                this.showBack();
                Dialog.show("Asignado", "Jornada " + numJornada + " iniciada correctamente.", "OK", null);
            }
        });
        
        f1.add(BorderLayout.CENTER, bodyJornada);
        f1.add(BorderLayout.SOUTH, btnAsignar);
        f1.show();
    }
    
    private void ejecutarGestionVehiculo() {
        Form f1 = new Form("Registro de Vehículo (Paso 1)", new BorderLayout());
        f1.setUIID("FormGestor");
        f1.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());
        
        TextField txtPlaca = new TextField("", "Ej: M-123456");
        TextField txtMarca = new TextField("", "Ej: Toyota");
        ComboBox<String> cmbTipo = new ComboBox<>("Diésel", "Eléctrico");
        
        txtPlaca.setUIID("CampoTexto");
        txtMarca.setUIID("CampoTexto");
        cmbTipo.setUIID("CampoTexto");
        
        Container bodyVehiculo1 = BoxLayout.encloseY(
            new Label("Placa:"), txtPlaca, 
            new Label("Marca/Modelo:"), txtMarca,
            new Label("Tipo de Vehículo:"), cmbTipo
        );
        bodyVehiculo1.setScrollableY(true);
        
        Button btnSiguiente = new Button("Siguiente");
        btnSiguiente.setUIID("BotonLogin");
        btnSiguiente.addActionListener(e -> {
            if (txtPlaca.getText().trim().isEmpty() || txtMarca.getText().trim().isEmpty()) {
                Dialog.show("Error", "Todos los campos de texto son requeridos.", "OK", null);
                return;
            }

            boolean esElectrico = "Eléctrico".equalsIgnoreCase(cmbTipo.getSelectedItem()) || "Electrico".equalsIgnoreCase(cmbTipo.getSelectedItem());
            
            Form f2 = new Form("Especificaciones del " + cmbTipo.getSelectedItem(), new BorderLayout());
            f2.setUIID("FormGestor");
            f2.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> f1.showBack());
            
            TextField txtDato1 = new TextField("", esElectrico ? "Capacidad Batería (kWh)" : "Capacidad Tanque (Litros)");
            TextField txtDato2 = new TextField("", esElectrico ? "Nivel de Carga (%)" : "Nivel de Combustible (Litros)");
            txtDato1.setUIID("CampoTexto");
            txtDato2.setUIID("CampoTexto");

            Container bodyVehiculo2 = BoxLayout.encloseY(
                new Label(esElectrico ? "Batería:" : "Tanque:"), txtDato1,
                new Label("Carga/Nivel Actual:"), txtDato2
            );
            bodyVehiculo2.setScrollableY(true);
            
            Button btnGuardar = new Button("Guardar Vehículo");
            btnGuardar.setUIID("BotonLogin");
            btnGuardar.addActionListener(e2 -> {
                double val1 = 0;
                double val2 = 0;
                try {
                    val1 = Double.parseDouble(txtDato1.getText().trim());
                    val2 = Double.parseDouble(txtDato2.getText().trim());
                } catch (Exception ex) {}

                com.github.project.model.Vehiculo v;
                if (esElectrico) {
                    v = new com.github.project.model.VehiculoElectrico(txtPlaca.getText(), txtMarca.getText(), "Standard", "Eléctrico", "Activo", "VE-" + txtPlaca.getText(), val1, val2, 0);
                } else {
                    v = new com.github.project.model.VehiculoDiesel(txtPlaca.getText(), txtMarca.getText(), "Standard", "Diésel", "Activo", "VD-" + txtPlaca.getText(), val1, val2);
                }
                gestor.agregarVehiculo(v);
                
                this.removeAll();
                maquetarVisual();
                this.revalidate();
                
                this.showBack();
                Dialog.show("Éxito", "Vehículo " + txtPlaca.getText() + " registrado y activo.", "OK", null);
            });
            
            f2.add(BorderLayout.CENTER, bodyVehiculo2);
            f2.add(BorderLayout.SOUTH, btnGuardar);
            f2.show();
        });
        
        f1.add(BorderLayout.CENTER, bodyVehiculo1);
        f1.add(BorderLayout.SOUTH, btnSiguiente);
        f1.show();
    }
}