package com.example.ivan.olympusgames;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.ivan.olympusgames.SQLite.Cache_Busquedas;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.ivan.olympusgames.R.id.appbar;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    private ImageSwitcher imageSwitcher;

    private int[] gallery = { R.drawable.anuncio1, R.drawable.anuncio2, R.drawable.anuncio3, R.drawable.anuncio4};

    private int position=0;

    private static final Integer DURATION = 4500;

    private Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                return new ImageView(MainActivity.this);
            }
        });

        // Set animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(gallery[position]);
                        position++;
                        if (position == gallery.length) {
                            position = 0;
                        }
                    }
                });
            }

        }, 0, DURATION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Material Search");

        //agregarToolbar();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //int idinicio = navigationView.getMenu().findItem(R.id.item_listadeseos).getItemId();

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        Menu nav_Menu = navigationView.getMenu();
        //nav_Menu.findItem(R.id.item_modificar).setVisible(false);
        //nav_Menu.findItem(R.id.item_listadeseos).setVisible(false);
        nav_Menu.findItem(R.id.item_reservas).setVisible(false);
        nav_Menu.findItem(R.id.item_cerrar_sesión).setVisible(false);

        contenido = (RelativeLayout) findViewById(R.id.content_main);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        com.example.ivan.olympusgames.SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        com.example.ivan.olympusgames.SearchView.addQueryTextListener(searchView, lstView, MainActivity.this);
    }


    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        seleccionarItem(menuItem);
                        drawer.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico1 = null;
        Fragment fragmentoGenerico2 = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                itemDrawer.setChecked(true);
                fragmentoGenerico1 = new FragmentoCategorias();
                break;
            case R.id.item_login:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.item_registrar:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, Registro.class));
                break;
            case R.id.item_modificar:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, ModificarPerfil.class));
                break;
            case R.id.item_plataformas:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, Plataformas.class));
                break;
            case R.id.item_listadeseos:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, ListaDeseos1.class));
                break;
            case R.id.acercade:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, AcercaDe.class));
                break;
            case R.id.escribir:
                itemDrawer.setChecked(true);
                try {
                    String datosMail = "mailto:olympusgames@gmail.es"
                            + "?cc=ivanEscobarSanchez@hotmail.com"
                            + "&subject="
                            + Uri.encode("Duda acerca producto tienda");
                    Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse(datosMail));
                    this.startActivity(email);
                    break;
                } catch (ActivityNotFoundException e) {
                Toast.makeText(this
                    , "ActivityNotFound "+e.getMessage()
                    , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ayuda:
                itemDrawer.setChecked(true);
                startActivity(new Intent(this, Ayuda.class));
                break;
        }
        if (fragmentoGenerico1 != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.content_main, fragmentoGenerico1)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_carrito:
                startActivity(new Intent(this, Carrito.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // Stops the slider when the Activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    // avoid exception:
                    // "Only the original thread that created a view hierarchy can touch its views"
                    runOnUiThread(new Runnable() {
                        public void run() {
                            imageSwitcher.setImageResource(gallery[position]);
                            position++;
                            if (position == gallery.length) {
                                position = 0;
                            }
                        }
                    });
                }

            }, 0, DURATION);
        }

    }
}

