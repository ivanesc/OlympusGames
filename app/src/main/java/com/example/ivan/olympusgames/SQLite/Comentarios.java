package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Fernando on 16/05/2017.
 */

public class Comentarios {

    private static SQLite_DB olympusgames_db;

    int Id_Juego;
    String Usuario;
    String Comentario;
    String Fecha;

    public Comentarios(int Id_Juego, String Usuario, String Comentario, String Fecha, Context contexto) {
        this.Id_Juego = Id_Juego;
        this.Usuario = Usuario;
        this.Comentario = Comentario;
        this.Fecha = Fecha;

        if(!exist(contexto, Id_Juego, Usuario, Comentario, Fecha)){
            add(contexto);
        }
    }

    //Agrega un dato a la tabla Comentarios
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Comentarios.Id_Juego, this.Id_Juego);
        values.put(SQLite_DB.Tabla_Comentarios.Usuario, this.Usuario);
        values.put(SQLite_DB.Tabla_Comentarios.Comentario, this.Comentario);
        values.put(SQLite_DB.Tabla_Comentarios.Fecha, this.Fecha);

        db.insert(SQLite_DB.Tabla_Comentarios.Nombre_Tabla, null, values);
        db.close();
    }

    //Elimina todos los comentarios para un juego de la tabla Comentarios
    public static void clean(Context contexto, int id_juego) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        db.delete(SQLite_DB.Tabla_Comentarios.Nombre_Tabla, SQLite_DB.Tabla_Comentarios.Id_Juego+"=?", new String[]{"" + id_juego});
        db.close();
    }

    //Obtener todos los datos de un id_juego de la tabla Comentarios
    public static int getAll(Context contexto, String id_juego) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Comentarios.Nombre_Tabla // Tabla
                , new String[]{"id", "comentario"} // Columnas
                , SQLite_DB.Tabla_Comentarios.Id_Juego+"=?" // Cláusula where
                , new String[]{""+id_juego} // Vector de argumentos
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

    //Comprueba si existe un comentario en la tabla Comentarios
    public static boolean exist(Context contexto, int id_Juego, String usuario, String comentario, String fecha) {
        boolean res = false;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Comentarios.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Comentarios.Id_Juego} // Columnas
                , SQLite_DB.Tabla_Comentarios.Usuario+"=? AND "+SQLite_DB.Tabla_Comentarios.Comentario+"=?"
                  +" AND "+SQLite_DB.Tabla_Comentarios.Fecha+"=? AND "+SQLite_DB.Tabla_Comentarios.Id_Juego+"=?"// Cláusula where
                , new String[]{""+usuario, ""+comentario, ""+fecha, ""+id_Juego} // Vector de argumentos
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

    public static String[] getComentarioAt(Context contexto, String id_juego, String pos) {
        String cont[] = new String[3];

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Comentarios.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Comentarios.Usuario, SQLite_DB.Tabla_Comentarios.Comentario,
                        SQLite_DB.Tabla_Comentarios.Fecha} // Columnas
                , SQLite_DB.Tabla_Comentarios.Id_Juego+"=?" // Cláusula where
                , new String[]{""+id_juego} // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , ""+pos // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cont[0] = cursor.getString(0);
            cont[1] = cursor.getString(1);
            cont[2] = cursor.getString(2);
            cursor.moveToNext();
        }
        cursor.close();

        return cont;
    }
}
