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
    String Pass;
    String Correo;
    String Url_Imagen;
    String Token;
    String Tienda_Preferida;

    public Preferencias_Usuario(String Nombre, String Pass, String Token, String Tienda_Preferida,
                                Context contexto) {
        this.Nombre = Nombre;
        this.Pass = Pass;
        this.Token = Token;
        this.Tienda_Preferida = Tienda_Preferida;

        //Si ya había datos, actualizo
        if(getAll(contexto) == 0){
            add(contexto);
        }else{
            update(contexto);
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
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Pass, this.Pass);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Token, this.Token);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Tienda_Preferida, this.Tienda_Preferida);

        db.insert(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, null, values);
        db.close();
    }

    //Actualiza un dato a la tabla Preferencias_Usuario
    private void update(Context contexto) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Nombre, this.Nombre);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Pass, this.Pass);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Token, this.Token);
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Tienda_Preferida, this.Tienda_Preferida);

        db.update(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, values, SQLite_DB.Tabla_Preferencias_Usuario.Id+"=?", new String[]{"" + this.Id});
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
                , new String[]{SQLite_DB.Tabla_Preferencias_Usuario.Id, SQLite_DB.Tabla_Preferencias_Usuario.Nombre} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cursor.moveToNext();
            cont++;
        }
        cursor.close();

        return cont;
    }

    //Actualiza el correo de la tabla Preferencias_Usuario
    public static void updateCorreo(Context contexto, String correo) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Correo, correo);

        db.update(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, values, SQLite_DB.Tabla_Preferencias_Usuario.Id+"=?", new String[]{"" + 1});
        db.close();
    }

    //Obtener correo de la tabla Preferencias_Usuario
    public static String getCorreo(Context contexto) {
        String res = "";

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Preferencias_Usuario.Id, SQLite_DB.Tabla_Preferencias_Usuario.Correo} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            res = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();

        return res;
    }

    //Obtener pass de la tabla Preferencias_Usuario
    public static String getPass(Context contexto) {
        String res = "";

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Preferencias_Usuario.Id, SQLite_DB.Tabla_Preferencias_Usuario.Pass} // Columnas
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
            res = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();

        return res;
    }

    //Actualiza pass de la tabla Preferencias_Usuario
    public static void setPass(Context contexto, String pass) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Pass, pass);

        db.update(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, values, SQLite_DB.Tabla_Preferencias_Usuario.Id+"=?", new String[]{"" + 1});
        db.close();
    }

    //Obtener user de la tabla Preferencias_Usuario
    public static String getUser(Context contexto) {
        String res = "";

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Preferencias_Usuario.Id, SQLite_DB.Tabla_Preferencias_Usuario.Nombre} // Columnas
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
            res = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();

        return res;
    }

    //Obtener icon de la tabla Preferencias_Usuario
    public static String getIcon(Context contexto) {
        String res = "";

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Preferencias_Usuario.Id, SQLite_DB.Tabla_Preferencias_Usuario.Url_Imagen} // Columnas
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
            res = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();

        return res;
    }

    //Actualiza image_url de la tabla Preferencias_Usuario
    public static void setIcon(Context contexto, String icon) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Url_Imagen, icon);

        db.update(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, values, SQLite_DB.Tabla_Preferencias_Usuario.Id+"=?", new String[]{"" + 1});
        db.close();
    }

    //Obtener token de la tabla Preferencias_Usuario
    public static String getToken(Context contexto) {
        String res = "";

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Preferencias_Usuario.Id, SQLite_DB.Tabla_Preferencias_Usuario.Token} // Columnas
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
            res = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();

        return res;
    }

    //Actualiza token de la tabla Preferencias_Usuario
    public static void setToken(Context contexto, String token) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Token, token);

        db.update(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, values, SQLite_DB.Tabla_Preferencias_Usuario.Id+"=?", new String[]{"" + 1});
        db.close();
    }

    //Obtener tienda preferida de la tabla Preferencias_Usuario
    public static String getTienda(Context contexto) {
        String res = "";

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Preferencias_Usuario.Id, SQLite_DB.Tabla_Preferencias_Usuario.Tienda_Preferida} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            res = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();

        return res;
    }

    //Actualiza tienda preferida de la tabla Preferencias_Usuario
    public static void setTienda(Context contexto, String tienda) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Preferencias_Usuario.Tienda_Preferida, tienda);

        db.update(SQLite_DB.Tabla_Preferencias_Usuario.Nombre_Tabla, values, SQLite_DB.Tabla_Preferencias_Usuario.Id+"=?", new String[]{"" + 1});
        db.close();
    }
}
