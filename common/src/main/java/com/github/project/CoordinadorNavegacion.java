package com.github.project;

import com.codename1.ui.Dialog;

public final class CoordinadorNavegacion {
    
    private static CoordinadorNavegacion instancia;
    private Usuario usuarioAutenticado;

    private CoordinadorNavegacion() {}

    public static synchronized CoordinadorNavegacion getInstancia() {
        if (instancia == null) {
            instancia = new CoordinadorNavegacion();
        }
        return instancia;
    }

    public void registrarSesion(Usuario usuario) {
        this.usuarioAutenticado = usuario;
        despacharPantallaRaiz();
    }

    public void cerrarSesion() {
        // Cuadro interactivo con opción Si/No 
        boolean confirmarCierre = Dialog.show( 
                null,
               "¿Quieres cerrar sesión?",
                "Sí",
                "No"
        );

        // Si el usuario presiona "Si", procedemos a cerrar la sesion
        if (confirmarCierre) {
            this.usuarioAutenticado = null;
            new LoginForm().show();
        }
        /*Si presiona "No", el método termina y el usuario se mantiene en
        su pantalla actual*/
    }

    public void despacharPantallaRaiz() {
        if (usuarioAutenticado == null || !usuarioAutenticado.isActivo()) { 
            new LoginForm().show();
            return;
        }

        String rol = usuarioAutenticado.getTipoUsuario().toUpperCase();
        
        switch (rol) {
            case "CONDUCTOR" -> new InicioConductorForm().show();
            case "MONITOR" -> new InicioMonitorForm().show();
            case "GESTOR" -> new InicioGestorForm().show();
            default -> new LoginForm().show();
        }
    }
    
    public Usuario getUsuarioAutenticado() {
        return this.usuarioAutenticado;
    }
}