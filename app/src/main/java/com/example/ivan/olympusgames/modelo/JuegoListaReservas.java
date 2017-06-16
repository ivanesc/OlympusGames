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

    static {
        JUEGOS.add(new JuegoListaReservas("105601", "Listo para recoger", "3", 20.30, "14-07-2017"));
        JUEGOS.add(new JuegoListaReservas("139862", "Enviando a tienda", "1", 11,"20-11-2015"));
        JUEGOS.add(new JuegoListaReservas("114509", "Listo para recoger", "2", 16.80,"19-02-2016"));
    }

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
