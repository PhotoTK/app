package com.cs495.phototk.ui.map;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.celestial.CelestialActivity;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*READ ME: The following code in onCreate is for the navigation bar. Try not to modify it. In addition, change the activity_Map_center.xml instead of changing activity_Map.xml
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Constants
    private static final String TAG = "MapsActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int USER_RESOLVABLE_ERROR_CODE = 51;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final float DEFAULT_ZOOM = 18f;

    // Member Variables
    private GoogleMap mMap;
    private Boolean mLocationPermissionsGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private FirebaseUser mCurrentUser;

    // UI Member Variables
    Button mSaveLocationButton;
    Button mClearLocationsButton;

    // Database Member Variables
    DatabaseReference mLocationsDatabase;
    List<MapLocation> mLocationsList;
    ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // initialize activity
        init();

        // Initialize bottom navigation menu
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent0 = new Intent(MapsActivity.this, MainActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(MapsActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.ic_celestial:
                        Intent intent3 = new Intent(MapsActivity.this, CelestialActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

                    case R.id.ic_map:

                        break;
                }
                return false;
            }
        });

        // get location permissions
        getLocationPermissions();

        // only get user's location data if a valid user is signed-in
        if (isUserSignedIn()) {
            Log.d(TAG, "onCreate: User is signed-in, querying location database");
            // attach value event listener to location database reference
            Query locationQuery = mLocationsDatabase.orderByChild("uid").equalTo(mCurrentUser.getUid());
            locationQuery.addListenerForSingleValueEvent(mValueEventListener);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: called");
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Check if GPS is enabled, request user to enable if disabled

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(MapsActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(MapsActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d(TAG, "onSuccess: GPS is enabled");
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(MapsActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: called");
                if (e instanceof ResolvableApiException) {
                    Log.d(TAG, "onFailure: User Resolvable exception encountered");
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MapsActivity.this, USER_RESOLVABLE_ERROR_CODE);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: called");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_RESOLVABLE_ERROR_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: user resolved error");
                getDeviceLocation();
            }
        }
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    mLastKnownLocation = task.getResult();
                    if (mLastKnownLocation != null) {
                        Log.d(TAG, "onComplete: location successfully found");
                        LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                    }
                    else {
                        Log.d(TAG, "onComplete: requesting updated location");
                        final LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000);
                        locationRequest.setFastestInterval(3000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult == null) {
                                    Log.d(TAG, "onLocationResult: cannot update location");
                                    return;
                                }
                                Log.d(TAG, "onLocationResult: location updated");
                                mLastKnownLocation = locationResult.getLastLocation();
                                LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                                mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                            }
                        };
                        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                }
                else {
                    Log.e(TAG, "onComplete: cannot get current location");
                    Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getLocationPermissions() {
        Log.d(TAG, "getLocationPermissions: getting location permissions");
        String[] permissions = {FINE_LOCATION};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
                Log.e(TAG, "getLocationPermissions: permission denied, now requesting permission from user");
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
            else {
                initMap();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionsGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "onRequestPermissionsResult: a permission was denied");
                    mLocationPermissionsGranted = false;
                    return;
                }
            }
            // Permissions granted, initialize map
            Log.d(TAG, "onRequestPermissionsResult: All permissions granted, initializing map");
            mLocationPermissionsGranted = true;
            initMap();
        }
    }

    private void init() {
        // get current user
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        // init ui
        initUI();
        // init listeners
        initOnClickListeners();
        // init database
        initDatabase();
    }

    private void initUI() {
        // Instantiate save and clear buttons
        mSaveLocationButton = (Button) findViewById(R.id.btnSaveLocation);
        mClearLocationsButton = (Button) findViewById(R.id.btnClearLocations);
    }

    private void initOnClickListeners() {
        // save location button click listener
        mSaveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserSignedIn()) {
                    // user is signed-in, save current location
                    Log.d(TAG, "onClick: location saved");
                    saveLocation();
                    Toast.makeText(MapsActivity.this, "Location Saved Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    // no user signed-in, cannot save current location
                    Log.d(TAG, "onClick: cannot save location, no user signed-in");
                    Toast.makeText(MapsActivity.this, "Not Signed-in...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // clear locations button click listener
        mClearLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserSignedIn()) {
                    // user is signed-in, clear locations
                    Log.d(TAG, "onClick: locations cleared");
                    Toast.makeText(MapsActivity.this, "TODO: Clear Locations", Toast.LENGTH_SHORT).show();
                }
                else {
                    // no user signed-in, cannot clear locations
                    Log.d(TAG, "onClick: cannot clear locations, no user signed-in");
                    Toast.makeText(MapsActivity.this, "Not Signed-in...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initDatabase() {
        Log.d(TAG, "initDatabase: called");
        // init database
        mLocationsDatabase = FirebaseDatabase.getInstance().getReference("location");
        // initialize mLocationsList
        mLocationsList = new ArrayList<>();
        // create value event listener
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: called");
                mLocationsList.clear();
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "onDataChange: snapshot exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MapLocation location = snapshot.getValue(MapLocation.class);
                        mLocationsList.add(location);
                    }
                    displayLocationMarkers();
                    Log.d(TAG, "onDataChange: number of locations received in query = " + mLocationsList.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: called");
            }
        };
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
    }

    private Boolean isUserSignedIn() {
        return mCurrentUser != null;
    }

    private void saveLocation() {
        Log.d(TAG, "saveLocation: called");
        // get current user's uid
        String uid = mCurrentUser.getUid();
        // get title and comments
        // TODO: Allow user to set custom title and comments
        String title = "This is a test title";
        String comments = "These are test comments";
        // get current mapLocation
        double lat = mLastKnownLocation.getLatitude();
        double lng = mLastKnownLocation.getLongitude();
        // instantiate a new mapLocation object
        MapLocation location = new MapLocation(uid, title, comments, lat, lng);
        // save new location to database
        String locID = mLocationsDatabase.push().getKey();
        mLocationsDatabase.child(locID).setValue(location);
    }

    private void displayLocationMarkers() {
        for (MapLocation location : mLocationsList) {
            // create LatLng object for each location
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(location.getTitle()).snippet("" + latLng));
        }
    }
}
