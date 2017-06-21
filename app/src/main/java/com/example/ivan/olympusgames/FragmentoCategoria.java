package com.example.ivan.olympusgames;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivan.olympusgames.SQLite.Busquedas;
import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.SQLite.Novedades;
import com.example.ivan.olympusgames.SQLite.Ofertas;
import com.example.ivan.olympusgames.modelo.Juego;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Categorías"
 */
public class FragmentoCategoria extends Fragment {

    static List<Juego> OFERTAS = new ArrayList<>();
    static List<com.example.ivan.olympusgames.modelo.Juego> BUSQUEDAS = new ArrayList<>();
    static List<com.example.ivan.olympusgames.modelo.Juego> NOVEDADES = new ArrayList<>();

    private static final String INDICE_SECCION
            = "com.OlympusGames.FragmentoCategoriasTab.extra.INDICE_SECCION";
    static int indiceSeccion;

    private static RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private static AdaptadorCategorias adaptador;

    public static FragmentoCategoria nuevaInstancia(int indiceSeccion) {
        FragmentoCategoria fragment = new FragmentoCategoria();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 1 );
        reciclador.setLayoutManager(layoutManager);

        indiceSeccion = getArguments().getInt(INDICE_SECCION);

        /* Obtener datos para añadirlos a las listas y a SQLite */

        //Si hay conexión, descargamos nuevos datos
        if(Internet.isConnected(this.getContext())) {
            //NOVEDADES
            String novedades = Internet.getNovedades();
            String novedadesSplit[] = novedades.split("<br/>");
            for(int i=1; i<novedadesSplit.length; i++){
                String novedadesItem[] = novedadesSplit[i].split("%/%");

                int Id_Juego = Integer.parseInt(novedadesItem[0].replace("\n",""));
                String Nombre_Juego = novedadesItem[1];
                String Descripcion_Juego = novedadesItem[2];
                String Generos = novedadesItem[3];
                String Plataformas = novedadesItem[4];
                String Precios = novedadesItem[5];
                String Valoraciones = novedadesItem[6];
                String URL_Icon ="";
                String URL_Imagen1 ="";
                String URL_Imagen2 ="";
                String URL_Imagen3 ="";
                String URL_Imagen4 ="";
                String URL_Imagen5 ="";
                try {
                    new Datos_Juegos(Id_Juego, Nombre_Juego, Descripcion_Juego, Generos, Plataformas, Precios,
                            Valoraciones, URL_Icon, URL_Imagen1, URL_Imagen2, URL_Imagen3, URL_Imagen4,
                            URL_Imagen5, this.getContext());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new Novedades(Id_Juego, this.getContext());
            }

            //OFERTAS
            String ofertas = Internet.getOfertas();
            String ofertasSplit[] = ofertas.split("<br/>");
            for(int i=1; i<ofertasSplit.length; i++){
                String ofertasItem[] = ofertasSplit[i].split("%/%");

                int Id_Juego = Integer.parseInt(ofertasItem[0].replace("\n",""));
                String Nombre_Juego = ofertasItem[1];
                String Descripcion_Juego = ofertasItem[2];
                String Generos = ofertasItem[3];
                String Plataformas = ofertasItem[4];
                String Precios = ofertasItem[5];
                String Valoraciones = ofertasItem[6];
                String URL_Icon ="";
                String URL_Imagen1 ="";
                String URL_Imagen2 ="";
                String URL_Imagen3 ="";
                String URL_Imagen4 ="";
                String URL_Imagen5 ="";
                try {
                    new Datos_Juegos(Id_Juego, Nombre_Juego, Descripcion_Juego, Generos, Plataformas, Precios,
                            Valoraciones, URL_Icon, URL_Imagen1, URL_Imagen2, URL_Imagen3, URL_Imagen4,
                            URL_Imagen5, this.getContext());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new Ofertas(Id_Juego, this.getContext());
            }
        }

        /* Cargar datos de SQLite */
        //NOVEDADES
        NOVEDADES.clear();
        int nNovedades = Novedades.getAll(this.getContext());
        for(int i=0; i< nNovedades; i++){
            int id = Novedades.getId(this.getContext(), i+1);
            String game[] = Datos_Juegos.getGame(this.getContext(), id);

            String nombre = game[1];
            String plataformas = game[4].replace("/////", ", ");
            String precio[] = game[5].split("/////");

            NOVEDADES.add(new com.example.ivan.olympusgames.modelo.Juego(Float.parseFloat(precio[0].replace(",",".")), nombre, plataformas, game[7]));
        }

        //OFERTAS
        OFERTAS.clear();
        int nOfertas = Ofertas.getAll(this.getContext());
        for(int i=0; i< nOfertas; i++){
            int id = Ofertas.getId(this.getContext(), i+1);
            String game[] = Datos_Juegos.getGame(this.getContext(), id);

            String nombre = game[1];
            String plataformas = game[4].replace("/////", ", ");
            String precio[] = game[5].split("/////");
            OFERTAS.add(new com.example.ivan.olympusgames.modelo.Juego(Float.parseFloat(precio[0].replace(",",".")), nombre, plataformas, game[7]));
        }

        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadorCategorias(NOVEDADES);
                break;
            case 1:
                adaptador = new AdaptadorCategorias(OFERTAS);
                break;
            case 2:
                adaptador = new AdaptadorCategorias(BUSQUEDAS);
                break;
        }

        reciclador.setAdapter(adaptador);

        return view;
    }

    public static void Busqueda(String nombre, String plataforma, String genero, Context contexto) {
        //Si hay conexión, descargamos nuevos datos
        if(Internet.isConnected(contexto)) {
            //BUSQUEDAS
            String busqueda = Internet.getBusquedas(nombre, plataforma, genero);
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
                            URL_Imagen5, contexto);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new Busquedas(Id_Juego, contexto);
            }
        }

        //BUSQUEDAS
        BUSQUEDAS.clear();
        int nBusquedas = Busquedas.getAll(contexto);
        if(!nombre.equals("")) {
            for (int i = 0; i < nBusquedas; i++) {
                int id = Busquedas.getId(contexto, i + 1);
                String game[] = Datos_Juegos.getGame(contexto, id);

                String name = game[1];
                String gens = game[3];
                String plats = game[4];
                String plataformas = game[4].replace("/////", ", ");
                String precio[] = game[5].split("/////");
                if (name.toLowerCase().contains(nombre.toLowerCase()))
                    BUSQUEDAS.add(new com.example.ivan.olympusgames.modelo.Juego(Float.parseFloat(precio[0].replace(",", ".")), name, plataformas, game[7]));
            }
        }else if(!plataforma.equals("")) {
            for (int i = 0; i < nBusquedas; i++) {
                int id = Busquedas.getId(contexto, i + 1);
                String game[] = Datos_Juegos.getGame(contexto, id);

                String name = game[1];
                String gens = game[3];
                String plats = game[4];
                String plataformas = game[4].replace("/////", ", ");
                String precio[] = game[5].split("/////");
                if (plataformas.toLowerCase().contains(plataforma.toLowerCase()))
                    BUSQUEDAS.add(new com.example.ivan.olympusgames.modelo.Juego(Float.parseFloat(precio[0].replace(",", ".")), name, plataformas, game[7]));
            }
        }else if(!genero.equals("")) {
            for (int i = 0; i < nBusquedas; i++) {
                int id = Busquedas.getId(contexto, i + 1);
                String game[] = Datos_Juegos.getGame(contexto, id);

                String name = game[1];
                String gens = game[3];
                String plats = game[4];
                String generos = game[3].replace("/////", ", ");
                String plataformas = game[4].replace("/////", ", ");
                String precio[] = game[5].split("/////");
                if (generos.toLowerCase().contains(genero.toLowerCase()))
                    BUSQUEDAS.add(new com.example.ivan.olympusgames.modelo.Juego(Float.parseFloat(precio[0].replace(",", ".")), name, plataformas, game[7]));
            }
        }

        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadorCategorias(NOVEDADES);
                break;
            case 1:
                adaptador = new AdaptadorCategorias(OFERTAS);
                break;
            case 2:
                adaptador = new AdaptadorCategorias(BUSQUEDAS);
                break;
        }

        reciclador.setAdapter(adaptador);
    }
}
