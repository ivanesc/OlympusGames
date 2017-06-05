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


public class Carrito extends AppCompatActivity {

    EditText cantJuego1;
    EditText cantJuego2;
    EditText cantJuego3;
    Button inc1;
    Button desc1;
    Button inc2;
    Button desc2;
    Button inc3;
    Button desc3;
    Button pedidos;
    String texto;
    int cant1=1;
    int cant2=1;
    int cant3=1;
    String cant1_mod= "";
    String cant2_mod= "";
    String cant3_mod= "";
    int valorTotal=0;

    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    String[] lstSource = {

            "Harry",
            "Ron",
            "Hermione",
            "Snape",
            "Malfoy",
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Seven",
            "Eight",
            "Nine",
            "Ten"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
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

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {
                lstView.setVisibility(View.VISIBLE);
                contenido.setVisibility(View.INVISIBLE);
                barra.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lstView will return default

                lstView.setVisibility(View.INVISIBLE);
                //lstView.getLayoutParams().height = 0;
                //lstView.getLayoutParams().width = 0;
                contenido.setVisibility(View.VISIBLE);
                barra.setVisibility(View.VISIBLE);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    List<String> lstFound = new ArrayList<String>();
                    for(String item:lstSource){
                        if(item.contains(newText))
                            lstFound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(Carrito.this,android.R.layout.simple_list_item_1,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
                    ArrayAdapter adapter = new ArrayAdapter(Carrito.this,android.R.layout.simple_list_item_1,lstSource);
                    lstView.setAdapter(adapter);
                }
                return true;
            }

        });

        cantJuego1 = (EditText)findViewById(R.id.cantJuego1);
        cantJuego2 = (EditText)findViewById(R.id.cantJuego2);
        cantJuego3 = (EditText)findViewById(R.id.cantJuego3);

        inc1 = (Button)findViewById(R.id.botonInc1);
        desc1 = (Button)findViewById(R.id.botonDesc1);
        inc2 = (Button)findViewById(R.id.botonInc2);
        desc2 = (Button)findViewById(R.id.botonDesc2);
        inc3 = (Button)findViewById(R.id.botonInc3);
        desc3 = (Button)findViewById(R.id.botonDesc3);
        pedidos = (Button)findViewById(R.id.botonPedidos);

        inc1.setOnClickListener( new View.OnClickListener() {
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

        inc2.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v){
                cant2++;
                cant2_mod = String.valueOf(cant2);
                cantJuego2.setText(cant2_mod);
            }
        });

        desc2.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v){
                if(cant2 > 1) {
                    cant2--;
                    cant2_mod = String.valueOf(cant2);
                    cantJuego2.setText(cant2_mod);
                }
            }
        });

        inc3.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v){
                cant3++;
                cant3_mod = String.valueOf(cant3);
                cantJuego3.setText(cant3_mod);
            }
        });

        desc3.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v){
                if(cant3 > 1) {
                    cant3--;
                    cant3_mod = String.valueOf(cant3);
                    cantJuego3.setText(cant3_mod);
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
        });

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

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                itemDrawer.setChecked(true);
                fragmentoGenerico = new FragmentoCategorias();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.item_plataformas:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, Plataformas.class));
                break;
            case R.id.acercade:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, AcercaDe.class));
                break;
            case R.id.escribir:
                itemDrawer.setChecked(true);
                try {
                    String datosMail = "mailto:olympusgames@gmail.es"
                            + "?cc=ivanEscobarSanchez@hotmail.com"
                            + "&subject="
                            + Uri.encode("Duda acerca producto tienda");
                    Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse(datosMail));
                    this.startActivity(email);
                    break;
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ayuda:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, Ayuda.class));
                break;
        }
        /*if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.content_carrito2, fragmentoGenerico)
                    .commit();
        }*/

        // Setear título actual
        setTitle(itemDrawer.getTitle());
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
}


