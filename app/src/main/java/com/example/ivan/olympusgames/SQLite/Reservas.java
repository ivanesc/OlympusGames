package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Fernando on 16/05/2017.
 */

public class Reservas {

    private static SQLite_DB olympusgames_db;
    static int limite = 10;

    int Num_Juegos;
    String Precio_Total;
    String Fecha;
    String Tienda;
    String Lista_Juegos;

    public Reservas(int Num_Juegos, String Precio_Total, String Fecha,
                      String Tienda, String Lista_Juegos, Context contexto) {
        this.Num_Juegos = Num_Juegos;
        this.Precio_Total = Precio_Total;
        this.Fecha = Fecha;
        this.Tienda = Tienda;
        this.Lista_Juegos = Lista_Juegos;

        //Compruebo si se ha llegado al límite
        if (getAll(contexto) == limite) {
            delete(contexto, getFirst(contexto));
        }
        add(contexto);
    }

    //Agrega un dato a la tabla Reservas
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Reservas.Num_Juegos, this.Num_Juegos);
        values.put(SQLite_DB.Tabla_Reservas.Precio_Total, this.Precio_Total);
        values.put(SQLite_DB.Tabla_Reservas.Fecha, this.Fecha);
        values.put(SQLite_DB.Tabla_Reservas.Tienda, this.Tienda);
        values.put(SQLite_DB.Tabla_Reservas.Lista_Juegos, this.Lista_Juegos);

        db.insert(SQLite_DB.Tabla_Reservas.Nombre_Tabla, null, values);
        db.close();
    }

    //Elimina un dato de la tabla Reservas
    public static void delete(Context contexto, int id) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        db.delete(SQLite_DB.Tabla_Reservas.Nombre_Tabla, SQLite_DB.Tabla_Reservas.Id+"=?", new String[]{"" + id});
        db.close();
    }

    //Obtener todos los datos de la tabla Reservas
    public static int getAll(Context contexto) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Reservas.Nombre_Tabla // Tabla
                , new String[]{"id", "lista_juegos"} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , null // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.e("Dato " + SQLite_DB.Tabla_Reservas.Nombre_Tabla, cursor.getInt(0)
                    + ": "
                    + cursor.getString(1));
            cursor.moveToNext();
            cont++;
        }
        cursor.close();

        Log.e("Datos leidos", "" + cont);
        return cont;
    }

    //Obtener id del primer dato de la tabla Reservas
    public static int getFirst(Context contexto) {
        int id = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Reservas.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Reservas.Id} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , SQLite_DB.Tabla_Reservas.Id // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            id = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();

        return id;
    }
}
