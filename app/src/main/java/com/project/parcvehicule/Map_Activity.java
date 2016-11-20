package com.project.parcvehicule;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Activity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private MarkerOptions marker_item = new MarkerOptions();
    private GoogleMap map;
    private Intent intent_recu;
    private double lattitude, longitude;
    private String name_objet;
    private boolean gps_enabled, wifi_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_);

        intent_recu = getIntent();
        Bundle extras = getIntent().getExtras();
        lattitude = extras.getDouble("lattitude");
        longitude = extras.getDouble("longitude");
        name_objet = extras.getString("objet");
        System.out.println("data recu map 1 :" + lattitude);
        System.out.println("data recu map 2:" + longitude);
        System.out.println("data recu map 3:" + name_objet);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        map = mapFragment.getMap();

        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        wifi_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

      //  map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
      //  lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);

        if (wifi_enabled) {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        }


        if (gps_enabled) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);


            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {

            } else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {

            } else {
           //     Toast.makeText(Map_Activity.this, "Pas de connection a internet", Toast.LENGTH_LONG).show();

            }


        } else

        {
         //   Toast.makeText(Map_Activity.this,"Impossible de détecter vos emplacement courant veuillez activer GPS....", Toast.LENGTH_LONG).show();


        }

      /*  marker_item.position(new LatLng(lattitude, longitude));
        marker_item.title(name_objet)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_map_mission));
        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker_item);*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng pos_mission = new LatLng(lattitude, longitude);
       // LatLng sydney = new LatLng(35.857200, 10.595476);

       // googleMap.addMarker(new MarkerOptions()
            //    .title(name_objet)
             //   .position(pos_mission));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos_mission, 13));

        marker_item.position(new LatLng(lattitude, longitude));
        marker_item.title(name_objet)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_map_mission));
        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        map.addMarker(marker_item);

    }


    @Override
    public void onLocationChanged(Location location) {

        if(location==null) {

            MarkerOptions mp = new MarkerOptions();
            mp.visible(true);
            System.out.println("latitude="+location.getLatitude());
            System.out.println("longitude="+location.getLongitude());
            mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
            mp.title("Ma position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            map.addMarker(mp);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 12));

        }
        else
        {
            float[] last_new_location = new float[3];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                    location.getLatitude(), location.getLongitude(), last_new_location);
            double latnewdifference = (last_new_location[0] * 0.000621);

            if (latnewdifference > 0.1) {

                map.clear();




                    marker_item.position(new LatLng(lattitude, longitude));
                    marker_item.title(name_objet)
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.icon_map_mission));
                    //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    map.addMarker(marker_item);
                }

            }
        }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        if ("gps".equals(provider)) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.removeUpdates(this);

        }

    }
}
