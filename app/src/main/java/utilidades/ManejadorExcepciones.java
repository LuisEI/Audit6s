package utilidades;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.liraheta.audit6s.CrashActivity;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sqlite.ConexionSQLiteHelper;

public class ManejadorExcepciones implements Thread.UncaughtExceptionHandler {

    private final Activity myContext;

    public ManejadorExcepciones(Activity context) {
        myContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        GuardarExcepcion(CargarNumeroTablet(),
                GetDate(),
                GetTime(),
                CurrentVersion(),
                CantidadImagenes(),
                stackTrace.toString(),
                0);

        Intent intent = new Intent(myContext, CrashActivity.class);
        myContext.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);

    }

    private void GuardarExcepcion(String numero_tablet, String date, String time, String version, String memoria, String error, int sync){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(myContext.getApplicationContext(), "db_audit6s", null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_NUMERO_TABLET, numero_tablet);
        values.put(Utilidades.CAMPO_DATE, date);
        values.put(Utilidades.CAMPO_TIME, time);
        values.put(Utilidades.CAMPO_VERSION, version);
        values.put(Utilidades.CAMPO_MEMORIA, memoria);
        values.put(Utilidades.CAMPO_ERROR, error);
        values.put(Utilidades.CAMPO_SYNC_E, sync);

        Long idResultante = db.insert(Utilidades.TABLA_EXCEPCIONES, Utilidades.CAMPO_NUMERO_TABLET, values);

        db.close();
        conn.close();

        Log.e("Crash App Error", error);
    }

    private String CargarNumeroTablet(){
        SharedPreferences preferences = myContext.getSharedPreferences("opciones", Context.MODE_PRIVATE);
        String numberTablet = preferences.getString("tablet", "");
        return numberTablet;
    }

    private String CantidadImagenes(){
        File dir = myContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        long length = 0;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                length += file.length();
        }
        return String.valueOf(length/1048576);
    }

    private String CurrentVersion(){
        PackageInfo pckginfo = null;
        String currentVersionName = "";
        try {
            pckginfo = myContext.getApplicationContext().getPackageManager().getPackageInfo(myContext.getApplicationContext().getPackageName(), 0);
            currentVersionName = pckginfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionName;
    }

    private String GetTime(){
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    private String GetDate(){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
}