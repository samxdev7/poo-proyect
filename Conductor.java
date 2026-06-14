public class Conductor {

    private String nombre;
    private String licencia;
    private int edad;

    public Conductor() {
    }

    public Conductor(String nombre, String licencia, int edad) {
        this.nombre = nombre;
        this.licencia = licencia;
        this.edad = edad;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void conducir() {
        System.out.println("Conduciendo vehículo.");
    }

    public void reportarEstado() {
        System.out.println("Estado reportado.");
    }
}