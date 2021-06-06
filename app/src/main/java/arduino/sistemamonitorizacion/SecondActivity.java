package arduino.sistemamonitorizacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    float[]  limitesTemp =  new float[2] ;
    float[]  limitesHume =  new float[2] ;
    float[]  limitesDis =  new float[2] ;
    Button temperatura,humedad,distancia;
    public static final String MyPREFERENCESLIMIT = "Limites" ;
    public static final String LimitesTempMax = "maxKeyTemp";
    public static final String LimitesTempMin = "minKeyTemp";
    public static final String LimitesHumeMax = "maxKeyHume";
    public static final String LimitesHumeMin = "minKeyHume";
    public static final String LimitesDisMax = "maxKeyDis";
    public static final String LimitesDisMin = "minKeyDis";
    SharedPreferences sharedpreferences;
    private final String urlLocal = "http://192.168.1.6:4000/v";
    private final String urlLimitesTemp = "http://192.168.1.6:4000/limites_modulos?modulo_id=1&username=";
    private final String urlLimitesHume= "http://192.168.1.6:4000/limites_modulos?modulo_id=2&username=";
    private final String urlLimitesDis = "http://192.168.1.6:4000/limites_modulos?modulo_id=3&username=";

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
                        Intent intent = new Intent(SecondActivity.this, BiometricActivity.class);
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
            String urlTem = "";
            String urlHume = "";
            String urlDis = "";
            urlTem = urlLimitesTemp+personEmail;
            String liminTemp = consumoWSG14.obtenerRespuestaPeticion(urlTem, this);
            limitesTemp = consumoWSG14.limites(liminTemp, this);
            urlHume = urlLimitesHume+personEmail;
            String liminHume = consumoWSG14.obtenerRespuestaPeticion(urlHume, this);
            limitesHume = consumoWSG14.limites(liminHume, this);
            urlDis = urlLimitesDis+personEmail;
            String liminDis = consumoWSG14.obtenerRespuestaPeticion(urlDis, this);
            limitesDis = consumoWSG14.limites(liminDis, this);
            //tengo que restablecer prefer
            sharedpreferences = getSharedPreferences(MyPREFERENCESLIMIT, Context.MODE_PRIVATE);
            sharedpreferences.edit().clear().commit();

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(LimitesTempMax, Float.toString(limitesTemp[0]) );
            editor.putString(LimitesTempMin, Float.toString(limitesTemp[1]));
            editor.putString(LimitesHumeMax, Float.toString(limitesHume[0]) );
            editor.putString(LimitesHumeMin, Float.toString(limitesHume[1]));
            editor.putString(LimitesDisMax, Float.toString(limitesDis[0]) );
            editor.putString(LimitesDisMin, Float.toString(limitesDis[1]));
            editor.commit();
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