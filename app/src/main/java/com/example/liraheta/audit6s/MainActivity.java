package com.example.liraheta.audit6s;

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

import fragments.FragmentAuditoria;
import fragments.FragmentCalificar;
import fragments.FragmentConsulta;
import fragments.FragmentHome;
import fragments.FragmentIngreso;
import fragments.FragmentSetting;
import fragments.FragmentSincronizar;
import fragments.Fragment_Informacion;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentHome.OnFragmentInteractionListener, FragmentAuditoria.OnFragmentInteractionListener,
        FragmentConsulta.OnFragmentInteractionListener, FragmentSetting.OnFragmentInteractionListener,
        FragmentIngreso.OnFragmentInteractionListener, FragmentCalificar.OnFragmentInteractionListener,
        FragmentSincronizar.OnFragmentInteractionListener, Fragment_Informacion.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenerdorFragment, new FragmentHome()).addToBackStack(null).commit();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenerdorFragment, new Fragment_Informacion()).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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

}
