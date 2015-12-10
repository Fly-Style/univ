package com.univ.java.androidgps.gpslengthcounter.app;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{

    private LocationManager locationManager;
    private LocationListener locationListener;
    private final long time = 10000; // miliseconds;
    private final int distance = 5; // meters;
    private double meters = 0;

    private double lattitude = 0, longtitude = 0;


    private final class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {
            // called when the GPS provider is turned on (user turning on the GPS on the phone)
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // called when the status of the GPS provider changes
        }
    }

    private void updateWithNewLocation(Location location) {
        String latLongString;
        TextView myLocationText;
        TextView myDistanseText;
        myLocationText = (TextView)findViewById(R.id.myLocationText);
        myDistanseText = (TextView)findViewById(R.id.textView);
        if (location != null) {
            System.out.println("YES");
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            if (this.lattitude == 0 && this.longtitude == 0) {
                this.lattitude = lat;
                this.longtitude = lng;
            }
            meters += Math.sqrt((Math.pow(lat - this.lattitude,2)) + (Math.pow(lng - this.longtitude,2)));
            latLongString = "Lat:" + lat + "\nLong:" + lng;
        } else {
            latLongString = "No location found";
        }
        myLocationText.setText("Your Current Position is:\n" +
                latLongString);
        myDistanseText.setText("Distanse : " + meters);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButtonReset = (Button)findViewById(R.id.button);
        myButtonReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                meters = 0;
            }
        });

        String context = Context.LOCATION_SERVICE;
        String provider = LocationManager.NETWORK_PROVIDER;
        locationManager = (LocationManager)getSystemService(context);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String bestProvider = locationManager.getBestProvider(criteria, true);

        locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, time, distance, locationListener); // !!!
        Location location = locationManager.getLastKnownLocation(bestProvider);

        updateWithNewLocation(location);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
