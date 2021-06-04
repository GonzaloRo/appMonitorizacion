package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UbicacionInsertarActivity extends AppCompatActivity {

    EditText editNombre, editLat, editLon, editCliente;
    private final String urlWeb = "http://192.168.1.8:4000/ubicacion_api_add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_insertar);

        editNombre = (EditText) findViewById(R.id.editDireccion);
        editLat = (EditText) findViewById(R.id.editLat);
        editLon = (EditText) findViewById(R.id.editLon);
        editCliente = (EditText) findViewById(R.id.editCliente);

    }

    public void insertarUbicacion(View view) {

        String regInsertados = "Registro Insertado";

        String nombre = editNombre.getText().toString();
        String lat = editLat.getText().toString();
        String lon = editLon.getText().toString();
        int codCliente = Integer.parseInt(editCliente.getText().toString());

        String urlW = "";

        urlW = urlWeb+"?cliente_id="+codCliente+"&direccion="+nombre+"&lat="+lat+"&lon="+lon;
        Log.v("Url", urlW);
        String ubicacionW = ConsumoNodeJS.obtenerRespuestaPeticion(urlW, this);
        Toast.makeText(this,  regInsertados , Toast.LENGTH_SHORT).show();

    }

    public void limpiarTexto(View view) {
    }
}