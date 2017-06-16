package com.example.ivan.olympusgames;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.olympusgames.modelo.JuegoInteriorReservas;
import com.example.ivan.olympusgames.modelo.JuegoListaReservas;

public class AdaptadorInteriorReserva extends RecyclerView.Adapter<AdaptadorInteriorReserva.ViewHolder> {

    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    private EscuchaEventosClick escucha;

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombreJuegoReserva;
        public TextView plataformaReserva;
        public TextView generoReserva;
        public TextView precioReserva;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);

            nombreJuegoReserva = (TextView) v.findViewById(R.id.nombre_juego_cont_res);
            plataformaReserva = (TextView) v.findViewById(R.id.plataforma_cont_res);
            generoReserva = (TextView) v.findViewById(R.id.genero_juego_cont_res);
            precioReserva = (TextView) v.findViewById(R.id.precio_cont_res);
            imagen = (ImageView) v.findViewById(R.id.miniatura_juego_cont_res);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdaptadorInteriorReserva(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return JuegoInteriorReservas.JUEGOS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_contenido_reserva, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        JuegoInteriorReservas itemActual = JuegoInteriorReservas.JUEGOS.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(itemActual.getDrawable())
                .centerCrop()
                .into(viewHolder.imagen);

        viewHolder.nombreJuegoReserva.setText(itemActual.getNombre());
        viewHolder.plataformaReserva.setText(itemActual.getPlataforma());
        viewHolder.generoReserva.setText(itemActual.getGenero());
        viewHolder.precioReserva.setText(itemActual.getPrecio() + "€");

    }
}
