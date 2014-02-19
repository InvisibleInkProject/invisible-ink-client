package no.invisibleink.core.location;

import no.invisibleink.helper.NoLocationException;
import no.invisibleink.view.MainActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

public class LocationManager implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {

	private static final String LOG = "LocationManager";
	
    // A request to connect to Location Services
    private LocationRequest mLocationRequest;

    // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient;	

    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;

    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;

    /*
     * Note if updates have been turned on. Starts out as "false"; is set to "true" in the
     * method handleRequestSuccess of LocationUpdateReceiver.
     *
     */
    boolean mUpdatesRequested = false;   
    
    MainActivity activity;
    
    
	public LocationManager(MainActivity activity) {
		this.activity = activity;
	}
	
	public void onCreate() {
        // Create a new global location parameters object
        mLocationRequest = LocationRequest.create();

        /*
         * Set the update interval
         */
        mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the interval ceiling to one minute
        mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        // Note that location updates are off until the user turns them on
        mUpdatesRequested = false;

        // Open Shared Preferences
        mPrefs = activity.getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // Get an editor
        mEditor = mPrefs.edit();

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(activity, this, this);		
	}
	
	public void onStop() {
        // If the client is connected
        if (mLocationClient.isConnected()) {
            stopPeriodicUpdates();
            saveCurrentLocation();
        }

        // After disconnect() is called, the client is considered "dead".
        mLocationClient.disconnect();	
	}
	
	public void onPause() {
        // Save the current setting for updates
        mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, mUpdatesRequested);
        mEditor.commit();
	}
	
	public void onStart() {
        /*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
        mLocationClient.connect();
	}
	
	public void onResume() {
        // If the app already has a setting for getting location updates, get it
        if (mPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)) {
            mUpdatesRequested = mPrefs.getBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);

        // Otherwise, turn off location updates until requested
        } else {
            mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
            mEditor.commit();
        }		
	}
	
    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        activity,
                        LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */

            } catch (IntentSender.SendIntentException e) {

                // Log the error
                e.printStackTrace();
            }
        } else {

            // If no resolution is available, display a dialog to the user with the error.
// TODO:
//            showErrorDialog(connectionResult.getErrorCode());
        }
		
	}

    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */	
	@Override
	public void onConnected(Bundle bundle) {
// TODO:		
//        mConnectionStatus.setText(R.string.connected);

        if (mUpdatesRequested) {
            startPeriodicUpdates();
        }
	}

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */	
	@Override
	public void onDisconnected() {
// TODO:		
//        mConnectionStatus.setText(R.string.disconnected);
	}

    /**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
// TODO:
//            Log.d(LocationUtils.APPTAG, getString(R.string.play_services_available));

            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, activity, 0);
            if (dialog != null) {
// TODO:            	
//                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
//                errorFragment.setDialog(dialog);
//                errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
            }
            return false;
        }
    }	
	
    /**
     * Invoked by the "Start Updates" button
     * Sends a request to start location updates
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    public void startUpdates() {
        mUpdatesRequested = true;

        if (servicesConnected()) {
            startPeriodicUpdates();
        }
    }

    /**
     * Invoked by the "Stop Updates" button
     * Sends a request to remove location updates
     * request them.
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    public void stopUpdates() {
        mUpdatesRequested = false;

        if (servicesConnected()) {
            stopPeriodicUpdates();
        }
        
    }
    
    /**
     * In response to a request to start updates, send a request
     * to Location Services
     */
    private void startPeriodicUpdates() {
        mLocationClient.requestLocationUpdates(mLocationRequest, activity);
    }

    /**
     * In response to a request to stop updates, send a request to
     * Location Services
     */
    private void stopPeriodicUpdates() {
        mLocationClient.removeLocationUpdates(activity);
    }
    
    /**
     * Calls getLastLocation() to get the current location
     * 
     * @return last location or null
     * @throws Exception 
     */
    public Location getLocation() throws NoLocationException {
    	if (servicesConnected() && mLocationClient.isConnected()) {
    		return mLocationClient.getLastLocation();
    	} else {
    		throw new NoLocationException();
    	}
    }
    
	
	private void saveCurrentLocation() {
        try {
        	mEditor.putLong(LocationUtils.KEY_LAST_LATIDUTE, Double.doubleToRawLongBits(getLocation().getLatitude()));
        	mEditor.putLong(LocationUtils.KEY_LAST_LONGITUDE, Double.doubleToRawLongBits(getLocation().getLongitude()));
        	mEditor.commit();
        	Log.d(LOG, "Save location in SharedPreferences");
		} catch (NoLocationException e) {
			Log.w(LOG, "Coudn't save location in SharedPreferences");
		}		
	}
	
	public Location getSavedLocation() throws NoLocationException {
		Double lastLocationLat = Double.longBitsToDouble(mPrefs.getLong(LocationUtils.KEY_LAST_LATIDUTE, Double.doubleToLongBits(0)));
		Double lastLocationLon = Double.longBitsToDouble(mPrefs.getLong(LocationUtils.KEY_LAST_LATIDUTE, Double.doubleToLongBits(0)));
		if (lastLocationLat != Double.NaN && lastLocationLon != Double.NaN) {
			Location lastLoc = new Location("");
    		lastLoc.setLatitude(lastLocationLat);
    		lastLoc.setLongitude(lastLocationLon);
    		return lastLoc;
		} else {
			throw new NoLocationException();
		}
	}
	
}
