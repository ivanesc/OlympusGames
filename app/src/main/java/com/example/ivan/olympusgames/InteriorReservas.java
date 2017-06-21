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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.example.ivan.olympusgames.SQLite.Reservas_Cache;
import com.example.ivan.olympusgames.modelo.JuegoInteriorReservas;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

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

    String identificador;

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

        Bundle extras = getIntent().getExtras();
        identificador = extras.getString("identificador");

        //Añadir juegos a la lista
        String datos[] = Reservas_Cache.getReserva(InteriorReservas.this, identificador);
        String Lista_Juegos[] = datos[4].split("/////");
        String Lista_Plataformas[] = datos[5].split("/////");
        int njuegos = Lista_Juegos.length;

        JuegoInteriorReservas.JUEGOS.clear();
        for(int i = 0; i< njuegos; i++) {
            int id = Integer.parseInt(Lista_Juegos[i]);
            int posPlataforma = Integer.parseInt(Lista_Plataformas[i]);
            String datos_Juego[] = Datos_Juegos.getGame(InteriorReservas.this, id);

            String nombre_juego = datos_Juego[1];
            String plataforma = datos_Juego[4].split("/////")[posPlataforma];
            String generos = datos_Juego[3].replace("/////",", ");
            float precio = Float.parseFloat(datos_Juego[5].split("/////")[posPlataforma]);
            String icon = datos_Juego[7];
            JuegoInteriorReservas.JUEGOS.add(new JuegoInteriorReservas(nombre_juego, plataforma, generos, precio, icon));
        }

        AdaptadorInteriorReserva adaptador = new AdaptadorInteriorReserva(this);
        reciclador.setAdapter(adaptador);

    }

    @Override
    public void onResume() {
        super.onResume();
        Button boton = (Button)findViewById(R.id.cancelaReserva);
        String estado = Reservas_Cache.getEstadoReserva(InteriorReservas.this, identificador);

        if(estado.equals("Recogido")){
            boton.setText("ELIMINAR RESERVA");
        }else{
            boton.setText("CANCELAR RESERVA");
        }
    }

    @Override
    public void onItemClick(AdaptadorInteriorReserva.ViewHolder holder, int posicion) {

    }

    public void onItemIteriorReservasClick(View v) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        TextView textView = (TextView)v.findViewById(R.id.nombre_juego_cont_res);
        String nombre = textView.getText().toString();
        String id_juedo[] = Datos_Juegos.getGame(InteriorReservas.this, nombre);

        Intent intent = new Intent(this, JuegoDetallado.class);
        intent.putExtra("id", id_juedo[0]);
        startActivity(intent);
    }

    public void onCancelarReservaClick(View v) {
        String estado = Reservas_Cache.getEstadoReserva(InteriorReservas.this, identificador);

        if(estado.equals("Recogido")){
            Reservas_Cache.delete(InteriorReservas.this, identificador);
            Toast.makeText(InteriorReservas.this,
                    "Reserva "+identificador+" eliminada.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(InteriorReservas.this, Reservas.class);
            InteriorReservas.this.startActivity(intent);
        }else{
            if(Internet.isConnected(InteriorReservas.this)) {
                String res = Internet.deleteReserva(Preferencias_Usuario.getUser(InteriorReservas.this), Preferencias_Usuario.getToken(InteriorReservas.this), identificador);
                if(res.substring(res.indexOf("msj-start") + 9, res.indexOf("msj-end")).equals("error")){
                    Toast.makeText(InteriorReservas.this,
                            "Tu no has realizado la reserva.", Toast.LENGTH_SHORT).show();
                }else{
                    Reservas_Cache.delete(InteriorReservas.this, identificador);
                    Toast.makeText(InteriorReservas.this,
                            "Reserva "+identificador+" cancelada.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InteriorReservas.this, Reservas.class);
                    InteriorReservas.this.startActivity(intent);
                }
            }else {
                Toast.makeText(InteriorReservas.this,
                        "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
