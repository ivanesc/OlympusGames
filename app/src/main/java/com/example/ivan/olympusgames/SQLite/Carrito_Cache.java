package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Fernando on 02/06/2017.
 */

public class Carrito_Cache {
    private static SQLite_DB olympusgames_db;

    String Id_Juego;
    String PosPlataforma;
    int Cantidad;


    public Carrito_Cache(String Id_Juego, String PosPlataforma, Context contexto) {
        this.Id_Juego = Id_Juego;
        this.PosPlataforma = PosPlataforma;

        //Si no existe se añade a la lista
        if(!exist(contexto, Integer.parseInt(this.Id_Juego))) {
            add(contexto);
        }
    }

    //Agrega un dato a la tabla Carrito_Cache
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Carrito.Id_Juego, this.Id_Juego);
        values.put(SQLite_DB.Tabla_Carrito.Cantidad, 1);
        values.put(SQLite_DB.Tabla_Carrito.PosPlataforma, this.PosPlataforma);

        db.insert(SQLite_DB.Tabla_Carrito.Nombre_Tabla, null, values);
        db.close();
    }

    //Elimina un dato de la tabla Carrito_Cache
    public static void delete(Context contexto, int id_juego) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        db.delete(SQLite_DB.Tabla_Carrito.Nombre_Tabla, SQLite_DB.Tabla_Carrito.Id_Juego+"=?", new String[]{"" + id_juego});
        db.close();
    }

    //Elimina todos los datos de la tabla Carrito_Cache
    public static void clean(Context contexto) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        db.delete(SQLite_DB.Tabla_Carrito.Nombre_Tabla, null, null);
        db.close();
    }

    //Actualiza un dato a la tabla Carrito_Cache
    public static void updateCant(Context contexto, int id_juego, int cant) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Carrito.Cantidad, cant);

        db.update(SQLite_DB.Tabla_Carrito.Nombre_Tabla, values, SQLite_DB.Tabla_Carrito.Id_Juego+"=?", new String[]{"" + id_juego});
        db.close();
    }

    //Obtener todos los datos de la tabla Carrito_Cache
    public static int getAll(Context contexto) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Carrito.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Carrito.Id} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , null // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cursor.moveToNext();
            cont++;
        }
        cursor.close();

        return cont;
    }

    //Comprueba si existe un dato en la tabla Carrito_Cache
    public static boolean exist(Context contexto, int id_juego) {
        boolean res = false;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Carrito.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Carrito.Id} // Columnas
                , SQLite_DB.Tabla_Carrito.Id_Juego+"=?" // Cláusula where
                , new String[]{""+id_juego} // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            res = true;
            cursor.moveToNext();
        }
        cursor.close();

        return res;
    }

    //Get datos de una reserva
    public static String[] getReservaAt(Context contexto, int pos) {
        String cont[] = new String[3];

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Carrito.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Carrito.Id_Juego, SQLite_DB.Tabla_Carrito.PosPlataforma
                        , SQLite_DB.Tabla_Carrito.Cantidad} // Columnas
                , null
                , null
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , ""+pos // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cont[0] = cursor.getString(0);
            cont[1] = cursor.getString(1);
            cont[2] = ""+cursor.getInt(2);
            cursor.moveToNext();
        }
        cursor.close();

        return cont;
    }
}
