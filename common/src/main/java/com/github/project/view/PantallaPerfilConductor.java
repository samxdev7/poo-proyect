package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Label;
import com.codename1.ui.Container;
import com.codename1.ui.Button; 
import com.codename1.ui.Dialog;
import com.github.project.model.Usuario;
import com.github.project.model.Conductor;
import com.github.project.model.Monitor;

public class PantallaPerfilConductor extends Form {
    public PantallaPerfilConductor() {
        super("Mi Perfil", BoxLayout.y());
        Toolbar tb = getToolbar();
        
        /*Ajuste dinámico: despacharPantallaRaiz() devuelve al Conductor
        a su inicio original,pero evita que el Monitor o Gestor se queden 
        atrapados en la pantalla del chofer al volver.*/
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> {
            CoordinadorNavegacion.getInstancia().despacharPantallaRaiz();
        });
        
        // Detectamos el usuario activo del sistema
        Usuario usuarioActivo = CoordinadorNavegacion.getInstancia().getUsuarioAutenticado();
        
        Container tarjetaDatos = new Container(BoxLayout.y());
        tarjetaDatos.setUIID("TarjetaContenedor");
        
        if (usuarioActivo != null) {
            String rol = usuarioActivo.getTipoUsuario().toUpperCase();
            
            if ("CONDUCTOR".equals(rol)) {
                String licencia = ((Conductor) usuarioActivo).getLicenciaDeConducir();
                tarjetaDatos.addAll(
                    new Label("Conductor: " + usuarioActivo.getNombre()), 
                    new Label("Licencia: " + (licencia != null ? licencia : "No registrada")), 
                    new Label("Estado Operativo: " + (usuarioActivo.isActivo() ? "Activo" : "Inactivo"))
                );
            } else if ("MONITOR".equals(rol)) {
                String idEmpleado = ((Monitor) usuarioActivo).getIdMonitor();
                tarjetaDatos.addAll(
                    new Label("Monitor: " + usuarioActivo.getNombre()), 
                    new Label("ID Empleado: " + (idEmpleado != null ? idEmpleado : "No registrado")), 
                    new Label("Estado Operativo: " + (usuarioActivo.isActivo() ? "Activo" : "Inactivo"))
                );
            } else {
                tarjetaDatos.addAll(
                    new Label("Gestor: " + usuarioActivo.getNombre()), 
                    new Label("Rol de Sistema: " + usuarioActivo.getTipoUsuario()), 
                    new Label("Estado Operativo: " + (usuarioActivo.isActivo() ? "Activo" : "Inactivo"))
                );
            }
            
            this.add(tarjetaDatos);
            
            //Boton global de reportes insertado al fondo para todos los perfiles
            Button btnReporte = new Button("Generar Reporte");
            btnReporte.setTickerEnabled(false);
            btnReporte.setUIID("BotonLogin"); 
            
            btnReporte.addActionListener(e -> {
                String detallesReporte = "BITÁCORA DE ACTIVIDAD \n" +
                                         "Usuario: " + usuarioActivo.getNombre() + "\n" +
                                         "Rol: " + usuarioActivo.getTipoUsuario() + "\n" +
                                         "Estado: " + (usuarioActivo.isActivo() ? "Activo" : "Inactivo") + "\n" +
                                         "Sistema sincronizado correctamente.";
                
                Dialog.show("Reporte de Perfil", detallesReporte, "Entendido", null);
            });
            
            this.add(btnReporte);
            
        } else {
            tarjetaDatos.add(new Label("No se detectó ninguna sesión activa."));
            this.add(tarjetaDatos);
        }
    }
}