package com.github.project.view;

import com.codename1.ui.Dialog;
import com.github.project.model.Usuario;
import com.github.project.model.Cooperativa;
import com.github.project.model.GestorCooperativa;

public final class CoordinadorNavegacion {
    
    private static CoordinadorNavegacion instancia;
    private Usuario usuarioAutenticado;
    private Cooperativa cooperativaGlobal;

    private CoordinadorNavegacion() {
        cooperativaGlobal = new Cooperativa("RUC-123", "Transportes UNI", "Managua");
        cooperativaGlobal.getUsuarios().add(new GestorCooperativa(
                "U01", "root", "root", "GESTOR", true, 
                cooperativaGlobal, "GES-01"));
    }

    public static synchronized CoordinadorNavegacion getInstancia() {
        if (instancia == null) {
            instancia = new CoordinadorNavegacion();
        }
        return instancia;
    }

    public Cooperativa getCooperativaGlobal() {
        return cooperativaGlobal;
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

        // Delegación polimórfica a la capa de modelo
        usuarioAutenticado.mostrarMenuPropio();
    }
    
    public Usuario getUsuarioAutenticado() {
        return this.usuarioAutenticado;
    }
}