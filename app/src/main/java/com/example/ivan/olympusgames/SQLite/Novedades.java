package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Fernando on 16/05/2017.
 */

public class Novedades {

    private static SQLite_DB olympusgames_db;
    static int limite = 20;

    int Id_Juego;

    public Novedades(int Id_Juego, Context contexto) {
        this.Id_Juego = Id_Juego;

        //Si no existe se añade a la lista
        if(!exist(contexto, this.Id_Juego)) {
            //Compruebo si se ha llegado al límite
            if (getAll(contexto) == limite) {
                delete(contexto, getFirst(contexto));
            }
            add(contexto);
        }
    }

    //Agrega un dato a la tabla Novedades
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Novedades.Id_Juego, this.Id_Juego);

        db.insert(SQLite_DB.Tabla_Novedades.Nombre_Tabla, null, values);
        db.close();
    }

    //Elimina un dato de la tabla Novedades
    public static void delete(Context contexto, int id) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        db.delete(SQLite_DB.Tabla_Novedades.Nombre_Tabla, SQLite_DB.Tabla_Novedades.Id+"=?", new String[]{"" + id});
        db.close();
    }

    //Obtener todos los datos de la tabla Novedades
    public static int getAll(Context contexto) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Novedades.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Novedades.Id, SQLite_DB.Tabla_Novedades.Id_Juego} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , ""+limite // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cursor.moveToNext();
            cont++;
        }
        cursor.close();

        return cont;
    }

    //Obtener id del primer dato de la tabla Novedades
    public static int getFirst(Context contexto) {
        int id = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Novedades.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Novedades.Id} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , SQLite_DB.Tabla_Novedades.Id // Cláusula order by.
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

    //Comprueba si existe un dato en la tabla Novedades
    public static boolean exist(Context contexto, int id_juego) {
        boolean res = false;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Novedades.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Novedades.Id} // Columnas
                , SQLite_DB.Tabla_Novedades.Id_Juego+"=?" // Cláusula where
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
