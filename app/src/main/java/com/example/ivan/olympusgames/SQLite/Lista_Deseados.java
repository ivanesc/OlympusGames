package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Fernando on 16/05/2017.
 */

public class Lista_Deseados {

    private static SQLite_DB olympusgames_db;

    int Id_Juego;

    public Lista_Deseados(int Id_Juego, Context contexto) {
        this.Id_Juego = Id_Juego;

        if(!gameFav(contexto, Id_Juego))
            add(contexto);
    }

    //Agrega un dato a la tabla Lista_Deseados
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Lista_Deseados.Id_Juego, this.Id_Juego);

        db.insert(SQLite_DB.Tabla_Lista_Deseados.Nombre_Tabla, null, values);
        db.close();
    }

    //Elimina un dato de la tabla Lista_Deseados
    public static void delete(Context contexto, int id_juego) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        db.delete(SQLite_DB.Tabla_Lista_Deseados.Nombre_Tabla, SQLite_DB.Tabla_Lista_Deseados.Id_Juego+"=?", new String[]{"" + id_juego});
        db.close();
    }

    //Obtener todos los datos de la tabla Lista_Deseados
    public static int getAll(Context contexto) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Lista_Deseados.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Lista_Deseados.Id, SQLite_DB.Tabla_Lista_Deseados.Id_Juego} // Columnas
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

    //Obtener id del juego en la posicion especificada
    public static int getIdAt(Context contexto, int pos) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Lista_Deseados.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Lista_Deseados.Id_Juego} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , ""+pos // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cont = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();

        return cont;
    }

    //Comprobar si un juego está en la tabla Lista_Deseados
    public static boolean gameFav(Context contexto, int id_juego) {
        boolean res = false;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Lista_Deseados.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Lista_Deseados.Id} // Columnas
                , SQLite_DB.Tabla_Lista_Deseados.Id_Juego+"=?" // Cláusula where
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
}
