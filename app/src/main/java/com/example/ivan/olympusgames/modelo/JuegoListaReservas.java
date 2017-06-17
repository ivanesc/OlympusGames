package com.example.ivan.olympusgames.modelo;


import com.example.ivan.olympusgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class JuegoListaReservas {
    private String id;
    private String estado;
    private String articulos;
    private double preciototal;
    private String fecha;

    public JuegoListaReservas(String id, String estado, String articulos, double preciototal, String fecha) {
        this.id = id;
        this.estado = estado;
        this.articulos = articulos;
        this.preciototal = preciototal;
        this.fecha = fecha;
    }

    public static final List<JuegoListaReservas> JUEGOS = new ArrayList<>();

    public double getPrecio() {
        return preciototal;
    }

    public String getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getArticulos() {
        return articulos;
    }

    public String getFecha() { return fecha; }

}
