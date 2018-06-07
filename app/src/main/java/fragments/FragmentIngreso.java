package fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
                txtInput.getEditText().setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
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
                        Toast.makeText(getContext(), "First Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        txtTurno.getEditText().setText("Segundo");
                        Toast.makeText(getContext(), "Second Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        txtTurno.getEditText().setText("Tercero");
                        Toast.makeText(getContext(), "Third Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        txtTurno.getEditText().setText("Cuarto");
                        Toast.makeText(getContext(), "Four Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                }
                dialogTurno.dismiss();
            }
        });
        dialogTurno = builder.create();
        dialogTurno.show();

    }

    private void Validar() {
        Comprobacion(txtDivision, "Debe seleccionar la Division");
        Comprobacion(txtPlanta, "Debe seleccionar la Planta");
        Comprobacion(txtGerente, "Debe seleccionar el Gerente ");
        Comprobacion(txtArea, "Debe seleccionar el Area");
        Comprobacion(txtLider, "Debe seleccionar el Lider");
        Comprobacion(txtTurno, "Debe seleccionar el Turno");
        Comprobacion(txtFecha, "Debe seleccionar la Fecha");
        Comprobacion(txtAuditor, "Debe seleccionar el Auditor");
    }

    private void Comprobacion(TextInputLayout txtInput, String texto){
        if(txtInput.getEditText().getText().toString().equals("")){
            txtInput.setError(texto);
        }else {
            txtInput.setError(null);
        }
    }

    private void LlenarLista(String tabla){
        SQLiteDatabase db = conn.getReadableDatabase();
        listaSpinner = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ tabla, null);

        while (cursor.moveToNext()){
            listaSpinner.add(cursor.getString(1));
        }

        db.close();
        cursor.close();
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
            Validar();
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
