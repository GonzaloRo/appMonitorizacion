package arduino.sistemamonitorizacion;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConsumoNodeJS {

    public static String obtenerRespuestaPeticion(String url, Context ctx) {

        String respuesta = " ";

        // Estableciendo tiempo de espera del servicio
        HttpParams parametros = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parametros, 3000);
        HttpConnectionParams.setSoTimeout(parametros, 5000);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Creando objetos de conexion
        HttpClient cliente = new DefaultHttpClient(parametros);
        HttpGet httpGet = new HttpGet(url);

        try {
            Log.v("Intentando setiar",url );
            HttpResponse httpRespuesta = cliente.execute(httpGet);
            StatusLine estado = httpRespuesta.getStatusLine();
            int codigoEstado = estado.getStatusCode();
            if (codigoEstado == 200) {
                HttpEntity entidad = httpRespuesta.getEntity();
                respuesta = EntityUtils.toString(entidad);
            }

        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG)
                    .show();
            // Desplegando el error en el LogCat
            Log.v("Error de Conexion", e.toString());
        }
        return respuesta;
    }

    public static List<Usuario> obtenerUsersExterno(String json, Context ctx) {
        List<Usuario> listaUsers = new ArrayList<Usuario>();

        try {
            JSONArray usuariosJSON = new JSONArray(json);
            for (int i = 0; i < usuariosJSON.length(); i++) {
                JSONObject obj = usuariosJSON.getJSONObject(i);
                Usuario usuario = new Usuario();
                usuario.setNombreUsuario(obj.getString("fullname"));
                usuario.setUsernameUsuario(obj.getString("username"));
                usuario.setContrasenaUsuario(obj.getString("password"));
                usuario.setCodClienteUsuario(obj.getInt("cliente_id"));
                listaUsers.add(usuario);
            }
            return listaUsers;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }

    public static List<Ubicacion> obtenerUbicacionesExterno(String json, Context ctx) {
        Log.v("JSON_UBICACIONES", json);
        List<Ubicacion> listaUbicaciones = new ArrayList<Ubicacion>();

        try {
            JSONArray ubicacionesJSON = new JSONArray(json);
            for (int i = 0; i < ubicacionesJSON.length(); i++) {
                JSONObject obj = ubicacionesJSON.getJSONObject(i);
                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setCodClienteUbicacion(obj.getInt("id"));
                ubicacion.setLatUbicacion(obj.getString("lat"));
                ubicacion.setLogUbicacion(obj.getString("lon"));
                ubicacion.setNombreUbicacion(obj.getString("direccion"));
                listaUbicaciones.add(ubicacion);
            }
            return listaUbicaciones;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }
}
