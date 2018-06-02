package entidades;

public class HallazgoDetalle {

    private int id_detalle;
    private int id_hallazgo;
    private String descripcion;

    public HallazgoDetalle() {

    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_hallazgo() {
        return id_hallazgo;
    }

    public void setId_hallazgo(int id_hallazgo) {
        this.id_hallazgo = id_hallazgo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
