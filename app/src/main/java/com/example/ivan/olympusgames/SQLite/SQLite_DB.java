package com.example.ivan.olympusgames.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fernando on 16/05/2017.
 */

public class SQLite_DB extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "olympusgames.db";
    private static final int VERSION_ACTUAL = 28;
    private final Context contexto;

    public static class Tabla_Datos_Juegos {

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
        public static final String Valoracion = "valoracion";
    }

    public static class Tabla_Novedades {

        public static final String Nombre_Tabla = "novedades";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
    }

    public static class Tabla_Ofertas {

        public static final String Nombre_Tabla = "ofertas";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
    }

    public static class Tabla_Busquedas {

        public static final String Nombre_Tabla = "busquedas";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
    }

    public static class Tabla_Comentarios {

        public static final String Nombre_Tabla = "comentarios";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
        public static final String Usuario = "usuario";
        public static final String Comentario = "comentario";
        public static final String Fecha = "fecha";
    }

    public static class Tabla_Reservas {

        public static final String Nombre_Tabla = "reservas";
        public static final String Id = "id";
        public static final String Num_Juegos = "num_juegos";
        public static final String Precio_Total = "precio_total";
        public static final String Fecha = "fecha";
        public static final String Tienda = "tienda";
        public static final String Lista_Juegos = "lista_juegos";
        public static final String Lista_Plataformas = "lista_plataformas";
        public static final String Estado = "estado";
        public static final String Identificador = "identificador";
        public static final String Cantidad = "cantidad";
    }

    public static class Tabla_Lista_Deseados {

        public static final String Nombre_Tabla = "lista_deseados";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
    }

    public static class Tabla_Banner {

        public static final String Nombre_Tabla = "banner";
        public static final String Id = "id";
        public static final String Url_Imagen1 = "url_imagen1";
        public static final String Url_Imagen2 = "url_imagen2";
        public static final String Url_Imagen3 = "url_imagen3";
        public static final String Url_Imagen4 = "url_imagen4";
    }

    public static class Tabla_Preferencias_Usuario {

        public static final String Nombre_Tabla = "preferencias_usuario";
        public static final String Id = "id";
        public static final String Nombre = "nombre";
        public static final String Pass = "pass";
        public static final String Correo = "correo";
        public static final String Url_Imagen = "url_imagen";
        public static final String Token = "token";
        public static final String Tienda_Preferida = "tienda_preferida";
    }

    public static class Tabla_Carrito {

        public static final String Nombre_Tabla = "carrito";
        public static final String Id = "id";
        public static final String Id_Juego = "id_juego";
        public static final String PosPlataforma = "pos_plataforma";
        public static final String Cantidad = "cantidad";
    }

    public static class Tabla_Cache_Busquedas {

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
                        "%s TEXT,%s TEXT,%s TEXT, %s TEXT);",
                Tabla_Datos_Juegos.Nombre_Tabla, Tabla_Datos_Juegos.Id_Juego, Tabla_Datos_Juegos.Nombre_Juego,
                Tabla_Datos_Juegos.Descripcion_Juego, Tabla_Datos_Juegos.Generos, Tabla_Datos_Juegos.Plataformas,
                Tabla_Datos_Juegos.Precios, Tabla_Datos_Juegos.Valoraciones, Tabla_Datos_Juegos.URL_Icon,
                Tabla_Datos_Juegos.URL_Imagen1, Tabla_Datos_Juegos.URL_Imagen2, Tabla_Datos_Juegos.URL_Imagen3,
                Tabla_Datos_Juegos.URL_Imagen4, Tabla_Datos_Juegos.URL_Imagen5, Tabla_Datos_Juegos.Valoracion));
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
                        "%s TEXT, %s TEXT, %s TEXT);",
                Tabla_Comentarios.Nombre_Tabla, Tabla_Comentarios.Id, Tabla_Comentarios.Id_Juego,
                Tabla_Comentarios.Usuario, Tabla_Comentarios.Comentario, Tabla_Comentarios.Fecha));
        //Crear tabla Reservas_Cache
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER," +
                        "%s TEXT, %s DATE, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                Tabla_Reservas.Nombre_Tabla, Tabla_Reservas.Id, Tabla_Reservas.Num_Juegos,
                Tabla_Reservas.Precio_Total, Tabla_Reservas.Fecha, Tabla_Reservas.Tienda,
                Tabla_Reservas.Lista_Juegos, Tabla_Reservas.Estado, Tabla_Reservas.Identificador,
                Tabla_Reservas.Lista_Plataformas, Tabla_Reservas.Cantidad));
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
                        "%s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                Tabla_Preferencias_Usuario.Nombre_Tabla, Tabla_Preferencias_Usuario.Id,
                Tabla_Preferencias_Usuario.Nombre, Tabla_Preferencias_Usuario.Pass,
                Tabla_Preferencias_Usuario.Url_Imagen, Tabla_Preferencias_Usuario.Token,
                Tabla_Preferencias_Usuario.Tienda_Preferida, Tabla_Preferencias_Usuario.Correo));
        //Crear tabla Tabla_Carrito
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT," +
                        "%s INTEGER, %s TEXT);",
                Tabla_Carrito.Nombre_Tabla, Tabla_Carrito.Id, Tabla_Carrito.Id_Juego,
                Tabla_Carrito.Cantidad, Tabla_Carrito.PosPlataforma));
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
