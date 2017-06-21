package com.example.ivan.olympusgames;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;

public class DrawerManager {

    public static void prepararDrawer(final DrawerLayout drawer, NavigationView navigationView, final Activity activity) {
        Menu nav_Menu = navigationView.getMenu();
        MenuItems(nav_Menu, activity);
        selectItem(nav_Menu, activity);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        NavigationItemSelected(activity, menuItem);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

    public static boolean NavigationItemSelected(Activity activity, MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.item_inicio:
                try{
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_plataformas:
                try{
                    Intent intent5 = new Intent(activity, Plataformas.class);
                    activity.startActivity(intent5);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_genero:
                try{
                    Intent intent6 = new Intent(activity, Generos.class);
                    activity.startActivity(intent6);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_masreservados:
                if(Internet.isConnected(activity)) {
                    try {
                        Intent intent7 = new Intent(activity, MasReservados.class);
                        activity.startActivity(intent7);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(activity
                                , "ActivityNotFound " + e.getMessage()
                                , Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(activity,
                            "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_login:
                try{
                    Intent intent2 = new Intent(activity, Login.class);
                    activity.startActivity(intent2);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_registrar:
                try{
                    Intent intent3 = new Intent(activity, Registro.class);
                    activity.startActivity(intent3);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_cerrar_sesión:
                try{
                    if(Internet.isConnected(activity)) {
                        Preferencias_Usuario.setToken(activity, "");
                        String nombre = Preferencias_Usuario.getUser(activity);
                        Internet.logout(nombre);
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    }else{
                        Toast.makeText(activity,
                                "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_modificar:
                try{
                    Intent intent4 = new Intent(activity, ModificarPerfil.class);
                    activity.startActivity(intent4);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_listadeseos:
                try{
                    Intent intent = new Intent(activity, ListaDeseos1.class);
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_reservas:
                try{
                    Intent intent = new Intent(activity, Reservas.class);
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_acercade:
                try {
                    Intent intent8 = new Intent(activity, AcercaDe.class);
                    activity.startActivity(intent8);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_escribir:
                try {
                    String datosMail = "mailto:olympusgames@gmail.es"
                            + "?cc=ivanEscobarSanchez@hotmail.com"
                            + "&subject="
                            + Uri.encode("Duda acerca producto tienda");
                    Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse(datosMail));
                    activity.startActivity(email);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_ayuda:
                try {
                    Intent intent9 = new Intent(activity, Ayuda.class);
                    activity.startActivity(intent9);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* Ocultar items innecesarios */
    public static void MenuItems(Menu nav_Menu, Context context){
        String token = Preferencias_Usuario.getToken(context);

        if(token.equals("")){    //No se ha hecho login
            nav_Menu.findItem(R.id.item_modificar).setVisible(false);
            nav_Menu.findItem(R.id.item_cerrar_sesión).setVisible(false);
            nav_Menu.findItem(R.id.item_reservas).setVisible(false);
        }else{      //Si se ha hecho login
            nav_Menu.findItem(R.id.item_login).setVisible(false);
            nav_Menu.findItem(R.id.item_registrar).setVisible(false);
        }
    }

    /* Marcar item actual */
    public static void selectItem(Menu nav_Menu, Activity activity){
        String activityName = activity.getTitle().toString();

        switch(activityName){
            case "Inicio":
                nav_Menu.findItem(R.id.item_inicio).setChecked(true);
                break;
            case "Plataformas":
                nav_Menu.findItem(R.id.item_plataformas).setChecked(true);
                break;
            case "Generos":
                nav_Menu.findItem(R.id.item_genero).setChecked(true);
                break;
            case "+ Reservados":
                nav_Menu.findItem(R.id.item_masreservados).setChecked(true);
                break;
            case "Login":
                nav_Menu.findItem(R.id.item_login).setChecked(true);
                break;
            case "Registro":
                nav_Menu.findItem(R.id.item_registrar).setChecked(true);
                break;
            case "Editar perfil":
                nav_Menu.findItem(R.id.item_modificar).setChecked(true);
                break;
            case "Lista deseos":
                nav_Menu.findItem(R.id.item_listadeseos).setChecked(true);
                break;
            case "Reservas":
                nav_Menu.findItem(R.id.item_reservas).setChecked(true);
                break;
            case "Acerca de":
                nav_Menu.findItem(R.id.item_acercade).setChecked(true);
                break;
            case "Ayuda":
                nav_Menu.findItem(R.id.item_ayuda).setChecked(true);
                break;
            default:
                Log.e("ACTIVITY","Activity: "+activityName);
        }
    }
}
