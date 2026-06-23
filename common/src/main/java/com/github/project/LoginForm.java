package com.github.project;

import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Button;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

public class LoginForm extends Form {

    public LoginForm() {
        super("Acceso al Sistema", new BoxLayout(BoxLayout.Y_AXIS));
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        getToolbar().setHidden(true);
        
        Label etiquetaTitulo = new Label("Inicie Sesión, PorFavor");
        // Reutiliza tu estilo de titulos para mantener la consistencia
        etiquetaTitulo.setUIID("SubtituloSeccion"); 

        TextField campoUsuario = new TextField("", "Usuario (driver / monitor / gestor)");
        TextField campoPassword = new TextField("", "Contraseña");
        campoPassword.setConstraint(TextField.PASSWORD);
        
        Button botonIngresar = new Button("Iniciar Sesión");
        Label etiquetaError = new Label("Acceso inválido o cuenta inactiva");
        
        // Mapeo exacto con tus selectores
        campoUsuario.setUIID("CampoTexto");
        campoPassword.setUIID("CampoTexto");
        botonIngresar.setUIID("BotonLogin");
        etiquetaError.setUIID("LabelError");
        
        etiquetaError.setHidden(true);

        botonIngresar.addActionListener(new ActionListener<ActionEvent>() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtUser = campoUsuario.getText().trim();
                String txtPass = campoPassword.getText();
                Usuario mockUsuario = null;
                
                if ("driver".equalsIgnoreCase(txtUser)) {
                    mockUsuario = new Conductor("U01", "Cisneros", "123", "CONDUCTOR", true, null,"DL-12345678",true);
                } else if ("monitor".equalsIgnoreCase(txtUser)) {
                    mockUsuario = new Monitor("U02", "Monitor UNI", "123", "MONITOR", true, null,"MON-001");
                } else if ("gestor".equalsIgnoreCase(txtUser)) {
                    mockUsuario = new GestorCooperativa("U03", "Admin Cooperativa", "123", "GESTOR", true, null,"GES-001");
                }   
                
                // Entra directo si el objeto se logro crear con exito
                if (mockUsuario != null) {
               CoordinadorNavegacion.getInstancia().registrarSesion(mockUsuario);
            
               // Te manda directo a la pantalla del chofer para ver el diseño
               if ("chofer".equals(txtUser)) {
                new InicioConductorForm().show();
              }
             }else {
             etiquetaError.setHidden(false);
              LoginForm.this.revalidate();
              }
            }
        });

        this.addAll(etiquetaTitulo,campoUsuario, campoPassword, botonIngresar, etiquetaError);
    }
}