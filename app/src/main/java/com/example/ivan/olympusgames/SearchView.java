package com.example.ivan.olympusgames;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

    public static void addSearchViewListener(MaterialSearchView searchView, final ListView lstView, final RelativeLayout contenido,
                                             final AppBarLayout barra){
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
    }

    public static void addSearchViewListener(MaterialSearchView searchView, final ListView lstView, final LinearLayout contenido,
                                              final AppBarLayout barra){
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
    }

    public static void addQueryTextListener(MaterialSearchView searchView, final ListView lstView, final Context contexto) {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                palabra_busqueda = query;
                new Cache_Busquedas(palabra_busqueda, contexto);
                //Realizar búsqueda por palabra
                FragmentoCategoria.Busqueda(palabra_busqueda, filtro_plataforma, filtro_genero, contexto);
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

                    ArrayAdapter adapter = new ArrayAdapter(contexto,android.R.layout.simple_list_item_1,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
                    ArrayAdapter adapter = new ArrayAdapter(contexto,android.R.layout.simple_list_item_1,lstSource);
                    lstView.setAdapter(adapter);
                }
                return true;
            }

        });
    }
}
