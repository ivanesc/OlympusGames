package com.example.ivan.olympusgames;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class ModificarPerfil extends AppCompatActivity {
    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    ImageView foto_gallery2;

    private static final int PICK_IMAGE = 100;

    Uri imageUri;

    String[] lstSource = {

            "Harry",
            "Ron",
            "Hermione",
            "Snape",
            "Malfoy",
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Seven",
            "Eight",
            "Nine",
            "Ten"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarperfil);

        foto_gallery2 = (ImageView) findViewById(R.id.buttonFotoPerfil2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            //seleccionarItem(navigationView.getMenu().getItem(0));
        }

        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.item_modificar).setVisible(false);
        nav_Menu.findItem(R.id.item_listadeseos).setVisible(false);
        nav_Menu.findItem(R.id.item_reservas).setVisible(false);
        nav_Menu.findItem(R.id.item_cerrar_sesión).setVisible(false);

        contenido = (RelativeLayout) findViewById(R.id.content_modificar_perfil);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        foto_gallery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery2, PICK_IMAGE);
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {
                lstView.setVisibility(View.VISIBLE);
                contenido.setVisibility(View.INVISIBLE);
                barra.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lstView will return default

                lstView.setVisibility(View.INVISIBLE);
                //lstView.getLayoutParams().height = 0;
                //lstView.getLayoutParams().width = 0;
                contenido.setVisibility(View.VISIBLE);
                barra.setVisibility(View.VISIBLE);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    List<String> lstFound = new ArrayList<String>();
                    for(String item:lstSource){
                        if(item.contains(newText))
                            lstFound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(ModificarPerfil.this,android.R.layout.simple_list_item_1,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
                    ArrayAdapter adapter = new ArrayAdapter(ModificarPerfil.this,android.R.layout.simple_list_item_1,lstSource);
                    lstView.setAdapter(adapter);
                }
                return true;
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            foto_gallery2.setImageURI(imageUri);
        }
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

    public boolean seleccionarItem(MenuItem itemDrawer) {
        // Setear título actual
        setTitle(itemDrawer.getTitle());
        return (new DrawerManager()).onNavigationItemSelected(this, itemDrawer);
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
}
