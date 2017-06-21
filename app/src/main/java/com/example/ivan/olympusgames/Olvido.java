package com.example.ivan.olympusgames;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Olvido extends AppCompatActivity {
    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    EditText nombre_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Material Search");

        //agregarToolbar();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float n = 0;
                String iconPath = Preferencias_Usuario.getIcon(Olvido.this);
                Drawable icon = getResources().getDrawable(R.drawable.ares);
                int fondo = Color.rgb(72,72,72);

                if(iconPath != null) {
                    if(!Preferencias_Usuario.getToken(Olvido.this).equals("")) {
                        if (iconPath.equals(""))
                            icon = getResources().getDrawable(R.drawable.fotoperfil);
                        else icon = Drawable.createFromPath(iconPath);

                        fondo = Color.rgb(63, 81, 181);
                    }
                }

                if (drawer.isDrawerOpen(GravityCompat.START)) n=0;
                else if(n == 0){
                    n=1;
                    ((LinearLayout)findViewById(R.id.fondoMenu)).setBackground(new ColorDrawable(fondo));
                    ((ImageView)findViewById(R.id.iconoMenu)).setImageDrawable(icon);
                }
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            DrawerManager.prepararDrawer(drawer, navigationView, Olvido.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_olvido);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        nombre_usuario = (EditText)findViewById(R.id.olvido_relleno);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, Olvido.this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_carrito:
                startActivity(new Intent(this, Carrito.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void onOlvidoButtonClick(View v) {
        int err = 0;
        String nombre = nombre_usuario.getText().toString();

        if(nombre.equals("")) err = 1;
        else if(isNumeric(nombre)) err = 2;

        switch(err){
            case 0: //No hay errores en los datos de entrada. Enviar petición
                if(Internet.isConnected(Olvido.this)) {
                    String res = Internet.changePass(nombre);
                    if (res.substring(res.indexOf("msj-start") + 9, res.indexOf("msj-end")).equals("error")) {
                        nombre_usuario.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "Ese usuario no está registrado.", Toast.LENGTH_SHORT).show();
                    } else if (res.substring(res.indexOf("msj-start") + 9, res.indexOf("msj-end")).equals("OK")) {
                        Toast.makeText(getApplicationContext(),
                                "Se ha enviado un correo a tu dirección de correo electrónico.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Olvido.this, Login.class));
                    }
                }else{
                    Toast.makeText(Olvido.this,
                            "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1: //Nombre de usuario vacío
                nombre_usuario.setBackground(new ColorDrawable(0xFFFF0000));
                Toast.makeText(getApplicationContext(),
                        "No has introducido el nombre de usuario.", Toast.LENGTH_SHORT).show();
                break;
            case 2: //Nombre de usuario introducido no cumple con el formato
                nombre_usuario.setBackground(new ColorDrawable(0xFFFF0000));
                Toast.makeText(getApplicationContext(),
                        "Debes introducir letras en el nombre de usuario.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}
