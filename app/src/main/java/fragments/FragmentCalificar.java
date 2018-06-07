package fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liraheta.audit6s.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    //Instancias de la activity foto
    static final int REQUEST_TAKE_PHOTO = 2;
    String mCurrentPhotoPath;
    private ImageView mImageView;
    private Intent takePictureIntent;


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

        return vista;
    }

    private void CalificarS1(){
        double startN1 = Double.parseDouble(txtStarN1.getText().toString());
        double startN2 = Double.parseDouble(txtStarN2.getText().toString());
        double startN3 = Double.parseDouble(txtStarN3.getText().toString());

        txtNotaS1.setText(String.format("%.0f%%",(startN1 + startN2 + startN3)/15*100));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void LanzarCalificador(final TextView tv, final FloatingActionButton button, String titulo, String msj) {
        //Se crea la ventana para calificar el punto de auditoria
        final View alertFormView = getLayoutInflater().inflate(R.layout.alert_dialog_calificar, null);

        TextView txtTitulo = alertFormView.findViewById(R.id.txtAlertTitulo);
        TextView txtMensaje = alertFormView.findViewById(R.id.txtAlertMensaje);
        Button btnHallazgo = alertFormView.findViewById(R.id.btnAlertHallazgo);
        Button btnCalificar = alertFormView.findViewById(R.id.btnAlertCalif);
        final RatingBar rb = alertFormView.findViewById(R.id.rbCalificar);

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
                LanzarVentanaHallazo();
            }
        });

        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(String.format("%.0f", rb.getRating()));
                alertDialog.dismiss();
                CalificarS1();
                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorActivado)));
            }
        });

    }

    private void LanzarVentanaHallazo() {

        //Se crea la ventana de hallazgos
        final View alertFormView = getLayoutInflater().inflate(R.layout.alert_dialog_hallazgo, null);

        //Instancia de los componentes
        Button btnTomarFoto = alertFormView.findViewById(R.id.btnTomarFoto);
        Button btnOKHallazgo = alertFormView.findViewById(R.id.btnOkHallazgo);
        Button btnAgregarHallazgo = alertFormView.findViewById(R.id.btnAgregarHallazgo);
        mImageView = alertFormView.findViewById(R.id.imaFoto);

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setView(alertFormView);
        final AlertDialog alertHallazgo = builder.create();

        btnOKHallazgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertHallazgo.dismiss();
            }
        });

        alertHallazgo.show();

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

        // se guarda la direccion de la imagen
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
            LanzarCalificador(txtStarN1, fab_n1,"SELECCIONAR", "¿Hay objetos que no corresponden al area de trabajo?");
        }
        else if(v.getId() == R.id.fab_n2){
            LanzarCalificador(txtStarN2, fab_n2,"SELECCIONAR", "¿Se han definido niveles de inventario de materiales necesarios para realizar el trabajo?");
        }
        else if(v.getId() == R.id.fab_n3){
            LanzarCalificador(txtStarN3, fab_n3,"SELECCIONAR", "¿Los niveles de inventario estan dentro de los limites establecidos?");
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
