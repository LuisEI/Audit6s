package fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.engine.Resource;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.liraheta.audit6s.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import entidades.Area;
import entidades.Auditor;
import entidades.Auditoria;
import entidades.Division;
import entidades.Encontrado;
import entidades.Gerente;
import entidades.HallazgoDetalle;
import entidades.Hallazgos;
import entidades.Lider;
import entidades.Planta;
import entidades.Responsable;
import sqlite.ConexionSQLiteHelper;
import utilidades.Utilidades;
import utilidades.VersionChecker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSincronizar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSincronizar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSincronizar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnSyncLocal;
    private Button btnSyncRemoto;
    private View vista;
    private RequestQueue request;
    private JsonArrayRequest jsonArrayRequestDivision;
    private JsonArrayRequest jsonArrayRequestPlanta;
    private JsonArrayRequest jsonArrayRequestArea;
    private JsonArrayRequest jsonArrayRequestGerente;
    private JsonArrayRequest jsonArrayRequestAuditor;
    private JsonArrayRequest jsonArrayRequestHallazgos;
    private JsonArrayRequest jsonArrayRequestDetalles;
    private JsonArrayRequest jsonArrayRequestLider;
    private JsonArrayRequest jsonArrayRequestResponsable;
    private ProgressBar pbUpdateApp;

    private ConexionSQLiteHelper conn;
    private boolean registroPendientes;
    //private String url_base = "http://web01.avxslv.com/lean/";    http://192.168.100.7/WebServiceApp/
    private String url_base = "";

    private int numberOfRequestsToMake = 0;
    private boolean manualUpdateData = true;

    private OnFragmentInteractionListener mListener;
    private TextView txtResgistrosNoSyn;
    private ProgressBar pbUpload;
    //Chequear red wifi
    private NetworkInfo wifiCheck;
    private ImageView imageWifi;

    private int cantidadHallazgos = 0;
    private int acumulador = 0;
    private boolean sonido;
    private boolean isUpdate = false;

    private Timer myTimer;
    private WifiInfo wifiInfo;
    private TextView txtLevelWifi;
    private WifiManager wifiManager;
    private NumberProgressBar wifiBar;
    private TextView txtUpdateApp;

    public FragmentSincronizar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSincronizar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSincronizar newInstance(String param1, String param2) {
        FragmentSincronizar fragment = new FragmentSincronizar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_fragment_sincronizar, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        SharedPreferences preferences = getActivity().getSharedPreferences("opciones", Context.MODE_PRIVATE);
        sonido = preferences.getBoolean("sonido", false);

        btnSyncLocal = vista.findViewById(R.id.btnSyncLocal);
        btnSyncRemoto = vista.findViewById(R.id.btnSyncRemoto);
        txtResgistrosNoSyn = vista.findViewById(R.id.txtRegistroNoSync);
        txtUpdateApp = vista.findViewById(R.id.txtUpdateAppVersion);
        pbUpload = vista.findViewById(R.id.pbUpload);
        pbUpdateApp = vista.findViewById(R.id.pbDownload);
        imageWifi = vista.findViewById(R.id.logo_wifi);
        txtLevelWifi = vista.findViewById(R.id.txtLevelWifi);
        wifiBar = vista.findViewById(R.id.wifiBar);

        url_base = CargarUrlWeb();

        btnSyncLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isUpdate){
                    requestAppPermissions();
                    ExisteNuevaVersion();
                }else {
                    ActualizarApp();
                }
            }
        });
        btnSyncRemoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(url_base.equals("")){
                    Toast.makeText(getContext(), "Revise en Ajustes la direccion de los Web Services", Toast.LENGTH_SHORT).show();
                }else{
                    if(registroPendientes){
                        if(existeConexionWifi(getContext())){
                            SincronizacionRemota();
                        }else{
                            if(sonido){
                                MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.wifi);
                                mp.start();
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                            builder.setTitle("No esta conectado a la red WIFI")
                                    .setMessage("¿Desea abrir los ajustes para conectarse a la red WIFI?")
                                    .setPositiveButton("Abrir menu WIFI", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                                        }
                                    })
                                    .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Todas las auditorias se han enviado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        request = Volley.newRequestQueue(getContext());
        RegistrosNoSincronizados();
        CantidadDeHallazgos();
        if(existeConexionWifi(getContext())){
            imageWifi.setImageResource(R.drawable.wifi_area_small);
        } else {
            imageWifi.setImageResource(R.drawable.wifi_area_small_stop);
        }

        wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        wifiBar.setMax(10);
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 500);

        new UpdateAuto().execute();

        return vista;
    }

    private void TimerMethod()
    {
        getActivity().runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            wifiInfo = wifiManager.getConnectionInfo();
            int numberOfLevels = 10;
            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
            NivelWiFi(level);
            Log.i("WIFI", String.valueOf(level));
        }
    };

    private void NivelWiFi(int nivel){
        txtLevelWifi.setText(nivel < 5 ? "La potencia de la señal es muy baja" : "La señal es muy buena, puede enviar Auditorias");
        wifiBar.setProgress(nivel);
    }

    public boolean existeConexionWifi(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void RegistrosNoSincronizados() {

        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_AUDITORIA +" WHERE "+ Utilidades.CAMPO_SYNC +" = ?", new String[]{"0"});
        int registros = cursor.getCount();
        db.close();
        cursor.close();
        conn.close();

        if(registros > 0){
            txtResgistrosNoSyn.setText(String.valueOf(registros));
            txtResgistrosNoSyn.setVisibility(View.VISIBLE);
            btnSyncRemoto.setBackgroundResource(R.drawable.button_background_pink);
            registroPendientes = true;
        }else {
            txtResgistrosNoSyn.setText(String.valueOf(registros));
            txtResgistrosNoSyn.setVisibility(View.INVISIBLE);
            btnSyncRemoto.setBackgroundResource(R.drawable.button_background_blue);
            registroPendientes = false;
        }
    }

    private void CantidadDeHallazgos() {
        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + Utilidades.TABLA_AUDITORIA +" JOIN "+ Utilidades.TABLA_ENCONTRADO
                +" ON "+ Utilidades.TABLA_AUDITORIA +"."+Utilidades.CAMPO_ID_AUDITORIA+" = "+ Utilidades.TABLA_ENCONTRADO +"."+ Utilidades.CAMPO_ID_AUDITORIA
                +" WHERE "+ Utilidades.CAMPO_SYNC +" = ?", new String[]{"0"});
        cantidadHallazgos = cursor.getCount();
        db.close();
        cursor.close();
        conn.close();
    }

    private void SincronizacionRemota() {

        pbUpload.setVisibility(View.VISIBLE);
        pbUpload.setMax(cantidadHallazgos);

        List<Auditoria> listaAuditorias = obtenerListaAuditorias();
        for(Auditoria auditParams: listaAuditorias){
            EnviarAuditoria(url_base,auditParams);
        }
    }

    private void EnviarAuditoria(final String url_base, final Auditoria auditParams) {

        String url = url_base + "GuardarAuditoria.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("OK")){
                    numberOfRequestsToMake--;
                    List<Encontrado> listaE = obtenerListaEncontrado(auditParams.getId_auditoria());

                    for(Encontrado encontrado: listaE){
                        EnviarEncontradoServidor(url_base,encontrado);
                    }
                    if(listaE.size() == 0) ActualizarEstadoSync(auditParams.getId_auditoria());
                    if(numberOfRequestsToMake == 0){
                        Toast.makeText(getContext(), "Tarea completada", Toast.LENGTH_SHORT).show();
                        pbUpload.setVisibility(View.INVISIBLE);
                        RegistrosNoSincronizados();
                    }
                }else {
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                numberOfRequestsToMake--;
                if(numberOfRequestsToMake == 0){
                    pbUpload.setVisibility(View.INVISIBLE);
                    RegistrosNoSincronizados();
                }
                Toast.makeText(getContext(), "Error al enviar tabla Auditoria " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id_auditoria", String.valueOf(auditParams.getId_auditoria()));
                parametros.put("area", String.valueOf(auditParams.getArea()));
                parametros.put("lider", String.valueOf(auditParams.getLider()));
                parametros.put("auditor", String.valueOf(auditParams.getAuditor()));
                parametros.put("turno", String.valueOf(auditParams.getTurno()));
                parametros.put("fecha", auditParams.getFecha());
                parametros.put("s1_obs_1", String.valueOf(auditParams.getS1_obs_1()));
                parametros.put("s1_obs_2", String.valueOf(auditParams.getS1_obs_2()));
                parametros.put("s1_obs_3", String.valueOf(auditParams.getS1_obs_3()));
                parametros.put("s2_obs_1", String.valueOf(auditParams.getS2_obs_1()));
                parametros.put("s2_obs_2", String.valueOf(auditParams.getS2_obs_2()));
                parametros.put("s2_obs_3", String.valueOf(auditParams.getS2_obs_3()));
                parametros.put("s2_obs_4", String.valueOf(auditParams.getS2_obs_4()));
                parametros.put("s3_obs_1", String.valueOf(auditParams.getS3_obs_1()));
                parametros.put("s3_obs_2", String.valueOf(auditParams.getS3_obs_2()));
                parametros.put("s3_obs_3", String.valueOf(auditParams.getS3_obs_3()));
                parametros.put("s3_obs_4", String.valueOf(auditParams.getS3_obs_4()));
                parametros.put("s4_obs_1", String.valueOf(auditParams.getS4_obs_1()));
                parametros.put("s4_obs_2", String.valueOf(auditParams.getS4_obs_2()));
                parametros.put("s4_obs_3", String.valueOf(auditParams.getS4_obs_3()));
                parametros.put("s4_obs_4", String.valueOf(auditParams.getS4_obs_4()));
                parametros.put("s5_obs_1", String.valueOf(auditParams.getS5_obs_1()));
                parametros.put("s5_obs_2", String.valueOf(auditParams.getS5_obs_2()));
                parametros.put("s5_obs_3", String.valueOf(auditParams.getS5_obs_3()));
                parametros.put("s5_obs_4", String.valueOf(auditParams.getS5_obs_4()));
                parametros.put("res_s1", String.valueOf(auditParams.getRes_s1()));
                parametros.put("res_s2", String.valueOf(auditParams.getRes_s2()));
                parametros.put("res_s3", String.valueOf(auditParams.getRes_s3()));
                parametros.put("res_s4", String.valueOf(auditParams.getRes_s4()));
                parametros.put("res_s5", String.valueOf(auditParams.getRes_s5()));
                parametros.put("res_total", String.valueOf(auditParams.getRes_total()));
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);
        numberOfRequestsToMake++;
    }

    private void ActualizarEstadoSync(int id_auditoria){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_SYNC, 1);
        db.update(Utilidades.TABLA_AUDITORIA, values, Utilidades.CAMPO_ID_AUDITORIA+" = ?",new String[]{String.valueOf(id_auditoria)});

        db.close();
        conn.close();
    }

    private void EnviarEncontradoServidor(String url_base, final Encontrado e) {

        String url = url_base + "GuardarEncontrado.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                numberOfRequestsToMake--;
                pbUpload.setProgress(1 + acumulador++, true);
                if(response.replace("\t","").equals("OK")){
                    ActualizarEstadoSync(e.getId_auditoria());
                }else{
                    Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                }
                if(numberOfRequestsToMake == 0){
                    pbUpload.setVisibility(View.INVISIBLE);
                    RegistrosNoSincronizados();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                numberOfRequestsToMake--;
                pbUpload.setProgress(acumulador++, true);
                Toast.makeText(getContext(), "Error al enviar tabla Encontrado " + error.toString(), Toast.LENGTH_SHORT).show();
                if(numberOfRequestsToMake == 0){
                    pbUpload.setVisibility(View.INVISIBLE);
                    RegistrosNoSincronizados();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                String imagenString = ConvertirImagenString(e.getImagen());

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id_detalle", String.valueOf(e.getId_detalle()));
                parametros.put("imagen", imagenString);
                parametros.put("nombre_img", e.getImagen());
                parametros.put("id_auditoria", String.valueOf(e.getId_auditoria()));
                parametros.put("comentario", e.getComentario());

                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(stringRequest);
        numberOfRequestsToMake++;
    }

    private String ConvertirImagenString(String imagen) {

        String rutaImagen = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + imagen;
        String imgString = null;
        File f = new File(rutaImagen);
        if(f.exists()){
            Bitmap bm = BitmapFactory.decodeFile(rutaImagen);

            Bitmap resized_1 = Bitmap.createScaledBitmap(bm, (int)(bm.getWidth() * 0.65), (int)(bm.getHeight() * 0.65), true);
            Bitmap resized_2 = Bitmap.createScaledBitmap(resized_1, (int)(resized_1.getWidth() * 0.65), (int)(resized_1.getHeight() * 0.65), true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resized_2.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] byteArrayImage = baos.toByteArray();

            imgString = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        }else{
            Toast.makeText(getContext(), "La imagen no se encontro en la tablet", Toast.LENGTH_SHORT).show();
        }
        return imgString;
    }

    private List<Auditoria> obtenerListaAuditorias() {

        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        List<Auditoria> listaAuditorias = new ArrayList<>();
        Auditoria a;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_AUDITORIA +" WHERE "+ Utilidades.CAMPO_SYNC +" = ?", new String[]{"0"});

        while (cursor.moveToNext()){

            a = new Auditoria();
            a.setId_auditoria(cursor.getInt(0));
            a.setArea(cursor.getInt(1));
            a.setLider(cursor.getInt(2));
            a.setAuditor(cursor.getInt(3));
            a.setTurno(cursor.getInt(4));
            a.setFecha(cursor.getString(5));
            a.setS1_obs_1(cursor.getInt(6));
            a.setS1_obs_2(cursor.getInt(7));
            a.setS1_obs_3(cursor.getInt(8));
            a.setS2_obs_1(cursor.getInt(9));
            a.setS2_obs_2(cursor.getInt(10));
            a.setS2_obs_3(cursor.getInt(11));
            a.setS2_obs_4(cursor.getInt(12));
            a.setS3_obs_1(cursor.getInt(13));
            a.setS3_obs_2(cursor.getInt(14));
            a.setS3_obs_3(cursor.getInt(15));
            a.setS3_obs_4(cursor.getInt(16));
            a.setS4_obs_1(cursor.getInt(17));
            a.setS4_obs_2(cursor.getInt(18));
            a.setS4_obs_3(cursor.getInt(19));
            a.setS4_obs_4(cursor.getInt(20));
            a.setS5_obs_1(cursor.getInt(21));
            a.setS5_obs_2(cursor.getInt(22));
            a.setS5_obs_3(cursor.getInt(23));
            a.setS5_obs_4(cursor.getInt(24));
            a.setRes_s1(cursor.getInt(25));
            a.setRes_s2(cursor.getInt(26));
            a.setRes_s3(cursor.getInt(27));
            a.setRes_s4(cursor.getInt(28));
            a.setRes_s5(cursor.getInt(29));
            a.setRes_total(cursor.getInt(30));

            listaAuditorias.add(a);
        }

        db.close();
        cursor.close();
        conn.close();

        return listaAuditorias;
    }

    private List<Encontrado> obtenerListaEncontrado(int id_auditoria){

        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        List<Encontrado> listaEncontrados = new ArrayList<>();
        Encontrado e;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_ENCONTRADO +" WHERE "+ Utilidades.CAMPO_ID_AUDITORIA +" = ?", new String[]{String.valueOf(id_auditoria)});

        while (cursor.moveToNext()){

            e = new Encontrado();

            e.setId_detalle(cursor.getInt(0));
            e.setImagen(cursor.getString(1));
            e.setId_auditoria(cursor.getInt(2));
            e.setComentario(cursor.getString(3));

            listaEncontrados.add(e);
        }

        db.close();
        cursor.close();
        conn.close();

        return listaEncontrados;
    }

    private void SincronizacionLocal(){

        jsonArrayRequestDivision = new JsonArrayRequest(Request.Method.POST, url_base + "CargarDivision.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarDivisionLocal(ListaObjetosDivision(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestDivision);

        jsonArrayRequestPlanta = new JsonArrayRequest(Request.Method.POST, url_base + "CargarPlanta.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarPlantaLocal(ListaObjetosPlanta(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestPlanta);

        jsonArrayRequestArea = new JsonArrayRequest(Request.Method.POST, url_base + "CargarArea.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarAreaLocal(ListaObjetosArea(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestArea);

        jsonArrayRequestLider = new JsonArrayRequest(Request.Method.POST, url_base + "CargarLider.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarLiderLocal(ListaObjetosLider(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestLider);

        jsonArrayRequestResponsable = new JsonArrayRequest(Request.Method.POST, url_base + "CargarResponsable.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarResponsableLocal(ListaObjetosResponsable(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestResponsable);

        jsonArrayRequestGerente = new JsonArrayRequest(Request.Method.POST, url_base + "CargarGerente.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarGerenteLocal(ListaObjetosGerente(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestGerente);

        jsonArrayRequestAuditor = new JsonArrayRequest(Request.Method.POST, url_base + "CargarAuditor.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarAuditorLocal(ListaObjetosAuditor(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestAuditor);

        jsonArrayRequestHallazgos = new JsonArrayRequest(Request.Method.POST, url_base + "CargarHallazgo.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarHallazgosLocal(ListaObjetosHallazgo(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestHallazgos);

        jsonArrayRequestDetalles = new JsonArrayRequest(Request.Method.POST, url_base + "CargarDetalle.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarDetalleLocal(ListaObjetosDetalle(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("login","true");
                return parametros;
            }
        };
        request.add(jsonArrayRequestDetalles);

    }

    private List<Division> ListaObjetosDivision(JSONArray jsonArray){

        List<Division> lista= new ArrayList<Division>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Division div = new Division();
                div.setId_division(object.getInt("id_division"));
                div.setDivision(object.getString("division"));
                lista.add(div);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Planta> ListaObjetosPlanta(JSONArray jsonArray){

        List<Planta> lista= new ArrayList<Planta>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Planta planta = new Planta();
                planta.setId_planta(object.getInt("id_planta"));
                planta.setPlanta(object.getString("planta"));
                planta.setId_division(object.getInt("id_division"));
                lista.add(planta);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Area> ListaObjetosArea(JSONArray jsonArray){

        List<Area> lista= new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Area area = new Area();
                area.setId_area(object.getInt("id_area"));
                area.setArea(object.getString("area"));
                area.setId_planta(object.getInt("id_planta"));
                area.setId_gerente(object.getInt("id_gerente"));
                lista.add(area);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Lider> ListaObjetosLider(JSONArray jsonArray){

        List<Lider> lista= new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Lider lider = new Lider();
                lider.setId_lider(object.getInt("id_lider"));
                lider.setLider(object.getString("lider"));
                lista.add(lider);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Responsable> ListaObjetosResponsable(JSONArray jsonArray){

        List<Responsable> lista= new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Responsable responsable = new Responsable();
                responsable.setId_area(object.getInt("id_area"));
                responsable.setId_lider(object.getInt("id_lider"));
                lista.add(responsable);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Gerente> ListaObjetosGerente(JSONArray jsonArray){

        List<Gerente> lista = new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Gerente gerente = new Gerente();
                gerente.setId_gerente(object.getInt("id_gerente"));
                gerente.setGerente(object.getString("gerente"));
                lista.add(gerente);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Auditor> ListaObjetosAuditor(JSONArray jsonArray){

        List<Auditor> lista= new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Auditor auditor = new Auditor();
                auditor.setId_auditor(object.getInt("id_auditor"));
                auditor.setAuditor(object.getString("auditor"));
                auditor.setClave((object.getString("clave")));
                lista.add(auditor);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Hallazgos> ListaObjetosHallazgo(JSONArray jsonArray){

        List<Hallazgos> lista = new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Hallazgos hallazgos = new Hallazgos();
                hallazgos.setId_hallazgo(object.getInt("id_hallazgo"));
                hallazgos.setHallazgo(object.getString("hallazgo"));
                lista.add(hallazgos);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<HallazgoDetalle> ListaObjetosDetalle(JSONArray jsonArray){

        List<HallazgoDetalle> lista= new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                HallazgoDetalle detalle = new HallazgoDetalle();
                detalle.setId_detalle(object.getInt("id_detalle"));
                detalle.setId_hallazgo(object.getInt("id_hallazgo"));
                detalle.setDescripcion(object.getString("descripcion"));
                lista.add(detalle);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void GuardarDivisionLocal(List<Division> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_DIVISION, null, null);

        for(Division dato: lista){
            values.put(Utilidades.CAMPO_ID_DIVISION, dato.getId_division());
            values.put(Utilidades.CAMPO_DIVISION, dato.getDivision());

            Long idResultante = db.insert(Utilidades.TABLA_DIVISION, Utilidades.CAMPO_ID_DIVISION, values);
        }

        db.close();
        conn.close();
    }

    private void GuardarPlantaLocal(List<Planta> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_PLANTA, null, null);

        for(Planta dato: lista){
            values.put(Utilidades.CAMPO_ID_PLANTA, dato.getId_planta());
            values.put(Utilidades.CAMPO_PLANTA, dato.getPlanta());
            values.put(Utilidades.CAMPO_ID_DIVISION, dato.getId_division());

            Long idResultante = db.insert(Utilidades.TABLA_PLANTA, Utilidades.CAMPO_ID_PLANTA, values);
        }

        db.close();
        conn.close();
    }

    private void GuardarAreaLocal(List<Area> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_AREA, null, null);

        for(Area dato: lista){
            values.put(Utilidades.CAMPO_ID_AREA, dato.getId_area());
            values.put(Utilidades.CAMPO_AREA, dato.getArea());
            values.put(Utilidades.CAMPO_ID_PLANTA, dato.getId_planta());
            values.put(Utilidades.CAMPO_ID_GERENTE, dato.getId_gerente());

            Long idResultante = db.insert(Utilidades.TABLA_AREA, Utilidades.CAMPO_ID_AREA, values);
        }

        db.close();
        conn.close();
    }

    private void GuardarLiderLocal(List<Lider> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_LIDER, null, null);

        for(Lider dato: lista){
            values.put(Utilidades.CAMPO_ID_LIDER, dato.getId_lider());
            values.put(Utilidades.CAMPO_LIDER, dato.getLider());

            Long idResultante = db.insert(Utilidades.TABLA_LIDER, Utilidades.CAMPO_ID_LIDER, values);
        }

        db.close();
        conn.close();
    }

    private void GuardarResponsableLocal(List<Responsable> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_RESPONSABLE, null, null);

        for(Responsable dato: lista){
            values.put(Utilidades.CAMPO_ID_AREA, dato.getId_area());
            values.put(Utilidades.CAMPO_ID_LIDER, dato.getId_lider());
            Long idResultante = db.insert(Utilidades.TABLA_RESPONSABLE, Utilidades.CAMPO_ID_AREA, values);
        }

        db.close();
        conn.close();
    }

    private void GuardarGerenteLocal(List<Gerente> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_GERENTE, null, null);

        for(Gerente dato: lista){
            values.put(Utilidades.CAMPO_ID_GERENTE, dato.getId_gerente());
            values.put(Utilidades.CAMPO_GERENTE, dato.getGerente());

            Long idResultante = db.insert(Utilidades.TABLA_GERENTE, Utilidades.CAMPO_ID_GERENTE, values);
        }

        db.close();
        conn.close();
    }

    private void GuardarAuditorLocal(List<Auditor> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_AUDITOR, null, null);

        for(Auditor dato: lista){
            values.put(Utilidades.CAMPO_ID_AUDITOR, dato.getId_auditor());
            values.put(Utilidades.CAMPO_AUDITOR, dato.getAuditor());
            values.put(Utilidades.CAMPO_CLAVE, dato.getClave());

            Long idResultante = db.insert(Utilidades.TABLA_AUDITOR, Utilidades.CAMPO_ID_AUDITOR, values);
        }

        db.close();
        conn.close();
    }

    private void GuardarHallazgosLocal(List<Hallazgos> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_HALLAZGOS, null, null);

        for(Hallazgos dato: lista){
            values.put(Utilidades.CAMPO_ID_HALLAZGO, dato.getId_hallazgo());
            values.put(Utilidades.CAMPO_HALLAZGO, dato.getHallazgo());

            Long idResultante = db.insert(Utilidades.TABLA_HALLAZGOS, Utilidades.CAMPO_ID_HALLAZGO, values);
        }

        db.close();
        conn.close();
    }

    private void GuardarDetalleLocal(List<HallazgoDetalle> lista){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(Utilidades.TABLA_DETALLE, null, null);

        for(HallazgoDetalle dato: lista){
            values.put(Utilidades.CAMPO_ID_DETALLE, dato.getId_detalle());
            values.put(Utilidades.CAMPO_ID_HALLAZGO, dato.getId_hallazgo());
            values.put(Utilidades.CAMPO_DESCRIPCION, dato.getDescripcion());

            Long idResultante = db.insert(Utilidades.TABLA_DETALLE, Utilidades.CAMPO_ID_DETALLE, values);
        }

        db.close();
        conn.close();

    }

    private String CargarUrlWeb(){
        SharedPreferences preferences = getActivity().getSharedPreferences("opciones", Context.MODE_PRIVATE);
        String url = preferences.getString("url_web", "https://sistemas.avxslv.com/lean/");
        return url;
    }


    //###########################################################################################


    private Handler handler = new Handler();

    private Runnable finishBackgroundDownload = new Runnable() {
        @Override
        public void run() {
            pbUpdateApp.setVisibility(View.VISIBLE);
            if(mVC.isNewVersionAvailable()){
                txtUpdateApp.setText("Version actual: " + mVC.getCurrentVersionName() +"\n"+ "Version disponible: " + mVC.getLatestVersionName());
                btnSyncLocal.setText(R.string.update_app);
                btnSyncLocal.setBackgroundResource(R.drawable.button_background_pink);
                isUpdate = true;
            }else{
                txtUpdateApp.setText("La app esta actualizada");
            }
        }
    };

    private Runnable backgroundDownload = new Runnable() {
        @Override
        public void run() {
            mVC.getData(getContext());
            handler.post(finishBackgroundDownload);
        }
    };

    private VersionChecker mVC = new VersionChecker();

    private int REQUEST_WRITE_STORAGE_REQUEST_CODE = 1;






    private void ExisteNuevaVersion() {
        pbUpdateApp.setVisibility(View.VISIBLE);
        pbUpdateApp.setIndeterminate(false);
        Thread downloadThread = new Thread(backgroundDownload, "VersionChecker");
        downloadThread.start();
    }

    private void ActualizarApp() {
        pbUpdateApp.setVisibility(View.VISIBLE);
        pbUpdateApp.setIndeterminate(false);
        new LongOperation().execute(mVC.getDownloadURL());
    }

    private class LongOperation extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... sUrl) {
            String path = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "app-audit6s.apk";
            try {
                URL url = new URL(sUrl[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(false);
                connection.setConnectTimeout(10 * 1000);
                connection.setReadTimeout(10 * 1000);
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

                connection.connect();

                int fileLength = connection.getContentLength();

                InputStream input = connection.getInputStream();
                OutputStream output = new FileOutputStream(path);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) ((total / (float) fileLength) * 100));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("App", "Ocurrio un error");
                Log.e("App", e.getMessage());
            }
            return path;

        }

        @Override
        protected void onPostExecute(String path) {
            pbUpdateApp.setProgress(0);
            pbUpdateApp.setVisibility(View.INVISIBLE);
            pbUpdateApp.setIndeterminate(true);
            btnSyncLocal.setText(R.string.buscar_actualizacion);
            btnSyncLocal.setBackgroundResource(R.drawable.button_background_blue);

            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.setDataAndType(FileProvider.getUriForFile(getContext(), "com.example.android.fileprovider", new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + "app-audit6s.apk")), "application/vnd.android.package-archive");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            getContext().startActivity(i);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Integer... progress) {
            pbUpdateApp.setProgress(progress[0]);
        }
    }

    private void requestAppPermissions() {

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(getActivity(),
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_WRITE_STORAGE_REQUEST_CODE);
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getActivity().getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getActivity().getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }


    //#############################################################################################


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        myTimer.cancel();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class UpdateAuto extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(!url_base.equals("")){
                if(existeConexionWifi(getContext())){
                    SincronizacionLocal();
                    manualUpdateData = false;
                }
            }
            return null;
        }

    }
}
