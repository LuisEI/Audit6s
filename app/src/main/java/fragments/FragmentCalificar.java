package fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liraheta.audit6s.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import adapters.SpinnerAdaptador;
import adapters.ViewPageAdaptador;
import sqlite.ConexionSQLiteHelper;
import utilidades.Utilidades;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCalificar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCalificar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCalificar extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View vista;
    private TextView txtStarN1;
    private TextView txtStarN2;
    private TextView txtStarN3;
    private TextView txtStarN4;
    private TextView txtStarN5;
    private TextView txtStarN6;
    private TextView txtStarN7;
    private TextView txtStarN8;
    private TextView txtStarN9;
    private TextView txtStarN10;
    private TextView txtStarN11;
    private TextView txtStarN12;
    private TextView txtStarN13;
    private TextView txtStarN14;
    private TextView txtStarN15;
    private TextView txtStarN16;
    private TextView txtStarN17;
    private TextView txtStarN18;
    private TextView txtStarN19;

    private FloatingActionButton fab_n1;
    private FloatingActionButton fab_n2;
    private FloatingActionButton fab_n3;
    private FloatingActionButton fab_n4;
    private FloatingActionButton fab_n5;
    private FloatingActionButton fab_n6;
    private FloatingActionButton fab_n7;
    private FloatingActionButton fab_n8;
    private FloatingActionButton fab_n9;
    private FloatingActionButton fab_n10;
    private FloatingActionButton fab_n11;
    private FloatingActionButton fab_n12;
    private FloatingActionButton fab_n13;
    private FloatingActionButton fab_n14;
    private FloatingActionButton fab_n15;
    private FloatingActionButton fab_n16;
    private FloatingActionButton fab_n17;
    private FloatingActionButton fab_n18;
    private FloatingActionButton fab_n19;

    private ImageButton btnHallazgoN1;
    private ImageButton btnHallazgoN2;
    private ImageButton btnHallazgoN3;
    private ImageButton btnHallazgoN4;
    private ImageButton btnHallazgoN5;
    private ImageButton btnHallazgoN6;
    private ImageButton btnHallazgoN7;
    private ImageButton btnHallazgoN8;
    private ImageButton btnHallazgoN9;
    private ImageButton btnHallazgoN10;
    private ImageButton btnHallazgoN11;
    private ImageButton btnHallazgoN12;
    private ImageButton btnHallazgoN13;
    private ImageButton btnHallazgoN14;
    private ImageButton btnHallazgoN15;
    private ImageButton btnHallazgoN16;
    private ImageButton btnHallazgoN17;
    private ImageButton btnHallazgoN18;
    private ImageButton btnHallazgoN19;


    private TextView txtNotaS1;
    private TextView txtNotaS2;
    private TextView txtNotaS3;
    private TextView txtNotaS4;
    private TextView txtNotaS5;

    //Matrix de Hallazgos
    private List<Map<String,Integer>> matrizHallazgos;

    private ConexionSQLiteHelper conn;
    boolean hallazgoSelect = false;

    //Instancias de la activity foto
    static final int REQUEST_TAKE_PHOTO = 2;
    String mCurrentPhotoPath;
    private ImageView mImageView;
    private Intent takePictureIntent;
    private ArrayList<String> listaSpinner;
    private ArrayList<String> listaImagenes;
    private Map<Integer,Integer> mapaHallazgoReferencia;
    private ViewPager viewPager;
    private TextView txtHallazgo;


    public FragmentCalificar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCalificar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCalificar newInstance(String param1, String param2) {
        FragmentCalificar fragment = new FragmentCalificar();
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
        vista = inflater.inflate(R.layout.fragment_fragment_calificar, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);

        txtStarN1 = vista.findViewById(R.id.txtStarN1);
        txtStarN2 = vista.findViewById(R.id.txtStarN2);
        txtStarN3 = vista.findViewById(R.id.txtStarN3);
        txtStarN4 = vista.findViewById(R.id.txtStarN4);
        txtStarN5 = vista.findViewById(R.id.txtStarN5);
        txtStarN6 = vista.findViewById(R.id.txtStarN6);
        txtStarN7 = vista.findViewById(R.id.txtStarN7);
        txtStarN8 = vista.findViewById(R.id.txtStarN8);
        txtStarN9 = vista.findViewById(R.id.txtStarN9);
        txtStarN10 = vista.findViewById(R.id.txtStarN10);
        txtStarN11 = vista.findViewById(R.id.txtStarN11);
        txtStarN12 = vista.findViewById(R.id.txtStarN12);
        txtStarN13 = vista.findViewById(R.id.txtStarN13);
        txtStarN14 = vista.findViewById(R.id.txtStarN14);
        txtStarN15 = vista.findViewById(R.id.txtStarN15);
        txtStarN16 = vista.findViewById(R.id.txtStarN16);
        txtStarN17 = vista.findViewById(R.id.txtStarN17);
        txtStarN18 = vista.findViewById(R.id.txtStarN18);
        txtStarN19 = vista.findViewById(R.id.txtStarN19);

        txtNotaS1 = vista.findViewById(R.id.txtNotaS1);
        txtNotaS2 = vista.findViewById(R.id.txtNotaS2);
        txtNotaS3 = vista.findViewById(R.id.txtNotaS3);
        txtNotaS4 = vista.findViewById(R.id.txtNotaS4);
        txtNotaS5 = vista.findViewById(R.id.txtNotaS5);

        fab_n1 = vista.findViewById(R.id.fab_n1);
        fab_n2 = vista.findViewById(R.id.fab_n2);
        fab_n3 = vista.findViewById(R.id.fab_n3);
        fab_n4 = vista.findViewById(R.id.fab_n4);
        fab_n5 = vista.findViewById(R.id.fab_n5);
        fab_n6 = vista.findViewById(R.id.fab_n6);
        fab_n7 = vista.findViewById(R.id.fab_n7);
        fab_n8 = vista.findViewById(R.id.fab_n8);
        fab_n9 = vista.findViewById(R.id.fab_n9);
        fab_n10 = vista.findViewById(R.id.fab_n10);
        fab_n11 = vista.findViewById(R.id.fab_n11);
        fab_n12 = vista.findViewById(R.id.fab_n12);
        fab_n13 = vista.findViewById(R.id.fab_n13);
        fab_n14 = vista.findViewById(R.id.fab_n14);
        fab_n15 = vista.findViewById(R.id.fab_n15);
        fab_n16 = vista.findViewById(R.id.fab_n16);
        fab_n17 = vista.findViewById(R.id.fab_n17);
        fab_n18 = vista.findViewById(R.id.fab_n18);
        fab_n19 = vista.findViewById(R.id.fab_n19);

        btnHallazgoN1 = vista.findViewById(R.id.btnHallazgoN1);
        btnHallazgoN2 = vista.findViewById(R.id.btnHallazgoN2);
        btnHallazgoN3 = vista.findViewById(R.id.btnHallazgoN3);
        btnHallazgoN4 = vista.findViewById(R.id.btnHallazgoN4);
        btnHallazgoN5 = vista.findViewById(R.id.btnHallazgoN5);
        btnHallazgoN6 = vista.findViewById(R.id.btnHallazgoN6);
        btnHallazgoN7 = vista.findViewById(R.id.btnHallazgoN7);
        btnHallazgoN8 = vista.findViewById(R.id.btnHallazgoN8);
        btnHallazgoN9 = vista.findViewById(R.id.btnHallazgoN9);
        btnHallazgoN10 = vista.findViewById(R.id.btnHallazgoN10);
        btnHallazgoN11 = vista.findViewById(R.id.btnHallazgoN11);
        btnHallazgoN12 = vista.findViewById(R.id.btnHallazgoN12);
        btnHallazgoN13 = vista.findViewById(R.id.btnHallazgoN13);
        btnHallazgoN14 = vista.findViewById(R.id.btnHallazgoN14);
        btnHallazgoN15 = vista.findViewById(R.id.btnHallazgoN15);
        btnHallazgoN16 = vista.findViewById(R.id.btnHallazgoN16);
        btnHallazgoN17 = vista.findViewById(R.id.btnHallazgoN17);
        btnHallazgoN18 = vista.findViewById(R.id.btnHallazgoN18);
        btnHallazgoN19 = vista.findViewById(R.id.btnHallazgoN19);

        fab_n1.setOnClickListener(this);
        fab_n2.setOnClickListener(this);
        fab_n3.setOnClickListener(this);
        fab_n4.setOnClickListener(this);
        fab_n5.setOnClickListener(this);
        fab_n6.setOnClickListener(this);
        fab_n7.setOnClickListener(this);
        fab_n8.setOnClickListener(this);
        fab_n9.setOnClickListener(this);
        fab_n10.setOnClickListener(this);
        fab_n11.setOnClickListener(this);
        fab_n12.setOnClickListener(this);
        fab_n13.setOnClickListener(this);
        fab_n14.setOnClickListener(this);
        fab_n15.setOnClickListener(this);
        fab_n16.setOnClickListener(this);
        fab_n17.setOnClickListener(this);
        fab_n18.setOnClickListener(this);
        fab_n19.setOnClickListener(this);

        btnHallazgoN1.setOnClickListener(this);
        btnHallazgoN2.setOnClickListener(this);
        btnHallazgoN3.setOnClickListener(this);
        btnHallazgoN4.setOnClickListener(this);
        btnHallazgoN5.setOnClickListener(this);
        btnHallazgoN6.setOnClickListener(this);
        btnHallazgoN7.setOnClickListener(this);
        btnHallazgoN8.setOnClickListener(this);
        btnHallazgoN9.setOnClickListener(this);
        btnHallazgoN10.setOnClickListener(this);
        btnHallazgoN11.setOnClickListener(this);
        btnHallazgoN12.setOnClickListener(this);
        btnHallazgoN13.setOnClickListener(this);
        btnHallazgoN14.setOnClickListener(this);
        btnHallazgoN15.setOnClickListener(this);
        btnHallazgoN16.setOnClickListener(this);
        btnHallazgoN17.setOnClickListener(this);
        btnHallazgoN18.setOnClickListener(this);
        btnHallazgoN19.setOnClickListener(this);

        matrizHallazgos = new ArrayList<>();

        for (int i=0; i<19; i++){
            matrizHallazgos.add(new HashMap<String, Integer>());
        }

        return vista;
    }

    private void CalificarNota(FloatingActionButton b){

        if(b.getId() == R.id.fab_n1 || b.getId() == R.id.fab_n2 || b.getId() == R.id.fab_n3){
            double startN1 = Double.parseDouble(txtStarN1.getText().toString());
            double startN2 = Double.parseDouble(txtStarN2.getText().toString());
            double startN3 = Double.parseDouble(txtStarN3.getText().toString());
            txtNotaS1.setText(String.format("%.0f%%",(startN1 + startN2 + startN3)/15*100));
        }else if(b.getId() == R.id.fab_n4 || b.getId() == R.id.fab_n5 || b.getId() == R.id.fab_n6 || b.getId() == R.id.fab_n7){
            CalculoNota(txtStarN4, txtStarN5, txtStarN6, txtStarN7, txtNotaS2);
        }else if(b.getId() == R.id.fab_n8 || b.getId() == R.id.fab_n9 || b.getId() == R.id.fab_n10 || b.getId() == R.id.fab_n11){
            CalculoNota(txtStarN8, txtStarN9, txtStarN10, txtStarN11, txtNotaS3);
        }else if(b.getId() == R.id.fab_n12 || b.getId() == R.id.fab_n13 || b.getId() == R.id.fab_n14 || b.getId() == R.id.fab_n15){
            CalculoNota(txtStarN12, txtStarN13, txtStarN14, txtStarN15, txtNotaS4);
        }else if(b.getId() == R.id.fab_n16 || b.getId() == R.id.fab_n17 || b.getId() == R.id.fab_n18 || b.getId() == R.id.fab_n19){
            CalculoNota(txtStarN16, txtStarN17, txtStarN18, txtStarN19, txtNotaS5);
        }

    }

    private static void CalculoNota(TextView txtEstrallas1, TextView txtEstrallas2, TextView txtEstrallas3, TextView txtEstrallas4, TextView txtNota) {
        double startN1 = Double.parseDouble(txtEstrallas1.getText().toString());
        double startN2 = Double.parseDouble(txtEstrallas2.getText().toString());
        double startN3 = Double.parseDouble(txtEstrallas3.getText().toString());
        double startN4 = Double.parseDouble(txtEstrallas4.getText().toString());
        txtNota.setText(String.format("%.0f%%",(startN1 + startN2 + startN3 + startN4)/20*100));
    }

    private void LlenarListaSpinner(int index){
        SQLiteDatabase db = conn.getReadableDatabase();
        listaSpinner = new ArrayList<>();
        mapaHallazgoReferencia = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_DETALLE +" WHERE id_hallazgo = ?", new String[]{String.valueOf(index + 1)});
        int count = 0;
        while (cursor.moveToNext()){
            listaSpinner.add(cursor.getString(2));
            mapaHallazgoReferencia.put(count++, cursor.getInt(1));
        }

        db.close();
        cursor.close();
    }

    private String GetHallazgo(int id){
        SQLiteDatabase db = conn.getReadableDatabase();
        String descripcion = "";
        Cursor cursor = db.rawQuery("SELECT descripcion FROM "+ Utilidades.TABLA_DETALLE +" WHERE id_detalle = ?", new String[]{String.valueOf(id)});
        while (cursor.moveToNext()){
            descripcion = cursor.getString(0);
        }

        db.close();
        cursor.close();
        return descripcion;
    }

    private int GetIndex(FloatingActionButton boton){
        int index = -1;

        if(boton.getId() == R.id.fab_n1) index = 0;
        else if(boton.getId() == R.id.fab_n2) index = 1;
        else if(boton.getId() == R.id.fab_n3) index = 2;
        else if(boton.getId() == R.id.fab_n4) index = 3;
        else if(boton.getId() == R.id.fab_n5) index = 4;
        else if(boton.getId() == R.id.fab_n6) index = 5;
        else if(boton.getId() == R.id.fab_n7) index = 6;
        else if(boton.getId() == R.id.fab_n8) index = 7;
        else if(boton.getId() == R.id.fab_n9) index = 8;
        else if(boton.getId() == R.id.fab_n10) index = 9;
        else if(boton.getId() == R.id.fab_n11) index = 10;
        else if(boton.getId() == R.id.fab_n12) index = 11;
        else if(boton.getId() == R.id.fab_n13) index = 12;
        else if(boton.getId() == R.id.fab_n14) index = 13;
        else if(boton.getId() == R.id.fab_n15) index = 14;
        else if(boton.getId() == R.id.fab_n16) index = 15;
        else if(boton.getId() == R.id.fab_n17) index = 16;
        else if(boton.getId() == R.id.fab_n18) index = 17;
        else if(boton.getId() == R.id.fab_n19) index = 18;

        return index;
    }

    private void MostrarBotonHallazgo(int index){

        if(index == 0) btnHallazgoN1.setVisibility(View.VISIBLE);
        else if(index == 1) btnHallazgoN2.setVisibility(View.VISIBLE);
        else if(index == 2) btnHallazgoN3.setVisibility(View.VISIBLE);
        else if(index == 3) btnHallazgoN4.setVisibility(View.VISIBLE);
        else if(index == 4) btnHallazgoN5.setVisibility(View.VISIBLE);
        else if(index == 5) btnHallazgoN6.setVisibility(View.VISIBLE);
        else if(index == 6) btnHallazgoN7.setVisibility(View.VISIBLE);
        else if(index == 7) btnHallazgoN8.setVisibility(View.VISIBLE);
        else if(index == 8) btnHallazgoN9.setVisibility(View.VISIBLE);
        else if(index == 9) btnHallazgoN10.setVisibility(View.VISIBLE);
        else if(index == 10) btnHallazgoN11.setVisibility(View.VISIBLE);
        else if(index == 11) btnHallazgoN12.setVisibility(View.VISIBLE);
        else if(index == 12) btnHallazgoN13.setVisibility(View.VISIBLE);
        else if(index == 13) btnHallazgoN14.setVisibility(View.VISIBLE);
        else if(index == 14) btnHallazgoN15.setVisibility(View.VISIBLE);
        else if(index == 15) btnHallazgoN16.setVisibility(View.VISIBLE);
        else if(index == 16) btnHallazgoN17.setVisibility(View.VISIBLE);
        else if(index == 17) btnHallazgoN18.setVisibility(View.VISIBLE);
        else if(index == 18) btnHallazgoN19.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void LanzarCalificadorPunto16(final TextView tv, final FloatingActionButton button) {

        final View viewPunto16 = getLayoutInflater().inflate(R.layout.alert_dialog_punto16, null);
        final NumberPicker npArea = viewPunto16.findViewById(R.id.npPersonaArea);
        final NumberPicker npNoSaben = viewPunto16.findViewById(R.id.npNoConocenObj);
        final TextView txtResultP16 = viewPunto16.findViewById(R.id.txtResultP16);
        final RatingBar rbPunto16 = viewPunto16.findViewById(R.id.rbPunto16);
        Button btnCalP16 = viewPunto16.findViewById(R.id.btnCalPunto16);

        npArea.setMinValue(1);
        npArea.setMaxValue(50);

        npNoSaben.setMinValue(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setView(viewPunto16);
        final AlertDialog alertDialog = builder.create();

        npArea.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                npNoSaben.setMaxValue(newVal);
                double p = CalcularPorcentaje(npArea.getValue(), npNoSaben.getValue());
                txtResultP16.setText((String.format("%.0f%%", p*100)));
                rbPunto16.setRating(ObtenerEstrella(p));
            }
        });

        npNoSaben.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                double p = CalcularPorcentaje(npArea.getValue(), npNoSaben.getValue());
                txtResultP16.setText((String.format("%.0f%%", p*100)));
                rbPunto16.setRating(ObtenerEstrella(p));
            }
        });

        alertDialog.show();

        btnCalP16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(String.format("%.0f", rbPunto16.getRating()));
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                alertDialog.dismiss();
            }
        });
    }

    private float ObtenerEstrella(double percent) {
        float start;
        if(percent == 1){
            start=5;
        }else if(percent >= 0.8){
            start=4;
        }else if(percent >= 0.6){
            start=3;
        }else if(percent >= 0.4){
            start=2;
        }else if(percent >= 0.2){
            start=1;
        }else {
            start=0;
        }
        return start;
    }

    private double CalcularPorcentaje(int personas, int noSaben) {
        return (personas - noSaben) * 1.0 / personas;
    }

    private void LanzarCalificador(final TextView tv, final FloatingActionButton button, float paso, String titulo, String msj) {
        //Se crea la ventana para calificar el punto de auditoria
        final View alertFormView = getLayoutInflater().inflate(R.layout.alert_dialog_calificar, null);

        TextView txtTitulo = alertFormView.findViewById(R.id.txtAlertTitulo);
        TextView txtMensaje = alertFormView.findViewById(R.id.txtAlertMensaje);
        FloatingActionButton btnHallazgo = alertFormView.findViewById(R.id.btnAlertHallazgo);
        Button btnCalificar = alertFormView.findViewById(R.id.btnAlertCalif);
        final RatingBar rb = alertFormView.findViewById(R.id.rbCalificar);

        rb.setStepSize(paso);

        txtTitulo.setText(titulo);
        txtMensaje.setText(msj);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setView(alertFormView);
        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btnHallazgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanzarVentanaHallazo(button);
            }
        });

        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(String.format("%.0f", rb.getRating()));
                if(matrizHallazgos.get(GetIndex(button)).size() > 0) MostrarBotonHallazgo(GetIndex(button));
                CalificarNota(button);
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
                alertDialog.dismiss();
            }
        });

    }

    private void LanzarVentanaHallazo(final FloatingActionButton b) {

        //Se crea la ventana de hallazgos
        final View alertFormView = getLayoutInflater().inflate(R.layout.alert_dialog_hallazgo, null);

        //Instancia de los componentes
        final Spinner spHallazgo = alertFormView.findViewById(R.id.spHallazgo);
        Button btnTomarFoto = alertFormView.findViewById(R.id.btnTomarFoto);
        Button btnOKHallazgo = alertFormView.findViewById(R.id.btnOkHallazgo);
        Button btnAgregarHallazgo = alertFormView.findViewById(R.id.btnAgregarHallazgo);
        mImageView = alertFormView.findViewById(R.id.imaFoto);


        LlenarListaSpinner(GetIndex(b));

        final SpinnerAdaptador adaptador = new SpinnerAdaptador(getContext(), R.layout.spinner_textview);
        adaptador.addAll(listaSpinner);
        adaptador.add("Seleccione el Hallazgo");
        spHallazgo.setAdapter(adaptador);
        spHallazgo.setSelection(adaptador.getCount());

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                hallazgoSelect = true;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setView(alertFormView);
        final AlertDialog alertHallazgo = builder.create();

        listaImagenes = new ArrayList<>();

        btnAgregarHallazgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hallazgoSelect && !spHallazgo.getSelectedItem().toString().equals("Seleccione el Hallazgo")){
                    matrizHallazgos.get(GetIndex(b)).put(mCurrentPhotoPath, spHallazgo.getSelectedItemPosition());
                    spHallazgo.setSelection(adaptador.getCount());
                    mImageView.setImageResource(R.drawable.cam);
                    hallazgoSelect = false;
                    Toast.makeText(getContext(), "Se agrego un hallazgo", Toast.LENGTH_SHORT).show();
                }else if(spHallazgo.getSelectedItem().toString().equals("Seleccione el Hallazgo")){
                    Toast.makeText(getContext(), "Debe seleccionar el hallazgo", Toast.LENGTH_SHORT).show();
                }else if(!hallazgoSelect){
                    Toast.makeText(getContext(), "Debe tomar una fotografia", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOKHallazgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hallazgoSelect){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                    builder.setTitle("No ha almacenado el hallazgo")
                            .setMessage("¿Desea almecenar el hallazgo?")
                            .setPositiveButton("Almacenar el hallazgo y salir", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    matrizHallazgos.get(GetIndex(b)).put(mCurrentPhotoPath, spHallazgo.getSelectedItemPosition());
                                    spHallazgo.setSelection(-1);
                                    mImageView.setImageResource(R.drawable.cam);
                                    hallazgoSelect = false;
                                    Toast.makeText(getContext(), "Se agrego un hallazgo", Toast.LENGTH_SHORT).show();
                                    alertHallazgo.dismiss();
                                }
                            })
                            .setNegativeButton("No guardar y Salir", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    hallazgoSelect = false;
                                    alertHallazgo.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {
                    alertHallazgo.dismiss();
                }
            }
        });

        alertHallazgo.show();

    }

    private void LanzarGaleriaHallazgo(FloatingActionButton button) {

        final View alertFormGaleria = getLayoutInflater().inflate(R.layout.alert_dialog_galeria_hallazgo, null);

        viewPager = alertFormGaleria.findViewById(R.id.galeria_hallazgo);
        txtHallazgo = alertFormGaleria.findViewById(R.id.galeria_hallazgo_text);
        Button botonSalir = alertFormGaleria.findViewById(R.id.btnSalirGaleria);

        final List<Integer> lista_Ref_hallazgo = new ArrayList<>();

        Iterator it = matrizHallazgos.get(GetIndex(button)).keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            listaImagenes.add(key);
            lista_Ref_hallazgo.add(mapaHallazgoReferencia.get(matrizHallazgos.get(GetIndex(button)).get(key)));
        }

        txtHallazgo.setText(GetHallazgo(lista_Ref_hallazgo.get(0)));

        ViewPageAdaptador adaptador = new ViewPageAdaptador(getContext(), listaImagenes);
        viewPager.setAdapter(adaptador);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txtHallazgo.setText(GetHallazgo(lista_Ref_hallazgo.get(position)));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setView(alertFormGaleria);
        final AlertDialog alertDialog = builder.create();

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void dispatchTakePictureIntent() {
        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error cuando se crea la imagen
            }
            // Continua solo si el archivo se ha creado correctamente
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //Inicia la camara y guarda el resultado
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Se crea el nombre de la imagen
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Se guarda la direccion de la imagen
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File file = new File(mCurrentPhotoPath);
            if(file.exists()){
                Bitmap mImage = BitmapFactory.decodeFile(mCurrentPhotoPath);
                Bitmap mImageBitmap = Bitmap.createScaledBitmap(mImage, 500, 400, true);
                mImageView.setImageBitmap(mImageBitmap);
            }
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
        if(v.getId() == R.id.fab_n1){
            LanzarCalificador(txtStarN1, fab_n1, 1,"SELECCIONAR", "¿Hay objetos que no corresponden al area de trabajo?");
        }
        else if(v.getId() == R.id.fab_n2){
            LanzarCalificador(txtStarN2, fab_n2, 1,"SELECCIONAR", "¿Se han definido niveles de inventario de materiales necesarios para realizar el trabajo?");
        }
        else if(v.getId() == R.id.fab_n3){
            LanzarCalificador(txtStarN3, fab_n3, 1,"SELECCIONAR", "¿Los niveles de inventario estan dentro de los limites establecidos?");
        }
        else if(v.getId() == R.id.fab_n4){
            LanzarCalificador(txtStarN4, fab_n4, 1,"ORGANIZAR", "¿Estan identificadas las  herramientas necesarias para realizar el trabajo?");
        }
        else if(v.getId() == R.id.fab_n5){
            LanzarCalificador(txtStarN5, fab_n5, 1,"ORGANIZAR", "¿Encuentro los elementos de mi lugar de trabajo en máximo 30 segundos?");
        }
        else if(v.getId() == R.id.fab_n6){
            LanzarCalificador(txtStarN6, fab_n6, 1,"ORGANIZAR", "¿Cada cosa esta colocada en el lugar establecido?");
        }
        else if(v.getId() == R.id.fab_n7){
            LanzarCalificador(txtStarN7, fab_n7, 1,"ORGANIZAR", "¿No se observa ninguna condicion insegura en el orden del area?");
        }
        else if(v.getId() == R.id.fab_n8){
            LanzarCalificador(txtStarN8, fab_n8, 1,"LIMPIEZA", "¿Existe un rol definido para mantener limpia el area y puesto de trabajo?");
        }
        else if(v.getId() == R.id.fab_n9){
            LanzarCalificador(txtStarN9, fab_n9, 1,"LIMPIEZA", "¿Se poseen las herramientas necesarias para realizar el rol de limpieza?");
        }
        else if(v.getId() == R.id.fab_n10){
            LanzarCalificador(txtStarN10, fab_n10, 1,"LIMPIEZA", "¿Los pisos y las paredes se encuentran limpios?");
        }
        else if(v.getId() == R.id.fab_n11){
            LanzarCalificador(txtStarN11, fab_n11, 1,"LIMPIEZA", "¿La estacion de trabajo ( escritorio, maquina, gavetas, computadora, estante) se encuentra limpias ( sin adhesivos, polvo, grasa, etc)?");
        }
        else if(v.getId() == R.id.fab_n12){
            LanzarCalificador(txtStarN12, fab_n12, 5,"ESTÁNDAR", "¿Existen un estandar visual de 6S´s del area y publicado en un lugar accesible?");
        }
        else if(v.getId() == R.id.fab_n13){
            LanzarCalificador(txtStarN13, fab_n13, 1,"ESTÁNDAR", "¿Existen señalizaciones de seguridad? (Uso de EPP, Extintores, equipos, etc)");
        }
        else if(v.getId() == R.id.fab_n14){
            LanzarCalificador(txtStarN14, fab_n14, 1,"ESTÁNDAR", "¿Se encuentra etiquetados y demarcados la estación de trabajo y los objetos dentro de la misma?");
        }
        else if(v.getId() == R.id.fab_n15){
            LanzarCalificador(txtStarN15, fab_n15, 1,"ESTÁNDAR", "¿Se respeta y se mantiene el estandar visual  de 6S´s definido?");
        }
        else if(v.getId() == R.id.fab_n16){
            LanzarCalificadorPunto16(txtStarN16, fab_n16);
        }
        else if(v.getId() == R.id.fab_n17){
            LanzarCalificador(txtStarN17, fab_n17, 5,"SOSTENER", "¿Resultados de auditorias anteriores a la vista y actualizados?");
        }
        else if(v.getId() == R.id.fab_n18){
            LanzarCalificador(txtStarN18, fab_n18, 1,"SOSTENER", "No existen hallazgos recurrentes");
        }
        else if(v.getId() == R.id.fab_n19){
            LanzarCalificador(txtStarN19, fab_n19, 5,"SOSTENER", "¿Existe un calendario (quien, cuando) y se realizan auto-auditorias en el area?");
        }
        else if(v.getId() == R.id.btnHallazgoN1){
            LanzarGaleriaHallazgo(fab_n1);
        }
        else if(v.getId() == R.id.btnHallazgoN2){
            LanzarGaleriaHallazgo(fab_n2);
        }
        else if(v.getId() == R.id.btnHallazgoN3){
            LanzarGaleriaHallazgo(fab_n3);
        }
        else if(v.getId() == R.id.btnHallazgoN4){
            LanzarGaleriaHallazgo(fab_n4);
        }
        else if(v.getId() == R.id.btnHallazgoN5){
            LanzarGaleriaHallazgo(fab_n5);
        }
        else if(v.getId() == R.id.btnHallazgoN6){
            LanzarGaleriaHallazgo(fab_n6);
        }
        else if(v.getId() == R.id.btnHallazgoN7){
            LanzarGaleriaHallazgo(fab_n7);
        }
        else if(v.getId() == R.id.btnHallazgoN8){
            LanzarGaleriaHallazgo(fab_n8);
        }
        else if(v.getId() == R.id.btnHallazgoN9){
            LanzarGaleriaHallazgo(fab_n9);
        }
        else if(v.getId() == R.id.btnHallazgoN10){
            LanzarGaleriaHallazgo(fab_n10);
        }
        else if(v.getId() == R.id.btnHallazgoN11){
            LanzarGaleriaHallazgo(fab_n11);
        }
        else if(v.getId() == R.id.btnHallazgoN12){
            LanzarGaleriaHallazgo(fab_n12);
        }
        else if(v.getId() == R.id.btnHallazgoN13){
            LanzarGaleriaHallazgo(fab_n13);
        }
        else if(v.getId() == R.id.btnHallazgoN14){
            LanzarGaleriaHallazgo(fab_n14);
        }
        else if(v.getId() == R.id.btnHallazgoN15){
            LanzarGaleriaHallazgo(fab_n15);
        }
        else if(v.getId() == R.id.btnHallazgoN17){
            LanzarGaleriaHallazgo(fab_n17);
        }
        else if(v.getId() == R.id.btnHallazgoN18){
            LanzarGaleriaHallazgo(fab_n18);
        }
        else if(v.getId() == R.id.btnHallazgoN19){
            LanzarGaleriaHallazgo(fab_n19);
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
