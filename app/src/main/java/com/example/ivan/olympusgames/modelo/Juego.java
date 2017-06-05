package com.example.ivan.olympusgames.modelo;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Juego {
    private float precio;
    private String nombre;
    private String Drawable;

    public Juego(float precio, String nombre, String Drawable) {
        this.precio = precio;
        this.nombre = nombre;
        this.Drawable = Drawable;
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDrawable() {
        return Drawable;
    }
}
