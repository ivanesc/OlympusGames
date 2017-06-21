package com.example.ivan.olympusgames;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.module.ManifestParser;
import com.example.ivan.olympusgames.SQLite.Preferencias_Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MapaTiendas extends AppCompatActivity implements OnMapReadyCallback {

    LocationManager locManager;
    LocationListener locListener;

    GoogleMap map;

    DrawerLayout drawer;

    MaterialSearchView searchView;

    ListView lstView;

    RelativeLayout contenido;

    AppBarLayout barra;

    Toolbar barra1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_tiendas);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
                String iconPath = Preferencias_Usuario.getIcon(MapaTiendas.this);
                Drawable icon = getResources().getDrawable(R.drawable.ares);
                int fondo = Color.rgb(72,72,72);

                if(iconPath != null) {
                    if(!Preferencias_Usuario.getToken(MapaTiendas.this).equals("")) {
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
            DrawerManager.prepararDrawer(drawer, navigationView, MapaTiendas.this);
        }

        contenido = (RelativeLayout) findViewById(R.id.content_mapa_tiendas);

        barra = (AppBarLayout) findViewById(R.id.appbar);

        barra1 = (Toolbar) findViewById(R.id.toolbar);

        lstView = (ListView)findViewById(R.id.lstView);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        SearchView.addSearchViewListener(searchView, lstView, contenido, barra);
        SearchView.addQueryTextListener(searchView, lstView, MapaTiendas.this);
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

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        map=googleMap;
        map.setMapType(googleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final String tienda = marker.getTitle().toString();

                new AlertDialog.Builder(MapaTiendas.this)
                        .setTitle("Tienda Habitual")
                        .setMessage("¿Quieres que '"+tienda+"' sea tu tienda habitual?")
                        .setNegativeButton("NO", null) // dismisses by default
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                // do the acknowledged action, beware, this is run on UI thread
                                Preferencias_Usuario.setTienda(MapaTiendas.this, tienda);
                                Toast.makeText(getApplicationContext(),
                                        "Has añadido '"+tienda+"' como tu tienda habitual.", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
                return false;
            }
        });

        LatLng España = new LatLng(37.5442706,-4.727752799999962);

        String res = Internet.getTiendas();
        String tiendas[] = res.split("<br/>");
        for(int i = 1; i<tiendas.length; i++){
            String tiendaItem[] = tiendas[i].split("%/%");

            String nombre_tienda = tiendaItem[0];
            float lat_tienda = Float.parseFloat(tiendaItem[1]);
            float lon_tienda = Float.parseFloat(tiendaItem[2]);
            LatLng tienda = new LatLng(lat_tienda,lon_tienda);
            googleMap.addMarker(new MarkerOptions().position(tienda)
                    .title(nombre_tienda));
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(España,5));
    }

    public void onLocButtonClick(View v) {
        String txt = ((EditText)findViewById(R.id.miubicacion)).getText().toString();

        if(txt.equals("")){ //Buscar tienda más cercana a la posición actual
            comenzarLocalizacion();
        }else{  //Buscar tiendas que contengan esa cadena
            map.setMapType(map.MAP_TYPE_NORMAL);
            map.getUiSettings().setZoomControlsEnabled(true);

            LatLng España = new LatLng(37.5442706,-4.727752799999962);

            map.clear();
            String res = Internet.getTiendasBusqueda(txt);
            String tiendas[] = res.split("<br/>");
            for(int i = 1; i<tiendas.length; i++){
                String tiendaItem[] = tiendas[i].split("%/%");

                String nombre_tienda = tiendaItem[0];
                float lat_tienda = Float.parseFloat(tiendaItem[1]);
                float lon_tienda = Float.parseFloat(tiendaItem[2]);
                LatLng tienda = new LatLng(lat_tienda,lon_tienda);
                map.addMarker(new MarkerOptions().position(tienda)
                        .title(nombre_tienda));
            }

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(España,5));
        }
    }

    public void comenzarLocalizacion() {
        //Obtenemos una referencia al LocationManager
        locManager = (LocationManager) getSystemService(MapaTiendas.this.LOCATION_SERVICE);

        //Nos registramos para recibir actualizaciones de la posición
        locListener = new LocationListener() {
            //Si se cambia de localización se muestra la nueva localización
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                map.setMapType(map.MAP_TYPE_NORMAL);
                map.getUiSettings().setZoomControlsEnabled(true);

                LatLng España = new LatLng(37.5442706,-4.727752799999962);

                map.clear();
                String res = Internet.getTiendasCercanas(""+lat, ""+lon);
                String tiendas[] = res.split("<br/>");
                for(int i = 1; i<tiendas.length; i++){
                    String tiendaItem[] = tiendas[i].split("%/%");

                    String nombre_tienda = tiendaItem[0];
                    float lat_tienda = Float.parseFloat(tiendaItem[1]);
                    float lon_tienda = Float.parseFloat(tiendaItem[2]);
                    LatLng tienda = new LatLng(lat_tienda,lon_tienda);
                    map.addMarker(new MarkerOptions().position(tienda)
                            .title(nombre_tienda));
                }

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(España,5));

                Log.e("LOC","Lat: "+location.getLatitude()+"; Lon: "+location.getLongitude());
            }
            public void onProviderDisabled(String provider) {}
            public void onProviderEnabled(String provider) {}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Refresca las coordenadas cada 100 segundos
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 0, locListener);
    }
}
