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

    public InicioGestorForm(GestorCooperativa gestor, Cooperativa cooperativa) {
        super(new BorderLayout());
        this.gestor = gestor;
        this.cooperativa = cooperativa;
        this.setUIID("FormGestor"); 
        maquetarVisual();
    }

    private void maquetarVisual() {
        Toolbar tb = getToolbar();
        tb.setUIID("ToolbarGestor"); 
        tb.setTitle("Cooperativa");
        
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
            new PantallaPerfilConductor().show();
        });
        
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            CoordinadorNavegacion.getInstancia().cerrarSesion();
        });

        Container centro = new Container(BoxLayout.y());
        centro.setScrollableY(true);

        TextField txtBuscarGestor = new TextField("", "Buscar conductor, bus o rutas...");
        txtBuscarGestor.setUIID("CampoTexto");

        Label lblTitulo = new Label("Resumen de Control Operativo:");
        lblTitulo.setUIID("SubtituloSeccion");

        MultiButton jActivas = new MultiButton("Jornadas en Curso: " + cooperativa.getJornadasActivas().size());
        jActivas.setTextLine2("Flota total: " + cooperativa.getFlotas().size() + " | Empleados: " + cooperativa.getUsuarios().size());
        jActivas.addActionListener(e -> {
            Dialog.show("Reporte", cooperativa.generarReporteGeneralDeActividad(), "Cerrar", null);
        });



        Label lblGestion = new Label("Panel de Decisiones Administrativas:");
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

        centro.addAll(txtBuscarGestor, lblTitulo, jActivas, lblGestion, btnRegistrar, btnModificar, btnJornada, btnFlota, btnRutas);
        this.add(BorderLayout.CENTER, centro);

        tb.addMaterialCommandToSideMenu("Registrar Usuario", FontImage.MATERIAL_PERSON_ADD, e -> ejecutarRegistrarUsuario());
        tb.addMaterialCommandToSideMenu("Modificar Usuario", FontImage.MATERIAL_EDIT, e -> ejecutarModificarUsuario());
        tb.addMaterialCommandToSideMenu("Gestionar Flota", FontImage.MATERIAL_DIRECTIONS_BUS, e -> ejecutarGestionVehiculo());
    }

    private void ejecutarRegistrarUsuario() {
        TextField txtNombre = new TextField("", "Nombre completo");
        txtNombre.setUIID("CampoTexto");
        
        TextField txtPassword = new TextField("", "Mínimo 6 caracteres");
        txtPassword.setUIID("CampoTexto");
        txtPassword.setConstraint(TextField.PASSWORD);
        
        ComboBox<String> cmbRol = new ComboBox<>("Conductor", "Monitor", "Gestor");
        cmbRol.setUIID("CampoTexto"); 
        
        Command cmdSiguiente = new Command("Siguiente");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado1 = Dialog.show(
            "Registrar Usuario (Paso 1)", 
            BoxLayout.encloseY(
                new Label("Nombre completo:"), txtNombre, 
                new Label("Contraseña provisoria:"), txtPassword, 
                new Label("Rol operativo:"), cmbRol
            ), 
            new Command[] { cmdSiguiente, cmdCancelar }
        );
        
        if (resultado1 == cmdSiguiente) {
            if (txtNombre.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
                Dialog.show("Error", "Nombre y contraseña son obligatorios.", "OK", null);
                return;
            }
            
            String rolSeleccionado = cmbRol.getSelectedItem();
            String etiquetaEspecial = "CONDUCTOR".equalsIgnoreCase(rolSeleccionado) ? "Licencia de Conducir:" : "ID de Empleado:";
            
            TextField txtIdEspecial = new TextField("", etiquetaEspecial);
            txtIdEspecial.setUIID("CampoTexto");
            
            Command cmdGuardar = new Command("Guardar");
            
            Command resultado2 = Dialog.show(
                "Datos de " + rolSeleccionado, 
                BoxLayout.encloseY(
                    new Label(etiquetaEspecial), txtIdEspecial
                ), 
                new Command[] { cmdGuardar, cmdCancelar }
            );
            
            if (resultado2 == cmdGuardar) {
                if (txtIdEspecial.getText().trim().isEmpty()) {
                    Dialog.show("Error", "El dato especial es obligatorio.", "OK", null);
                    return;
                }
                
                Usuario nuevo;
                if ("GESTOR".equalsIgnoreCase(rolSeleccionado)) {
                    nuevo = new GestorCooperativa("UX", txtNombre.getText(), txtPassword.getText(), "GESTOR", true, cooperativa, txtIdEspecial.getText());
                } else if ("MONITOR".equalsIgnoreCase(rolSeleccionado)) {
                    nuevo = new Monitor("UX", txtNombre.getText(), txtPassword.getText(), "MONITOR", true, cooperativa, txtIdEspecial.getText());
                } else {
                    nuevo = new Conductor("UX", txtNombre.getText(), txtPassword.getText(), "CONDUCTOR", true, cooperativa, txtIdEspecial.getText(), true);
                }
                gestor.agregarUsuarioACooperativa(nuevo);
                this.removeAll();
                maquetarVisual();
                this.revalidate();
                Dialog.show("Éxito", "Usuario '" + txtNombre.getText() + "' registrado correctamente.", "OK", null);
            }
        }
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

        Command cmdSeleccionar = new Command("Seleccionar");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado1 = Dialog.show(
            "Modificar Personal (Paso 1)", 
            BoxLayout.encloseY(new Label("Seleccionar empleado:"), cmbUsuario), 
            new Command[] { cmdSeleccionar, cmdCancelar }
        );
        
        if (resultado1 == cmdSeleccionar && cmbUsuario.getSelectedItem() != null) {
            String nombreBuscado = cmbUsuario.getSelectedItem();
            Usuario usuarioSeleccionado = null;
            for (Usuario u : cooperativa.getUsuarios()) {
                if (u.getNombre().equals(nombreBuscado)) {
                    usuarioSeleccionado = u;
                    break;
                }
            }
            
            if (usuarioSeleccionado != null) {
                TextField txtNuevaPassword = new TextField(usuarioSeleccionado.getContrasena(), "Nueva contraseña");
                txtNuevaPassword.setUIID("CampoTexto");
                
                ComboBox<String> cmbEstado = new ComboBox<>("ACTIVO", "INACTIVO");
                cmbEstado.setSelectedItem(usuarioSeleccionado.isActivo() ? "ACTIVO" : "INACTIVO");
                cmbEstado.setUIID("CampoTexto");
                
                Command cmdGuardar = new Command("Guardar Cambios");
                
                Command resultado2 = Dialog.show(
                    "Modificando a " + usuarioSeleccionado.getNombre(), 
                    BoxLayout.encloseY(
                        new Label("Contraseña:"), txtNuevaPassword,
                        new Label("Estado Operativo:"), cmbEstado
                    ), 
                    new Command[] { cmdGuardar, cmdCancelar }
                );
                
                if (resultado2 == cmdGuardar) {
                    if (txtNuevaPassword.getText().trim().isEmpty()) {
                        Dialog.show("Error", "La contraseña no puede estar vacía.", "OK", null);
                        return;
                    }
                    
                    usuarioSeleccionado.cambiarContrasena(txtNuevaPassword.getText());
                    boolean estadoNuevo = "ACTIVO".equals(cmbEstado.getSelectedItem());
                    
                    if (estadoNuevo != usuarioSeleccionado.isActivo()) {
                        if (estadoNuevo) {
                            gestor.activarUsuario(usuarioSeleccionado.getIdUsuario());
                        } else {
                            gestor.desactivarUsuario(usuarioSeleccionado.getIdUsuario());
                        }
                    }
                    
                    Dialog.show("Éxito", "Usuario " + usuarioSeleccionado.getNombre() + " actualizado correctamente.", "OK", null);
                }
            }
        }
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

        Command cmdAsignar = new Command("Asignar");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado = Dialog.show(
            "Planificar Jornada", 
            BoxLayout.encloseY(
                new Label("Conductor Disponible:"), cmbChofer, 
                new Label("Vehículo de la Flota:"), cmbVehiculo,
                new Label("Ruta Asignada:"), cmbRuta
            ), 
            new Command[] { cmdAsignar, cmdCancelar }
        );
        
        if (resultado == cmdAsignar) {
            if (cmbChofer.getSelectedItem() == null || cmbVehiculo.getSelectedItem() == null || cmbRuta.getSelectedItem() == null) {
                Dialog.show("Error", "Debe seleccionar opciones válidas.", "OK", null);
                return;
            }
            
            // Buscar conductor
            Conductor conductorSeleccionado = null;
            for (Usuario u : cooperativa.getUsuarios()) {
                if (cmbChofer.getSelectedItem().equals(u.getNombre())) {
                    conductorSeleccionado = (Conductor) u;
                    break;
                }
            }
            
            // Buscar Vehículo
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
                String numJornada = "J0" + (cooperativa.getJornadasActivas().size() + cooperativa.getJornadasFinalizadas().size() + 1);
                com.github.project.model.Jornada nuevaJornada = new com.github.project.model.Jornada(
                        numJornada, "06:00 AM", vehiculoSeleccionado, conductorSeleccionado, rutaSeleccionada);
                
                cooperativa.getJornadasActivas().add(nuevaJornada);
                
                this.removeAll();
                maquetarVisual();
                this.revalidate();
                
                Dialog.show("Asignado", "Jornada " + numJornada + " iniciada correctamente.", "OK", null);
            }
        }
    }
    
    private void ejecutarGestionVehiculo() {
        TextField txtPlaca = new TextField("", "Ej: M-123456");
        TextField txtMarca = new TextField("", "Ej: Toyota");
        ComboBox<String> cmbTipo = new ComboBox<>("Diésel", "Eléctrico");
        
        txtPlaca.setUIID("CampoTexto");
        txtMarca.setUIID("CampoTexto");
        cmbTipo.setUIID("CampoTexto");
        
        Command cmdGuardar = new Command("Guardar");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado = Dialog.show(
            "Alta de Vehículo", 
            BoxLayout.encloseY(
                new Label("Placa:"), txtPlaca, 
                new Label("Marca/Modelo:"), txtMarca,
                new Label("Tipo de Combustible:"), cmbTipo
            ), 
            new Command[] { cmdGuardar, cmdCancelar }
        );
        
        if (resultado == cmdGuardar) {
            if (txtPlaca.getText().trim().isEmpty() || txtMarca.getText().trim().isEmpty()) {
                Dialog.show("Error", "Todos los campos de texto son requeridos.", "OK", null);
                return;
            }
            com.github.project.model.Vehiculo v;
            if ("Diésel".equals(cmbTipo.getSelectedItem())) {
                v = new com.github.project.model.VehiculoDiesel(txtPlaca.getText(), txtMarca.getText(), "Standard", "Diésel", "Activo", "VD-" + txtPlaca.getText(), 60, 60);
            } else {
                v = new com.github.project.model.VehiculoElectrico(txtPlaca.getText(), txtMarca.getText(), "Standard", "Eléctrico", "Activo", "VE-" + txtPlaca.getText(), 100, 100, 0);
            }
            gestor.agregarVehiculo(v);
            
            this.removeAll();
            maquetarVisual();
            this.revalidate();
            
            Dialog.show("Éxito", "Vehículo " + txtPlaca.getText() + " registrado y activo.", "OK", null);
        }
    }
}