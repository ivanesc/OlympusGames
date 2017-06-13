package com.example.ivan.olympusgames;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    EditText old_pass;
    EditText new_pass;
    EditText pass_conf;
    Button boton;

    private static final int PICK_IMAGE = 100;

    Uri imageUri;
    String imageUrl = "";

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
            DrawerManager.prepararDrawer(drawer, navigationView, ModificarPerfil.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_modificar_perfil);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, ModificarPerfil.this);

        foto_gallery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery2, PICK_IMAGE);
            }
        });

        imageUrl = Preferencias_Usuario.getIcon(ModificarPerfil.this);
        try {
            File icon = new File(imageUrl);
            if(icon.exists()) {
                foto_gallery2.setImageDrawable(Drawable.createFromPath(icon.getPath()));
                imageUri = Uri.fromFile(icon);
            }
        }catch(Exception e){}

        old_pass = (EditText)findViewById(R.id.edit_password_old);
        new_pass = (EditText)findViewById(R.id.edit_new_password);
        pass_conf = (EditText)findViewById(R.id.edit_confirm_password);

        boton = (Button)findViewById(R.id.but_save_change);
        boton.setOnClickListener(new View.OnClickListener() {
            String user;
            String password_now, password_old, password_new, password_conf;

            @Override
            public void onClick(View v) {
                int err = 0;
                user = Preferencias_Usuario.getUser(ModificarPerfil.this);
                password_now = Preferencias_Usuario.getPass(ModificarPerfil.this);
                password_old = old_pass.getText().toString();
                password_new = new_pass.getText().toString();
                password_conf = pass_conf.getText().toString();

                old_pass.setBackground(new ColorDrawable(0xFF455A64));
                new_pass.setBackground(new ColorDrawable(0xFF455A64));
                pass_conf.setBackground(new ColorDrawable(0xFF455A64));

                //Comprobar si existe algún error
                if(password_old.equals("")) err = 1;
                else if(password_new.equals("")) err = 2;
                else if(password_conf.equals("")) err = 3;
                else if(password_old.length() > 8) err = 4;
                else if(password_new.length() > 8) err = 5;
                else if(!password_new.equals(password_conf)) err = 6;
                else if(!password_now.equals(password_old)) err = 7;
                Log.e("PASS","Now: "+password_now+"; New: "+password_new);

                if(imageUri != null){
                    String icon = guardarImagen(ModificarPerfil.this, "user", "icon", ((BitmapDrawable)foto_gallery2.getDrawable()).getBitmap());
                    Preferencias_Usuario.setIcon(ModificarPerfil.this,icon);
                    Toast.makeText(getApplicationContext(),
                            "Icono modificado.", Toast.LENGTH_SHORT).show();
                }

                if(!password_old.equals("") || !password_new.equals("") || !password_conf.equals("")) {
                    switch (err) {
                        case 0: //No hay errores en los datos de entrada. Enviar petición
                            if(Internet.isConnected(ModificarPerfil.this)) {
                                String res = Internet.updateUsuario(user, password_new);
                                res = res.substring(res.indexOf("msj-start") + 9, res.indexOf("msj-end"));
                                if (res.equals("error")) {
                                    Toast.makeText(getApplicationContext(),
                                            "No se pueden modificar los datos.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Se han modificado los datos con éxito.", Toast.LENGTH_SHORT).show();
                                    Preferencias_Usuario.setPass(ModificarPerfil.this, password_new);
                                }
                            }else{
                                Toast.makeText(ModificarPerfil.this,
                                        "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1: //password_old vacía
                            old_pass.setBackground(new ColorDrawable(0xFFFF0000));
                            Toast.makeText(getApplicationContext(),
                                    "No has introducido tu contraseña anterior.", Toast.LENGTH_SHORT).show();
                            break;
                        case 2: //password_new vacía
                            new_pass.setBackground(new ColorDrawable(0xFFFF0000));
                            Toast.makeText(getApplicationContext(),
                                    "No has introducido la nueva contraseña.", Toast.LENGTH_SHORT).show();
                            break;
                        case 3: //password_conf vacía
                            pass_conf.setBackground(new ColorDrawable(0xFFFF0000));
                            Toast.makeText(getApplicationContext(),
                                    "No has introducido la confirmación de la nueva contraseña.", Toast.LENGTH_SHORT).show();
                            break;
                        case 4: //La contraseña introducida no cumple con el formato
                            old_pass.setBackground(new ColorDrawable(0xFFFF0000));
                            Toast.makeText(getApplicationContext(),
                                    "El número máximo de caracteres para la contraseña es 8.", Toast.LENGTH_SHORT).show();
                            break;
                        case 5: //La nueva contraseña introducida no cumple con el formato
                            new_pass.setBackground(new ColorDrawable(0xFFFF0000));
                            Toast.makeText(getApplicationContext(),
                                    "El número máximo de caracteres para la contraseña es 8.", Toast.LENGTH_SHORT).show();
                            break;
                        case 6: //Las nuevas contraseñas no coinciden
                            new_pass.setBackground(new ColorDrawable(0xFFFF0000));
                            pass_conf.setBackground(new ColorDrawable(0xFFFF0000));
                            Toast.makeText(getApplicationContext(),
                                    "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                            break;
                        case 7: //La contraseña anterior es incorrecta
                            old_pass.setBackground(new ColorDrawable(0xFFFF0000));
                            Toast.makeText(getApplicationContext(),
                                    "La contraseña anterior es incorrecta.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
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

