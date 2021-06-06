package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UsuarioInsertarActivity extends AppCompatActivity {


    EditText editUsername;
    EditText editContrasena;
    EditText editNombre;
    EditText editCliente;

    private final String urlWeb = "http://192.168.1.6:4000/usuario_api_add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_insertar);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editContrasena = (EditText) findViewById(R.id.editContrasena);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editCliente = (EditText) findViewById(R.id.editCliente);
    }

    public void insertarUsuario(View view) {
        Usuario usuario = new Usuario();
        String regInsertados = "Registro Insertado";

        String username = editUsername.getText().toString();
        String contrasena = editContrasena.getText().toString();
        String nombre = editNombre.getText().toString();
        int codcliente = Integer.parseInt(editCliente.getText().toString());

        usuario.setUsernameUsuario(username);
        usuario.setContrasenaUsuario(contrasena);
        usuario.setNombreUsuario(nombre);
        usuario.setCodClienteUsuario(codcliente);

        String urlW = "";

        urlW = urlWeb+"?username="+username+"&password="+contrasena+"&fullname="+nombre+"&cliente_id="+codcliente+"&es_main="+0;
        Log.v("Url", urlW);
        String clienteW = ConsumoNodeJS.obtenerRespuestaPeticion(urlW, this);
        Toast.makeText(this,  regInsertados , Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View view) {
    }
}