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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.SQLite.Lista_Deseados;
import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.example.ivan.olympusgames.SQLite.Reservas_Cache;
import com.example.ivan.olympusgames.modelo.JuegoListaDeseos;
import com.example.ivan.olympusgames.modelo.JuegoMasReservados;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class MasReservados extends AppCompatActivity
        implements AdaptadorMasReservados.EscuchaEventosClick {

    public static final String EXTRA_POSICION = "com.example.ivan.olympusgames.MasReservados.extra.POSICION";

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
        setContentView(R.layout.activity_mas_reservados);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float n = 0;
                String iconPath = Preferencias_Usuario.getIcon(MasReservados.this);
                Drawable icon = getResources().getDrawable(R.drawable.ares);
                int fondo = Color.rgb(72,72,72);

                if(iconPath != null) {
                    if(!Preferencias_Usuario.getToken(MasReservados.this).equals("")) {
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
            DrawerManager.prepararDrawer(drawer, navigationView, MasReservados.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_mas_reservados);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView) findViewById(R.id.lstView);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, MasReservados.this);

        reciclador = (RecyclerView) findViewById(R.id.reciclador8);

        layoutManager = new GridLayoutManager(this, 1);

        reciclador.setLayoutManager(layoutManager);

        //Añadir juegos a la lista
        JuegoMasReservados.JUEGOS.clear();
        String busqueda = Internet.getMasReservados();
        String busquedaSplit[] = busqueda.split("<br/>");
        for (int i = 1; i < busquedaSplit.length; i++) {
            String busquedaItem[] = busquedaSplit[i].split("%/%");

            int Id_Juego = Integer.parseInt(busquedaItem[0].replace("\n", ""));
            String Nombre_Juego = busquedaItem[1];
            String Descripcion_Juego = busquedaItem[2];
            String Generos = busquedaItem[3];
            String Plataformas = busquedaItem[4];
            String Precios = busquedaItem[5];
            String Valoraciones = busquedaItem[6];
            String URL_Icon = "";
            String URL_Imagen1 = "";
            String URL_Imagen2 = "";
            String URL_Imagen3 = "";
            String URL_Imagen4 = "";
            String URL_Imagen5 = "";
            try {
                new Datos_Juegos(Id_Juego, Nombre_Juego, Descripcion_Juego, Generos, Plataformas, Precios,
                        Valoraciones, URL_Icon, URL_Imagen1, URL_Imagen2, URL_Imagen3, URL_Imagen4,
                        URL_Imagen5, MasReservados.this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                JuegoMasReservados.JUEGOS.add(new JuegoMasReservados(Nombre_Juego, Plataformas.replace("/////",", "), Generos.replace("/////",", "), Double.parseDouble(Precios.split("/////")[0].replace(",",".")), Datos_Juegos.getGame(MasReservados.this, Nombre_Juego)[7], ""+i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        AdaptadorMasReservados adaptador = new AdaptadorMasReservados(this);
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

    public void onItemClick(AdaptadorMasReservados.ViewHolder holder, int posicion) {

    }

    public void onItemMasReservadosClick(View v) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        String itemName = ((TextView) v.findViewById(R.id.nombre_juego_masres)).getText().toString();
        Intent intent = new Intent(this, JuegoDetallado.class);
        String datos[] = Datos_Juegos.getGame(MasReservados.this, itemName);
        String id = datos[0];
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
