package com.github.project;

import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.Button;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;

public class InicioConductorForm extends Form {

    public InicioConductorForm() {
        super(new BorderLayout());
        this.setUIID("FormConductor"); // Vincula esta pantalla con tu bloque de css
        maquetarInterfazVisual();
    }

    private void maquetarInterfazVisual() {
        Toolbar tb = getToolbar();
        tb.setTitle("Conductor");
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            CoordinadorNavegacion.getInstancia().cerrarSesion();
        });

        Container contenedorCentral = new Container(BoxLayout.y());
        contenedorCentral.setScrollableY(true);

        // Tarjeta de consulta con TableLayout 
        Container tarjetaJornada = new Container(new TableLayout(3, 2));
        tarjetaJornada.setUIID("TarjetaContenedor");
        
        Label lblVehiculoTitulo = new Label("Vehículo:");
        Label lblVehiculoValor = new Label("Bus UNI-102");
        Label lblRutaTitulo = new Label("Ruta asignada:");
        Label lblRutaValor = new Label("Ruta 114");
        Label lblEstadoTitulo = new Label("Estado:");
        Label lblEstado = new Label("EN RUTA");
        
        lblVehiculoTitulo.setTickerEnabled(false);
        lblVehiculoValor.setTickerEnabled(false);
        lblRutaTitulo.setTickerEnabled(false);
        lblRutaValor.setTickerEnabled(false); 
        lblEstadoTitulo.setTickerEnabled(false);
        lblEstado.setTickerEnabled(false);
        
        lblEstado.setUIID("EstadoActivo");

        tarjetaJornada.add(lblVehiculoTitulo).add(lblVehiculoValor);
        tarjetaJornada.add(lblRutaTitulo).add(lblRutaValor);
        tarjetaJornada.add(lblEstadoTitulo).add(lblEstado);

        Label tituloControl = new Label("Siguiente hito del viaje:");
        tituloControl.setTickerEnabled(false);
        
        // Container con BoxLayout Vertical
        Container proxPuntoControl = new Container(BoxLayout.y());
        proxPuntoControl.setUIID("MultiButton"); // Reutiliza el diseño de tarjeta del CSS

        Label hitoLinea1 = new Label("Rotonda Universitaria");
        Label hitoLinea2 = new Label("Hora programada: 14:30 PM");

        hitoLinea1.setTickerEnabled(false); 
        hitoLinea2.setTickerEnabled(false); 

        proxPuntoControl.addAll(hitoLinea1, hitoLinea2);

        contenedorCentral.addAll(tarjetaJornada, tituloControl, proxPuntoControl);
        this.add(BorderLayout.CENTER, contenedorCentral);

        // Barra inferior estatica de navegación movil
        Container navInferior = new Container(new BoxLayout(BoxLayout.X_AXIS));
        navInferior.setUIID("NavInferior");

        Button btnPerfil = new Button("Mi Perfil");
        Button btnHistorial = new Button("Historial");

        btnPerfil.addActionListener(e -> new PantallaPerfilConductor().show());
        btnHistorial.addActionListener(e -> new PantallaHistorialConductor().show());

        navInferior.addAll(btnPerfil, btnHistorial);
        this.add(BorderLayout.SOUTH, navInferior);
    }
}