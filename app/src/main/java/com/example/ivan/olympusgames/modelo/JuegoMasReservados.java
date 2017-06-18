package com.example.ivan.olympusgames.modelo;


import com.example.ivan.olympusgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class JuegoMasReservados {
    private double precio;
    private String nombre;
    private int Drawable;
    private String plataforma;
    private String genero;
    private String ranking;

    public JuegoMasReservados(String nombre, String plataforma, String genero, double precio, int Drawable, String ranking) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.genero = genero;
        this.precio = precio;
        this.Drawable = Drawable;
        this.ranking = ranking;
    }

    public static final List<JuegoMasReservados> JUEGOS = new ArrayList<>();

    static {
        JUEGOS.add(new JuegoMasReservados("Super Mario", "3DS", "Plataformas", 20.30, R.drawable.ares, "1" ));
        JUEGOS.add(new JuegoMasReservados("Super Mario", "3DS", "Plataformas", 20.30, R.drawable.ares, "2" ));
        JUEGOS.add(new JuegoMasReservados("Super Mario", "3DS", "Plataformas", 20.30, R.drawable.ares, "3" ));
        JUEGOS.add(new JuegoMasReservados("Super Mario", "3DS", "Plataformas", 20.30, R.drawable.ares, "4" ));
        JUEGOS.add(new JuegoMasReservados("Super Mario", "3DS", "Plataformas", 20.30, R.drawable.ares, "5" ));
    }

    public double getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public String getGenero() {
        return genero;
    }

    public int getDrawable() {
        return Drawable;
    }

    public String getRanking() {
        return ranking;
    }
}
