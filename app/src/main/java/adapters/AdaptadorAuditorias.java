package adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liraheta.audit6s.R;

import java.util.ArrayList;

import entidades.Auditoria;
import sqlite.ConexionSQLiteHelper;
import utilidades.Utilidades;

public class AdaptadorAuditorias extends RecyclerView.Adapter<AdaptadorAuditorias.AuditoriasViewHolder> implements View.OnClickListener {


    ArrayList<Auditoria> listaAuditorias;
    private View.OnClickListener listener;
    ConexionSQLiteHelper conn;
    Context context;
    SQLiteDatabase db;

    public AdaptadorAuditorias(ArrayList<Auditoria> listaAuditorias, Context c) {
        this.listaAuditorias = listaAuditorias;
        this.context = c;
    }

    @Override
    public AuditoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_auditorias,parent,false);
        view.setOnClickListener(this);

        return new AuditoriasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuditoriasViewHolder holder, int position) {
        holder.id_auditoria.setText(String.valueOf(listaAuditorias.get(position).getId_auditoria()));
        holder.nombre_area.setText(ObtenerArea(listaAuditorias.get(position).getArea()));
        holder.nombre_lider.setText(ObtenerLider(listaAuditorias.get(position).getLider()));
        holder.nota_final.setText(String.valueOf(listaAuditorias.get(position).getRes_total())+"%");
    }

    private String ObtenerLider(int id) {
        String lider = "";

        conn = new ConexionSQLiteHelper(context, "db_audit6s", null, 1);
        db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + Utilidades.CAMPO_LIDER +" FROM "+ Utilidades.TABLA_LIDER +" WHERE "+ Utilidades.CAMPO_ID_LIDER +" = ?", new String[]{String.valueOf(id)});

        while (cursor.moveToNext()){
            lider = cursor.getString(0);
        }

        db.close();
        cursor.close();
        conn.close();

        return lider;
    }

    private String ObtenerArea(int area) {
        String areaString = "";

        conn = new ConexionSQLiteHelper(context, "db_audit6s", null, 1);
        db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + Utilidades.CAMPO_AREA +" FROM "+ Utilidades.TABLA_AREA +" WHERE "+ Utilidades.CAMPO_ID_AREA +" = ?", new String[]{String.valueOf(area)});

        while (cursor.moveToNext()){
            areaString = cursor.getString(0);
        }

        db.close();
        cursor.close();
        conn.close();

        return areaString;
    }

    @Override
    public int getItemCount() {
        return listaAuditorias.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class AuditoriasViewHolder extends RecyclerView.ViewHolder {

        TextView id_auditoria, nombre_area, nombre_lider, nota_final;

        public AuditoriasViewHolder(View itemView) {
            super(itemView);
            id_auditoria = itemView.findViewById(R.id.id_auditoria);
            nombre_area = itemView.findViewById(R.id.nombre_area);
            nombre_lider = itemView.findViewById(R.id.nombre_lider);
            nota_final = itemView.findViewById(R.id.nota_total);
        }
    }
}
