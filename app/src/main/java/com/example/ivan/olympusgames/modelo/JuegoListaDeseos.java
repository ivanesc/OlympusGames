package com.example.ivan.olympusgames.modelo;


import com.example.ivan.olympusgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class JuegoListaDeseos {
    private float precio;
    private String nombre;
    private String Drawable;
    private String plataforma;
    private String genero;

    public JuegoListaDeseos(String nombre, String plataforma, String genero, float precio, String Drawable) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.genero = genero;
        this.precio = precio;
        this.Drawable = Drawable;
    }

    public static final List<JuegoListaDeseos> JUEGOS = new ArrayList<>();

    public float getPrecio() {
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
}
