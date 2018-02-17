package com.example.kevin.geofencing_mendez;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by Kevin Mendez on 2/17/2018.
 */

public class Constants {
    public static final String GEOFENCE_REQUQEST_ID ="Geofence";
    public static final float GEOFENCE_RADIUS = 400.0f;
    public static  final long GEOFENCE_DURATION = 60 * 60 * 1000;
    public static final int UPDATE_INTERVAL =  1000;
    public static final int FASTEST_INTERVAL = 900;
    public static final HashMap<String, LatLng> AREA_LANDMARKS = new HashMap<String, LatLng>();


    static {
        // stanford university.
        AREA_LANDMARKS.put(GEOFENCE_REQUQEST_ID, new LatLng(37.427476, -122.170262));
    }


//        public static final String GEOFENCE_ID_STAN_UNI = "STAN_UNI";
//        public static final float GEOFENCE_RADIUS_IN_METERS = 100;

        /**
         * Map for storing information about stanford university in the Stanford.
         */

}
