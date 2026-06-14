public class Monitor {

    private String nombre;
    private String codigo;
    private String turno;

    public Monitor() {
    }

    public Monitor(String nombre, String codigo, String turno) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.turno = turno;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void monitorearVehiculo() {
        System.out.println("Monitoreando vehículo.");
    }

    public void registrarIncidente() {
        System.out.println("Incidente registrado.");
    }
}