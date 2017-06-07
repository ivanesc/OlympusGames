package com.example.ivan.olympusgames;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

public class DrawerManager {
    public boolean onNavigationItemSelected(Activity activity, MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.item_inicio:
                item.setChecked(true);
                try{
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_login:
                item.setChecked(true);
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
                item.setChecked(true);
                try{
                    Intent intent3 = new Intent(activity, Registro.class);
                    activity.startActivity(intent3);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_modificar:
                item.setChecked(true);
                try{
                    Intent intent4 = new Intent(activity, ModificarPerfil.class);
                    activity.startActivity(intent4);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_plataformas:
                item.setChecked(true);
                try{
                    Intent intent5 = new Intent(activity, Plataformas.class);
                    activity.startActivity(intent5);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_listadeseos:
                item.setChecked(true);
                try{
                    Intent intent = new Intent(activity, ListaDeseos1.class);
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.acercade:
                item.setChecked(true);
                try {
                    Intent intent6 = new Intent(activity, AcercaDe.class);
                    activity.startActivity(intent6);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.escribir:
                item.setChecked(true);
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
            case R.id.ayuda:
                item.setChecked(true);
                try {
                    Intent intent7 = new Intent(activity, Ayuda.class);
                    activity.startActivity(intent7);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(activity
                            , "ActivityNotFound "+e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }
                break;
        }

        // Setear título actual
        //setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
