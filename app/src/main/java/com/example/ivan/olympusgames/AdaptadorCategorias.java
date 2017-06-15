package com.example.ivan.olympusgames;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.olympusgames.R;

import java.util.List;

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {


    private final List<com.example.ivan.olympusgames.modelo.Juego> items;

    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    private EscuchaEventosClick escucha;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView plataformas;
        public TextView precio;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombre_juego);
            plataformas = (TextView) v.findViewById(R.id.plataforma);
            precio = (TextView) v.findViewById(R.id.precio_juego);
            imagen = (ImageView) v.findViewById(R.id.miniatura_juego);
        }
    }

    public AdaptadorCategorias(List<com.example.ivan.olympusgames.modelo.Juego> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_juegos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        com.example.ivan.olympusgames.modelo.Juego item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.plataformas.setText(item.getPlataformas());
        viewHolder.precio.setText(item.getPrecio()+"€");

    }


}