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
    private String Drawable;
    private String plataforma;
    private String genero;
    private String cantidad;

    public JuegoInteriorReservas(String nombre, String plataforma, String genero, double precio, String Drawable) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.genero = genero;
        this.precio = precio;
        this.Drawable = Drawable;
    }

    public static final List<JuegoInteriorReservas> JUEGOS = new ArrayList<>();

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

    public String getCantidad() { return cantidad; }

}
