package com.example.ivan.olympusgames;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.olympusgames.modelo.JuegoDetalle;
import com.example.ivan.olympusgames.modelo.JuegoListaDeseos;

public class AdaptadorJuegoDetallado extends RecyclerView.Adapter<AdaptadorJuegoDetallado.ViewHolder> {

    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    private EscuchaEventosClick escucha;

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        //public TextView plataforma;
        public TextView genero;
        public TextView precio;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombre_juego_det);
            //plataforma = (TextView) v.findViewById(R.id.plataforma_det_relleno);
            genero = (TextView) v.findViewById(R.id.genero_det_relleno);
            precio = (TextView) v.findViewById(R.id.precio_det);
            imagen = (ImageView) v.findViewById(R.id.miniatura_juego_det);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdaptadorJuegoDetallado(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return JuegoDetalle.JUEGOS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_detallado, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        JuegoDetalle itemActual = JuegoDetalle.JUEGOS.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(itemActual.getDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(itemActual.getNombre());
        //viewHolder.plataforma.setText(itemActual.getPlataforma());
        viewHolder.genero.setText(itemActual.getGenero());
        viewHolder.precio.setText(itemActual.getPrecio() + "€");

        /*viewHolder.galeria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdaptadorJuegoDetallado.this, GaleriaImagenes.class));
            }
        });*/

    }
}
