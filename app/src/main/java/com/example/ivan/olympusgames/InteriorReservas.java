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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class InteriorReservas extends AppCompatActivity
        implements AdaptadorInteriorReserva.EscuchaEventosClick {

    public static final String EXTRA_POSICION = "com.example.ivan.olympusgames.InteriorReservas.extra.POSICION";

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
        setContentView(R.layout.activity_interior_reservas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contenido = (RelativeLayout) findViewById(R.id.content_interiorreserva);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView) findViewById(R.id.lstView);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, InteriorReservas.this);

        reciclador = (RecyclerView) findViewById(R.id.reciclador7);

        layoutManager = new GridLayoutManager(this, 1);

        reciclador.setLayoutManager(layoutManager);

        AdaptadorInteriorReserva adaptador = new AdaptadorInteriorReserva(this);
        reciclador.setAdapter(adaptador);

    }

    @Override
    public void onItemClick(AdaptadorInteriorReserva.ViewHolder holder, int posicion) {
        Intent intent = new Intent(this, JuegoDetallado.class);
        //Intent intent = new Intent(this, ActividadDetalle.class);
        //intent.putExtra(EXTRA_POSICION, posicion);
        startActivity(intent);
    }
}
