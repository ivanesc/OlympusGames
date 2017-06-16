package com.example.ivan.olympusgames;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.olympusgames.modelo.JuegoListaDeseos;
import com.example.ivan.olympusgames.modelo.JuegoListaReservas;

public class AdaptadorListaReservas extends RecyclerView.Adapter<AdaptadorListaReservas.ViewHolder> {

    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    private EscuchaEventosClick escucha;

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView idReserva;
        public TextView estadoReserva;
        public TextView numArticulos;
        public TextView precioTotal;
        public TextView fechaReserva;

        public ViewHolder(View v) {
            super(v);

            idReserva = (TextView) v.findViewById(R.id.reservaid);
            estadoReserva = (TextView) v.findViewById(R.id.estadoReserva);
            numArticulos = (TextView) v.findViewById(R.id.numarticulosreserva_relleno);
            precioTotal = (TextView) v.findViewById(R.id.preciototalreserva_relleno);
            fechaReserva = (TextView) v.findViewById(R.id.fechaReserva);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdaptadorListaReservas(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return JuegoListaReservas.JUEGOS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_reservas, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        JuegoListaReservas itemActual = JuegoListaReservas.JUEGOS.get(i);

        viewHolder.idReserva.setText("#"+itemActual.getId());
        viewHolder.estadoReserva.setText(itemActual.getEstado());
        viewHolder.numArticulos.setText(itemActual.getArticulos());
        viewHolder.precioTotal.setText(itemActual.getPrecio() + "€");
        viewHolder.fechaReserva.setText(itemActual.getFecha());

    }
}
