package com.example.ivan.olympusgames;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.modelo.JuegoListaDeseos;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class AdaptadorListaDeseos extends RecyclerView.Adapter<AdaptadorListaDeseos.ViewHolder> {

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
        public TextView val;
        public ImageView imagen;
        public ImageView imagen_val;

        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombre_juego_ld);
            plataforma = (TextView) v.findViewById(R.id.plataforma_ld);
            genero = (TextView) v.findViewById(R.id.genero_juego_ld);
            precio = (TextView) v.findViewById(R.id.precio_ld);
            val = (TextView) v.findViewById(R.id.valoracion);
            imagen = (ImageView) v.findViewById(R.id.miniatura_juego_ld);
            imagen_val = (ImageView) v.findViewById(R.id.valoracion_ld);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) { escucha.onItemClick(this, getAdapterPosition()); }
    }

    public AdaptadorListaDeseos(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return JuegoListaDeseos.JUEGOS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_deseos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        JuegoListaDeseos itemActual = JuegoListaDeseos.JUEGOS.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(itemActual.getDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(itemActual.getNombre());
        viewHolder.plataforma.setText(itemActual.getPlataforma());
        viewHolder.genero.setText(itemActual.getGenero());
        viewHolder.precio.setText(itemActual.getPrecio() + "€");

        //Cambiar valoracion
        float valoracion = 0;
        try {
            valoracion = Float.parseFloat(Datos_Juegos.getGame(null, itemActual.getNombre())[6].split("/////")[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(valoracion < 5) viewHolder.imagen_val.setImageResource(R.drawable.valoracion_baja);
        else if(valoracion < 8) viewHolder.imagen_val.setImageResource(R.drawable.valoracion_media);
        else viewHolder.imagen_val.setImageResource(R.drawable.valoracion_alta);

        viewHolder.val.setText(""+valoracion);

    }
}
