package arduino.sistemamonitorizacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class BiometricActivity extends AppCompatActivity {
    //UI Views
    private TextView authStatusTV;


    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);  //Init UI views
        authStatusTV = findViewById(R.id.authStatusTv);

        //init bio metric
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(BiometricActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //error de autentificacion
                authStatusTV.setText("Error de autentificacion " +errString);
                Toast.makeText(BiometricActivity.this, "Error de autentificacion"+ errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //autentificacion realizada
                authStatusTV.setText("Exito al autentificarse!!!");
                Toast.makeText(BiometricActivity.this, "Exito al autentificarse!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BiometricActivity.this, GestionesActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //Fallo en la autentificacion
                authStatusTV.setText("La autentifacacion fallo");
                Toast.makeText(BiometricActivity.this, "La autentifacacion fallo", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autentificacion del sistema")
                .setSubtitle("Uso de login fingerprint authentication")
                .setNegativeButtonText("Clave de Usuario")
                .build();
                //handle authBtn click, Star authentication
                Button  authBtn = findViewById(R.id.authBtn);
                authBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        biometricPrompt.authenticate(promptInfo);
                    }
                });

    }
}