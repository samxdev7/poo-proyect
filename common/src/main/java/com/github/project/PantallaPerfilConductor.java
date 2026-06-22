package com.github.project;

import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Label;
import com.codename1.ui.Container;
import com.codename1.ui.Button; 
import com.codename1.ui.Dialog;
import com.codename1.components.MultiButton;

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
            
            // Mantiene al 100% tus etiquetas y datos actuales
            if ("CONDUCTOR".equals(rol)) {
                tarjetaDatos.addAll(
                    new Label("Conductor: " + usuarioActivo.getNombre()), 
                    new Label("Licencia: Profesional 5ta"), 
                    new Label("Estado Operativo: Activo")
                );
            } else {
                // Muestra su informacion correspondiente sin datos de tránsito
                tarjetaDatos.addAll(
                    new Label("Usuario: " + usuarioActivo.getNombre()), 
                    new Label("Rol de Sistema: " + usuarioActivo.getTipoUsuario()), 
                    new Label("Estado Operativo: Activo")
                );
            }
            
            this.add(tarjetaDatos);
            
            //Se muestra tu historial de infracciones 
        if ("CONDUCTOR".equals(rol)) {
            Container inf1 = new Container(BoxLayout.y());
            inf1.setUIID("TarjetaInfraccion"); 

            Label inf1Line1 = new Label("21/06/2026 - Exceso de velocidad");
            Label inf1Line2 = new Label("Nivel: Moderado. Parada Universitaria.");

            // Desactivamos la animacion
            inf1Line1.setTickerEnabled(false);
            inf1Line2.setTickerEnabled(false);

            // Asignamos los UIID para el diseño del CSS
            inf1Line1.setUIID("InfraccionLinea1");
            inf1Line2.setUIID("InfraccionLinea2");

            inf1.addAll(inf1Line1, inf1Line2);

            Container inf2 = new Container(BoxLayout.y());
            inf2.setUIID("TarjetaInfraccion"); 

            Label inf2Line1 = new Label("15/05/2026 - Desvío de ruta");
            Label inf2Line2 = new Label("Nivel: Leve. Reportado por monitor.");

            // Desactivamos la animación
            inf2Line1.setTickerEnabled(false);
            inf2Line2.setTickerEnabled(false);

            // Asignamos los mismos UIID para heredar el tamaño del CSS
            inf2Line1.setUIID("InfraccionLinea1");
            inf2Line2.setUIID("InfraccionLinea2");

            inf2.addAll(inf2Line1, inf2Line2);
            
            // Agregamos los nuevos contenedores directo al Form
            this.addAll(inf1, inf2);
        }
            
            //Boton global de reportes insertado al fondo para todos los perfiles
            Button btnReporte = new Button("Generar Reporte");
            btnReporte.setTickerEnabled(false);
            btnReporte.setUIID("BotonLogin"); // Reutiliza tu estilo CSS de botones principales
            
            btnReporte.addActionListener(e -> {
                String detallesReporte = "BITACORA DE ACTIVIDAD \n" +
                                         "Usuario: " + usuarioActivo.getNombre() + "\n" +
                                         "Rol: " + usuarioActivo.getTipoUsuario() + "\n" +
                                         "Fecha: 21/06/2026\n" +
                                         "Operaciones registradas: 12 acciones\n" +
                                         "Sincronización remota: Exitosa";
                
                Dialog.show("Reporte de Actividades", detallesReporte, "Entendido", null);
            });
            
            this.add(btnReporte);
            
        } else {
            tarjetaDatos.add(new Label("No se detectó ninguna sesión activa."));
            this.add(tarjetaDatos);
        }
    }
}