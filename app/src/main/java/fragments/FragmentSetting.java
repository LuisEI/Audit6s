package fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liraheta.audit6s.R;

import java.util.ArrayList;
import java.util.List;

import sqlite.ConexionSQLiteHelper;
import utilidades.Utilidades;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSetting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentSetting() {
        // Required empty public constructor
    }

    private View vista;
    private EditText id_div;
    private EditText div;
    private Button btnReg;
    private EditText id_div_leer;
    private EditText div_leer;
    private Button btnLeer;
    private Button btnAgregarDatos;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSetting.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSetting newInstance(String param1, String param2) {
        FragmentSetting fragment = new FragmentSetting();
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
        vista = inflater.inflate(R.layout.fragment_fragment_setting, container, false);

        id_div = vista.findViewById(R.id.id_div);
        div = vista.findViewById(R.id.div);
        btnReg = vista.findViewById(R.id.btnReg);

        btnAgregarDatos = vista.findViewById(R.id.btnAgragarDatos);

        id_div_leer = vista.findViewById(R.id.id_div_leer);
        div_leer = vista.findViewById(R.id.div_leer);
        btnLeer = vista.findViewById(R.id.btnLeer);

        //ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuarion();
            }
        });

        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeerBase();
            }
        });

        btnAgregarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistroAuto();
            }
        });

        return vista;
    }

    private void LeerBase() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametro = {id_div_leer.getText().toString()};
        String[] campos = {Utilidades.CAMPO_DIVISION};

        try{
            Cursor cursor = db.query(Utilidades.TABLA_DIVISION, campos, Utilidades.CAMPO_ID_DIVISION + "=?", parametro,null, null,null,null);
            cursor.moveToFirst();
            div_leer.setText(cursor.getString(0));
            cursor.close();

        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        db.close();
        conn.close();
    }

    private void RegistroAuto(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        Long idResultante;
        int conta = 1;
        List<String> lista = new ArrayList<>();
        lista.add("CMAP");
        lista.add("SMP");
        lista.add("LEADED");
        lista.add("SMD");
        lista.add("ANODE LOAD");

        for(String dato: lista){
            values.put(Utilidades.CAMPO_ID_PLANTA, conta);
            values.put(Utilidades.CAMPO_PLANTA, dato);
            InsertarDatos(db, Utilidades.TABLA_PLANTA, Utilidades.CAMPO_PLANTA, values);
            conta++;
        }

        values.clear();
        lista.clear();
        conta = 1;

        lista.add("CERAMICA");
        lista.add("TANTALUM");
        lista.add("LEADED");

        for(String dato: lista){
            values.put(Utilidades.CAMPO_ID_DIVISION, conta);
            values.put(Utilidades.CAMPO_DIVISION, dato);
            InsertarDatos(db, Utilidades.TABLA_DIVISION, Utilidades.CAMPO_ID_DIVISION, values);
            conta++;
        }

        values.clear();
        lista.clear();
        conta = 1;

        lista.add("Omar Granados");
        lista.add("Clemente Aguilar");
        lista.add("Carlos Umaña");
        lista.add("Carlos Cabrera");
        lista.add("Jonathan Rodriguez");
        lista.add("Oliberto Corvera");
        lista.add("Guido Zepeda");
        lista.add("Marta Sanchez");
        lista.add("Nelson Muñoz");
        lista.add("Carlos Hernandez");
        lista.add("Alex Cruz");

        for(String dato: lista){
            values.put(Utilidades.CAMPO_ID_GERENTE, conta);
            values.put(Utilidades.CAMPO_GERENTE, dato);
            InsertarDatos(db, Utilidades.TABLA_GERENTE, Utilidades.CAMPO_ID_GERENTE, values);
            conta++;
        }

        values.clear();
        lista.clear();
        conta = 1;

        lista.add("Chip Cage");
        lista.add("Terminacion - Pal 1");
        lista.add("Plating - APS 5");
        lista.add("Test -FLASH 26");
        lista.add("Tape & Reel - TW 36");
        lista.add("Taller R&M SMP");

        List<String> lista2 = new ArrayList<>();
        lista2.add("Marlene Elias");
        lista2.add("Staycy Escobar");
        lista2.add("Orlando Batlle");
        lista2.add("Ramon Araujo");
        lista2.add("Armando Mendoza");
        lista2.add("Carlos Urbina");

        for(String dato: lista){
            values.put(Utilidades.CAMPO_ID_AREA, conta);
            values.put(Utilidades.CAMPO_AREA, dato);
            values.put(Utilidades.CAMPO_LIDER, lista2.get(conta-1));

            InsertarDatos(db, Utilidades.TABLA_AREA, Utilidades.CAMPO_LIDER, values);
            conta++;
        }

        values.clear();
        lista.clear();
        conta = 1;

        lista.add("Jonathan Alarcon");
        lista.add("Veronica Abarca");
        lista.add("Karen Bonilla");
        lista.add("Janeth Lopez");
        lista.add("Marta Ayala");
        lista.add("Flor Acosta");
        lista.add("Miguel Urbano");

        for(String dato: lista){
            values.put(Utilidades.CAMPO_ID_AUDITOR, conta);
            values.put(Utilidades.CAMPO_AUDITOR, dato);
            InsertarDatos(db, Utilidades.TABLA_AUDITOR, Utilidades.CAMPO_ID_AREA, values);
            conta++;
        }

        Toast.makeText(getContext(), "Se han agregado los datos", Toast.LENGTH_SHORT).show();

        db.close();
        conn.close();
    }

    private void InsertarDatos(SQLiteDatabase db, String Tabla, String CampoID, ContentValues values) {
        Long idResultante;
        idResultante = db.insert(Tabla, CampoID, values);
    }

    private void RegistrarUsuarion() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_ID_DIVISION, id_div.getText().toString());
        values.put(Utilidades.CAMPO_DIVISION, div.getText().toString());

        Long idResultante = db.insert(Utilidades.TABLA_DIVISION, Utilidades.CAMPO_ID_DIVISION, values);
        Toast.makeText(getContext(), "ID_Division es: " + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
        conn.close();
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
