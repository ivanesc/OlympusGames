package com.example.ivan.olympusgames.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fernando on 16/05/2017.
 */

public class SQLite_DB extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "olympusgames.db";
    private static final int VERSION_ACTUAL = 18;
    private final Context contexto;

    public static class Tabla_Datos_Juegos {
        /* los juegos permanecen mientras esten en alguna de las otras tablas que lo necesitan
        * id_juego integer primary key
        * nombre text
        * generos text (separados por ,)
        * plataformas text (separados por ,)
        * precios text (separados por ,)
        * valoraciones text (separados por ,)
        * url_icon text
        * url_imagen1 text
        * url_imagen2 text
        * url_imagen3 text
        * url_imagen4 text
        * url_imagen5 text
        */
        public static final String Nombre_Tabla = "datos_juegos";
        public static final String Id_Juego = "id_juego";
        public static final String Nombre_Juego = "nombre";
        public static final String Descripcion_Juego = "descripcion";
        public static final String Generos = "generos";
        public static final String Plataformas = "plataformas";
        public static final String Precios = "precios";
        public static final String Valoraciones = "valoraciones";
        public static final String URL_Icon = "url_icon";
        public static final String URL_Imagen1 = "url_imagen1";
        public static final String URL_Imagen2 = "url_imagen2";
        public static final String URL_Imagen3 = "url_imagen3";
        public static final String URL_Imagen4 = "url_imagen4";
        public static final String URL_Imagen5 = "url_imagen5";
    }

    public static class Tabla_Novedades {
        /* límite 20 juegos
        * id integer primary key autoincrement
        * id_juego integer coger datos de datos_juegos
        */
        public static final String Nombre_Tabla = "novedades";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
    }

    public static class Tabla_Ofertas {
        /* límite 20 juegos
        * id integer primary key autoincrement
        * id_juego integer coger datos de datos_juegos
        */
        public static final String Nombre_Tabla = "ofertas";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
    }

    public static class Tabla_Busquedas {
        /* límite 20 juegos
        * id integer primary key autoincrement
        * id_juego integer coger datos de datos_juegos
        */
        public static final String Nombre_Tabla = "busquedas";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
    }

    public static class Tabla_Comentarios {
        /* límite 10 comentarios por juego
        * id integer primary key autoincrement
        * id_juego integer
        * usuario text
        * comentario text
        */
        public static final String Nombre_Tabla = "comentarios";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
        public static final String Usuario = "usuario";
        public static final String Comentario = "comentario";
    }

    public static class Tabla_Reservas {
        /* límite de 10 reservas
        * id integer primary key autoincrement
        * num_juegos integer
        * precio_total text
        * fecha text
        * tienda text
        * lista_juegos text (separados por /////) id_juego de los juegos. coger datos de datos_juegos
        */
        public static final String Nombre_Tabla = "reservas";
        public static final String Id = "id";
        public static final String Num_Juegos = "num_juegos";
        public static final String Precio_Total = "precio_total";
        public static final String Fecha = "fecha";
        public static final String Tienda = "tienda";
        public static final String Lista_Juegos = "lista_juegos";
    }

    public static class Tabla_Lista_Deseados {
        /* Sin límite
        * id integer primary key autoincrement
        * id_juego text coger datos de datos_juegos
        */
        public static final String Nombre_Tabla = "lista_deseados";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
    }

    public static class Tabla_Banner {
        /* Limitado a 4 imagenes
        * id integer primary key
        * url_imagen1 text
        * url_imagen2 text
        * url_imagen3 text
        * url_imagen4 text
        */
        public static final String Nombre_Tabla = "banner";
        public static final String Id = "id";
        public static final String Url_Imagen1 = "url_imagen1";
        public static final String Url_Imagen2 = "url_imagen2";
        public static final String Url_Imagen3 = "url_imagen3";
        public static final String Url_Imagen4 = "url_imagen4";
    }

    public static class Tabla_Preferencias_Usuario {
        /*
        * id integer primary key autoincrement
        * nombre text
        * url_imagen text
        * token text
        * tienda_preferida text
        */
        public static final String Nombre_Tabla = "preferencias_usuario";
        public static final String Id = "id";
        public static final String Nombre = "nombre";
        public static final String Pass = "pass";
        public static final String Url_Imagen = "url_imagen";
        public static final String Token = "token";
        public static final String Tienda_Preferida = "tienda_preferida";
    }

    public static class Tabla_Carrito {
        /*
        * id integer primary key autoincrement
        * id_juego text
        * cantidad integer
        */
        public static final String Nombre_Tabla = "carrito";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
        public static final String Cantidad = "cantidad";
    }

    public static class Tabla_Cache_Busquedas {
        /*
        * id integer primary key autoincrement
        * busqueda text
        */
        public static final String Nombre_Tabla = "cache_busquedas";
        public static final String Id = "id";
        public static final String Busqueda = "busqueda";
    }



    public SQLite_DB(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    /*@Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crear tabla Datos_Juegos
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY," + "%s TEXT," +
                        "%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,"+
                        "%s TEXT,%s TEXT,%s TEXT);",
                Tabla_Datos_Juegos.Nombre_Tabla, Tabla_Datos_Juegos.Id_Juego, Tabla_Datos_Juegos.Nombre_Juego,
                Tabla_Datos_Juegos.Descripcion_Juego, Tabla_Datos_Juegos.Generos, Tabla_Datos_Juegos.Plataformas,
                Tabla_Datos_Juegos.Precios, Tabla_Datos_Juegos.Valoraciones, Tabla_Datos_Juegos.URL_Icon,
                Tabla_Datos_Juegos.URL_Imagen1, Tabla_Datos_Juegos.URL_Imagen2, Tabla_Datos_Juegos.URL_Imagen3,
                Tabla_Datos_Juegos.URL_Imagen4, Tabla_Datos_Juegos.URL_Imagen5));
        //Crear tabla Novedades
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER);",
                Tabla_Novedades.Nombre_Tabla, Tabla_Novedades.Id,
                Tabla_Novedades.Id_Juego));
        //Crear tabla Ofertas
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER);",
                Tabla_Ofertas.Nombre_Tabla, Tabla_Ofertas.Id,
                Tabla_Ofertas.Id_Juego));
        //Crear tabla Busquedas
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER);",
                Tabla_Busquedas.Nombre_Tabla, Tabla_Busquedas.Id,
                Tabla_Busquedas.Id_Juego));
        //Crear tabla Comentarios
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER," +
                        "%s TEXT, %s TEXT);",
                Tabla_Comentarios.Nombre_Tabla, Tabla_Comentarios.Id, Tabla_Comentarios.Id_Juego,
                Tabla_Comentarios.Usuario, Tabla_Comentarios.Comentario));
        //Crear tabla Reservas
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER," +
                        "%s TEXT, %s DATE, %s TEXT, %s TEXT);",
                Tabla_Reservas.Nombre_Tabla, Tabla_Reservas.Id, Tabla_Reservas.Num_Juegos,
                Tabla_Reservas.Precio_Total, Tabla_Reservas.Fecha, Tabla_Reservas.Tienda,
                Tabla_Reservas.Lista_Juegos));
        //Crear tabla Lista_Deseados
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER);",
                Tabla_Lista_Deseados.Nombre_Tabla, Tabla_Lista_Deseados.Id,
                Tabla_Lista_Deseados.Id_Juego));
        //Crear tabla Banner
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT," +
                        "%s TEXT, %s TEXT, %s TEXT);",
                Tabla_Banner.Nombre_Tabla, Tabla_Banner.Id, Tabla_Banner.Url_Imagen1,
                Tabla_Banner.Url_Imagen2, Tabla_Banner.Url_Imagen3, Tabla_Banner.Url_Imagen4));
        //Crear tabla Preferencias_Usuario
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT," +
                        "%s TEXT, %s TEXT, %s TEXT);",
                Tabla_Preferencias_Usuario.Nombre_Tabla, Tabla_Preferencias_Usuario.Id,
                Tabla_Preferencias_Usuario.Nombre, Tabla_Preferencias_Usuario.Pass,
                Tabla_Preferencias_Usuario.Url_Imagen, Tabla_Preferencias_Usuario.Token,
                Tabla_Preferencias_Usuario.Tienda_Preferida));
        //Crear tabla Tabla_Carrito
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT," +
                        "%s INTEGER);",
                Tabla_Carrito.Nombre_Tabla, Tabla_Carrito.Id, Tabla_Carrito.Id_Juego,
                Tabla_Carrito.Cantidad));
        //Crear tabla Tabla_Cache_Busquedas
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT);",
                Tabla_Cache_Busquedas.Nombre_Tabla, Tabla_Cache_Busquedas.Id, Tabla_Cache_Busquedas.Busqueda));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borrar todas las tablas
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Datos_Juegos.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Novedades.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Ofertas.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Busquedas.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Comentarios.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Reservas.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Lista_Deseados.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Banner.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Preferencias_Usuario.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Carrito.Nombre_Tabla);
        db.execSQL("DROP TABLE IF EXISTS " + Tabla_Cache_Busquedas.Nombre_Tabla);
        onCreate(db);
    }
}
