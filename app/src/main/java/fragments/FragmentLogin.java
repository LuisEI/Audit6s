package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liraheta.audit6s.MainActivity;
import com.example.liraheta.audit6s.R;
import com.github.clans.fab.FloatingActionButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import adapters.SpinnerAdaptador;
import interfaces.DrawerLocker;
import sqlite.ConexionSQLiteHelper;
import utilidades.Utilidades;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLogin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "LoginActivity";

    private OnFragmentInteractionListener mListener;
    private Button loginButton;
    private Spinner spinnerLogin;
    private TextView txtPassLogin;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
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
        View vista = inflater.inflate(R.layout.fragment_fragment_login, container, false);

        loginButton = vista.findViewById(R.id.btn_login);
        spinnerLogin = vista.findViewById(R.id.spinnerLogin);
        txtPassLogin = vista.findViewById(R.id.input_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        LlenarLista();
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        return vista;
    }

    private String md5(String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean CompararClave(String auditor, String clave){

        SQLiteOpenHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String pass = "";
        Boolean autorizado = false;

        Cursor cursor = db.rawQuery("SELECT clave FROM "+ Utilidades.TABLA_AUDITOR +" WHERE "+ Utilidades.CAMPO_AUDITOR +" = ?", new String[]{auditor});

        while (cursor.moveToNext()){
            pass = cursor.getString(0);
        }

        cursor.close();
        conn.close();

        if(pass.equals(md5(clave))){
            autorizado = true;
        }

        return autorizado;
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.ThemeOverlay_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginSuccess() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtPassLogin.getWindowToken(), 0);
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.contenerdorFragment, new FragmentHome());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public boolean validate() {

        boolean valid = true;

        String password = txtPassLogin.getText().toString();

        if(spinnerLogin.getCount() != 0){
            if (spinnerLogin.getSelectedItem().toString().equals("Seleccione el auditor")) {
                valid = false;
                Toast.makeText(getContext(), "No ha seleccionado un auditor", Toast.LENGTH_LONG).show();
            }else{
                if(CompararClave(spinnerLogin.getSelectedItem().toString(), password)){
                    ((MainActivity)getActivity()).setAuditor(spinnerLogin.getSelectedItemPosition() + 1);
                    txtPassLogin.setError(null);
                }else{
                    txtPassLogin.setError("La contraseña no es valida");
                    valid = false;
                    MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.error);
                    mp.start();
                }
            }

            return valid;

        } else {
            Toast.makeText(getContext(), "No hay datos en la base, sincronize!", Toast.LENGTH_LONG).show();
            valid = false;
            return valid;
        }

    }

    private void LlenarLista(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getContext(), "db_audit6s", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        ArrayList<String> listaSpinner = new ArrayList<>();
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_AUDITOR, null);

        while (cursor.moveToNext()){
            listaSpinner.add(cursor.getString(1));
        }

        db.close();
        cursor.close();

        final SpinnerAdaptador adaptador = new SpinnerAdaptador(getContext(), R.layout.spinner_textview);
        adaptador.addAll(listaSpinner);
        adaptador.add("Seleccione el auditor");
        spinnerLogin.setAdapter(adaptador);
        spinnerLogin.setSelection(adaptador.getCount());
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
