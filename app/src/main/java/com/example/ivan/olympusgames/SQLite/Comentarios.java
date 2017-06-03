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
    int limite_juego = 10;

    int Id_Juego;
    String Usuario;
    String Comentario;

    public Comentarios(int Id_Juego, String Usuario, String Comentario, Context contexto) {
        this.Id_Juego = Id_Juego;
        this.Usuario = Usuario;
        this.Comentario = Comentario;

        //Agrega solo 10 comentarios, por lo que los 10 primeros valores devueltos por el servidor deben ser los más actuales
        //Cada vez que se entra en un juego, se borran todos los comentarios almacenados para ese Id_Juego y se guardan los 10 primeros
        if(getAll(contexto, this.Id_Juego) < limite_juego){
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
    public static int getAll(Context contexto, int id_juego) {
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
            Log.e("Dato " + SQLite_DB.Tabla_Comentarios.Nombre_Tabla, cursor.getInt(0)
                    + ": "
                    + cursor.getString(1));
            cursor.moveToNext();
            cont++;
        }
        cursor.close();

        Log.e("Datos leidos", "" + cont);
        return cont;
    }
}
