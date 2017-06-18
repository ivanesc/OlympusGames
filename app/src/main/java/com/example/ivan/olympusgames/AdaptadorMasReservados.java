package com.example.ivan.olympusgames;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.olympusgames.modelo.JuegoListaDeseos;
import com.example.ivan.olympusgames.modelo.JuegoMasReservados;

public class AdaptadorMasReservados extends RecyclerView.Adapter<AdaptadorMasReservados.ViewHolder> {

    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    private EscuchaEventosClick escucha;

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView plataforma;
        public TextView genero;
        public TextView precio;
        public ImageView imagen;
        public TextView ranking;

        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombre_juego_masres);
            plataforma = (TextView) v.findViewById(R.id.plataforma_masres);
            genero = (TextView) v.findViewById(R.id.genero_juego_masres);
            precio = (TextView) v.findViewById(R.id.precio_masres);
            imagen = (ImageView) v.findViewById(R.id.miniatura_juego_masres);
            ranking = (TextView) v.findViewById(R.id.rankingreserva);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) { escucha.onItemClick(this, getAdapterPosition()); }
    }

    public AdaptadorMasReservados(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return JuegoMasReservados.JUEGOS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_mas_reservados, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        JuegoMasReservados itemActual = JuegoMasReservados.JUEGOS.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(itemActual.getDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(itemActual.getNombre());
        viewHolder.plataforma.setText(itemActual.getPlataforma());
        viewHolder.genero.setText(itemActual.getGenero());
        viewHolder.precio.setText(itemActual.getPrecio() + "€");
        viewHolder.ranking.setText(itemActual.getRanking());

    }
}
