package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Button;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.github.project.model.Cooperativa;
import com.github.project.model.Usuario;


public class LoginForm extends Form {

    public LoginForm() {
        super("Acceso al Sistema", new BoxLayout(BoxLayout.Y_AXIS));
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        getToolbar().setHidden(true);
        
        Label etiquetaTitulo = new Label("Inicie Sesión, Por Favor");
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
                
                Cooperativa coop = CoordinadorNavegacion.getInstancia().getCooperativaGlobal();
                Usuario usuarioEncontrado = null;
                
                for (Usuario u : coop.getUsuarios()) {
                    if (u.verificarUsuario(txtUser, txtPass)) {
                        usuarioEncontrado = u;
                        break;
                    }
                }
                
                if (usuarioEncontrado != null) {
                    CoordinadorNavegacion.getInstancia().registrarSesion(usuarioEncontrado);
                } else {
                    etiquetaError.setHidden(false);
                    LoginForm.this.revalidate();
                }
            }
        });

        this.addAll(etiquetaTitulo,campoUsuario, campoPassword, botonIngresar, etiquetaError);
    }
}