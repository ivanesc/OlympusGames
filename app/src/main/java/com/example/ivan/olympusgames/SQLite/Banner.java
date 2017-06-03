package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Fernando on 16/05/2017.
 */

public class Banner {

    private static SQLite_DB olympusgames_db;

    int Id = 1;
    String Url_Imagen1;
    String Url_Imagen2;
    String Url_Imagen3;
    String Url_Imagen4;

    public Banner(String Url_Imagen1, String Url_Imagen2, String Url_Imagen3, String Url_Imagen4,
                    Context contexto) {
        this.Url_Imagen1 = Url_Imagen1;
        this.Url_Imagen2 = Url_Imagen2;
        this.Url_Imagen3 = Url_Imagen3;
        this.Url_Imagen4 = Url_Imagen4;

        //Si ya había datos, actualizo
        if(getAll(contexto) == 0){
            add(contexto);
        }else{
            update(contexto, this.Id);
        }
    }

    //Agrega un dato a la tabla Banner
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Banner.Id, this.Id);
        values.put(SQLite_DB.Tabla_Banner.Url_Imagen1, this.Url_Imagen1);
        values.put(SQLite_DB.Tabla_Banner.Url_Imagen2, this.Url_Imagen2);
        values.put(SQLite_DB.Tabla_Banner.Url_Imagen3, this.Url_Imagen3);
        values.put(SQLite_DB.Tabla_Banner.Url_Imagen4, this.Url_Imagen4);

        db.insert(SQLite_DB.Tabla_Banner.Nombre_Tabla, null, values);
        db.close();
    }

    //Actualiza un dato a la tabla Banner
    private void update(Context contexto, int id) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Banner.Url_Imagen1, this.Url_Imagen1);
        values.put(SQLite_DB.Tabla_Banner.Url_Imagen2, this.Url_Imagen2);
        values.put(SQLite_DB.Tabla_Banner.Url_Imagen3, this.Url_Imagen3);
        values.put(SQLite_DB.Tabla_Banner.Url_Imagen4, this.Url_Imagen4);

        db.update(SQLite_DB.Tabla_Banner.Nombre_Tabla, values, SQLite_DB.Tabla_Banner.Id+"=?", new String[]{"" + id});
        db.close();
    }

    //Obtener todos los datos de la tabla Banner
    public static int getAll(Context contexto) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Banner.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Banner.Id, SQLite_DB.Tabla_Banner.Url_Imagen1,
                        SQLite_DB.Tabla_Banner.Url_Imagen2, SQLite_DB.Tabla_Banner.Url_Imagen3,
                        SQLite_DB.Tabla_Banner.Url_Imagen4} // Columnas
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
}
