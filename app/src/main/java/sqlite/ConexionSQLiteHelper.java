package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper{

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_DIVISION);
        db.execSQL(Utilidades.CREAR_TABLA_PLANTA);
        db.execSQL(Utilidades.CREAR_TABLA_GERENTE);
        db.execSQL(Utilidades.CREAR_TABLA_AREA);
        db.execSQL(Utilidades.CREAR_TABLA_AUDITOR);
        db.execSQL(Utilidades.CREAR_TABLA_HALLAZGOS);
        db.execSQL(Utilidades.CREAR_TABLA_DETALLE);
        db.execSQL(Utilidades.CREAR_TABLA_AUDITORIA);
        db.execSQL(Utilidades.CREAR_TABLA_ENCONTRADO);
        db.execSQL(Utilidades.CREAR_TABLA_LIDER);
        db.execSQL(Utilidades.CREAR_TABLA_RESPONSABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_DIVISION);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_PLANTA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_GERENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_AREA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_AUDITOR);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_HALLAZGOS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_AUDITORIA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_ENCONTRADO);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_LIDER);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_RESPONSABLE);
        onCreate(db);
    }
}
