package com.example.ivan.olympusgames.modelo;


import com.example.ivan.olympusgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class JuegoInteriorReservas {
    private double precio;
    private String nombre;
    private int Drawable;
    private String plataforma;
    private String genero;

    public JuegoInteriorReservas(String nombre, String plataforma, String genero, double precio, int Drawable) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.genero = genero;
        this.precio = precio;
        this.Drawable = Drawable;
    }

    public static final List<JuegoInteriorReservas> JUEGOS = new ArrayList<>();

    static {
        JUEGOS.add(new JuegoInteriorReservas("Juego1", "3DS", "Aventuras", 5, R.drawable.ares));
        JUEGOS.add(new JuegoInteriorReservas("Juego2", "PS4", "Rol", 3.2, R.drawable.ares));
        JUEGOS.add(new JuegoInteriorReservas("Juego3", "PC", "Rol", 12f, R.drawable.ares));
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
}
