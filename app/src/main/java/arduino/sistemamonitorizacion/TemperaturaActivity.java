package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import android.util.Log;

import android.widget.EditText;
import android.widget.Toast;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;


public class TemperaturaActivity extends AppCompatActivity {
    private EditText textField1;
    public static final String TAG = "TemperaturaActivity";
    private Boolean hasConnection = false;
    private Thread thread2;
    ArrayList<Float> limites;
    private int time = 2;
    private Socket mSocket;
    private String temperatura;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "usernameKey";
    SharedPreferences sharedpreferences;

    private final String urlLocal = "http://192.168.1.3:4000/limites_modulos?modulo_id=1&username="+"ag";
    {
        try {
            mSocket = IO.socket("http://192.168.1.3:4000/");
        } catch (URISyntaxException e) {
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            setTitle("Monitoriando Temperatura");
            Log.i(TAG, "handleMessage: typing stopped time is " + time);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String email_get = sharedpreferences.getString(Email, "");
        Toast.makeText(this, "El prefer es: " + email_get, Toast.LENGTH_LONG).show();

        textField1 = findViewById(R.id.textField1);
        temperatura = getIntent().getStringExtra("temperatura");
        //String nodejs = consumoWSG14.obtenerRespuestaPeticion(urlLocal, this);
        //limites = consumoWSG14.limites(nodejs, this);
        if(savedInstanceState != null){
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }
        if(hasConnection){

        }else {
            mSocket.connect();
            mSocket.on("Temperatura", onTemp);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    thread2 = new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    while (time > 0) {

                                        Log.i(TAG, "while" + time);
                                        synchronized (this) {
                                            Log.i(TAG, "synchronized");

                                            JSONObject Temp = new JSONObject();
                                            try {
                                              try {
                                                  Temp.put("Temperatura", temperatura );
                                                mSocket.emit("Temperatura", Temp);
                                                mSocket.on("Temperatura", onTemp);
                                                wait(10000);
                                                Log.i(TAG, "run: typing " + time);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                                Log.w(TAG, "catch: typing " + e);
                                            }
                                            //  time--;
                                        }
                                        handler2.sendEmptyMessage(0);
                                    }

                                }
                            }
                    );
                    thread2.start();

                }
            });
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }

    Emitter.Listener onTemp = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;
                    if (length == 0) {
                        return;
                    }
                    //Here i'm getting weird error..................///////run :1 and run: 0
                    Log.i(TAG, "run: ");
                    Log.i(TAG, "run: " + args.length);
                    String temperatura = args[0].toString();
                    try {
                        JSONObject object = new JSONObject(temperatura);
                        temperatura = object.getString("Temperatura");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    textField1.setText(temperatura+"ºC");

                }
            });
        }
    };
}

