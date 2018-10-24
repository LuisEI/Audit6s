package fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liraheta.audit6s.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapters.AdaptadorAuditorias;
import adapters.VPAdaptadorConsulta;
import adapters.ViewPageAdaptador;
import entidades.Auditoria;
import entidades.Encontrado;
import sqlite.ConexionSQLiteHelper;
import utilidades.Utilidades;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentConsulta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentConsulta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConsulta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<Auditoria> listaAuditorias;
    RecyclerView recyclerAuditoria;

    ConexionSQLiteHelper conn;
    SQLiteDatabase db;

    private TextView id_auditoria,
            area_consulta,
            lider_consulta,
            txtNotaS1_consulta,
            txtNotaS2_consulta,
            txtNotaS3_consulta,
            txtNotaS4_consulta,
            txtNotaS5_consulta,
            txtNotaTotal_consulta,
            txtFecha_consulta,
            txtSemana_consulta,
            txtHallazgo,
            txtCantidadHallazgos;

    private ViewPager auditoriaViewPager;


    public FragmentConsulta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentConsulta.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConsulta newInstance(String param1, String param2) {
        FragmentConsulta fragment = new FragmentConsulta();
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
        final View vista = inflater.inflate(R.layout.fragment_fragment_consulta, container, false);
        listaAuditorias = new ArrayList<>();
        recyclerAuditoria = vista.findViewById(R.id.recyclerID);
        recyclerAuditoria.setLayoutManager(new LinearLayoutManager(getContext()));

        auditoriaViewPager = vista.findViewById(R.id.auditoriaViewPager);

        id_auditoria = vista.findViewById(R.id.id_auditoria_consulta);
        area_consulta = vista.findViewById(R.id.area_consulta);
        lider_consulta = vista.findViewById(R.id.lider_consulta);
        txtNotaS1_consulta = vista.findViewById(R.id.txtNotaS1_consulta);
        txtNotaS2_consulta = vista.findViewById(R.id.txtNotaS2_consulta);
        txtNotaS3_consulta = vista.findViewById(R.id.txtNotaS3_consulta);
        txtNotaS4_consulta = vista.findViewById(R.id.txtNotaS4_consulta);
        txtNotaS5_consulta = vista.findViewById(R.id.txtNotaS5_consulta);
        txtNotaTotal_consulta = vista.findViewById(R.id.txtNotaTotal_consulta);
        txtFecha_consulta = vista.findViewById(R.id.txtFecha_consulta);
        txtSemana_consulta = vista.findViewById(R.id.txtSemana_consulta);
        txtHallazgo = vista.findViewById(R.id.txtDescripcion_consulta);
        txtCantidadHallazgos = vista.findViewById(R.id.txtCantidadHallazgos);

        LLenarListaAuditorias();
        AdaptadorAuditorias adapter = new AdaptadorAuditorias(listaAuditorias, getContext());
        recyclerAuditoria.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Seleccionado ID: " + String.valueOf(listaAuditorias.get(recyclerAuditoria.getChildAdapterPosition(v)).getId_auditoria()), Toast.LENGTH_SHORT).show();
                MostrarDatosAuditoria(ObtenerAuditoriaPorID(listaAuditorias.get(recyclerAuditoria.getChildAdapterPosition(v)).getId_auditoria()));
            }
        });

        return vista;
    }

    private void MostrarDatosAuditoria(Auditoria auditoria) {

        auditoriaViewPager.setAdapter(null);
        txtHallazgo.setText("");
        txtCantidadHallazgos.setText("0");

        id_auditoria.setText(String.valueOf(auditoria.getId_auditoria()));
        area_consulta.setText(ObtenerArea(auditoria.getArea()));
        lider_consulta.setText(ObtenerLider(auditoria.getLider()));
        txtNotaS1_consulta.setText(String.valueOf(auditoria.getRes_s1()));
        txtNotaS2_consulta.setText(String.valueOf(auditoria.getRes_s2()));
        txtNotaS3_consulta.setText(String.valueOf(auditoria.getRes_s3()));
        txtNotaS4_consulta.setText(String.valueOf(auditoria.getRes_s4()));
        txtNotaS5_consulta.setText(String.valueOf(auditoria.getRes_s5()));
        txtNotaTotal_consulta.setText(String.valueOf(auditoria.getRes_total()));
        txtFecha_consulta.setText(ConvertidorFecha(auditoria.getFecha()));
        txtSemana_consulta.setText(ObtenerSemana(auditoria.getFecha()));

        CargarImagenes(auditoria.getId_auditoria());
    }

    private void CargarImagenes(int id) {

        final List<Integer> lista_id_detalle = new ArrayList<>();
        final List<String> lista_comentarios = new ArrayList<>();
        List<String> listaImagenes = new ArrayList<>();
        final List<String> descripciones;

        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_ENCONTRADO +" WHERE "+ Utilidades.CAMPO_ID_AUDITORIA +" = ?", new String[]{String.valueOf(id)});

        while (cursor.moveToNext()){
            lista_id_detalle.add(cursor.getInt(0));
            listaImagenes.add(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + cursor.getString(1));
            lista_comentarios.add(cursor.getString(3));
        }

        descripciones = ObtenerDescripcionListaDetalle(lista_id_detalle, lista_comentarios);

        db.close();
        cursor.close();
        conn.close();

        if(descripciones.size() > 0){
            txtHallazgo.setText(descripciones.get(0));
            txtCantidadHallazgos.setText(String.valueOf(lista_id_detalle.size()));
            VPAdaptadorConsulta adaptador = new VPAdaptadorConsulta(getContext(), listaImagenes);
            auditoriaViewPager.setAdapter(adaptador);
            auditoriaViewPager.clearOnPageChangeListeners();
            //auditoriaViewPager.setOffscreenPageLimit(3);
            auditoriaViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position < descripciones.size()) txtHallazgo.setText(descripciones.get(position));
                    else txtHallazgo.setText("Hallazgo perdido");
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private List<String> ObtenerDescripcionListaDetalle(List<Integer> id_detalle, List<String> comentarios){
        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        List<String> descripcion = new ArrayList<>();

        Cursor cursor = null;

        for(int id: id_detalle){
            if(id != 0){
                cursor = db.rawQuery("SELECT " + Utilidades.CAMPO_DESCRIPCION + " FROM "+ Utilidades.TABLA_DETALLE +" WHERE "+ Utilidades.CAMPO_ID_DETALLE +" = ?", new String[]{String.valueOf(id)});
                while (cursor.moveToNext()){
                    descripcion.add(cursor.getString(0));
                }
            } else {
                descripcion.add(comentarios.get(descripcion.size()));
            }

        }

        if(cursor != null) cursor.close();
        db.close();
        conn.close();
        return descripcion;
    }

    private String ConvertidorFecha(String fecha_string){
        String dateTime = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = format.parse(fecha_string);
            dateTime = dateFormat.format(date);
        } catch (Exception e) {
            Log.e("ExceptionFecha", e.toString());
        }
        return dateTime;
    }

    private String ObtenerSemana(String fecha_string){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        int week = 0;
        try {
            Date date = format.parse(fecha_string);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            week = cal.get(Calendar.WEEK_OF_YEAR);
        } catch (Exception e) {
            Log.e("ExceptionFecha", e.toString());
        }
        return String.format("Week %d", week);
    }

    private String ObtenerLider(int id) {
        String lider = "";

        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + Utilidades.CAMPO_LIDER +" FROM "+ Utilidades.TABLA_LIDER +" WHERE "+ Utilidades.CAMPO_ID_LIDER +" = ?", new String[]{String.valueOf(id)});

        while (cursor.moveToNext()){
            lider = cursor.getString(0);
        }

        db.close();
        cursor.close();
        conn.close();

        return lider;
    }

    private String ObtenerArea(int area) {
        String areaString = "";

        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + Utilidades.CAMPO_AREA +" FROM "+ Utilidades.TABLA_AREA +" WHERE "+ Utilidades.CAMPO_ID_AREA +" = ?", new String[]{String.valueOf(area)});

        while (cursor.moveToNext()){
            areaString = cursor.getString(0);
        }

        db.close();
        cursor.close();
        conn.close();

        return areaString;
    }

    private void LLenarListaAuditorias() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Auditoria a;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_AUDITORIA, null);

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
            a.setSync(cursor.getInt(31));

            listaAuditorias.add(a);
        }

        db.close();
        cursor.close();

    }

    private Auditoria ObtenerAuditoriaPorID(int id){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Auditoria a = null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_AUDITORIA + " WHERE id_auditoria = ?",new String[]{String.valueOf(id)});

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

        }

        db.close();
        cursor.close();
        conn.close();
        return a;
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
