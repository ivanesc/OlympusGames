package com.example.ivan.olympusgames;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ivan.olympusgames.R.id.simpleGallery;

public class JuegoDetallado extends AppCompatActivity
        implements AdaptadorJuegoDetallado.EscuchaEventosClick{
    public static final String EXTRA_POSICION = "com.example.ivan.olympusgames.ListaDeseos1.extra.POSICION";

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;
    // array of images
    int[] images = {R.drawable.imagendefecto,R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image3, R.drawable.image4};

    RecyclerView reciclador;
    LinearLayoutManager layoutManager;

    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    private class ComentarioJuego {
        private String fecha="Sin fecha";
        private String autor="Sin autor";
        private String comentario="Sin comentario";

        public ComentarioJuego(){}

        public ComentarioJuego(String fecha, String autor
                , String comentario) {
            this.fecha = fecha;
            this.autor = autor;
            this.comentario = comentario;
        }

        public String getFecha() {
            return fecha;
        }

        public String getAutor() {
            return autor;
        }

        public String getComentario() {
            return comentario;
        }

        @Override
        public String toString() {
            return this.fecha+" - "+this.autor+" - "+this.comentario;
        }
    }

    ArrayList<ComentarioJuego> comentarios;
    ListView lvComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_detallado);
        populateComentarios();
        showLVComentarios_Complejo();

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
            //seleccionarItem(navigationView.getMenu().getItem(9));
        }

        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.item_modificar).setVisible(false);
        nav_Menu.findItem(R.id.item_listadeseos).setVisible(false);
        nav_Menu.findItem(R.id.item_reservas).setVisible(false);
        nav_Menu.findItem(R.id.item_cerrar_sesión).setVisible(false);

        contenido = (RelativeLayout) findViewById(R.id.content_juegodetallado);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView) findViewById(R.id.lstView);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, JuegoDetallado.this);

        simpleGallery = (Gallery) findViewById(R.id.simpleGallery); // get the reference of Gallery
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView); // get the reference of ImageView
        customGalleryAdapter = new CustomGalleryAdapter(getApplicationContext(), images); // initialize the adapter
        simpleGallery.setAdapter(customGalleryAdapter); // set the adapter
        selectedImageView.setImageResource(images[0]);
        // perform setOnItemClickListener event on the Gallery
        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set the selected image in the ImageView
                selectedImageView.setImageResource(images[position]);
            }
        });

        reciclador = (RecyclerView) findViewById(R.id.reciclador4);

        layoutManager = new GridLayoutManager(this, 1);

        reciclador.setLayoutManager(layoutManager);

        AdaptadorJuegoDetallado adaptador = new AdaptadorJuegoDetallado(this);
        reciclador.setAdapter(adaptador);

    }

    private int populateComentarios() {
        comentarios=new ArrayList<ComentarioJuego>();
        comentarios.add( new ComentarioJuego("14-06-2016","Iván Escobar", "Juego verdaderamente fabuloso y adictivo") );
        comentarios.add( new ComentarioJuego("20-08-2017","Fernando Puentes", "Es una gozada este juego. Lo volvería a jugar 100 veces más") );
        return comentarios.size();
    }

    private void showLVComentarios_Complejo() {
        lvComentarios=(ListView) findViewById(R.id.lvComplejo);
        lvComentarios.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        ArrayAdapter adaptadorVehiculos=
                new ArrayAdapter( this // Context
                        , R.layout.comentarioitemlista //Resource
                        , comentarios // Vector o lista
                ) {
                    public View getView(int position
                            , View convertView
                            , ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getContext()
                                .getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

                        // Creamos la vista para cada fila
                        View fila = inflater.inflate(R.layout.comentarioitemlista, parent, false);

                        // Creamos cada uno de los widgets que forman una fila
                        TextView fechaView = (TextView) fila.findViewById(R.id.fechaComentario);
                        TextView autorView = (TextView) fila.findViewById(R.id.autor);
                        TextView textComentarioView = (TextView) fila.findViewById(R.id.textComentario);

                        // Establecemos los valores que queremos que muestren los widgets
                        ComentarioJuego tmpV=comentarios.get(position);
                        fechaView.setText(tmpV.getFecha());
                        autorView.setText(tmpV.getAutor());
                        textComentarioView.setText(tmpV.getComentario());
                        return fila;
                    }
                };
        lvComentarios.setAdapter(adaptadorVehiculos);
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
    public void onItemClick(AdaptadorJuegoDetallado.ViewHolder holder, int posicion) {
        //Intent intent = new Intent(this, GaleriaImagenes.class);
        //Intent intent = new Intent(this, ActividadDetalle.class);
        //intent.putExtra(EXTRA_POSICION, posicion);
        //startActivity(intent);
    }

}
