package com.example.ivan.olympusgames;

import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Carrito extends AppCompatActivity
        implements AdaptadorCarrito.EscuchaEventosClick{

    public static final String EXTRA_POSICION = "com.example.ivan.olympusgames.Carrito.extra.POSICION";

    RecyclerView reciclador;
    LinearLayoutManager layoutManager;

    EditText cantJuego1;
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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            //seleccionarItem(navigationView.getMenu().getItem(0));
        }

        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.item_modificar).setVisible(false);
        nav_Menu.findItem(R.id.item_listadeseos).setVisible(false);
        nav_Menu.findItem(R.id.item_reservas).setVisible(false);
        nav_Menu.findItem(R.id.item_cerrar_sesión).setVisible(false);

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

        AdaptadorCarrito adaptador = new AdaptadorCarrito(this);
        reciclador.setAdapter(adaptador);

        cantJuego1 = (EditText)findViewById(R.id.cantJuegoCarrito);

        inc1 = (Button)findViewById(R.id.botonInc1);
        desc1 = (Button)findViewById(R.id.botonDesc1);

        pedidos = (Button)findViewById(R.id.botonPedidos);

        /*inc1.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v){
                cant1++;
                cant1_mod = String.valueOf(cant1);
                cantJuego1.setText(cant1_mod);
            }
        });

        desc1.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v){
                if(cant1 > 1) {
                    cant1--;
                    cant1_mod = String.valueOf(cant1);
                    cantJuego1.setText(cant1_mod);
                }
            }
        });

        pedidos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                valorTotal=0;
                texto="";


                    int cant = Integer.valueOf(cantJuego1.getText().toString());

                    int valorproductos = 10;

                    String nombreProductos = "Juego1";

                    int total = cant*valorproductos;
                    valorTotal = valorTotal+total;

                    texto = texto+" "+nombreProductos+" Cant:"+cant+" total: "+total+" €"+"\n";


                    int cant2 = Integer.valueOf(cantJuego2.getText().toString());

                    int valorproductos2 = 20;

                    String nombreProductos2 = "Juego2";

                    int total2 = cant2*valorproductos2;
                    valorTotal = valorTotal+total2;

                    texto = texto+" "+nombreProductos2+" Cant:"+cant2+" total: "+total2+" €"+"\n";

                    int cant3 = Integer.valueOf(cantJuego3.getText().toString());

                    int valorproductos3 = 5;

                    String nombreProductos3 = "Juego3";

                    int total3 = cant3*valorproductos3;
                    valorTotal = valorTotal+total3;

                    texto = texto+" "+nombreProductos3+" Cant:"+cant3+" total: "+total3+" €"+"\n";


                texto = texto + "===============================";
                texto = texto+" Valor Totales a Pagar: "+valorTotal+" € \n";

                String enviarPedidos = texto;


                Toast.makeText(getApplicationContext(),texto, Toast.LENGTH_LONG).show();

                Intent intentPedidos = new Intent(Carrito.this, Carrito2.class);

                intentPedidos.putExtra("pedidos",enviarPedidos);



                startActivity(intentPedidos);

            }
        });*/

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        seleccionarItem(menuItem);
                        drawer.closeDrawers();
                        return true;
                    }
                });

    }

    public boolean seleccionarItem(MenuItem itemDrawer) {
        // Setear título actual
        setTitle(itemDrawer.getTitle());
        return (new DrawerManager()).onNavigationItemSelected(this, itemDrawer);
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
        Intent intent = new Intent(this, Carrito2.class);
        //Intent intent = new Intent(this, ActividadDetalle.class);
        //intent.putExtra(EXTRA_POSICION, posicion);
        startActivity(intent);
    }
}


