package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GestionesActivity extends AppCompatActivity {
    Button gestionar_user, gestionar_empresa, gestionar_ubicacion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiones);

        gestionar_user = findViewById(R.id.gestionar_user);
        gestionar_empresa = findViewById(R.id.gestionar_empresa);
        gestionar_ubicacion = findViewById(R.id.gestionar_ubicacion);

        gestionar_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.gestionar_user:
                        Intent intent = new Intent(GestionesActivity.this, UsuarioMenuActivity.class);
                        startActivity(intent);
                        break;
                    // ..
                }
            }
        });
        gestionar_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.gestionar_empresa:
                        Intent intent = new Intent(GestionesActivity.this, EmpresaMenuActivity.class);
                        startActivity(intent);
                        break;
                    // ..
                }
            }
        });
        gestionar_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.gestionar_ubicacion:
                        Intent intent = new Intent(GestionesActivity.this, UbicacionMenuActivity.class);
                        startActivity(intent);
                        break;
                    // ..
                }
            }
        });
    }
}