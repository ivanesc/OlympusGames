package com.example.ivan.olympusgames.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.ivan.olympusgames.Internet;
import com.example.ivan.olympusgames.prmja.http.prmja_com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

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
                          String URL_Imagen5, Context contexto) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
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
    private void add(Context contexto) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        /* Descargar imagenes */
        //Caratula
        Bitmap imagen = Internet.downloadImage(this.Nombre_Juego.replace(":","_"), "caratula.jpg");
        this.URL_Icon = guardarImagen(contexto, this.Nombre_Juego, "caratula.jpg", imagen);

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

    //Get datos de un juego con el id especificado
    public static String[] getGame(Context contexto, int id) {
        String cont[] = new String[13];

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Datos_Juegos.Id_Juego, SQLite_DB.Tabla_Datos_Juegos.Nombre_Juego
                        , SQLite_DB.Tabla_Datos_Juegos.Descripcion_Juego, SQLite_DB.Tabla_Datos_Juegos.Generos
                        , SQLite_DB.Tabla_Datos_Juegos.Plataformas, SQLite_DB.Tabla_Datos_Juegos.Precios
                        , SQLite_DB.Tabla_Datos_Juegos.Valoraciones, SQLite_DB.Tabla_Datos_Juegos.URL_Icon
                        , SQLite_DB.Tabla_Datos_Juegos.URL_Imagen1, SQLite_DB.Tabla_Datos_Juegos.URL_Imagen2
                        , SQLite_DB.Tabla_Datos_Juegos.URL_Imagen3, SQLite_DB.Tabla_Datos_Juegos.URL_Imagen4
                        , SQLite_DB.Tabla_Datos_Juegos.URL_Imagen5} // Columnas
                , SQLite_DB.Tabla_Datos_Juegos.Id_Juego+"=?" // Cláusula where
                , new String[]{""+id} // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cont[0] = cursor.getString(0);
            cont[1] = cursor.getString(1);
            cont[2] = cursor.getString(2);
            cont[3] = cursor.getString(3);
            cont[4] = cursor.getString(4);
            cont[5] = cursor.getString(5);
            cont[6] = cursor.getString(6);
            cont[7] = cursor.getString(7);
            cont[8] = cursor.getString(8);
            cont[9] = cursor.getString(9);
            cont[10] = cursor.getString(10);
            cont[11] = cursor.getString(11);
            cont[12] = cursor.getString(12);
            cursor.moveToNext();
        }
        cursor.close();

        return cont;
    }

    //Get datos de un juego con el nombre especificado
    public static String[] getGame(Context contexto, String name) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        String cont[] = new String[13];

        getFotos(contexto, name);

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Datos_Juegos.Id_Juego, SQLite_DB.Tabla_Datos_Juegos.Nombre_Juego
                        , SQLite_DB.Tabla_Datos_Juegos.Descripcion_Juego, SQLite_DB.Tabla_Datos_Juegos.Generos
                        , SQLite_DB.Tabla_Datos_Juegos.Plataformas, SQLite_DB.Tabla_Datos_Juegos.Precios
                        , SQLite_DB.Tabla_Datos_Juegos.Valoraciones, SQLite_DB.Tabla_Datos_Juegos.URL_Icon
                        , SQLite_DB.Tabla_Datos_Juegos.URL_Imagen1, SQLite_DB.Tabla_Datos_Juegos.URL_Imagen2
                        , SQLite_DB.Tabla_Datos_Juegos.URL_Imagen3, SQLite_DB.Tabla_Datos_Juegos.URL_Imagen4
                        , SQLite_DB.Tabla_Datos_Juegos.URL_Imagen5} // Columnas
                , SQLite_DB.Tabla_Datos_Juegos.Nombre_Juego+"=?" // Cláusula where
                , new String[]{""+name} // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cont[0] = cursor.getString(0);
            cont[1] = cursor.getString(1);
            cont[2] = cursor.getString(2);
            cont[3] = cursor.getString(3);
            cont[4] = cursor.getString(4);
            cont[5] = cursor.getString(5);
            cont[6] = cursor.getString(6);
            cont[7] = cursor.getString(7);
            cont[8] = cursor.getString(8);
            cont[9] = cursor.getString(9);
            cont[10] = cursor.getString(10);
            cont[11] = cursor.getString(11);
            cont[12] = cursor.getString(12);
            cursor.moveToNext();
        }
        cursor.close();

        return cont;
    }

    private static boolean foto_isExists(Context contexto, String name){
        boolean res = false;
        String cont = new String();

        if (olympusgames_db == null) {
            olympusgames_db = new SQLite_DB(contexto);
        }
        SQLiteDatabase db = olympusgames_db.getReadableDatabase();
        Cursor cursor = db.query(
                false // Distinct
                , SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla // Tabla
                , new String[]{SQLite_DB.Tabla_Datos_Juegos.URL_Imagen1} // Columnas
                , SQLite_DB.Tabla_Datos_Juegos.Nombre_Juego+"=?" // Cláusula where
                , new String[]{""+name} // Vector de argumentos
                , null // Cláusula group by.
                , null // Cláusula having
                , null // Cláusula order by.
                , "1" // Cláusula limit
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cont = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();

        if(!cont.equals("")) res = true;

        return res;
    }

    private static void getFotos(Context contexto, String name) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        Bitmap imagen;
        if(!foto_isExists(contexto, name)) {
            //imagen1
            imagen = Internet.downloadImage(name.replace(":", "_"), "Imagen0.jpg");
            String URL_Imagen1 = guardarImagen(contexto, name, "Imagen0.jpg", imagen);
            //imagen2
            imagen = Internet.downloadImage(name.replace(":", "_"), "Imagen1.jpg");
            String URL_Imagen2 = guardarImagen(contexto, name, "Imagen1.jpg", imagen);
            //imagen3
            imagen = Internet.downloadImage(name.replace(":", "_"), "Imagen2.jpg");
            String URL_Imagen3 = guardarImagen(contexto, name, "Imagen2.jpg", imagen);
            //imagen4
            imagen = Internet.downloadImage(name.replace(":", "_"), "Imagen3.jpg");
            String URL_Imagen4 = guardarImagen(contexto, name, "Imagen3.jpg", imagen);
            //imagen5
            imagen = Internet.downloadImage(name.replace(":", "_"), "Imagen4.jpg");
            String URL_Imagen5 = guardarImagen(contexto, name, "Imagen4.jpg", imagen);

            if (olympusgames_db == null) {
                olympusgames_db = new SQLite_DB(contexto);
            }
            SQLiteDatabase db = olympusgames_db.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen1, URL_Imagen1);
            values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen2, URL_Imagen2);
            values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen3, URL_Imagen3);
            values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen4, URL_Imagen4);
            values.put(SQLite_DB.Tabla_Datos_Juegos.URL_Imagen5, URL_Imagen5);

            db.update(SQLite_DB.Tabla_Datos_Juegos.Nombre_Tabla, values, SQLite_DB.Tabla_Datos_Juegos.Nombre_Juego + "=?", new String[]{"" + name});
            db.close();
        }
    }

    private static String guardarImagen(Context context, String nombre, String image, Bitmap imagen){
        ContextWrapper cw = new ContextWrapper(context);
        File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, nombre+"_"+image);

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }
}
