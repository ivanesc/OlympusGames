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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class Generos extends AppCompatActivity {

    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float n = 0;
                String iconPath = Preferencias_Usuario.getIcon(Generos.this);
                Drawable icon = getResources().getDrawable(R.drawable.ares);
                int fondo = Color.rgb(72,72,72);

                if(iconPath != null) {
                    if(!Preferencias_Usuario.getToken(Generos.this).equals("")) {
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
            DrawerManager.prepararDrawer(drawer, navigationView, Generos.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_generos);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, Generos.this);
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

    public void onGeneroBusquedaClick(View v) {
        String aux[] = getResources().getResourceName(v.getId()).split("/");
        String opcion = aux[aux.length-1];
        String genero = "";

        switch(opcion){
            case "opcion1":
                genero = "Aventura";
                break;
            case "opcion2":
                genero = "Puzzle";
                break;
            case "opcion3":
                genero = "Accion";
                break;
            case "opcion4":
                genero = "Fantasia";
                break;
            case "opcion5":
                genero = "Arcade";
                break;
            case "opcion6":
                genero = "Social";
                break;
            case "opcion7":
                genero = "Habilidad";
                break;
            case "opcion8":
                genero = "Estrategia";
                break;
            case "opcion9":
                genero = "Turnos";
                break;
            case "opcion10":
                genero = "Shooter";
                break;
            case "opcion11":
                genero = "Deportivo";
                break;
            case "opcion12":
                genero = "Mundo abierto";
                break;
            case "opcion13":
                genero = "Rol";
                break;
            case "opcion14":
                genero = "Simulador";
                break;
            case "opcion15":
                genero = "Survival";
                break;
            case "opcion16":
                genero = "Horror";
                break;
            case "opcion17":
                genero = "Coches";
                break;
            case "opcion18":
                genero = "Lucha";
                break;
            case "opcion19":
                genero = "Plataformas";
                break;
        }

        //Realizar búsqueda por palabra
        FragmentoCategoria.Busqueda("", "", genero, Generos.this);
        if(!Internet.isConnected(Generos.this)) {
            Toast.makeText(Generos.this,
                    "No hay conexión a internet. La búsqueda se realizará sobre la caché.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(Generos.this, MainActivity.class);
        intent.putExtra("genero", true);
        Generos.this.startActivity(intent);
    }

}

