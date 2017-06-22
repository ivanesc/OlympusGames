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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.SQLite.Carrito_Cache;
import com.example.ivan.olympusgames.SQLite.Comentarios;
import com.example.ivan.olympusgames.SQLite.Lista_Deseados;
import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.example.ivan.olympusgames.SQLite.Reservas_Cache;
import com.example.ivan.olympusgames.modelo.JuegoDetalle;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class JuegoDetallado extends AppCompatActivity
        implements AdaptadorJuegoDetallado.EscuchaEventosClick{
    public static final String EXTRA_POSICION = "com.example.ivan.olympusgames.ListaDeseos1.extra.POSICION";

    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;
    // array of images
    Drawable[] images;

    RecyclerView reciclador;
    LinearLayoutManager layoutManager;

    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    private RatingBar ratingBar;

    String plataforma_names[];
    String precios[];
    String valoraciones[];

    int plataformaSeleccionada;
    String precio;
    int id_juego;

    boolean fav;
    boolean carr;

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

    private class PlataformaJuego {
        private String nombrePlataforma="Sin nombre";

        public PlataformaJuego(){}

        public PlataformaJuego(String nombrePlataforma) {
            this.nombrePlataforma = nombrePlataforma;
        }

        public String getNomPlataforma() {
            return nombrePlataforma;
        }

        @Override
        public String toString() {
            return this.nombrePlataforma;
        }
    }

    ArrayList<ComentarioJuego> comentarios;
    ArrayList<PlataformaJuego> plataformas;
    ListView lvComentarios;
    ListView lvPlataformas;
    String datos[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_detallado);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");

        datos = Datos_Juegos.getGame(JuegoDetallado.this, Integer.parseInt(id));
        id_juego = Integer.parseInt(datos[0]);
        populateComentarios();
        populatePlataformas(datos[4]);
        showLVComentarios_Complejo();
        showLVPlataformas_Complejo();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float n = 0;
                String iconPath = Preferencias_Usuario.getIcon(JuegoDetallado.this);
                Drawable icon = getResources().getDrawable(R.drawable.ares);
                int fondo = Color.rgb(72,72,72);

                if(iconPath != null) {
                    if(!Preferencias_Usuario.getToken(JuegoDetallado.this).equals("")) {
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
            DrawerManager.prepararDrawer(drawer, navigationView, JuegoDetallado.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_juegodetallado);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView) findViewById(R.id.lstView);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, JuegoDetallado.this);

        /* Cargar imagenes */
        images = new Drawable[]{getDrawable(R.drawable.imagendefecto),
                getDrawable(R.drawable.imagendefecto),
                getDrawable(R.drawable.imagendefecto),
                getDrawable(R.drawable.imagendefecto),
                getDrawable(R.drawable.imagendefecto)};
        if(!datos[8].equals("")) images[0] = Drawable.createFromPath(datos[8]);
        if(!datos[9].equals("")) images[1] = Drawable.createFromPath(datos[9]);
        if(!datos[10].equals("")) images[2] = Drawable.createFromPath(datos[10]);
        if(!datos[11].equals("")) images[3] = Drawable.createFromPath(datos[11]);
        if(!datos[12].equals("")) images[4] = Drawable.createFromPath(datos[12]);
        simpleGallery = (Gallery) findViewById(R.id.simpleGallery); // get the reference of Gallery
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView); // get the reference of ImageView
        customGalleryAdapter = new CustomGalleryAdapter(getApplicationContext(), images); // initialize the adapter
        simpleGallery.setAdapter(customGalleryAdapter); // set the adapter
        selectedImageView.setImageDrawable(images[0]);
        // perform setOnItemClickListener event on the Gallery
        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set the selected image in the ImageView
                selectedImageView.setImageDrawable(images[position]);
            }
        });

        reciclador = (RecyclerView) findViewById(R.id.reciclador4);

        layoutManager = new GridLayoutManager(this, 1);

        reciclador.setLayoutManager(layoutManager);

        plataformaSeleccionada = 0;
        precios = datos[5].split("/////");
        valoraciones = datos[6].split("/////");
        precio = precios[plataformaSeleccionada];

        JuegoDetalle.JUEGOS.add(new JuegoDetalle(id_juego, datos[1], datos[2].toString(), datos[3].replace("/////",", "), Float.parseFloat(precio), datos[7]));
        AdaptadorJuegoDetallado adaptador = new AdaptadorJuegoDetallado(this);
        reciclador.setAdapter(adaptador);

        fav = Lista_Deseados.gameFav(JuegoDetallado.this, id_juego);
        carr = Carrito_Cache.exist(JuegoDetallado.this, id_juego);
    }

    private int populateComentarios() {
        comentarios=new ArrayList<ComentarioJuego>();

        if(Internet.isConnected(JuegoDetallado.this)) {
            String id_juego = ""+this.id_juego;
            String comentarios = Internet.getComentarios(id_juego);
            String comentarioSplit[] = comentarios.split("<br/>");
            for (int i = 1; i < comentarioSplit.length; i++) {
                String comentarioItem[] = comentarioSplit[i].split("%/%");

                String Nombre_Usuario = comentarioItem[0];
                String Comentario = comentarioItem[1];
                String Fecha = comentarioItem[2];

                new Comentarios(this.id_juego, Nombre_Usuario, Comentario, Fecha, JuegoDetallado.this);
            }
        }

        //Añadir juegos a la lista
        comentarios.clear();
        for(int i=Comentarios.getAll(JuegoDetallado.this, ""+this.id_juego); i>0; i--) {
            String datos[] = Comentarios.getComentarioAt(JuegoDetallado.this, ""+this.id_juego, ""+i);
            String nombre_usuario = datos[0];
            String comentario = datos[1];
            String fecha = datos[2];
            comentarios.add( new ComentarioJuego(fecha,nombre_usuario, comentario) );
        }
        return comentarios.size();
    }

    private int populatePlataformas(String plataforms) {
        plataformas=new ArrayList<PlataformaJuego>();
        plataforma_names = plataforms.split("/////");
        for(int i=0; i<plataforma_names.length; i++){
            plataformas.add( new PlataformaJuego(plataforma_names[i]) );
        }
        return plataformas.size();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight()+50;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildren2(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void showLVComentarios_Complejo() {
        lvComentarios=(ListView) findViewById(R.id.lvComplejo);
        lvComentarios.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        ArrayAdapter adaptadorComentarios=
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
        lvComentarios.setAdapter(adaptadorComentarios);
        setListViewHeightBasedOnChildren(lvComentarios);
    }

    private void showLVPlataformas_Complejo() {
        lvPlataformas=(ListView) findViewById(R.id.lvComplejoPlataformas);
        lvPlataformas.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter adaptadorPlataformas=
                new ArrayAdapter( this // Context
                        , R.layout.plataformadetalleitem //Resource
                        , plataformas // Vector o lista
                ) {
                    public View getView(int position
                            , View convertView
                            , ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getContext()
                                .getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

                        // Creamos la vista para cada fila
                        View fila = inflater.inflate(R.layout.plataformadetalleitem, parent, false);
                        if(position == 0){
                            TextView plataform = (TextView) fila.findViewById(R.id.plataforma_det_relleno);
                            plataform.setTextColor(Color.rgb(0,255,255));
                        }

                        // Creamos cada uno de los widgets que forman una fila
                        TextView nombrePlataformaView = (TextView) fila.findViewById(R.id.plataforma_det_relleno);

                        // Establecemos los valores que queremos que muestren los widgets
                        PlataformaJuego tmpV=plataformas.get(position);
                        nombrePlataformaView.setText(tmpV.getNomPlataforma());
                        return fila;
                    }
                };

        lvPlataformas.setAdapter(adaptadorPlataformas);
        setListViewHeightBasedOnChildren2(lvPlataformas);

        lvPlataformas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < lvPlataformas.getCount(); ++i) {

                }
            }
        });
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

    @Override
    public void onStop () {
        super.onStop();
        JuegoDetalle.JUEGOS.clear();
    }

    public void onPlataformaClick(View v){
        int pos = 0;
        int count = lvPlataformas.getAdapter().getCount();

        //Inicializar color plataformas
        for (int i = 0; i < count; i++)
        {
            ViewGroup row = (ViewGroup) lvPlataformas.getChildAt(i);
            TextView plataform = (TextView) row.findViewById(R.id.plataforma_det_relleno);
            plataform.setTextColor(Color.WHITE);
        }
        //Marcar plataforma seleccionada
        TextView item = (TextView)v.findViewById(R.id.plataforma_det_relleno);
        String plataforma = item.getText().toString();
        for(int i=0; i<plataforma_names.length; i++){
            if(plataforma_names[i].equals(plataforma)){
                pos = i;
                break;
            }
        }
        plataformaSeleccionada = pos;
        item.setTextColor(Color.rgb(0,255,255));

        //Cambiar precio
        precio = precios[plataformaSeleccionada];
        TextView precio_text = (TextView)findViewById(R.id.precio_det);
        precio_text.setText(precio+"€");

        //Cambiar valoracion
        float valoracion = Float.parseFloat(valoraciones[plataformaSeleccionada]);

        if(valoracion < 5) ((ImageView)findViewById(R.id.valoracion_det)).setImageResource(R.drawable.valoracion_baja);
        else if(valoracion < 8) ((ImageView)findViewById(R.id.valoracion_det)).setImageResource(R.drawable.valoracion_media);
        else ((ImageView)findViewById(R.id.valoracion_det)).setImageResource(R.drawable.valoracion_alta);

        ((TextView)findViewById(R.id.valoracion)).setText(""+valoracion);
    }

    public void onFavClick(View v){
        if(fav){    //Eliminar de favoritos
            Lista_Deseados.delete(JuegoDetallado.this, id_juego);
            Toast.makeText(JuegoDetallado.this,
                    "Se ha eliminado el producto de favoritos.", Toast.LENGTH_SHORT).show();
        }else{      //Añadir a favoritos
            new Lista_Deseados(id_juego, JuegoDetallado.this);
            Toast.makeText(JuegoDetallado.this,
                    "Se ha añadido el producto a favoritos.", Toast.LENGTH_SHORT).show();
        }

        setFavImage(fav);
        fav = !fav;
    }

    private void setFavImage(boolean fav){
        ImageButton boton_fav = (ImageButton)findViewById(R.id.favoritos);

        if(fav) boton_fav.setImageResource(R.drawable.listadeseos_off);
        else boton_fav.setImageResource(R.drawable.listadeseos_on);
    }

    public void onAddCarrClick(View v){
        if(!Preferencias_Usuario.getToken(JuegoDetallado.this).equals("")) {
            if (carr) {    //Eliminar del carrito
                Carrito_Cache.delete(JuegoDetallado.this, id_juego);
                Toast.makeText(JuegoDetallado.this,
                        "Se ha eliminado el producto del carrito.", Toast.LENGTH_SHORT).show();
            } else {      //Añadir al carrito
                new Carrito_Cache("" + id_juego, "" + plataformaSeleccionada, JuegoDetallado.this);
                Toast.makeText(JuegoDetallado.this,
                        "Se ha añadido el producto al carrito.", Toast.LENGTH_SHORT).show();
            }

            setCarrImage(carr, v);
            carr = !carr;
        }else{
            Toast.makeText(JuegoDetallado.this,
                    "Debes realizar login para poder utilizar el carrito.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(JuegoDetallado.this, Login.class);
            startActivity(intent);
        }
    }

    private void setCarrImage(boolean carr, View v){
        ImageButton boton_carr = (ImageButton)v.findViewById(R.id.action_carrito);

        if(carr) boton_carr.setImageResource(R.drawable.add_carro);
        else boton_carr.setImageResource(R.drawable.delete_carro);
    }

    public void onReservaDirectaClick(View v) {
        if(Internet.isConnected(JuegoDetallado.this)){
            if(!Preferencias_Usuario.getToken(JuegoDetallado.this).equals("")) {
                Calendar c = Calendar.getInstance();
                String fecha = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
                String tienda = "La Loma";
                String Estado = "Pendiente";
                String Identificador = "#" + fecha.replace("/", "") + c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND);
                String datosJuego[] = Datos_Juegos.getGame(JuegoDetallado.this, id_juego);
                float precio_total = Float.parseFloat(datosJuego[5].split("/////")[plataformaSeleccionada]);
                String Lista_Juegos = ""+id_juego;
                String Lista_Plataformas = ""+plataformaSeleccionada;

                String nombre_usuario = Preferencias_Usuario.getUser(JuegoDetallado.this);
                String token = Preferencias_Usuario.getToken(JuegoDetallado.this);

                new Reservas_Cache(1, String.format("%.2f", precio_total), fecha, tienda, Lista_Juegos, Lista_Plataformas,
                        Estado, Identificador, ""+1, JuegoDetallado.this);
                Internet.addReserva(nombre_usuario, "" + 1, Lista_Juegos, fecha, tienda,
                        String.format("%.2f", precio_total), Lista_Plataformas, Estado, Identificador, ""+1, token);
                Toast.makeText(JuegoDetallado.this,
                        "Reserva realizada con éxito.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JuegoDetallado.this, Reservas.class);
                startActivity(intent);
            }else{
                Toast.makeText(JuegoDetallado.this,
                        "Debes hacer login para poder reservar productos.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JuegoDetallado.this, Login.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(JuegoDetallado.this,
                    "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onEnviarComentarioClick(View v) {
        String comentario = ((EditText)findViewById(R.id.write_comentario)).getText().toString();
        if(Internet.isConnected(JuegoDetallado.this)){
            String token = Preferencias_Usuario.getToken(JuegoDetallado.this);
            if(!token.equals("")) {
                String nombre_usuario = Preferencias_Usuario.getUser(JuegoDetallado.this);
                Calendar c = Calendar.getInstance();
                String fecha = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);

                String res = Internet.addComentario("" + this.id_juego, nombre_usuario, comentario, fecha, token);
                if (res.substring(res.indexOf("msj-start") + 9, res.indexOf("msj-end")).equals("OK")) {
                    new Comentarios(this.id_juego, nombre_usuario, comentario, fecha, JuegoDetallado.this);

                    Toast.makeText(getApplicationContext(),
                            "Se ha publicado el comentario.", Toast.LENGTH_SHORT).show();

                    populateComentarios();
                    showLVComentarios_Complejo();
                }
            }else{
                Toast.makeText(getApplicationContext(),
                        "Debes hacer login para publicar comentarios.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JuegoDetallado.this, Login.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(JuegoDetallado.this,
                    "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
        }
    }
}
