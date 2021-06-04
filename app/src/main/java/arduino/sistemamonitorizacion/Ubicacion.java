package arduino.sistemamonitorizacion;

public class Ubicacion {

    private  String nombreUbicacion;
    private  String latUbicacion;
    private  String logUbicacion;
    private int codClienteUbicacion;

    public Ubicacion() {
    }

    public Ubicacion(String nombreUbicacion, String latUbicacion, String logUbicacion, int codClienteUbicacion) {
        this.nombreUbicacion = nombreUbicacion;
        this.latUbicacion = latUbicacion;
        this.logUbicacion = logUbicacion;
        this.codClienteUbicacion = codClienteUbicacion;
    }

    public String getNombreUbicacion() {
        return nombreUbicacion;
    }

    public void setNombreUbicacion(String nombreUbicacion) {
        this.nombreUbicacion = nombreUbicacion;
    }

    public String getLatUbicacion() {
        return latUbicacion;
    }

    public void setLatUbicacion(String latUbicacion) {
        this.latUbicacion = latUbicacion;
    }

    public String getLogUbicacion() {
        return logUbicacion;
    }

    public void setLogUbicacion(String logUbicacion) {
        this.logUbicacion = logUbicacion;
    }

    public int getCodClienteUbicacion() {
        return codClienteUbicacion;
    }

    public void setCodClienteUbicacion(int codClienteUbicacion) {
        this.codClienteUbicacion = codClienteUbicacion;
    }
}
