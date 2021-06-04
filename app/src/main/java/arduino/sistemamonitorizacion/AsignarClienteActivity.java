package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class AsignarClienteActivity extends AppCompatActivity {

    EditText editCliente;
    private final String urlWeb = "http://192.168.1.8:4000/usuario_api_add";
    GoogleSignInClient mGoogleSignInClient;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "usernameKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_cliente);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        editCliente = (EditText) findViewById(R.id.editCliente);
    }

    public void insertarCodCliente(View view) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String regInsertados = "";
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            int codcliente = Integer.parseInt(editCliente.getText().toString());
            String urlW = "";
            String fullname = personName.replace(" ","");
            urlW = urlWeb+"?username="+personEmail+"&password=1234"+"&fullname="+fullname+"&cliente_id="+codcliente+"&es_main="+0;
            Log.v("Url", urlW);
            String clienteW = ConsumoNodeJS.obtenerRespuestaPeticion(urlW, this);
            //tengo que restablecer prefer
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            sharedpreferences.edit().clear().commit();

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(Email, personEmail);
            editor.commit();
            String email_get = sharedpreferences.getString(Email, "");

            Toast.makeText(this, "El prefer es: " + email_get, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AsignarClienteActivity.this, SecondActivity.class);
            startActivity(intent);
        }
    }

    public void limpiarTexto(View view) {
    }
}