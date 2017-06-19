package com.example.ivan.olympusgames;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Cache_Busquedas;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 12/06/2017.
 */

public class SearchView {

    static List<String> lstFound;
    static String[] lstSource;
    static String palabra_busqueda = "";
    static String filtro_plataforma = "";
    static String filtro_genero = "";

    public static void addSearchViewListener(final MaterialSearchView searchView, final ListView lstView, final RelativeLayout contenido,
                                             final AppBarLayout barra){
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {
                lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String sugerencia = ((TextView)view).getText().toString();
                        searchView.setQuery(sugerencia, false);
                    }
                });
                lstView.setVisibility(View.VISIBLE);
                contenido.setVisibility(View.INVISIBLE);
                barra.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                //If closed Search View , lstView will return default
                lstView.setVisibility(View.INVISIBLE);
                contenido.setVisibility(View.VISIBLE);
                barra.setVisibility(View.VISIBLE);

            }
        });
    }

    public static void addSearchViewListener(final MaterialSearchView searchView, final ListView lstView, final LinearLayout contenido,
                                             final AppBarLayout barra){
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {
                lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String sugerencia = ((TextView)view).getText().toString();
                        searchView.setQuery(sugerencia, false);
                    }
                });
                lstView.setVisibility(View.VISIBLE);
                contenido.setVisibility(View.INVISIBLE);
                barra.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lstView will return default

                lstView.setVisibility(View.INVISIBLE);
                contenido.setVisibility(View.VISIBLE);
                barra.setVisibility(View.VISIBLE);

            }
        });
    }

    public static void addQueryTextListener(MaterialSearchView searchView, final ListView lstView, final Context contexto) {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                palabra_busqueda = query;
                new Cache_Busquedas(palabra_busqueda, contexto);
                //Realizar búsqueda por palabra
                FragmentoCategoria.Busqueda(palabra_busqueda, filtro_plataforma, filtro_genero, contexto);
                String activity_name = ((Activity)contexto).getTitle().toString();
                if(Internet.isConnected(contexto)) {
                    if (activity_name.equals("Inicio")) {
                        FragmentoCategorias.changeTab(2);
                    } else {
                        Intent intent = new Intent(contexto, MainActivity.class);
                        intent.putExtra("busqueda", true);
                        contexto.startActivity(intent);
                    }
                }else{
                    Toast.makeText(contexto,
                            "No hay conexión a internet. La búsqueda se realizará sobre la caché.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /* Actualizar cache de búsquedas */
                lstSource = Cache_Busquedas.get(contexto);
                if(newText != null && !newText.isEmpty()){
                    lstFound = new ArrayList<String>();
                    for(String item:lstSource){
                        if(item.contains(newText))
                            lstFound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(contexto,R.layout.search_view_style,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
                    ArrayAdapter adapter = new ArrayAdapter(contexto,R.layout.search_view_style,lstSource);
                    lstView.setAdapter(adapter);
                }
                return true;
            }

        });
    }
}
