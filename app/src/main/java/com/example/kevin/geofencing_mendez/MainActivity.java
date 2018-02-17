package com.example.kevin.geofencing_mendez;

import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private TextView tv_latitud, tv_longitud;
    private MapFragment mapFragment;
    private GoogleMap map;
    private Button get_location_button;

    private static final String GEOFENCE_REQUQEST_ID ="Geofence";
    private static final float GEOFENCE_RADIUS = 400.0f;
    private static  final long GEOFENCE_DURATION = 60 * 60 * 1000;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_latitud = (TextView) findViewById(R.id.tv_lat);
        tv_longitud = (TextView) findViewById(R.id.tv_long);
        get_location_button = (Button) findViewById(R.id.get_location);

        initMap();

        get_location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_location();
            }
        });


    }

    private void initMap() {
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    private void get_location(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitud = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitud,longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<android.location.Address> addressList = geocoder.getFromLocation(latitud,longitude,1);
                        String str = addressList.get(0).getLocality() + ", ";
                        str += addressList.get(0).getCountryName();
                        map.addMarker(new MarkerOptions().position(latLng).title(str));
                       // map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });

        }else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitud = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitud,longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<android.location.Address> addressList = geocoder.getFromLocation(latitud,longitude,1);
                        String str = addressList.get(0).getLocality() + ", ";
                        str += addressList.get(0).getCountryName();
                        map.addMarker(new MarkerOptions().position(latLng).title(str));
                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });

        }

    }
    private Geofence createGeofence(LatLng latLng, float radius){
        Log.d("GEOFENCE","geofence created");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQUQEST_ID)
                .setCircularRegion(latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration(GEOFENCE_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();

    }
    private GeofencingRequest createGeofenceRequest(Geofence geofence){
                GeofencingRequest request = new GeofencingRequest.Builder().
                        setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER).
                        addGeofence(geofence).build();
                return   request;
    }




    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getApplicationContext(),latLng.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getApplicationContext(),marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
//        LatLng oulu = new LatLng(64,25);
//        map.addMarker(new MarkerOptions().position(oulu).title("Marker in Oulu"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(oulu));
//        map.setOnMapClickListener(this);
//        map.setOnMarkerClickListener(this);





    }
}
