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
    private String Drawable;
    private String plataforma;
    private String genero;
    private String ranking;

    public JuegoMasReservados(String nombre, String plataforma, String genero, double precio, String Drawable, String ranking) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.genero = genero;
        this.precio = precio;
        this.Drawable = Drawable;
        this.ranking = ranking;
    }

    public static final List<JuegoMasReservados> JUEGOS = new ArrayList<>();

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

    public String getDrawable() {
        return Drawable;
    }

    public String getRanking() {
        return ranking;
    }
}
