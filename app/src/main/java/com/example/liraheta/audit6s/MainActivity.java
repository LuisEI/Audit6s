package com.example.liraheta.audit6s;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fragments.FragmentAuditoria;
import fragments.FragmentCalificar;
import fragments.FragmentConsulta;
import fragments.FragmentHome;
import fragments.FragmentIngreso;
import fragments.FragmentLogin;
import fragments.FragmentSetting;
import fragments.FragmentSincronizar;
import fragments.Fragment_Informacion;
import interfaces.DrawerLocker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentHome.OnFragmentInteractionListener, FragmentAuditoria.OnFragmentInteractionListener,
        FragmentConsulta.OnFragmentInteractionListener, FragmentSetting.OnFragmentInteractionListener,
        FragmentIngreso.OnFragmentInteractionListener, FragmentCalificar.OnFragmentInteractionListener,
        FragmentSincronizar.OnFragmentInteractionListener, Fragment_Informacion.OnFragmentInteractionListener,
        FragmentLogin.OnFragmentInteractionListener, DrawerLocker {

    private static int auditor;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    TextView txtAuditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenerdorFragment, new FragmentLogin()).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = this.getSupportFragmentManager().findFragmentById(R.id.contenerdorFragment);
            if(f instanceof FragmentCalificar){
                ((FragmentCalificar) f).ConfirmacionSalida();
            }else if(f instanceof FragmentHome){
                ((FragmentHome) f).SalirdeApp();
            }
            else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_info) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenerdorFragment, new Fragment_Informacion()).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        } else if (id == R.id.action_logout){

            Fragment f = this.getSupportFragmentManager().findFragmentById(R.id.contenerdorFragment);

            if(!(f instanceof FragmentLogin)){

                final View alertFormView = getLayoutInflater().inflate(R.layout.alert_dialog_cerrar_sesion, null);

                Button btnCancelar = alertFormView.findViewById(R.id.btnNoAbortarCalificacion);
                Button btnCerrarSesion = alertFormView.findViewById(R.id.btnAbortarCalificacion);
                TextView txtTitulo = alertFormView.findViewById(R.id.txtTituloMensajeAbortar);
                TextView txtMensaje = alertFormView.findViewById(R.id.txtMensajeAbortar);

                txtTitulo.setText("Cerrar Sesion");
                txtMensaje.setText("¿Desea cerrar la sesion?");

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setView(alertFormView);
                final AlertDialog alertDialog = builder.create();

                alertDialog.show();

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenerdorFragment, new FragmentLogin()).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    }
                });
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment myFragment = null;
        boolean fragmentSelecionado = false;

        if (id == R.id.nav_inicio) {
            myFragment = new FragmentHome();
            fragmentSelecionado = true;
        } else if (id == R.id.nav_ingreso) {
            myFragment = new FragmentIngreso();
            fragmentSelecionado = true;
        } else if (id == R.id.nav_consulta) {
            myFragment = new FragmentConsulta();
            fragmentSelecionado = true;
        } else if (id == R.id.nav_sincronizacion) {
            myFragment = new FragmentSincronizar();
            fragmentSelecionado = true;
        } else if (id == R.id.nav_informacion) {
            myFragment = new Fragment_Informacion();
            fragmentSelecionado = true;
        } else if (id == R.id.nav_setting) {
            myFragment = new FragmentSetting();
            fragmentSelecionado = true;
        } else if (id == R.id.nav_about) {
            myFragment = new FragmentAuditoria();
            fragmentSelecionado = true;
        } else if (id == R.id.nav_exit) {
            this.finish();
        }

        if(fragmentSelecionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenerdorFragment, myFragment).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setAuditor(int a){
        auditor=a;
    }

    public int getAuditor(){
        return auditor;
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
//        drawer.setDrawerLockMode(lockMode);
//        toggle.setDrawerIndicatorEnabled(enabled);
    }
}
