package com.example.ivan.olympusgames;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Cache_Busquedas;
import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {
    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    EditText usuario;
    EditText email;
    EditText pass;
    EditText pass_conf;
    Button boton;

    ImageView foto_gallery;

    private static final int PICK_IMAGE = 100;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        foto_gallery = (ImageView) findViewById(R.id.buttonFotoPerfil1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Material Search");

        //agregarToolbar();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float n = 0;
                String iconPath = Preferencias_Usuario.getIcon(Registro.this);
                Drawable icon = getResources().getDrawable(R.drawable.ares);
                int fondo = Color.rgb(72,72,72);

                if(iconPath != null) {
                    if(!Preferencias_Usuario.getToken(Registro.this).equals("")) {
                        if (iconPath.equals(""))
                            icon = getResources().getDrawable(R.drawable.fotoperfil);
                        else icon = Drawable.createFromPath(iconPath);

                        fondo = Color.rgb(63, 81, 181);
                    }
                }

                if (drawer.isDrawerOpen(GravityCompat.START)) n=0;
                else if(n == 0){
                    n=1;
                    ((LinearLayout)findViewById(R.id.fondoMenu)).setBackground(new ColorDrawable(fondo));
                    ((ImageView)findViewById(R.id.iconoMenu)).setImageDrawable(icon);
                }
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            DrawerManager.prepararDrawer(drawer, navigationView, Registro.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_registro);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, Registro.this);


        foto_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        usuario = (EditText)findViewById(R.id.reg_user);
        email = (EditText)findViewById(R.id.reg_email);
        pass = (EditText)findViewById(R.id.reg_password);
        pass_conf = (EditText)findViewById(R.id.conf_regpassword);

        boton = (Button)findViewById(R.id.but_register_user);
        boton.setOnClickListener(new View.OnClickListener() {
            String user, correo, password, password_conf;

            @Override
            public void onClick(View v) {
                int err = 0;
                user = usuario.getText().toString();
                correo = email.getText().toString();
                password = pass.getText().toString();
                password_conf = pass_conf.getText().toString();

                usuario.setBackground(new ColorDrawable(0xFF455A64));
                email.setBackground(new ColorDrawable(0xFF455A64));
                pass.setBackground(new ColorDrawable(0xFF455A64));
                pass_conf.setBackground(new ColorDrawable(0xFF455A64));

                //Comprobar si existe algún error
                if(user.equals("")) err = 1;
                else if(password.equals("")) err = 2;
                else if(password_conf.equals("")) err = 3;
                else if(isNumeric(user)) err = 4;
                else if(password.length() > 8) err = 5;
                else if(!password.equals(password_conf)) err = 6;
                else if(!correo.contains("@") || !correo.contains(".")) err = 7;

                switch(err){
                    case 0: //No hay errores en los datos de entrada. Enviar petición
                        if(Internet.isConnected(Registro.this)) {
                            String res = Internet.addUsuario(user, password_conf, correo);
                            if (res.substring(res.indexOf("msj-start") + 9, res.indexOf("msj-end")).equals("error")) {
                                usuario.setBackground(new ColorDrawable(0xFFFF0000));
                                Toast.makeText(getApplicationContext(),
                                        "Ese usuario ya está registrado.", Toast.LENGTH_SHORT).show();
                            } else if (res.substring(res.indexOf("msj-start") + 9, res.indexOf("msj-end")).equals("OK")) {
                                Toast.makeText(getApplicationContext(),
                                        "Registro completado con éxito.", Toast.LENGTH_SHORT).show();
                                if (imageUri != null) {
                                    String icon = guardarImagen(Registro.this, "user", "icon", ((BitmapDrawable) foto_gallery.getDrawable()).getBitmap());
                                    new Preferencias_Usuario("", "", "", "", Registro.this);
                                    Preferencias_Usuario.updateCorreo(Registro.this, correo);
                                    Preferencias_Usuario.setIcon(Registro.this, icon);
                                }

                                startActivity(new Intent(Registro.this, Login.class));
                            }
                        }else{
                            Toast.makeText(Registro.this,
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
                    case 3: //Confirmación de password vacía
                        pass_conf.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "No has introducido la confirmación de contraseña.", Toast.LENGTH_SHORT).show();
                        break;
                    case 4: //Nombre de usuario introducido no cumple con el formato
                        usuario.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "Debes introducir letras en el nombre de usuario.", Toast.LENGTH_SHORT).show();
                        break;
                    case 5: //Contraseña introducida no cumple con el formato
                        pass.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "El número máximo de caracteres para la contraseña es 8.", Toast.LENGTH_SHORT).show();
                        break;
                    case 6: //Las contraseñas no coinciden
                        pass.setBackground(new ColorDrawable(0xFFFF0000));
                        pass_conf.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                        break;
                    case 7: //Las contraseñas no coinciden
                        email.setBackground(new ColorDrawable(0xFFFF0000));
                        Toast.makeText(getApplicationContext(),
                                "Introduzca un correo válido.", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);
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

    private String guardarImagen (Context context, String nombre, String image, Bitmap imagen){
        ContextWrapper cw = new ContextWrapper(context);
        File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, nombre+"_"+image);

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }
}
