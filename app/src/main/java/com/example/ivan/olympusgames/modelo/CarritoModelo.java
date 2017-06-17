package com.example.ivan.olympusgames.modelo;


import com.example.ivan.olympusgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class CarritoModelo {
    private float precio;
    private String nombre;
    private String plataforma;
    private int cantidad;

    public CarritoModelo(String nombre, String plataforma, float precio, int cantidad) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public static final List<CarritoModelo> JUEGOS = new ArrayList<>();

    public float getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPlataforma() {
        return plataforma;
    }

}
