package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Container;
import com.codename1.ui.Label;

public class PantallaHistorialConductor extends Form {
    public PantallaHistorialConductor() {
        super("Historial de Jornadas", BoxLayout.y());
        Toolbar tb = getToolbar();
        
        /*Agrega el comando directamente a la barra izquierda usando 
        el metodo correcto*/
        // Boton de back
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> {
            CoordinadorNavegacion.getInstancia().despacharPantallaRaiz();
        });

        //Usamos Container con el UIID MultiButton
        Container itemHistorial = new Container(BoxLayout.y());
        itemHistorial.setUIID("MultiButton"); 
        itemHistorial.setLeadComponent(null); // Evita conflictos de enfoque

        Label linea1 = new Label("Fecha: 21/06/2026");
        Label linea2 = new Label("Recorrido: 45 km ");
        
        /*Configuramos los estilos nativos para que herede la apariencia 
        exacta del MultiButton*/
        linea1.setUIID("MultiButtonLine1");
        linea2.setUIID("MultiButtonLine2");

        linea1.setTickerEnabled(false);
        linea2.setTickerEnabled(false);

        // Agregamos los textos al contenedor de la tarjeta
        itemHistorial.addAll(linea1, linea2);
        
        // Al tocar la tarjeta, salta tu Dialog
        itemHistorial.addPointerReleasedListener(e -> com.codename1.ui.Dialog.show(
            "Detalle de Jornada", 
            "Pasajeros transportados: 120\nViajes completos: 4\nInfracciones: 0", 
            "Entendido", null
        ));

        this.add(itemHistorial);
    }
}