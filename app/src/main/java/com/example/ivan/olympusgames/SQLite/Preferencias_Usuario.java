package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Fernando on 16/05/2017.
 */

public class Preferencias_Usuario {

    private static SQLite_DB olympusgames_db;

    int Id = 1;
    String Nombre;
    String Url_Imagen;
    String Token;
    String Tienda_Preferida;

    public Preferencias_Usuario(String Nombre, String Url_Imagen, String Token, String Tienda_Preferida,
                                Context contexto) {
        this.Nombre = Nombre;
        this.Url_Imagen = Url_Imagen;
        this.Token = Token;
        this.Tienda_Preferida = Tienda_Preferida;

        //Si ya había datos, actualizo
        if(getAll(contexto) == 0){
            add(contexto);
        }else{
            update(contexto, this.Id);
        }
    }

    //Agrega un dato a la tabla Preferencias_Usuario
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Id, this.Id);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Nombre, this.Nombre);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Nombre, this.Nombre);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Url_Imagen, this.Url_Imagen);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Token, this.Token);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Tienda_Preferida, this.Tienda_Preferida);

        db.insert(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, null, values);
        db.close();
    }

    //Actualiza un dato a la tabla Preferencias_Usuario
    private void update(Context contexto, int id) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Nombre, this.Nombre);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Nombre, this.Nombre);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Url_Imagen, this.Url_Imagen);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Token, this.Token);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Tienda_Preferida, this.Tienda_Preferida);

        db.update(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, values, SQLite_DB.Tabla_Preferencias_Usuario.Id+"=?", new String[]{"" + id});
        db.close();
    }

    //Obtener todos los datos de la tabla Preferencias_Usuario
    public static int getAll(Context contexto) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla // Tabla
                , new String[]{"id", "nombre"} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.e(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, cursor.getInt(0)
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
