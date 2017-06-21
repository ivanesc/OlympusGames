package com.example.ivan.olympusgames;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.olympusgames.SQLite.Carrito_Cache;
import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.SQLite.Lista_Deseados;
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
        public TextView descripcion;
        public TextView genero;
        public TextView precio;
        public TextView val;
        public ImageView imagen;
        public ImageView imagen_val;
        public RatingBar ratingBar;
        public ImageButton boton_fav;
        public ImageButton boton_carr;

        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombre_juego_det);
            descripcion = (TextView) v.findViewById(R.id.descripcion_det_relleno);
            genero = (TextView) v.findViewById(R.id.genero_det_relleno);
            precio = (TextView) v.findViewById(R.id.precio_det);
            val = (TextView) v.findViewById(R.id.valoracion);
            imagen = (ImageView) v.findViewById(R.id.miniatura_juego_det);
            imagen_val = (ImageView) v.findViewById(R.id.valoracion_det);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            boton_fav = (ImageButton) v.findViewById(R.id.favoritos);
            boton_carr = (ImageButton) v.findViewById(R.id.action_carrito);

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

        final JuegoDetalle itemActual = JuegoDetalle.JUEGOS.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(itemActual.getDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(itemActual.getNombre());
        viewHolder.descripcion.setText(itemActual.getDescripcion());
        viewHolder.genero.setText(itemActual.getGenero());
        viewHolder.precio.setText(itemActual.getPrecio() + "€");

        boolean fav = Lista_Deseados.gameFav(null, itemActual.getId());
        if(fav) viewHolder.boton_fav.setImageResource(R.drawable.listadeseos_on);
        else viewHolder.boton_fav.setImageResource(R.drawable.listadeseos_off);

        boolean carr = Carrito_Cache.exist(null, itemActual.getId());
        if(carr) viewHolder.boton_carr.setImageResource(R.drawable.delete_carro);
        else viewHolder.boton_carr.setImageResource(R.drawable.add_carro);

        float valoracion_usuario = Float.parseFloat(Datos_Juegos.getVal(null, itemActual.getId()));
        viewHolder.ratingBar.setRating(valoracion_usuario);
        viewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Datos_Juegos.setVal(null, ""+itemActual.getId(), ""+rating);
            }
        });

        float valoracion = Float.parseFloat(Datos_Juegos.getGame(null, itemActual.getId())[6].split("/////")[0]);

        if(valoracion < 5) viewHolder.imagen_val.setImageResource(R.drawable.valoracion_baja);
        else if(valoracion < 8) viewHolder.imagen_val.setImageResource(R.drawable.valoracion_media);
        else viewHolder.imagen_val.setImageResource(R.drawable.valoracion_alta);

        viewHolder.val.setText(""+valoracion);

    }
}
