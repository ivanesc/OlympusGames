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

    private static String ip_server = "192.168.1.35:8080";

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
        try {
            res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetNovedades.jsp", params);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static String getOfertas(){
        String res = "";
        String[] params = {"",""};
        try {
            res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetOfertas.jsp", params);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static String getBusquedas(String nombre, String plataforma, String genero){
        String res = "";
        String[] params = {"nombre", "" + nombre, "genero", "" + plataforma, "plataforma", "" + genero};
        try {
            res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/GetBusqueda.jsp", params);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static String addUsuario(String nombre, String pass){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre, "pass", "" + pass};
        try {
            res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/AddUsuario.jsp", params);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static String login(String nombre, String pass){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre, "pass", "" + pass};
        try {
            res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/login.jsp", params);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static String logout(String nombre){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre};
        try {
            res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/logout.jsp", params);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static String updateUsuario(String nombre, String pass){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre, "pass", "" + pass};
        try {
            res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/UpdateUsuario.jsp", params);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static String addReserva(String nombre_usuario, String num_juegos, String lista_juegos,
                                    String fecha, String tienda, String precio_total,
                                    String lista_plataformas, String estado, String identificacion,
                                    String token){
        String res = "";
        String[] params = {"nombre_usuario", "" + nombre_usuario, "num_juegos", "" + num_juegos,
                "lista_juegos", "" + lista_juegos, "fecha", "" + fecha, "tienda", "" + tienda,
                "precio_total", "" + precio_total, "lista_plataformas","" + lista_plataformas,
                "estado", "" + estado, "identificacion", "" + identificacion, "token", "" + token};
        try {
            res = prmja_com.Get("http://"+ip_server+"/Olympus_WebServer/AddReserva.jsp", params);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }
}
