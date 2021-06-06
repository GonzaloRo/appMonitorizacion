package arduino.sistemamonitorizacion;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    static List<Ubicacion> listaUbicaciones;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "usernameKey";
    SharedPreferences sharedpreferences;

    String urlWeb = "http://192.168.1.3:4000/ubicacion_api_read";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        listaUbicaciones = new ArrayList<Ubicacion>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //voy a solicitar ubicaciones
        String email = "";
        String url = "";

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        email = sharedpreferences.getString(Email, "");
        // it was the first button
        url = urlWeb + "?cliente_id=" + email;
        String ubicacion = ConsumoNodeJS.obtenerRespuestaPeticion(url, this);
        try {

            listaUbicaciones.addAll(ConsumoNodeJS.obtenerUbicacionesExterno(ubicacion, this));

            for (int i = 0; i < listaUbicaciones.size(); i++) {
                Log.v("LAT", listaUbicaciones.get(i).getLatUbicacion());
                Log.v("LON", listaUbicaciones.get(i).getLogUbicacion());
                LatLng sydney = new LatLng(Double.parseDouble(listaUbicaciones.get(i).getLatUbicacion()), Double.parseDouble(listaUbicaciones.get(i).getLogUbicacion()));
                mMap.addMarker(new MarkerOptions().position(sydney).title("Ubicacion " + i));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera

    }
}