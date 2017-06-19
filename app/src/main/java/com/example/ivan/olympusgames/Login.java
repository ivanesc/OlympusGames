package com.example.ivan.olympusgames;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {
    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    EditText usuario;
    EditText pass;
    Button boton;
    Button boton_registrar;
    Button boton_olvido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Material Search");

        //agregarToolbar();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            DrawerManager.prepararDrawer(drawer, navigationView, Login.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_login);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, Login.this);

        usuario = (EditText)findViewById(R.id.log_user);
        pass = (EditText)findViewById(R.id.log_password);

        boton_registrar = (Button)findViewById(R.id.but_create);
        boton_registrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });

        boton_olvido = (Button)findViewById(R.id.but_forgot);
        boton_olvido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Olvido.class));
            }
        });

        boton = (Button)findViewById(R.id.but_enter_user);
        boton.setOnClickListener(new View.OnClickListener() {
            String user, password;

            @Override
            public void onClick(View v) {
                int err = 0;
                user = usuario.getText().toString();
                password = pass.getText().toString();

                usuario.setBackground(new ColorDrawable(0xFF455A64));
                pass.setBackground(new ColorDrawable(0xFF455A64));

                //Comprobar si existe algún error
                if(user.equals("")) err = 1;
                else if(password.equals("")) err = 2;
                else if(isNumeric(user)) err = 3;
                else if(password.length() > 8) err = 4;

                switch(err){
                    case 0: //No hay errores en los datos de entrada. Enviar petición
                        if(Internet.isConnected(Login.this)) {
                            String res = Internet.login(user, password);
                            res = res.substring(res.indexOf("msj-start") + 9, res.indexOf("msj-end"));
                            if (res.equals("error")) {
                                usuario.setBackground(new ColorDrawable(0xFFFF0000));
                                pass.setBackground(new ColorDrawable(0xFFFF0000));
                                Toast.makeText(getApplicationContext(),
                                        "No se pudo iniciar sesión. Datos incorrectos.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Has iniciado sesión con éxito.", Toast.LENGTH_SHORT).show();
                                new Preferencias_Usuario(user, password, res, "", Login.this);
                                startActivity(new Intent(Login.this, MainActivity.class));
                            }
                        }else{
                            Toast.makeText(Login.this,
                                    "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1: //Nombre de usuario vacío
                        usuario.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "No has introducido el nombre de usuario.", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: //Password vacía
                        pass.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "No has introducido la contraseña.", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: //Nombre de usuario introducido no cumple con el formato
                        usuario.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "Debes introducir letras en el nombre de usuario.", Toast.LENGTH_SHORT).show();
                        break;
                    case 4: //Contraseña introducida no cumple con el formato
                        pass.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "El número máximo de caracteres para la contraseña es 8.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
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
