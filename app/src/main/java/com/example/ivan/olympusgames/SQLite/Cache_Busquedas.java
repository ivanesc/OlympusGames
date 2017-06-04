package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Fernando on 02/06/2017.
 */

public class Cache_Busquedas {

    private static SQLite_DB olympusgames_db;
    static int limite = 20;

    String Busqueda;

    public Cache_Busquedas(String Busqueda, Context contexto) {
        this.Busqueda = Busqueda;

        //Si no existe se añade a la lista
        if(!exist(contexto, this.Busqueda)) {
            //Compruebo si se ha llegado al límite
            if (getAll(contexto) == limite) {
                delete(contexto, getFirst(contexto));
            }
            add(contexto);
        }
    }

    //Agrega un dato a la tabla Cache_Busquedas
    private void add(Context contexto) {
        if (olympusgames_db == null) {
            this.olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLite_DB.Tabla_Cache_Busquedas.Busqueda, this.Busqueda);

        db.insert(SQLite_DB.Tabla_Cache_Busquedas.Nombre_Tabla, null, values);
        db.close();
    }

    //Elimina un dato de la tabla Cache_Busquedas
    public static void delete(Context contexto, int id) {
        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getWritableDatabase();

        db.delete(SQLite_DB.Tabla_Cache_Busquedas.Nombre_Tabla, SQLite_DB.Tabla_Cache_Busquedas.Id+"=?", new String[]{"" + id});
        db.close();
    }

    //Obtener todos los datos de la tabla Cache_Busquedas
    public static int getAll(Context contexto) {
        int cont = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Cache_Busquedas.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Cache_Busquedas.Id, SQLite_DB.Tabla_Cache_Busquedas.Busqueda} // Columnas
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

    //Obtener id del primer dato de la tabla Cache_Busquedas
    public static int getFirst(Context contexto) {
        int id = 0;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Cache_Busquedas.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Cache_Busquedas.Id} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , SQLite_DB.Tabla_Cache_Busquedas.Id // Cláusula order by.
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

    //Comprueba si existe un dato en la tabla Cache_Busquedas
    public static boolean exist(Context contexto, String palabra) {
        boolean res = false;

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Cache_Busquedas.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Cache_Busquedas.Id} // Columnas
                , SQLite_DB.Tabla_Cache_Busquedas.Busqueda+"=?" // Cláusula where
                , new String[]{""+palabra} // Vector de argumentos
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

    //Obtener todos los datos de la tabla Cache_Busquedas
    public static String[] get(Context contexto) {
        String aux[] = new String[20];

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Cache_Busquedas.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Cache_Busquedas.Id, SQLite_DB.Tabla_Cache_Busquedas.Busqueda} // Columnas
                , null // Cláusula where
                , null // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , ""+limite // Cláusula limit
        );

        cursor.moveToFirst();
        int i=0;
        while (!cursor.isAfterLast()) {
            aux[i] = cursor.getString(1);
            cursor.moveToNext();
            i++;
        }
        cursor.close();

        String[] res;
        if(i==0){
            res = new String[i+1];
            res[0]="";
        }else {
            res = new String[i];
            for (int j = 0; j < i; j++) {
                res[j] = aux[j];
            }
        }

        return res;
    }
}
