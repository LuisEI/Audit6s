package fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liraheta.audit6s.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entidades.Division;
import sqlite.ConexionSQLiteHelper;
import utilidades.Utilidades;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentIngreso.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentIngreso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIngreso extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View vista;
    private ConexionSQLiteHelper conn;
    Vibrator vibe;

    private ArrayList<Division> divisionLista;
    private ArrayList<String> listaSpinner;

    private TextInputLayout txtDivision;
    private TextInputLayout txtPlanta;
    private TextInputLayout txtGerente;
    private TextInputLayout txtArea;
    private TextInputLayout txtLider;
    private TextInputLayout txtTurno;
    private TextInputLayout txtFecha;
    private TextInputLayout txtAuditor;

    private FloatingActionButton btnDivision;
    private FloatingActionButton btnPlanta;
    private FloatingActionButton btnGerente;
    private FloatingActionButton btnArea;
    private FloatingActionButton btnTurno;
    private FloatingActionButton btnFecha;
    private FloatingActionButton btnAuditor;

    Map<String,Integer> mapaDivision;
    Map<String,Integer> mapaPlanta;
    Map<String,Integer> mapaGerente;
    Map<String,Integer> mapaArea;
    Map<String,Integer> mapaAuditor;

    private int turno = 0;
    private String fechaMySQL;
    private AlertDialog dialogTurno;

    private Button btnValidar;

    public FragmentIngreso() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentIngreso.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentIngreso newInstance(String param1, String param2) {
        FragmentIngreso fragment = new FragmentIngreso();
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
        vista = inflater.inflate(R.layout.fragment_fragment_ingreso, container, false);

        txtDivision = vista.findViewById(R.id.txtDivision);
        txtPlanta = vista.findViewById(R.id.txtPlanta);
        txtGerente = vista.findViewById(R.id.txtGerente);
        txtArea = vista.findViewById(R.id.txtArea);
        txtLider = vista.findViewById(R.id.txtLider);
        txtTurno = vista.findViewById(R.id.txtTurno);
        txtFecha = vista.findViewById(R.id.txtFecha);
        txtAuditor = vista.findViewById(R.id.txtAuditor);

        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        txtDivision.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    btnDivision.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                    btnDivision.setImageResource(R.drawable.ic_done);
                }else {
                    btnDivision.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBtnDesactivado)));
                    btnDivision.setImageResource(R.drawable.ic_touch_ingreso);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPlanta.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    btnPlanta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                    btnPlanta.setImageResource(R.drawable.ic_done);
                }else {
                    btnPlanta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBtnDesactivado)));
                    btnPlanta.setImageResource(R.drawable.ic_touch_ingreso);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtGerente.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    btnGerente.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                    btnGerente.setImageResource(R.drawable.ic_done);
                }else {
                    btnGerente.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBtnDesactivado)));
                    btnGerente.setImageResource(R.drawable.ic_touch_ingreso);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtArea.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    btnArea.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                    btnArea.setImageResource(R.drawable.ic_done);
                }else {
                    btnArea.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBtnDesactivado)));
                    btnArea.setImageResource(R.drawable.ic_touch_ingreso);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtTurno.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    btnTurno.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                    btnTurno.setImageResource(R.drawable.ic_done);
                }else {
                    btnTurno.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBtnDesactivado)));
                    btnTurno.setImageResource(R.drawable.ic_touch_ingreso);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtFecha.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    btnFecha.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                    btnFecha.setImageResource(R.drawable.ic_done);
                }else {
                    btnFecha.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBtnDesactivado)));
                    btnFecha.setImageResource(R.drawable.ic_touch_ingreso);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtAuditor.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    btnAuditor.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                    btnAuditor.setImageResource(R.drawable.ic_done);
                }else {
                    btnAuditor.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBtnDesactivado)));
                    btnAuditor.setImageResource(R.drawable.ic_touch_ingreso);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDivision = vista.findViewById(R.id.btnDivision);
        btnPlanta = vista.findViewById(R.id.btnPlanta);
        btnGerente = vista.findViewById(R.id.btnGerente);
        btnArea = vista.findViewById(R.id.btnArea);
        btnTurno = vista.findViewById(R.id.btnTurno);
        btnFecha = vista.findViewById(R.id.btnFecha);
        btnAuditor = vista.findViewById(R.id.btnAuditor);
        btnValidar = vista.findViewById(R.id.btnValidar);

        btnDivision.setOnClickListener(this);
        btnPlanta.setOnClickListener(this);
        btnGerente.setOnClickListener(this);
        btnArea.setOnClickListener(this);
        btnTurno.setOnClickListener(this);
        btnFecha.setOnClickListener(this);
        btnAuditor.setOnClickListener(this);
        btnValidar.setOnClickListener(this);

        //Deshabilitar el teclado
        txtDivision.getEditText().setShowSoftInputOnFocus(false);
        txtPlanta.getEditText().setShowSoftInputOnFocus(false);
        txtGerente.getEditText().setShowSoftInputOnFocus(false);
        txtArea.getEditText().setShowSoftInputOnFocus(false);
        txtLider.getEditText().setShowSoftInputOnFocus(false);
        txtTurno.getEditText().setShowSoftInputOnFocus(false);
        txtFecha.getEditText().setShowSoftInputOnFocus(false);
        txtAuditor.getEditText().setShowSoftInputOnFocus(false);

        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);

        //LLenado de Spinner Division
        //consultarDatosSQLite();

        return vista;
    }

    private void LanzarListaSeleccionable(final TextInputLayout txtInput, String tabla, String titulo) {

        LlenarLista(tabla);

        final View alertFormView = getLayoutInflater().inflate(R.layout.alert_dialog_ingreso, null);

        ListView listView = alertFormView.findViewById(R.id.lvIngreso);
        TextView txtLvTitulo = alertFormView.findViewById(R.id.txtLvTitulo);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listaSpinner);
        listView.setAdapter(arrayAdapter);

        txtLvTitulo.setText(titulo);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setView(alertFormView);
        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtInput.getEditText().setText(arrayAdapter.getItem(position));
                if(txtInput.getId() == R.id.txtArea) LlamarLider(arrayAdapter.getItem(position));
                alertDialog.dismiss();
            }
        });
    }

    private void LanzarSelectorFecha(final TextInputLayout txtInput) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaMySQL = (new StringBuilder().append(year).append("-").append(month+1).append("-").append(dayOfMonth)).toString();
                txtInput.getEditText().setText(new StringBuilder().append(dayOfMonth).append("-").append(month+1).append("-").append(year));
            }
        });
        datePickerDialog.show();
    }

    private void SeleccionarTurno(){

        CharSequence[] values = {" Primer Turno "," Segundo Turno "," Tercer Turno "," Cuarto Turno "};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Seleccione el Turno");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        txtTurno.getEditText().setText("Primero");
                        turno = 1;
                        break;
                    case 1:
                        txtTurno.getEditText().setText("Segundo");
                        turno = 2;
                        break;
                    case 2:
                        txtTurno.getEditText().setText("Tercero");
                        turno = 3;
                        break;
                    case 3:
                        txtTurno.getEditText().setText("Cuarto");
                        turno = 4;
                        break;
                }
                dialogTurno.dismiss();
            }
        });
        dialogTurno = builder.create();
        dialogTurno.show();

    }

    private boolean Validar() {
        boolean valido = true;
        if (!Comprobacion(txtDivision, "Debe seleccionar la Division")) valido = false;
        if (!Comprobacion(txtPlanta, "Debe seleccionar la Planta")) valido = false;
        if (!Comprobacion(txtGerente, "Debe seleccionar el Gerente ")) valido = false;
        if (!Comprobacion(txtArea, "Debe seleccionar el Area")) valido = false;
        if (!Comprobacion(txtLider, "Debe seleccionar el Lider")) valido = false;
        if (!Comprobacion(txtTurno, "Debe seleccionar el Turno")) valido = false;
        if (!Comprobacion(txtFecha, "Debe seleccionar la Fecha")) valido = false;
        if (!Comprobacion(txtAuditor, "Debe seleccionar el Auditor")) valido = false;
        return valido;
    }

    private boolean Comprobacion(TextInputLayout txtInput, String texto){
        boolean valido = true;
        if(txtInput.getEditText().getText().toString().equals("")){
            txtInput.setError(texto);
            valido = false;
        }else {
            txtInput.setError(null);
        }
        return valido;
    }

    private void LlenarLista(String tabla){
        SQLiteDatabase db;
        listaSpinner = new ArrayList<String>();
        Cursor cursor;

        if(tabla.equals(Utilidades.TABLA_AREA)){
            int id = LlamarIDplanta(txtPlanta.getEditText().getText().toString());
            db = conn.getReadableDatabase();
            if(id != 0){
                cursor = db.rawQuery("SELECT * FROM "+ tabla+" WHERE id_planta like ?", new String[]{String.valueOf(id)+ "%"});
            }else{
                cursor = db.rawQuery("SELECT * FROM "+ tabla, null);
            }
        }else{
            db = conn.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM "+ tabla, null);
        }

        LLenarMapas(cursor, tabla);

        while (cursor.moveToNext()){
            listaSpinner.add(cursor.getString(1));
        }

        db.close();
        cursor.close();
    }

    private void LLenarMapas(Cursor cursor, String tabla) {

        if (tabla.equals(Utilidades.TABLA_DIVISION)){
            mapaDivision = new HashMap<>();
            while (cursor.moveToNext()){
                mapaDivision.put(cursor.getString(1), cursor.getInt(0));
            }
        } else if (tabla.equals(Utilidades.TABLA_PLANTA)){
            mapaPlanta = new HashMap<>();
            while (cursor.moveToNext()){
                mapaPlanta.put(cursor.getString(1), cursor.getInt(0));
            }
        } else if (tabla.equals(Utilidades.TABLA_GERENTE)){
            mapaGerente = new HashMap<>();
            while (cursor.moveToNext()){
                mapaGerente.put(cursor.getString(1), cursor.getInt(0));
            }
        } else if (tabla.equals(Utilidades.TABLA_AREA)){
            mapaArea = new HashMap<>();
            while (cursor.moveToNext()){
                mapaArea.put(cursor.getString(1), cursor.getInt(0));
            }
        } else if (tabla.equals(Utilidades.TABLA_AUDITOR)){
            mapaAuditor = new HashMap<>();
            while (cursor.moveToNext()){
                mapaAuditor.put(cursor.getString(1), cursor.getInt(0));
            }
        }

        cursor.moveToFirst();
        cursor.moveToPrevious();
    }

    private int LlamarIDplanta(String planta){
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_planta FROM "+ Utilidades.TABLA_PLANTA +" WHERE planta = ?", new String[]{planta});
        int id = 0;
        while (cursor.moveToNext()){
            id = cursor.getInt(0);
        }
        db.close();
        cursor.close();
        return id;
    }

    private void LlamarLider(String area){
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT lider FROM "+ Utilidades.TABLA_AREA +" WHERE area = ?", new String[]{area});

        while (cursor.moveToNext()){
            txtLider.getEditText().setText(cursor.getString(0));
        }

        db.close();
        cursor.close();
    }

    private void consultarDatosSQLite() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Division division;
        divisionLista = new ArrayList<Division>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_DIVISION, null);

        while (cursor.moveToNext()){

            division = new Division();
            division.setId_division(cursor.getInt(0));
            division.setDivision(cursor.getString(1));

            divisionLista.add(division);
        }

        obtenerLista();

        db.close();
        cursor.close();
    }

    private void obtenerLista() {
        listaSpinner = new ArrayList<String>();

        for(int i=0; i<divisionLista.size(); i++){
            listaSpinner.add(divisionLista.get(i).getDivision());
        }
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

    @Override
    public void onClick(View v) {

        vibe.vibrate(20);

        if(v.getId() == R.id.btnDivision){
            txtDivision.getEditText().requestFocus();
            LanzarListaSeleccionable(txtDivision, Utilidades.TABLA_DIVISION, "Seleccione la Division");
        }else if (v.getId() == R.id.btnPlanta){
            txtPlanta.getEditText().requestFocus();
            LanzarListaSeleccionable(txtPlanta, Utilidades.TABLA_PLANTA, "Seleccione la Planta");
        }else if (v.getId() == R.id.btnGerente){
            txtGerente.getEditText().requestFocus();
            LanzarListaSeleccionable(txtGerente, Utilidades.TABLA_GERENTE, "Seleccione el Gerente");
        }else if (v.getId() == R.id.btnArea){
            txtArea.getEditText().requestFocus();
            LanzarListaSeleccionable(txtArea, Utilidades.TABLA_AREA, "Seleccione el Area");
        }else if (v.getId() == R.id.btnTurno){
            SeleccionarTurno();
        }else if (v.getId() == R.id.btnFecha){
            LanzarSelectorFecha(txtFecha);
        }else if (v.getId() == R.id.btnAuditor){
            txtAuditor.getEditText().requestFocus();
            LanzarListaSeleccionable(txtAuditor, Utilidades.TABLA_AUDITOR, "Seleccione el Auditor");
        }else if (v.getId() == R.id.btnValidar){
            if (Validar()) {
                Toast.makeText(getContext(), "Es valido", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putInt("division", mapaDivision.get(txtDivision.getEditText().getText().toString()));
                bundle.putInt("planta", mapaPlanta.get(txtPlanta.getEditText().getText().toString()));
                bundle.putInt("gerente", mapaGerente.get(txtGerente.getEditText().getText().toString()));
                bundle.putInt("area", mapaArea.get(txtArea.getEditText().getText().toString()));
                bundle.putInt("auditor", mapaAuditor.get(txtAuditor.getEditText().getText().toString()));
                bundle.putInt("turno", turno);
                bundle.putString("fecha", fechaMySQL);

                FragmentCalificar fragmentCalificar = new FragmentCalificar();
                fragmentCalificar.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.contenerdorFragment, fragmentCalificar, "calificar");
                ft.addToBackStack(null);
                ft.commit();
            }
        }

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
