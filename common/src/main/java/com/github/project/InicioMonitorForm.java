package com.github.project;

import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout; 
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.Button;
import com.codename1.ui.TextField; 
import com.codename1.ui.Dialog;    
import com.codename1.ui.Command;   
import com.codename1.ui.Component; 
import com.codename1.ui.Font;
import com.codename1.components.MultiButton; 
import com.codename1.components.SpanButton;  

public class InicioMonitorForm extends Form {

    public InicioMonitorForm() {
        super(new BorderLayout());
        maquetarInterfazVisual();
    }

    private void maquetarInterfazVisual() {
        Toolbar tb = getToolbar();
        tb.setTitle("Punto de Control");
        
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
            new PantallaPerfilConductor().show();
        });
        
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            CoordinadorNavegacion.getInstancia().cerrarSesion();
        });

        // Contenedor central para scroll seguro de informacion de control y jornadas
        Container contenedorCentral = new Container(BoxLayout.y());
        contenedorCentral.setScrollableY(true); 

        // Barra de busqueda superior
        TextField txtBuscar = new TextField("", "Buscar parada, unidad o reporte...");
        txtBuscar.setUIID("CampoTexto");

        // Tarjeta de Punto de Control actual
        Label tituloTarjeta = new Label("Punto de Control Actual:");
        Container tarjetaControl = new Container(new TableLayout(2, 2));
        tarjetaControl.setUIID("TarjetaContenedor");
        tarjetaControl.add(new Label("Ubicación:")).add(new Label("Parada UNI"));
        tarjetaControl.add(new Label("Hora")).add(new Label("12:45 PM"));
        
        SpanButton btnLlegada = new SpanButton("Marcar\nLlegada");
        btnLlegada.setUIID("BotonLogin"); 
        btnLlegada.getTextAllStyles().setAlignment(Component.CENTER); 
        
        // Reducción de fuente
        btnLlegada.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        btnLlegada.getAllStyles().setPaddingUnit(com.codename1.ui.plaf.Style.UNIT_TYPE_DIPS);
        btnLlegada.getAllStyles().setPadding(3, 3, 2, 2); // Arriba, abajo, izquierda, derecha optimizados
        
        btnLlegada.addActionListener(e -> {
            boolean confirmar = Dialog.show(
                "Registrar Llegada", 
                "¿Confirmar arribo de unidad en Parada UNI?", 
                "Marcar", 
                "Cancelar"
            );
            if (confirmar) {
                Dialog.show("Éxito", "Llegada marcada correctamente.", "Entendido", null);
            }
        });

        SpanButton btnInfraccion = new SpanButton("Reportar\nInfracción");
        btnInfraccion.setUIID("BotonLogin");
        btnInfraccion.getTextAllStyles().setAlignment(Component.CENTER); 
        
        // Reduccion de fuente y paddings identica para el segundo boton
        btnInfraccion.getTextAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        btnInfraccion.getAllStyles().setPaddingUnit(com.codename1.ui.plaf.Style.UNIT_TYPE_DIPS);
        btnInfraccion.getAllStyles().setPadding(3, 3, 2, 2);
        
        btnInfraccion.addActionListener(e -> {
            TextField txtDetalle = new TextField("", "Ej: Exceso de velocidad unidad 04");
            txtDetalle.setUIID("CampoTexto");
            
            Command cmdEnviar = new Command("Enviar");
            Command cmdCancelar = new Command("Cancelar");
            
            Command resultado = Dialog.show(
                "Nueva Infracción", 
                BoxLayout.encloseY(new Label("Detalle del incidente:"), txtDetalle), 
                new Command[] { cmdEnviar, cmdCancelar }
            );
            
            if (resultado == cmdEnviar && !txtDetalle.getText().trim().isEmpty()) {
                Dialog.show("Reportado", "Infracción enviada al sistema.", "OK", null);
            }
        });
        //Historial de jornadas
        Label tituloJornadas = new Label("Mis Jornadas de Hoy:");
        tituloJornadas.setUIID("SubtituloSeccion");
        
        //Container para las jornadas 
        Container jornadasContainer = new Container(BoxLayout.y());
        jornadasContainer.setUIID("JornadasContainer");
        
        // Jornada 1 - Turno Mañana
        MultiButton jornada1 = new MultiButton("Turno Mañana:Completado");
        jornada1.setTextLine2("8 buses inspeccionados");
        jornadasContainer.add(jornada1);

        // Jornada 2 - Turno Tarde
        MultiButton jornada2 = new MultiButton("Turno Tarde:Activo");
        jornada2.setTextLine2("Monitoreo en desarrollo:Parada UNI");
        jornadasContainer.add(jornada2);

        // ========================================================
        // Agrega componentes al contenedor central
        contenedorCentral.addAll(
            txtBuscar, 
            tituloTarjeta, tarjetaControl, 
            tituloJornadas
        );
        //Agrega el Container de jornadas
        contenedorCentral.add(jornadasContainer);
        
        this.add(BorderLayout.CENTER, contenedorCentral);

        Container navInferior = new Container(new GridLayout(1, 2));
        
        // Añadimos los botones optimizados en tamaño
        navInferior.addAll(btnLlegada, btnInfraccion);
        
        // Se monta fijo en el area sur del formulario
        this.add(BorderLayout.SOUTH, navInferior);
    }
}