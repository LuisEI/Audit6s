package entidades;

public class Excepcion {

    private int id;

    private String numero_tablet;
    private String fecha;
    private String hora;
    private String version;
    private String memoria;
    private String error;

    public Excepcion() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero_tablet() {
        return numero_tablet;
    }

    public void setNumero_tablet(String numero_tablet) {
        this.numero_tablet = numero_tablet;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
