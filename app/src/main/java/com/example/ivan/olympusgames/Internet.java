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

    private static String ip_server = "192.168.1.119:8080";

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
}
