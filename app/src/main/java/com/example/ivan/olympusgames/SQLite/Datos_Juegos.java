package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Fernando on 16/05/2017.
 */

public class Datos_Juegos {

    private static SQLite_DB olympusgames_db;

    public int Id_Juego;
    public String Nombre_Juego;
    public String Descripcion_Juego;
    public String Generos;
    public String Plataformas;
    public String Precios;
    public String Valoraciones;
    public String URL_Icon;
    public String URL_Imagen1;
    public String URL_Imagen2;
    public String URL_Imagen3;
    public String URL_Imagen4;
    public String URL_Imagen5;

    public Datos_Juegos(int Id_Juego, String Nombre_Juego, String Descripcion_Juego, String Generos,
                          String Plataformas, String Precios, String Valoraciones, String URL_Icon,
                          String URL_Imagen1, String URL_Imagen2, String URL_Imagen3, String URL_Imagen4,
                          String URL_Imagen5, Context contexto) {
        this.Id_Juego = Id_Juego;
        this.Nombre_Juego = Nombre_Juego;
        this.Descripcion_Juego = Descripcion_Juego;
        this.Generos = Generos;
        this.Plataformas = Plataformas;
        this.Precios = Precios;
        this.Valoraciones = Valoraciones;
        this.URL_Icon = URL_Icon;
        this.URL_Imagen1 = URL_Imagen1;
        this.URL_Imagen2 = URL_Imagen2;
        this.URL_Imagen3 = URL_Imagen3;
        this.URL_Imagen4 = URL_Imagen4;
        this.URL_Imagen5 = URL_Imagen5;

        if(!exist(contexto, this.Id_Juego)){
            add(contexto);
        }
    }

    //Agrega un dato a la tabla Datos_Juegos
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Datos_Juegos.Id_Juego, this.Id_Juego);
        values.put(SQLite_DB.Tabla_Datos_Juegos.Nombre_Juego, this.Nombre_Juego);
        values.put(SQLite_DB.Tabla_Datos_Juegos.Descripcion_Juego, this.Descripcion_Juego);
        values.put(SQLite_DB.Tabla_Datos_Juegos.Generos, this.Generos);
        values.put(SQLite_DB.Tabla_Datos_Juegos.Plataformas, this.Plataformas);
        values.put(SQLite_DB.Tabla_Datos_Juegos.Precios, this.Precios);
        values.put(SQLite_DB.Tabla_Datos_Juegos.Valoraciones, this.Valoraciones);
        values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Icon, this.URL_Icon);
        values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen1, this.URL_Imagen1);
        values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen2, this.URL_Imagen2);
        values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen3, this.URL_Imagen3);
        values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen4, this.URL_Imagen4);
        values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen5, this.URL_Imagen5);

        db.insert(SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla, null, values);
        db.close();
    }

    //Elimina un dato de la tabla Datos_Juegos
    public static void delete(Context contexto, int id_juego) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        db.delete(SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla, SQLite_DB.Tabla_Datos_Juegos.Id_Juego+"=?", new String[] {""+id_juego});
        db.close();
    }

    //Obtener todos los datos de la tabla Datos_Juegos
    public static int getAll(Context contexto) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla // Tabla
                , new String[]{"id_juego", "nombre", "generos", "length(nombre)"} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "3" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.e("Dato "+SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla, cursor.getInt(0)
                    + ": "
                    + cursor.getString(1)
                    + ": "
                    + cursor.getString(2)
                    + ": "
                    + cursor.getInt(3));
            cursor.moveToNext();
            cont++;
        }
        cursor.close();

        Log.e("Datos leidos", ""+cont);
        return cont;
    }

    //Comprueba si existe un dato en la tabla Datos_Juegos
    public static boolean exist(Context contexto, int id_juego) {
        boolean res = false;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Datos_Juegos.Id_Juego} // Columnas
                , SQLite_DB.Tabla_Datos_Juegos.Id_Juego+"=?" // Cláusula where
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
