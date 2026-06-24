package com.github.project.view;

import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;

import com.github.project.model.Cooperativa;
import com.github.project.model.GestorCooperativa;
import com.github.project.model.Usuario;
import com.github.project.model.Conductor;
import com.github.project.model.Monitor;

public class ConsultarUsuariosForm extends Form {

    private GestorCooperativa gestor;
    private Cooperativa cooperativa;

    public ConsultarUsuariosForm(GestorCooperativa gestor, Cooperativa cooperativa) {
        super(new BorderLayout());
        this.gestor = gestor;
        this.cooperativa = cooperativa;
        this.setUIID("FormGestor");
        maquetarVisual();
    }

    private void maquetarVisual() {
        Toolbar tb = getToolbar();
        tb.setTitle("Directorio de Personal");
        
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> {
            new InicioGestorForm(gestor, cooperativa).showBack();
        });

        Container centro = new Container(BoxLayout.y());
        centro.setScrollableY(true);

        Label lblTitulo = new Label("Lista de Usuarios Registrados");
        lblTitulo.setUIID("SubtituloSeccion");
        centro.add(lblTitulo);

        if (cooperativa.getUsuarios().isEmpty()) {
            centro.add(new Label("No hay usuarios registrados en el sistema."));
        } else {
            for (Usuario u : cooperativa.getUsuarios()) {
                Container tarjeta = new Container(new TableLayout(4, 2));
                tarjeta.setUIID("TarjetaContenedor");
                
                tarjeta.add(new Label("Nombre:")).add(new Label(u.getNombre()));
                tarjeta.add(new Label("Rol:")).add(new Label(u.getTipoUsuario()));
                
                Label lblEstado = new Label(u.isActivo() ? "ACTIVO" : "INACTIVO");
                lblEstado.setUIID(u.isActivo() ? "EstadoActivo" : "EstadoInactivo");
                tarjeta.add(new Label("Estado:")).add(lblEstado);

                if (u instanceof Conductor) {
                    tarjeta.add(new Label("Licencia:")).add(new Label(((Conductor) u).getLicenciaDeConducir()));
                } else if (u instanceof Monitor) {
                    tarjeta.add(new Label("ID Monitor:")).add(new Label(((Monitor) u).getIdMonitor()));
                } else if (u instanceof GestorCooperativa) {
                    tarjeta.add(new Label("ID Gestor:")).add(new Label(((GestorCooperativa) u).getIdGestor()));
                }

                centro.add(tarjeta);
            }
        }

        this.add(BorderLayout.CENTER, centro);
    }
}
