package com.example.ivan.olympusgames.modelo;


import com.example.ivan.olympusgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class JuegoDetalle {
    private float precio;
    private String nombre;
    private int Drawable;
    //private String plataforma;
    private String genero;

    public JuegoDetalle(String nombre, String genero, float precio, int Drawable) {
        this.nombre = nombre;
        this.genero = genero;
        this.precio = precio;
        this.Drawable = Drawable;
    }

    public static final List<JuegoDetalle> JUEGOS = new ArrayList<>();

    static {
        JUEGOS.add(new JuegoDetalle("Spartan", "Aventuras", 5, R.drawable.ares));
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public int getDrawable() {
        return Drawable;
    }
}
