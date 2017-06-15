package com.example.ivan.olympusgames.modelo;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Juego {
    private float precio;
    private String nombre;
    private String plataformas;
    private String Drawable;

    public Juego(float precio, String nombre, String plataformas, String Drawable) {
        this.precio = precio;
        this.nombre = nombre;
        this.plataformas = plataformas;
        this.Drawable = Drawable;
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPlataformas() {
        return plataformas;
    }

    public String getDrawable() {
        return Drawable;
    }
}
