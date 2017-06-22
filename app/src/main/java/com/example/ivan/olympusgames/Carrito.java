package com.example.ivan.olympusgames;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Carrito_Cache;
import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.SQLite.Lista_Deseados;
import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.example.ivan.olympusgames.SQLite.Reservas_Cache;
import com.example.ivan.olympusgames.modelo.CarritoModelo;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;


public class Carrito extends AppCompatActivity
        implements AdaptadorCarrito.EscuchaEventosClick{

    public static final String EXTRA_POSICION = "com.example.ivan.olympusgames.Carrito_Cache.extra.POSICION";

    RecyclerView reciclador;
    LinearLayoutManager layoutManager;

    TextView cantJuego1;
    Button inc1;
    Button desc1;
    Button pedidos;
    String texto;
    int cant1=1;
    String cant1_mod= "";
    int valorTotal=0;

    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float n = 0;
                String iconPath = Preferencias_Usuario.getIcon(Carrito.this);
                Drawable icon = getResources().getDrawable(R.drawable.ares);
                int fondo = Color.rgb(72,72,72);

                if(iconPath != null) {
                    if(!Preferencias_Usuario.getToken(Carrito.this).equals("")) {
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
            DrawerManager.prepararDrawer(drawer, navigationView, Carrito.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_carrito);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, Carrito.this);

        reciclador = (RecyclerView) findViewById(R.id.reciclador5);

        layoutManager = new GridLayoutManager(this, 1);

        reciclador.setLayoutManager(layoutManager);

        //Añadir juegos a la lista
        CarritoModelo.JUEGOS.clear();
        for(int i = 0; i< Carrito_Cache.getAll(Carrito.this); i++) {
            String reserva[] = Carrito_Cache.getReservaAt(Carrito.this, i+1);
            int id_juego = Integer.parseInt(reserva[0]);
            int posPlataforma = Integer.parseInt(reserva[1]);
            int cant = Integer.parseInt(reserva[2]);

            String datos[] = Datos_Juegos.getGame(Carrito.this, id_juego);
            String nombre_juego = datos[1];
            String plataformas_juego = datos[4].split("/////")[posPlataforma];
            float precio_juego = Float.parseFloat(datos[5].split("/////")[posPlataforma]);

            CarritoModelo.JUEGOS.add(new CarritoModelo(nombre_juego, plataformas_juego, precio_juego, cant));
        }

        AdaptadorCarrito adaptador = new AdaptadorCarrito(this);
        reciclador.setAdapter(adaptador);

        cantJuego1 = (TextView)findViewById(R.id.cantJuegoCarrito);

        inc1 = (Button)findViewById(R.id.botonInc1);
        desc1 = (Button)findViewById(R.id.botonDesc1);

        pedidos = (Button)findViewById(R.id.botonPedidos);

    }

    @Override
    public void onResume(){
        super.onResume();
        Button boton = (Button)findViewById(R.id.botonPedidos);
        TextView msj = (TextView)findViewById(R.id.sinproductoscarrito);
        ImageView img = (ImageView)findViewById(R.id.imageView);

        if(Carrito_Cache.getAll(Carrito.this) > 0){
            boton.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            msj.setVisibility(View.INVISIBLE);
        }else{
            boton.setVisibility(View.INVISIBLE);
            img.setVisibility(View.INVISIBLE);
            msj.setVisibility(View.VISIBLE);
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
    public void onItemClick(AdaptadorCarrito.ViewHolder holder, int posicion) {
        /*Intent intent = new Intent(this, JuegoDetallado.class);
        //Intent intent = new Intent(this, ActividadDetalle.class);
        //intent.putExtra(EXTRA_POSICION, posicion);
        startActivity(intent);*/
    }

    public void onItemCarritoClick(View v) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        String itemName = ((TextView) v.findViewById(R.id.nombreJuegoCarrito)).getText().toString();
        String datos[] = Datos_Juegos.getGame(Carrito.this, itemName);
        String id = datos[0];
        Intent intent = new Intent(Carrito.this, JuegoDetallado.class);
        //Intent intent = new Intent(this, ActividadDetalle.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void onReservaClick(View v) {
        /* Carrito 2 para realizar reserva */
        int num_juegos = Carrito_Cache.getAll(Carrito.this);
        if(Internet.isConnected(Carrito.this)){
            if(num_juegos > 0) {
                if(!Preferencias_Usuario.getToken(Carrito.this).equals("")) {
                    String tienda_preferida = Preferencias_Usuario.getTienda(Carrito.this);
                    if(!tienda_preferida.equals("")) {
                        Calendar c = Calendar.getInstance();
                        String fecha = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
                        String tienda = tienda_preferida;
                        String Estado = "Pendiente";
                        String Identificador = "#" + fecha.replace("/", "") + c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND);
                        float precio_total = 0;
                        String Lista_Juegos = "";
                        String Lista_Plataformas = "";
                        String cantidades = "";
                        for (int i = 0; i < num_juegos; i++) {
                            String datosCarrito[] = Carrito_Cache.getReservaAt(Carrito.this, i + 1);
                            int id_juego = Integer.parseInt(datosCarrito[0]);
                            int posPlataforma = Integer.parseInt(datosCarrito[1]);
                            int cantidad = Integer.parseInt(datosCarrito[2]);
                            String datosJuego[] = Datos_Juegos.getGame(Carrito.this, id_juego);
                            precio_total += Float.parseFloat(datosJuego[5].split("/////")[posPlataforma]) * cantidad;

                            Lista_Juegos += id_juego;
                            Lista_Plataformas += posPlataforma;
                            cantidades += cantidad;
                            if (i < num_juegos - 1) {
                                Lista_Juegos += "/////";
                                Lista_Plataformas += "/////";
                                cantidades += "/////";
                            }
                        }
                        String nombre_usuario = Preferencias_Usuario.getUser(Carrito.this);
                        String token = Preferencias_Usuario.getToken(Carrito.this);

                        new Reservas_Cache(num_juegos, String.format("%.2f", precio_total), fecha, tienda, Lista_Juegos, Lista_Plataformas,
                                Estado, Identificador, cantidades, Carrito.this);
                        Internet.addReserva(nombre_usuario, "" + num_juegos, Lista_Juegos, fecha, tienda,
                                String.format("%.2f", precio_total), Lista_Plataformas, Estado, Identificador, cantidades, token);
                        Carrito_Cache.clean(Carrito.this);
                        Toast.makeText(Carrito.this,
                                "Reserva realizada con éxito.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Carrito.this, Reservas.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Carrito.this,
                                "Debes elegir tu tienda habitual para realizar la reserva.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Carrito.this, MapaTiendas.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(Carrito.this,
                            "Debes hacer login para poder reservar productos.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Carrito.this, Login.class);
                    startActivity(intent);
                }
            }else{
                Toast.makeText(Carrito.this,
                        "No hay productos en el carrito. Añade productos para realizar la reserva.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Carrito.this,
                    "No tienes conexión a internet.", Toast.LENGTH_SHORT).show();
        }
    }
}


