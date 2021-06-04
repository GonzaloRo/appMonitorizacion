package arduino.sistemamonitorizacion;

public class Usuario {

    private  String usernameUsuario;
    private  String contrasenaUsuario;
    private  String nombreUsuario;
    private int codClienteUsuario;

    public Usuario() {
    }

    public Usuario(String usernameUsuario, String contrasenaUsuario, String nombreUsuario, int codClienteUsuario) {
        this.usernameUsuario = usernameUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.nombreUsuario = nombreUsuario;
        this.codClienteUsuario = codClienteUsuario;
    }

    public String getUsernameUsuario() {
        return usernameUsuario;
    }

    public void setUsernameUsuario(String usernameUsuario) {
        this.usernameUsuario = usernameUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCodClienteUsuario() {
        return codClienteUsuario;
    }

    public void setCodClienteUsuario(int codClienteUsuario) {
        this.codClienteUsuario = codClienteUsuario;
    }
}
