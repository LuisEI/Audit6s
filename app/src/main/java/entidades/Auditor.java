package entidades;

public class Auditor {

    private int id_auditor;
    private String auditor;

    private String clave;

    public Auditor() {

    }

    public int getId_auditor() {
        return id_auditor;
    }

    public void setId_auditor(int id_auditor) {
        this.id_auditor = id_auditor;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
