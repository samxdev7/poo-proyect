package com.github.project;

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

public class InicioGestorForm extends Form {

    public InicioGestorForm() {
        super(new BorderLayout());
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

        MultiButton jActivas = new MultiButton("Jornadas en Curso: 8");
        jActivas.setTextLine2("Presione para simular cierre rápido de turnos");
        jActivas.addActionListener(e -> {
            Dialog.show("Simulación", "Cierre rápido de turnos ejecutado con éxito.", "OK", null);
        });

        centro.addAll(txtBuscarGestor, lblTitulo, jActivas);

        Label lblGestion = new Label("Panel de Decisiones Administrativas:");
        lblGestion.setUIID("SubtituloSeccion"); 
        centro.add(lblGestion);
        
        Button btnRegistrarUser = new Button("Registrar Nuevo Personal");
        btnRegistrarUser.setUIID("BotonLogin"); 
        btnRegistrarUser.addActionListener(e -> ejecutarRegistrarUsuario());
        
        Button btnModificarUser = new Button("Modificar Personal");
        btnModificarUser.setUIID("BotonLogin");
        btnModificarUser.addActionListener(e -> ejecutarModificarUsuario());
        
        Button btnJornada = new Button("Asignar Nueva Jornada");
        btnJornada.setUIID("BotonLogin");
        btnJornada.addActionListener(e -> ejecutarGestionJornada());

        Button btnVehiculo = new Button("Control Operativo de Flota");
        btnVehiculo.setUIID("BotonLogin");
        btnVehiculo.addActionListener(e -> ejecutarGestionVehiculo());

        centro.addAll(btnRegistrarUser, btnModificarUser, btnJornada, btnVehiculo);
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
        
        Command cmdGuardar = new Command("Registrar");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado = Dialog.show(
            "Alta de Personal Seguro", 
            BoxLayout.encloseY(
                new Label("Nombre Operario:"), txtNombre, 
                new Label("Contraseña de Acceso:"), txtPassword,
                new Label("Rol Asignado del Sistema:"), cmbRol
            ), 
            new Command[] { cmdGuardar, cmdCancelar }
        );
        
        if (resultado == cmdGuardar && !txtNombre.getText().trim().isEmpty() && !txtPassword.getText().trim().isEmpty()) {
            String rolSeleccionado = cmbRol.getSelectedItem();
            Dialog.show("Éxito", "Usuario '" + txtNombre.getText() + "' con rol [" + rolSeleccionado + "] registrado.", "OK", null);
        }
    }

    private void ejecutarModificarUsuario() {
        TextField txtBusqueda = new TextField("", "Nombre del usuario a cambiar");
        txtBusqueda.setUIID("CampoTexto");
        
        ComboBox<String> cmbNuevoRol = new ComboBox<>("Conductor", "Monitor", "Gestor");
        cmbNuevoRol.setUIID("CampoTexto");
        
        // Crea contenedor con los campos
        Container contenidoDialog = BoxLayout.encloseY(
            new Label("Buscar Trabajador:"), txtBusqueda, 
            new Label("Cambiar a nuevo Rol:"), cmbNuevoRol
        );
        
        // Crea Dialog manualmente
        Dialog dialog = new Dialog("Modificar Personal");
        dialog.setLayout(new BorderLayout());
        dialog.add(BorderLayout.CENTER, contenidoDialog);
        
        // Deshabilitar animaciones
        dialog.setTransitionInAnimator(null);      
        dialog.setTransitionOutAnimator(null);     
        
        // Agrega botones
        Container botonesContainer = new Container(BoxLayout.x());
        Button btnModificar = new Button("Actualizar");
        Button btnCancelar = new Button("Cancelar");
        
        btnModificar.addActionListener(e -> {
            if (!txtBusqueda.getText().trim().isEmpty()) {
                dialog.dispose();
                Dialog.show("Actualizado", "El usuario ha sido reconfigurado a " + cmbNuevoRol.getSelectedItem(), "OK", null);
            }
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        botonesContainer.addAll(btnModificar, btnCancelar);
        dialog.add(BorderLayout.SOUTH, botonesContainer);
        
        // Mostrar Dialog estatico
        dialog.show();
    }

    private void ejecutarGestionJornada() {
        TextField txtChofer = new TextField("", "Nombre del chofer");
        TextField txtRuta = new TextField("", "Ej: Ruta 114 o Ruta UNI");
        txtChofer.setUIID("CampoTexto");
        txtRuta.setUIID("CampoTexto");
        
        Command cmdAsignar = new Command("Asignar");
        Command cmdCancelar = new Command("Cancelar");
        
        Command resultado = Dialog.show(
            "Planificar Jornada", 
            BoxLayout.encloseY(new Label("Conductor de Unidad:"), txtChofer, new Label("Recorrido / Línea:"), txtRuta), 
            new Command[] { cmdAsignar, cmdCancelar }
        );
        
        if (resultado == cmdAsignar && !txtChofer.getText().trim().isEmpty()) {
            Dialog.show("Asignado", "Nueva jornada enlazada correctamente.", "OK", null);
        }
    }
    
    private void ejecutarGestionVehiculo() {
        TextField txtUnidad = new TextField("", "Número de bus");
        TextField txtEstado = new TextField("", "Activo / En Taller");
        txtUnidad.setUIID("CampoTexto");
        txtEstado.setUIID("CampoTexto");
        
        // Crea contenedor con los campos
        Container contenidoDialog = BoxLayout.encloseY(
            new Label("ID de Unidad (Flota):"), txtUnidad, 
            new Label("Estado de Operación:"), txtEstado
        );
        
        // Crea Dialog manualmente
        Dialog dialog = new Dialog("Control Operativo de Flota");
        dialog.setLayout(new BorderLayout());
        dialog.add(BorderLayout.CENTER, contenidoDialog);
        
        // Deshabilitar animaciones 
        dialog.setTransitionInAnimator(null);      
        dialog.setTransitionOutAnimator(null);     
        
        // Agregar botones
        Container botonesContainer = new Container(BoxLayout.x());
        Button btnActualizar = new Button("Actualizar");
        Button btnCancelar = new Button("Cancelar");
        
        btnActualizar.addActionListener(e -> {
            if (!txtUnidad.getText().trim().isEmpty()) {
                dialog.dispose();
                Dialog.show("Actualizado", "Estado de la Unidad " + txtUnidad.getText() + " sincronizado.", "OK", null);
            }
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        botonesContainer.addAll(btnActualizar, btnCancelar);
        dialog.add(BorderLayout.SOUTH, botonesContainer);
        
        // Mostrar Dialog estatico
        dialog.show();
    }
}