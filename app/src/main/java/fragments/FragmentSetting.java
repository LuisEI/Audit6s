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


        List<String[]> listaDetalle = new ArrayList<>();

        listaDetalle.add(new String[]{"1","1","Articulo Personal"});
        listaDetalle.add(new String[]{"1","2","Documentos"});
        listaDetalle.add(new String[]{"1","3","Muebles"});
        listaDetalle.add(new String[]{"1","4","Piezas de maquinas"});
        listaDetalle.add(new String[]{"1","5","Materiales de produccion"});
        listaDetalle.add(new String[]{"1","6","Herramientas de trabajo"});
        listaDetalle.add(new String[]{"1","7","Herramientas de mantenimiento"});
        listaDetalle.add(new String[]{"2","8","Inventario de entrada"});
        listaDetalle.add(new String[]{"2","9","Inventario de proceso"});
        listaDetalle.add(new String[]{"2","10","Inventario de salida"});
        listaDetalle.add(new String[]{"2","11","Documentos"});
        listaDetalle.add(new String[]{"2","12","Herramientas"});
        listaDetalle.add(new String[]{"2","13","Repuestos"});
        listaDetalle.add(new String[]{"3","14","Excede los niveles de inventario de entrada"});
        listaDetalle.add(new String[]{"3","15","Excede los niveles de inventario de salida"});
        listaDetalle.add(new String[]{"3","16","Excede los niveles de inventario en proceso"});
        listaDetalle.add(new String[]{"3","17","Excede los niveles de inventario de documentos"});
        listaDetalle.add(new String[]{"3","18","Excede los niveles de inventario de herramientas"});
        listaDetalle.add(new String[]{"3","19","Excede los niveles de inventario de repuestos"});
        listaDetalle.add(new String[]{"3","20","Desabastecido de inventario de entrada"});
        listaDetalle.add(new String[]{"3","21","Desabastecido de inventario de procesos"});
        listaDetalle.add(new String[]{"3","22","Desabastecido de inventario de salida"});
        listaDetalle.add(new String[]{"3","23","Desabastecido de inventario de Documentos"});
        listaDetalle.add(new String[]{"3","24","Desabastecido de inventario de Herramientas"});
        listaDetalle.add(new String[]{"3","25","Desabastecido de inventario de Repuestos"});
        listaDetalle.add(new String[]{"5","26","Lapicero"});
        listaDetalle.add(new String[]{"5","27","Lapiz"});
        listaDetalle.add(new String[]{"5","28","Espatula"});
        listaDetalle.add(new String[]{"5","29","Llave para desarmar"});
        listaDetalle.add(new String[]{"5","30","pinza"});
        listaDetalle.add(new String[]{"5","31","cuchilla"});
        listaDetalle.add(new String[]{"5","32","documento"});
        listaDetalle.add(new String[]{"5","33","calculadora"});
        listaDetalle.add(new String[]{"5","34","cuaderno"});
        listaDetalle.add(new String[]{"6","35","Maquina"});
        listaDetalle.add(new String[]{"6","36","Carrito de material"});
        listaDetalle.add(new String[]{"6","37","Equipos de medicion"});
        listaDetalle.add(new String[]{"6","38","Equipos de oficina"});
        listaDetalle.add(new String[]{"6","39","Herramientas manuales"});
        listaDetalle.add(new String[]{"6","40","Herramientas de oficina"});
        listaDetalle.add(new String[]{"7","41","Cables en el piso"});
        listaDetalle.add(new String[]{"7","42","Caida a diferente nivel"});
        listaDetalle.add(new String[]{"7","43","Derrame de liquidos"});
        listaDetalle.add(new String[]{"7","44","Peligro de electrocucion"});
        listaDetalle.add(new String[]{"7","45","Peligros de golpes"});
        listaDetalle.add(new String[]{"7","46","atrapamiento por partes moviles"});
        listaDetalle.add(new String[]{"7","47","Peligro de heridas o puncion"});
        listaDetalle.add(new String[]{"8","48","No lo tiene al alcance"});
        listaDetalle.add(new String[]{"8","49","No se ha definido la frecuencia adecuada"});
        listaDetalle.add(new String[]{"8","50","No ha contenmplado area y espacios importantes para limpiar"});
        listaDetalle.add(new String[]{"9","51","No se han contemplado las herramientas necesarias para hacer el rol"});
        listaDetalle.add(new String[]{"9","52","No posee escoba"});
        listaDetalle.add(new String[]{"9","53","No posee pala"});
        listaDetalle.add(new String[]{"9","54","No posee wipes"});
        listaDetalle.add(new String[]{"9","55","No posee quimico limpiador"});
        listaDetalle.add(new String[]{"9","56","No posee trapeador"});
        listaDetalle.add(new String[]{"10","57","Piso Sucio"});
        listaDetalle.add(new String[]{"10","58","Pared Sucia"});
        listaDetalle.add(new String[]{"10","59","Columna Sucia"});
        listaDetalle.add(new String[]{"11","60","Gavetas sucias"});
        listaDetalle.add(new String[]{"11","61","Escritorio Sucio"});
        listaDetalle.add(new String[]{"11","62","Superficie de maquina sucia"});
        listaDetalle.add(new String[]{"11","63","Bandejas de maquinas sucias"});
        listaDetalle.add(new String[]{"11","64","Estantes sucios"});
        listaDetalle.add(new String[]{"11","65","Computadora sucia"});
        listaDetalle.add(new String[]{"11","66","Maquina sucia"});
        listaDetalle.add(new String[]{"11","67","Mueble sucio"});
        listaDetalle.add(new String[]{"11","68","Sillas sucia"});
        listaDetalle.add(new String[]{"12","69","No posee estandar"});
        listaDetalle.add(new String[]{"12","70","No esta colocado el estandar"});
        listaDetalle.add(new String[]{"12","71","El estandar no esta actualizado"});
        listaDetalle.add(new String[]{"12","72","El estandar no esta a colores"});
        listaDetalle.add(new String[]{"13","73","No existe señalizacion en el acceso al area"});
        listaDetalle.add(new String[]{"13","74","No se respeta la señalizacion de uso de EPP"});
        listaDetalle.add(new String[]{"13","75","Los extintores no estan señalizados"});
        listaDetalle.add(new String[]{"13","76","Los paneles electricos no estan señalizados"});
        listaDetalle.add(new String[]{"13","77","Los espacios señalizados para no obstruir no se respetan"});
        listaDetalle.add(new String[]{"14","78","No cumple la rotulacion con el estandar de letra"});
        listaDetalle.add(new String[]{"14","79","No todo esta rotulado"});
        listaDetalle.add(new String[]{"14","80","La rotulacion esta dañada"});
        listaDetalle.add(new String[]{"14","81","No posee rotulacion"});
        listaDetalle.add(new String[]{"14","82","No posee de marcacion"});
        listaDetalle.add(new String[]{"14","83","La demarcacion esta dañada"});
        listaDetalle.add(new String[]{"14","84","La demarcacion no corresponde a los colores establecidos"});
        listaDetalle.add(new String[]{"15","85","No se repeta en los objetos que corresponden al area"});
        listaDetalle.add(new String[]{"15","86","No se respetan los niveles de inventario"});
        listaDetalle.add(new String[]{"15","87","No se respeta en la limpieza del area"});
        listaDetalle.add(new String[]{"15","88","No se respeta en la limpieza en la estacion de trabajo"});
        listaDetalle.add(new String[]{"15","89","No se respetan las demarcaciones del area"});
        listaDetalle.add(new String[]{"16","90","El personal no lo conoce"});
        listaDetalle.add(new String[]{"16","91","El personal no conoce su rol de limpieza"});
        listaDetalle.add(new String[]{"16","92","El personal no conoce el objetivo de las 6S´s"});
        listaDetalle.add(new String[]{"17","93","No esta el documento de registro de las auditorias anteriores"});
        listaDetalle.add(new String[]{"17","94","No esta actualizada la grafica detendenciade 6S´s"});
        listaDetalle.add(new String[]{"18","95","Se repiten los hallazgos del ultimo periodo"});
        listaDetalle.add(new String[]{"19","96","No posee un programa de autoauditorias de 6S´s"});
        listaDetalle.add(new String[]{"19","97","No se ha cumplido el programa"});
        listaDetalle.add(new String[]{"19","98","Lider no ha cumplido su autoevaluacion de 6S´s"});


        for(String[] dato: listaDetalle){
            String sql="INSERT INTO "+ Utilidades.TABLA_DETALLE +" VALUES (?,?,?)";
            db.execSQL(sql,dato);
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
