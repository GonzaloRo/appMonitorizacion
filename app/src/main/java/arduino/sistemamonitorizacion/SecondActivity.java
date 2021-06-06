package arduino.sistemamonitorizacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SecondActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name, email, id;
    Button signOut, ubicaciones, gestionar_sitio;

    Button temperatura,humedad,distancia;
    private final String urlLocal = "http://192.168.1.3:4000/v";


    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        String nodejs = consumoWSG14.obtenerRespuestaPeticion(urlLocal, this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        signOut = findViewById(R.id.button);
        ubicaciones = findViewById(R.id.ubicaciones);

        gestionar_sitio = findViewById(R.id.gestionar_sitio);

        temperatura = findViewById(R.id.temperatura);
        humedad = findViewById(R.id.humedad);
        distancia  = findViewById(R.id.distancia);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.button:
                        signOut();
                        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    // ..
                }
            }
        });


        temperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.temperatura:
                        Intent intent = new Intent(SecondActivity.this, TemperaturaActivity.class);
                        intent.putExtra("temperatura", "temperatura");
                        startActivity(intent);
                        break;
                    // ..
                }
            }
        });

        humedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.humedad:
                        Intent intent = new Intent(SecondActivity.this, HumendadActivity.class);
                        intent.putExtra("humedad", "humedad");
                        startActivity(intent);
                        break;
                    // ..
                }
            }
        });
        distancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.distancia:
                        Intent intent = new Intent(SecondActivity.this, DistanciaActivity.class);
                        intent.putExtra("distancia", "distancia");
                        startActivity(intent);
                        break;
                    // ..
                }
            }
        });

        ubicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.ubicaciones:
                        Intent intent = new Intent(SecondActivity.this, MapsActivity.class);
                        startActivity(intent);
                        break;
                    // ..
                }

            }
        });

        gestionar_sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.gestionar_sitio:
                        Intent intent = new Intent(SecondActivity.this, GestionesActivity.class);
                        startActivity(intent);
                        break;
                    // ..
                }

            }
        });
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SecondActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

}