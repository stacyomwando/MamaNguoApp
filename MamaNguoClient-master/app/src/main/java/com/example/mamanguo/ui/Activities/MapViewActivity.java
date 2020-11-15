package com.example.mamanguo.ui.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mamanguo.R;
import com.example.mamanguo.chooseServices.helperClasses.Order;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.mamanguo.chooseServices.helperClasses.Order.setLocation;
import static com.example.mamanguo.helpers.Constants.ERROR_DIALOG_REQUEST;
import static com.example.mamanguo.helpers.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.mamanguo.helpers.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapViewActivity";
    private MapView mapView;
    private GoogleMap gmap;
    private EditText searchBar;
    private ImageView mGps;


    private String location;
    private LatLng coordinates;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    //Check the state of the location permission/s
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private static float DEFAULT_ZOOM = 15;
    private final String API_KEY = "";
    private Button btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        initGoogleMap(savedInstanceState);
        searchBar = findViewById(R.id.searchBar);
        mGps = findViewById(R.id.ic_gps);
        btn_checkout = findViewById(R.id.btn_checkout);

        btn_checkout.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
            startActivity(intent);
        });
    }


    public void init() {
        Log.d(TAG, "init: Initializing");

        searchBar.setOnEditorActionListener((v, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                    keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                geoLocate();
            }
            return false;
        });

        mGps.setOnClickListener(v -> {
            Log.d(TAG, "onClick:  clicked gps icon");
            getDeviceLocation();
        });

        hideSoftKeyboard();
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geoLocating");
        String searchString = searchBar.getText().toString();
        Geocoder geocoder = new Geocoder(MapViewActivity.this);

        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, address.toString());
            LatLng coordinates = new LatLng(address.getLatitude(), address.getLongitude());
            String newAddress = address.getAddressLine(0);
            moveCamera(coordinates, DEFAULT_ZOOM, newAddress);

            Order.setLocation(newAddress);
            Order.setLatitude(address.getLatitude());
            Order.setLongitude(address.getLongitude());

            Log.d(TAG, "geoLocate: " + newAddress + ", " + coordinates);
        }
    }

    public String getLocationFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(MapViewActivity.this, Locale.getDefault());
        String newAddress = null;
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, address.toString());
            newAddress = address.getAddressLine(0);

            Order.setLocation(newAddress);
            Order.setLatitude(address.getLatitude());
            Order.setLongitude(address.getLongitude());

            Log.d(TAG, "geoLocate: " + newAddress);
        }
        return newAddress;
    }

    public void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    public void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: Getting the device location");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {

            if (mLocationPermissionGranted) {
                Task location = mFusedLocationClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();

                        if (currentLocation != null) {
                            LatLng coordinates = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            moveCamera(coordinates, DEFAULT_ZOOM, "My Location");
                            String locationName = getLocationFromCoordinates(
                                    currentLocation.getLatitude(),
                                    currentLocation.getLongitude()
                            );

                            //Set this as my location
                            searchBar.setText(MapViewActivity.this.getString(R.string.my_location));
                            setLocation(locationName);

                            Order.setLocation(locationName);
                            Order.setLatitude(currentLocation.getLatitude());
                            Order.setLongitude(currentLocation.getLongitude());

                            Log.d(TAG, "onComplete: Found location " + coordinates);
                        } else {
                            Toast.makeText(MapViewActivity.this, "Finding location...", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.d(TAG, "onComplete: Location is null");
                    }
                });

            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: securityException" + e.getMessage());
        }
    }

    public void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to latitude: " + latLng.latitude + " Longitude: " + latLng.longitude);
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));


        if (!title.equals("My Location")) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng).title(title);
            gmap.addMarker(markerOptions);
        }
        hideSoftKeyboard();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
                //getChatrooms();
            } else {
                getLocationPermission();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        if (mLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            getDeviceLocation();
            googleMap.setMyLocationEnabled(true);
            init();
        }
    }

    /*****Location permissions*****/
    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapViewActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapViewActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    //getChatrooms();
                } else {
                    getLocationPermission();
                }
            }
        }

    }

}
