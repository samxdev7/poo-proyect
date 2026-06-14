public class PuntoControl {

    // Atributos
    private int idPunto;
    private String ubicacion;
    private String tipoControl; // Ej: seguridad, peaje, revisión

    // Constructor
    public PuntoControl(int idPunto, String ubicacion, String tipoControl) {
        this.idPunto = idPunto;
        this.ubicacion = ubicacion;
        this.tipoControl = tipoControl;
    }

    // Getters y Setters
    public int getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(int idPunto) {
        this.idPunto = idPunto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipoControl() {
        return tipoControl;
    }

    public void setTipoControl(String tipoControl) {
        this.tipoControl = tipoControl;
    }

    // Método adicional
    public void mostrarPunto() {
        System.out.println("Punto de control en: " + ubicacion + " | Tipo: " + tipoControl);
    }
}