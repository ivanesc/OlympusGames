package com.example.ivan.olympusgames;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ivan.olympusgames.SQLite.Carrito_Cache;
import com.example.ivan.olympusgames.SQLite.Datos_Juegos;
import com.example.ivan.olympusgames.modelo.CarritoModelo;
import com.example.ivan.olympusgames.modelo.JuegoListaDeseos;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {

    interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    private EscuchaEventosClick escucha;

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView plataforma;
        public TextView precio;
        public TextView cantidad;
        public Button boton_mas;
        public Button boton_menos;

        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombreJuegoCarrito);
            plataforma = (TextView) v.findViewById(R.id.PlataformaCarrito);
            precio = (TextView) v.findViewById(R.id.PrecioCarrito);
            cantidad = (TextView) v.findViewById(R.id.cantJuegoCarrito);
            boton_mas = (Button) v.findViewById(R.id.botonInc1);
            boton_menos = (Button) v.findViewById(R.id.botonDesc1);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdaptadorCarrito(EscuchaEventosClick escucha) {
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return CarritoModelo.JUEGOS.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_carrito, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        final CarritoModelo itemActual = CarritoModelo.JUEGOS.get(i);

        viewHolder.nombre.setText(itemActual.getNombre());
        viewHolder.plataforma.setText(itemActual.getPlataforma());
        viewHolder.precio.setText(itemActual.getPrecio() + "€");
        viewHolder.cantidad.setText(""+itemActual.getCantidad());
        viewHolder.boton_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_juego[] = null;
                try {
                    id_juego = Datos_Juegos.getGame(null, itemActual.getNombre());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String c = viewHolder.cantidad.getText().toString();
                int cant = Integer.parseInt(c);
                cant++;
                viewHolder.cantidad.setText(""+cant);
                Carrito_Cache.updateCant(null, Integer.parseInt(id_juego[0]), cant);
            }
        });
        viewHolder.boton_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_juego[] = null;
                try {
                    id_juego = Datos_Juegos.getGame(null, itemActual.getNombre());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String c = viewHolder.cantidad.getText().toString();
                int cant = Integer.parseInt(c);
                if(cant>1) cant--;
                viewHolder.cantidad.setText(""+cant);
                Carrito_Cache.updateCant(null, Integer.parseInt(id_juego[0]), cant);
            }
        });
    }
}
