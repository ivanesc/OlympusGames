package com.example.ivan.olympusgames.modelo;

import com.example.ivan.olympusgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Juego {
    private float precio;
    private String nombre;
    private int idDrawable;

    public Juego(float precio, String nombre, int idDrawable) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public static final List<com.example.ivan.olympusgames.modelo.Juego> OFERTAS = new ArrayList<>();
    public static final List<com.example.ivan.olympusgames.modelo.Juego> POPULARES = new ArrayList<>();
    public static final List<com.example.ivan.olympusgames.modelo.Juego> NOVEDADES = new ArrayList<>();;

    static {

        NOVEDADES.add(new com.example.ivan.olympusgames.modelo.Juego(5, "Prueba1", R.drawable.ares));
        NOVEDADES.add(new com.example.ivan.olympusgames.modelo.Juego(3, "Prueba2", R.drawable.ares));
        NOVEDADES.add(new com.example.ivan.olympusgames.modelo.Juego(5, "Prueba1", R.drawable.ares));
        NOVEDADES.add(new com.example.ivan.olympusgames.modelo.Juego(3, "Prueba2", R.drawable.ares));
        NOVEDADES.add(new com.example.ivan.olympusgames.modelo.Juego(5, "Prueba1", R.drawable.ares));
        NOVEDADES.add(new com.example.ivan.olympusgames.modelo.Juego(3, "Prueba2", R.drawable.ares));

        OFERTAS.add(new com.example.ivan.olympusgames.modelo.Juego(5, "Prueba3", R.drawable.ares));
        OFERTAS.add(new com.example.ivan.olympusgames.modelo.Juego(3, "Prueba4", R.drawable.ares));

        POPULARES.add(new com.example.ivan.olympusgames.modelo.Juego(5, "Prueba5", R.drawable.ares));
        POPULARES.add(new com.example.ivan.olympusgames.modelo.Juego(3, "Prueba6", R.drawable.ares));
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
}
