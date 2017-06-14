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

    public CarritoModelo(String nombre, String plataforma, float precio) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.precio = precio;
    }

    public static final List<CarritoModelo> JUEGOS = new ArrayList<>();

    static {
        JUEGOS.add(new CarritoModelo("Juego1", "3DS", 5));
        JUEGOS.add(new CarritoModelo("Juego2", "PC", 9));
        JUEGOS.add(new CarritoModelo("Juego1", "3DS", 5));
        JUEGOS.add(new CarritoModelo("Juego2", "PC", 9));
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPlataforma() {
        return plataforma;
    }

}
