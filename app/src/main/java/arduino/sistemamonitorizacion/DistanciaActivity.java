package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static arduino.sistemamonitorizacion.SecondActivity.LimitesDisMax;
import static arduino.sistemamonitorizacion.SecondActivity.LimitesDisMin;
import static arduino.sistemamonitorizacion.SecondActivity.LimitesTempMax;
import static arduino.sistemamonitorizacion.SecondActivity.LimitesTempMin;
import static arduino.sistemamonitorizacion.SecondActivity.MyPREFERENCESLIMIT;


public class DistanciaActivity extends AppCompatActivity {
    private EditText textField1;
    public static final String TAG = "DistanciaActivity";
    private Boolean hasConnection = false;
    private Thread thread2;
    float[]  limites =  new float[2] ;
    private int time = 2;
    private Socket mSocket;
    private String distancia;
    Button solucion;
    SharedPreferences sharedpreferences;
    int flag = 1;

    private final String urlAlerta = "http://192.168.1.6:4000/a";
    {
        try {
            mSocket = IO.socket("http://192.168.1.6:4000/");
        } catch (URISyntaxException e) {
        }
    }
    @SuppressLint("HandlerLeak")
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setTitle("Monitoriando Distancia");

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distancia);
        sharedpreferences = getSharedPreferences(MyPREFERENCESLIMIT, Context.MODE_PRIVATE);
        String max = sharedpreferences.getString(LimitesDisMax, "");
        String min = sharedpreferences.getString(LimitesDisMin, "");
        textField1 = findViewById(R.id.textField1);
        solucion = findViewById(R.id.solucion);
        limites[0] = (Float) Float.parseFloat(max);
        limites[1] = (Float) Float.parseFloat(min);
        distancia = getIntent().getStringExtra("distancia");
        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }
        if (hasConnection) {

        } else {
            mSocket.connect();
            mSocket.on("Distancia", ondistan);
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

                                            JSONObject dis = new JSONObject();
                                            try {
                                                try {
                                                    dis.put("Distancia", distancia);
                                                    mSocket.emit("Distancia", dis);
                                                    mSocket.on("Distancia", ondistan);
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

    Emitter.Listener ondistan = new Emitter.Listener() {
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
                    String distancia = args[0].toString();
                    try {
                        JSONObject object = new JSONObject(distancia);
                        distancia = object.getString("Distancia");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(!( (Float) Float.parseFloat(distancia) > limites[0] || (Float) Float.parseFloat(distancia) < limites[1] ) ){
                        textField1.setText(distancia+" cm");

                    }else {
                        if(flag == 1)
                        {
                            textField1.setText("!Medidas fuera de rango");
                            String alerta = consumoWSG14.obtenerRespuestaPeticion(urlAlerta, DistanciaActivity.this);
                            Toast.makeText(DistanciaActivity.this, "!Medidas fuera de rango", Toast.LENGTH_LONG).show();
                            flag=0;
                        }


                    }


                }
            });
        }
    };
    public void solucion(View view) {
        mSocket.disconnect();
        time = -1;
        Intent intent = new Intent(DistanciaActivity.this, SecondActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        mSocket.disconnect();
        Intent intent = new Intent(DistanciaActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}