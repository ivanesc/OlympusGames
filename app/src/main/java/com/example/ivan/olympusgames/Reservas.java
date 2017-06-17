package com.example.ivan.olympusgames;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ivan.olympusgames.SQLite.Reservas_Cache;
import com.example.ivan.olympusgames.modelo.JuegoListaReservas;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Reservas extends AppCompatActivity
        implements AdaptadorListaReservas.EscuchaEventosClick {

    public static final String EXTRA_POSICION = "com.example.ivan.olympusgames.Reservas_Cache.extra.POSICION";

    RecyclerView reciclador;
    LinearLayoutManager layoutManager;

    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reservas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            DrawerManager.prepararDrawer(drawer, navigationView, Reservas.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_listareservas);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView) findViewById(R.id.lstView);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, Reservas.this);

        reciclador = (RecyclerView) findViewById(R.id.reciclador6);

        layoutManager = new GridLayoutManager(this, 1);

        reciclador.setLayoutManager(layoutManager);

        //Añadir juegos a la lista
        JuegoListaReservas.JUEGOS.clear();
        for(int i = 0; i< Reservas_Cache.getAll(Reservas.this); i++) {
            String datos[] = Reservas_Cache.getGame(Reservas.this, i+1);
            String identificador = datos[7];
            String estado = datos[6];
            String num_juegos = datos[0];
            String precio_total = datos[1];
            String fecha = datos[2];
            JuegoListaReservas.JUEGOS.add(new JuegoListaReservas(identificador, estado, num_juegos, Double.parseDouble(precio_total.replace(",",".")), fecha));
        }

        AdaptadorListaReservas adaptador = new AdaptadorListaReservas(this);
        reciclador.setAdapter(adaptador);

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

    @Override
    public void onItemClick(AdaptadorListaReservas.ViewHolder holder, int posicion) {

    }

    public void onReservaClick(View v){
        String identificador = (((TextView)v.findViewById(R.id.reservaid)).getText().toString());

        Intent intent = new Intent(this, InteriorReservas.class);
        intent.putExtra("identificador", identificador);
        startActivity(intent);
    }
}
