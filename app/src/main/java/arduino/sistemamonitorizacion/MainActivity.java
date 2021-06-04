package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    int RC_SIGN_IN = 0;
    SignInButton signin;
    GoogleSignInClient mGoogleSignInClient;
    static List<Usuario> listaUsers;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "usernameKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaUsers = new ArrayList<Usuario>();
        signin = findViewById(R.id.sign_in_button);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            // Signed in successfully, show authenticated UI.
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                Log.v("DATA USER", "email: " + personEmail);

                String urlW = "";
                urlW = "http://192.168.1.8:4000/user_api_read?username="+personEmail;
                String users = ConsumoNodeJS.obtenerRespuestaPeticion(urlW, this);
                    listaUsers.clear();
                try {
                    listaUsers.addAll(ConsumoNodeJS.obtenerUsersExterno(users, this));
                    Log.v("Cantidad", "cantidad: " + listaUsers.size() );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(listaUsers.size() > 0){
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                    //tengo que restablecer prefer
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    sharedpreferences.edit().clear().commit();

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(Email, personEmail);
                    editor.commit();
                    String email_get = sharedpreferences.getString(Email, "");

                    Toast.makeText(this, "El prefer es: " + email_get, Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, AsignarClienteActivity.class);
                    startActivity(intent);
                }


            }


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error login", "signInResult:failed code=" + e.getStatusCode());
        }
    }


}