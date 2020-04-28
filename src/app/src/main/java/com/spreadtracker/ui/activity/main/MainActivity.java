package com.spreadtracker.ui.activity.main;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.spreadtracker.App;
import com.spreadtracker.R;
import com.spreadtracker.contactstracing.Calculator;
import com.spreadtracker.contactstracing.Database;

import java.io.File;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private NavController mNavigation;
    private CoordinatorLayout mLayout;

    /**
     * The {@link GoogleMap} object presenting this fragment's map
     */
    private GoogleMap mMap;
    /**
     * The actual View that represents the {@link #mMap} object.
     */
    private MapView mMapView;

    private FusedLocationProviderClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMap(savedInstanceState);
        mLayout = findViewById(R.id.activity_main_layout);
    }

    public NavController getNav() {
        if (mNavigation != null) return mNavigation;
        NavHostFragment navFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_navHostFragment);
        mNavigation = navFragment.getNavController();
        return mNavigation;
    }

    public CoordinatorLayout getLayout() { return mLayout; }

    /**
     * Initializes the Google Maps API and the map view in this fragment,
     * and schedules an asynchronous callback, {@link #onMapReady(GoogleMap)}, to occur when the map has finished loading.
     * @param savedInstanceState If non-null, represents the saved instance state when this fragment is reconstructed.
     */
    private void initializeMap (@Nullable Bundle savedInstanceState) {
        mMapView = findViewById(R.id.activity_main_mapView);

        // Initialize maps API
        try {
            MapsInitializer.initialize(App.getInstance().getApplicationContext());
            Database database = new Database(new File(App.getInstance().getApplicationContext().getFilesDir(), "tracker.sqlite"));
            Calculator calculator = new Calculator(database);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

    /**
     * Called when the Google Map view, {@link #mMapView}, of this fragment has finished loading
     * and is actually displaying the Google Map.
     * @param googleMap A {@link GoogleMap} object that represents the map that just finished loading.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable the re-align north compass as it obstructs the info button
        mMap.getUiSettings().setCompassEnabled(false);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        zoomToCurrentLocation();
    }

    private void zoomToCurrentLocation () {
        if (mLocationClient == null) mLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                }
            }
        });
    }

    /*
     * Overridden activity lifecycle methods
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) mMapView.onLowMemory();
    }
}
