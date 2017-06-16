package com.example.ivan.olympusgames.modelo;


import com.example.ivan.olympusgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class JuegoDetalle {
    private float precio;
    private int id;
    private String nombre;
    private String descripcion;
    private String Drawable;
    private String genero;

    public JuegoDetalle(int id, String nombre, String descripcion, String genero, float precio, String Drawable) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.genero = genero;
        this.precio = precio;
        this.Drawable = Drawable;
    }

    public static final List<JuegoDetalle> JUEGOS = new ArrayList<>();

    public int getId() {
        return id;
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public String getDrawable() {
        return Drawable;
    }
}
