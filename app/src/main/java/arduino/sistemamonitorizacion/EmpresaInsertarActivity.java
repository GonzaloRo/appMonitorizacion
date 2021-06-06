package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EmpresaInsertarActivity extends AppCompatActivity {

    EditText editNombre, editDireccion, editTelefono, editRubro, editFecha, editCorreo;

    private final String urlWeb = "http://192.168.1.6:4000/cliente_api_add";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_insertar);

        editNombre = (EditText) findViewById(R.id.editNombre);
        editDireccion = (EditText) findViewById(R.id.editDireccion);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editRubro = (EditText) findViewById(R.id.editRubro);
        editFecha = (EditText) findViewById(R.id.editFecha);
        editCorreo = (EditText) findViewById(R.id.editCorreo);

    }

    public void insertarEmpresa(View view) {

        String regInsertados = "Registro Insertado";

        String nombre = editNombre.getText().toString();
        String direccion = editDireccion.getText().toString();
        int telefono = Integer.parseInt(editTelefono.getText().toString());
        String rubro = editRubro.getText().toString();
        String fecha = editFecha.getText().toString();
        String correo = editCorreo.getText().toString();

        String urlW = "";

        urlW = urlWeb+"?nombre="+nombre+"&direccion="+direccion+"&telefono="+telefono+"&rubro="+rubro+"&fecha="+fecha+"&correo="+correo+"&temperatura="+1+"&humedad="+1+"&proximidad="+1;
        Log.v("Url", urlW);
        String clienteW = ConsumoNodeJS.obtenerRespuestaPeticion(urlW, this);
        Toast.makeText(this,  regInsertados , Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View view) {
    }
}