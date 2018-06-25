package fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.liraheta.audit6s.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import entidades.Area;
import entidades.Auditor;
import entidades.Division;
import entidades.Gerente;
import entidades.HallazgoDetalle;
import entidades.Hallazgos;
import entidades.Planta;
import sqlite.ConexionSQLiteHelper;
import utilidades.Utilidades;

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

    private Button btnsyncLocal;
    private TextView txtResult;
    private View vista;
    private RequestQueue request;
    private JsonArrayRequest jsonArrayRequestDivision;
    private JsonArrayRequest jsonArrayRequestPlanta;
    private JsonArrayRequest jsonArrayRequestArea;
    private JsonArrayRequest jsonArrayRequestGerente;
    private JsonArrayRequest jsonArrayRequestAuditor;
    private JsonArrayRequest jsonArrayRequestHallazgos;
    private JsonArrayRequest jsonArrayRequestDetalles;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

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
        txtResult = vista.findViewById(R.id.txtResultJson);
        btnsyncLocal = vista.findViewById(R.id.btnSyncLocal);
        progressBar = vista.findViewById(R.id.progressBar);
        btnsyncLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SincronizacionLocal();
            }
        });
        request = Volley.newRequestQueue(getContext());
        return vista;
    }

    private void SincronizacionLocal(){

        progressBar.setVisibility(View.VISIBLE);
        String url_base = "http://192.168.100.7/WebServiceApp/";

        jsonArrayRequestDivision = new JsonArrayRequest(Request.Method.GET, url_base + "LoadDivision.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarDivisionLocal(ListaObjetosDivision(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        });
        request.add(jsonArrayRequestDivision);

        jsonArrayRequestPlanta = new JsonArrayRequest(Request.Method.GET, url_base + "LoadPlanta.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarPlantaLocal(ListaObjetosPlanta(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        });
        request.add(jsonArrayRequestPlanta);

        jsonArrayRequestArea = new JsonArrayRequest(Request.Method.GET, url_base + "LoadArea.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarAreaLocal(ListaObjetosArea(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        });
        request.add(jsonArrayRequestArea);

        jsonArrayRequestGerente = new JsonArrayRequest(Request.Method.GET, url_base + "LoadGerente.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarGerenteLocal(ListaObjetosGerente(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        });
        request.add(jsonArrayRequestGerente);

        jsonArrayRequestAuditor = new JsonArrayRequest(Request.Method.GET, url_base + "LoadAuditor.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarAuditorLocal(ListaObjetosAuditor(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        });
        request.add(jsonArrayRequestAuditor);

        jsonArrayRequestHallazgos = new JsonArrayRequest(Request.Method.GET, url_base + "LoadHallazgos.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarHallazgosLocal(ListaObjetosHallazgo(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        });
        request.add(jsonArrayRequestHallazgos);

        jsonArrayRequestDetalles = new JsonArrayRequest(Request.Method.GET, url_base + "LoadDetalle.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                GuardarDetalleLocal(ListaObjetosDetalle(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo conectar al servidor", Toast.LENGTH_LONG).show();
            }
        });
        request.add(jsonArrayRequestDetalles);

    }

    private List<Division> ListaObjetosDivision(JSONArray jsonArray){

        List<Division> lista= new ArrayList<Division>();
        try {
            JSONArray json = jsonArray;
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Division div = new Division();
                div.setId_division(object.getInt("id_division"));
                div.setDivision(object.getString("division"));
                lista.add(div);
            }
            txtResult.setText(sb.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Planta> ListaObjetosPlanta(JSONArray jsonArray){

        List<Planta> lista= new ArrayList<Planta>();
        try {
            JSONArray json = jsonArray;
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Planta planta = new Planta();
                planta.setId_planta(object.getInt("id_planta"));
                planta.setPlanta(object.getString("planta"));
                planta.setId_division(object.getInt("id_division"));
                lista.add(planta);
            }
            txtResult.setText(sb.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Area> ListaObjetosArea(JSONArray jsonArray){

        List<Area> lista= new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Area area = new Area();
                area.setId_area(object.getInt("id_area"));
                area.setArea(object.getString("area"));
                area.setLider(object.getString("lider"));
                area.setId_planta(object.getInt("id_planta"));
                area.setId_gerente(object.getInt("id_gerente"));
                lista.add(area);
            }
            txtResult.setText(sb.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Gerente> ListaObjetosGerente(JSONArray jsonArray){

        List<Gerente> lista = new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Gerente gerente = new Gerente();
                gerente.setId_gerente(object.getInt("id_gerente"));
                gerente.setGerente(object.getString("gerente"));
                lista.add(gerente);
            }
            txtResult.setText(sb.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Auditor> ListaObjetosAuditor(JSONArray jsonArray){

        List<Auditor> lista= new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Auditor auditor = new Auditor();
                auditor.setId_auditor(object.getInt("id_auditor"));
                auditor.setAuditor(object.getString("auditor"));
                lista.add(auditor);
            }
            txtResult.setText(sb.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Hallazgos> ListaObjetosHallazgo(JSONArray jsonArray){

        List<Hallazgos> lista = new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                Hallazgos hallazgos = new Hallazgos();
                hallazgos.setId_hallazgo(object.getInt("id_hallazgo"));
                hallazgos.setHallazgo(object.getString("hallazgo"));
                lista.add(hallazgos);
            }
            txtResult.setText(sb.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<HallazgoDetalle> ListaObjetosDetalle(JSONArray jsonArray){

        List<HallazgoDetalle> lista= new ArrayList<>();
        try {
            JSONArray json = jsonArray;
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<json.length(); i++){
                JSONObject object = new JSONObject(json.getString(i));
                HallazgoDetalle detalle = new HallazgoDetalle();
                detalle.setId_detalle(object.getInt("id_detalle"));
                detalle.setId_hallazgo(object.getInt("id_hallazgo"));
                detalle.setDescripcion(object.getString("descripcion"));
                lista.add(detalle);
            }
            txtResult.setText(sb.toString());
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
            values.put(Utilidades.CAMPO_LIDER, dato.getLider());
            values.put(Utilidades.CAMPO_ID_PLANTA, dato.getId_planta());
            values.put(Utilidades.CAMPO_ID_GERENTE, dato.getId_gerente());

            Long idResultante = db.insert(Utilidades.TABLA_AREA, Utilidades.CAMPO_ID_AREA, values);
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

        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), "Se ha actualizado la base de datos local", Toast.LENGTH_LONG).show();
    }


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
}
