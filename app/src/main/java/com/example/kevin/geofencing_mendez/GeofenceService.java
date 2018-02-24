package com.example.kevin.geofencing_mendez;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by kevin on 2/17/2018.
 */

public class GeofenceService extends IntentService {
    public static final String TAG ="GeofenceService";
    public GeofenceService() {
        super(TAG);
    }

    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "GeofencingEvent error " + geofencingEvent.getErrorCode());
        }else{
            Log.d("GEOFENCE", "GeofencingEvent started " + geofencingEvent.getErrorCode());
            int transaction = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_REQUQEST_ID)) {
                Log.d(TAG, "You are inside Peltola");
            } else {
                Log.d(TAG, "You are outside Peltola");
            }
        }
    }
}
