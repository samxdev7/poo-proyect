public class Ruta {

    // Atributos
    private int idRuta;
    private String origen;
    private String destino;
    private double distanciaKm;

    // Constructor
    public Ruta(int idRuta, String origen, String destino, double distanciaKm) {
        this.idRuta = idRuta;
        this.origen = origen;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
    }

    // Getters y Setters
    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    // Método adicional
    public void mostrarRuta() {
        System.out.println("Ruta: " + origen + " -> " + destino + " | " + distanciaKm + " km");
    }
}