public class GestorCooperativa {

    private String nombre;
    private String cargo;
    private int aniosExperiencia;

    public GestorCooperativa() {
    }

    public GestorCooperativa(String nombre, String cargo, int aniosExperiencia) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.aniosExperiencia = aniosExperiencia;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public void gestionarCooperativa() {
        System.out.println("Gestionando la cooperativa.");
    }

    public void generarReporte() {
        System.out.println("Generando reporte.");
    }
}