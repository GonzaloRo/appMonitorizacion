package arduino.sistemamonitorizacion;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import android.util.Log;

import android.widget.EditText;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class HumendadActivity extends AppCompatActivity {
    private EditText textField1;
    public static final String TAG = "HumedadActivity";
    private Boolean hasConnection = false;
    private Thread thread2;

    private int time = 2;
    private Socket mSocket;
    private String humedad;

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

            setTitle("Monitoriando Humedad");


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humendad);
        textField1 = findViewById(R.id.textField1);
        humedad = getIntent().getStringExtra("temperatura");
        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }
        if (hasConnection) {

        } else {
            mSocket.connect();
            mSocket.on("Temperatura", onHume);
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

                                            JSONObject Hume = new JSONObject();
                                            try {
                                                try {
                                                    Hume.put("Humedad", humedad);
                                                    mSocket.emit("Humedad", Hume);
                                                    mSocket.on("Humedad", onHume);
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

    Emitter.Listener onHume = new Emitter.Listener() {
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
                    String humedad = args[0].toString();
                    try {
                        JSONObject object = new JSONObject(humedad);
                        humedad = object.getString("Humedad");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    textField1.setText(humedad+"%");

                }
            });
        }
    };
}
