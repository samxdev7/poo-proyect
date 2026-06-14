public class Infraccion {

    // Atributos
    private int idInfraccion;
    private String descripcion;
    private double multa;
    private String gravedad; // leve, media, grave

    // Constructor
    public Infraccion(int idInfraccion, String descripcion, double multa, String gravedad) {
        this.idInfraccion = idInfraccion;
        this.descripcion = descripcion;
        this.multa = multa;
        this.gravedad = gravedad;
    }

    // Getters y Setters
    public int getIdInfraccion() {
        return idInfraccion;
    }

    public void setIdInfraccion(int idInfraccion) {
        this.idInfraccion = idInfraccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    // Método adicional
    public void mostrarInfraccion() {
        System.out.println("Infracción: " + descripcion +
                " | Multa: $" + multa +
                " | Gravedad: " + gravedad);
    }
}