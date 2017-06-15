package com.example.ivan.olympusgames;

import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.SQLite.Lista_Deseados;
import com.example.ivan.olympusgames.modelo.JuegoListaDeseos;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListaDeseos1 extends AppCompatActivity
        implements AdaptadorListaDeseos.EscuchaEventosClick {

    public static final String EXTRA_POSICION = "com.example.ivan.olympusgames.ListaDeseos1.extra.POSICION";

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
        setContentView(R.layout.activity_lista_deseos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            DrawerManager.prepararDrawer(drawer, navigationView, ListaDeseos1.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_listadeseos);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView) findViewById(R.id.lstView);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, ListaDeseos1.this);

        reciclador = (RecyclerView) findViewById(R.id.reciclador3);

        layoutManager = new GridLayoutManager(this, 1);

        reciclador.setLayoutManager(layoutManager);

        //Añadir juegos a la lista
        JuegoListaDeseos.JUEGOS.clear();
        for(int i=0; i<Lista_Deseados.getAll(ListaDeseos1.this); i++) {
            String datos[] = Datos_Juegos.getGame(ListaDeseos1.this, Lista_Deseados.getIdAt(ListaDeseos1.this, i+1));
            String nombre_juego = datos[1];
            String plataformas_juego = datos[4].replace("/////",", ");
            String generos_juego = datos[3].replace("/////",", ");
            float precio_juego = Float.parseFloat(datos[5].split("/////")[0]);
            String icon_juego = datos[7];
            JuegoListaDeseos.JUEGOS.add(new JuegoListaDeseos(nombre_juego, plataformas_juego, generos_juego, precio_juego, icon_juego));
        }

        AdaptadorListaDeseos adaptador = new AdaptadorListaDeseos(this);
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
    public void onItemClick(AdaptadorListaDeseos.ViewHolder holder, int posicion) {

    }

    public void onItemListaDeseosClick(View v) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        String itemName = ((TextView) v.findViewById(R.id.nombre_juego_ld)).getText().toString();
        Intent intent = new Intent(this, JuegoDetallado.class);
        String datos[] = Datos_Juegos.getGame(ListaDeseos1.this, itemName);
        String id = datos[0];
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
