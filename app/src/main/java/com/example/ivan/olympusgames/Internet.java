package com.example.ivan.olympusgames;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.ivan.olympusgames.prmja.http.prmja_com;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Fernando on 04/06/2017.
 */

public class Internet {

    private static String ip_server = "192.168.1.146:8080";

    static boolean msjShown = false;

    public static boolean isConnected(Context context){
        if(conectadoWifi(context) || conectadoRedMovil(context)){
            return true;
        }else{
            if(!msjShown) {
                showAlertDialog(context, "Conexion a Internet",
                        "Tu Dispositivo no tiene Conexion a Internet. Debes conectarte a una red.", false);
                msjShown = true;
            }
            return false;
        }
    }

    protected static Boolean conectadoWifi(Context contexto){
        ConnectivityManager connectivity = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static Boolean conectadoRedMovil(Context contexto){
        ConnectivityManager connectivity = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }

    public static Bitmap downloadImage(String Nombre_Juego, String image) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        Bitmap res = prmja_com.Download_Image("http://"+ip_server+"/Olympus_WebServer/imagenes/"+Nombre_Juego.replace(" ","%20")+"/"+image);
        return res;
    }

    public static String getNovedades(){
        String res = "";
        String[] params = {"",""};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetNovedades.jsp", params);

        return res;
    }

    public static String getOfertas(){
        String res = "";
        String[] params = {"",""};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetOfertas.jsp", params);

        return res;
    }

    public static String getBusquedas(String nombre, String plataforma, String genero){
        String res = "";
        String[] params = {"nombre", "" + nombre, "plataforma", "" + plataforma, "genero", "" + genero};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetBusqueda.jsp", params);

        return res;
    }

    public static String getMasReservados(){
        String res = "";
        String[] params = {"",""};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetMasReservados.jsp", params);

        return res;
    }

    public static String addUsuario(String nombre, String pass, String correo){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre, "pass", "" + pass, "correo", "" + correo};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/AddUsuario.jsp", params);

        return res;
    }

    public static String login(String nombre, String pass){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre, "pass", "" + pass};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/login.jsp", params);

        return res;
    }

    public static String logout(String nombre){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/logout.jsp", params);

        return res;
    }

    public static String updateUsuario(String nombre, String pass){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre, "pass", "" + pass};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/UpdateUsuario.jsp", params);

        return res;
    }

    public static String addReserva(String nombre_usuario, String num_juegos, String lista_juegos,
                                    String fecha, String tienda, String precio_total,
                                    String lista_plataformas, String estado, String identificacion,
                                    String cantidad, String token){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre_usuario, "num_juegos", "" + num_juegos,
                "lista_juegos", "" + lista_juegos, "fecha", "" + fecha, "tienda", "" + tienda,
                "precio_total", "" + precio_total, "lista_plataformas","" + lista_plataformas,
                "estado", "" + estado, "identificacion", "" + identificacion, "cantidad", "" + cantidad,
                "token", "" + token};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/AddReserva.jsp", params);

        return res;
    }



    public static String deleteReserva(String nombre_usuario, String token, String identificacion){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre_usuario, "identificacion", "" + identificacion,
                "token", "" + token};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/DeleteReserva.jsp", params);

        return res;
    }

    public static String stateReserva(String identificador, String token, String user){
        String res = "";
        String[] params = {"identificador", "" + identificador, "token", "" + token, "usuario", ""+user};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/StateReserva.jsp", params);

        return res;
    }

    public static String changePass(String user){
        String res = "";
        String[] params = {"usuario", ""+user};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/ChangePass.jsp", params);

        return res;
    }

    public static String getTiendas(){
        String res = "";
        String[] params = {};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetTiendas.jsp", params);

        return res;
    }

    public static String getTiendasBusqueda(String nombre){
        String res = "";
        String[] params = {"nombre", ""+nombre};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetTiendasBusqueda.jsp", params);

        return res;
    }

    public static String getTiendasCercanas(String lat, String lon){
        String res = "";
        String[] params = {"lat", ""+lat, "lon", ""+lon};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetTiendasCercanas.jsp", params);

        return res;
    }

    public static String addComentario(String id_juego, String nombre_usuario, String comentario,
            String fecha, String token){
        String res = "";
        String[] params = {"id_juego", ""+id_juego, "nombre_usuario", ""+nombre_usuario, "comentario", ""+comentario
                , "fecha", ""+fecha, "token", ""+token};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/AddComentario.jsp", params);

        return res;
    }

    public static String getComentarios(String id_juego){
        String res = "";
        String[] params = {"id_juego", ""+id_juego};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetComentarios.jsp", params);

        return res;
    }

    public static String getNotificaciones(String nombre_usuario){
        String res = "";
        String[] params = {"nombre_usuario", ""+nombre_usuario};
        res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetNotificaciones.jsp", params);

        return res;
    }
}
