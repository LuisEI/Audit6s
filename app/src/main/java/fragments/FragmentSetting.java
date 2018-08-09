package fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liraheta.audit6s.R;

import java.io.File;

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
public class FragmentSetting extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Button btnGuardarPreferencias;
    private EditText urlWebServices;

    public FragmentSetting() {
        // Required empty public constructor
    }

    private View vista;
    private Button btnBorrarRegistros;
    private CheckBox chkSonido;
    private ConexionSQLiteHelper conn;

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
        btnBorrarRegistros = vista.findViewById(R.id.btnBorrarRegistros);
        btnGuardarPreferencias = vista.findViewById(R.id.btnGuardarPreferencias);
        chkSonido = vista.findViewById(R.id.chkSonidos);

        urlWebServices = vista.findViewById(R.id.urlWebServices);
        urlWebServices.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        btnBorrarRegistros.setOnClickListener(this);
        btnGuardarPreferencias.setOnClickListener(this);

        CargarPreferencias();
        TextView txtMemory = vista.findViewById(R.id.txtMemoriaUtilizada);
        txtMemory.setText(CantidadImagenes() + " MB");

        return vista;
    }


    private void BorrarRegistrosAuditoria() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.delete(Utilidades.TABLA_AUDITORIA, null, null);
        db.delete(Utilidades.TABLA_ENCONTRADO, null, null);
        db.close();
        conn.close();

        BorrarImagenes();
    }

    private void BorrarImagenes(){
        File dir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        for(File file: dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    private String CantidadImagenes(){
        File dir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        long length = 0;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                length += file.length();
        }
        return String.valueOf(length/1048576);
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
        if(v.getId() == R.id.btnBorrarRegistros){
            BorrarRegistrosAuditoria();
            Toast.makeText(getContext(), "Se han eliminado los registros de auditoria", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.btnGuardarPreferencias){
            GuardarPreferencias();
        }
    }

    private void GuardarPreferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences("opciones", Context.MODE_PRIVATE);

        String url_web_services = urlWebServices.getText().toString();
        boolean sonido = chkSonido.isChecked();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("url_web", url_web_services);
        editor.putBoolean("sonido", sonido);

        editor.apply();

        Toast toast = Toast.makeText(getContext(), "Se guardaron los ajustes", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 40);
        toast.show();
    }

    private void CargarPreferencias(){
        SharedPreferences preferences = getActivity().getSharedPreferences("opciones", Context.MODE_PRIVATE);
        String url = preferences.getString("url_web", "");
        urlWebServices.setText(url);
        if(preferences.getBoolean("sonido", false)){
            chkSonido.setChecked(true);
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
