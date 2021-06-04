package arduino.sistemamonitorizacion;

public class Empresa {
    private  String nombreEmpresa;
    private  String direccionEmpresa;
    private  String telefonoEmpresa;
    private  String rubroEmpresa;
    private  String fechaEmpresa;
    private  String correoEmpresa;

    public Empresa() {
    }

    public Empresa(String nombreEmpresa, String direccionEmpresa, String telefonoEmpresa, String rubroEmpresa, String fechaEmpresa, String correoEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        this.direccionEmpresa = direccionEmpresa;
        this.telefonoEmpresa = telefonoEmpresa;
        this.rubroEmpresa = rubroEmpresa;
        this.fechaEmpresa = fechaEmpresa;
        this.correoEmpresa = correoEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getRubroEmpresa() {
        return rubroEmpresa;
    }

    public void setRubroEmpresa(String rubroEmpresa) {
        this.rubroEmpresa = rubroEmpresa;
    }

    public String getFechaEmpresa() {
        return fechaEmpresa;
    }

    public void setFechaEmpresa(String fechaEmpresa) {
        this.fechaEmpresa = fechaEmpresa;
    }

    public String getCorreoEmpresa() {
        return correoEmpresa;
    }

    public void setCorreoEmpresa(String correoEmpresa) {
        this.correoEmpresa = correoEmpresa;
    }
}
