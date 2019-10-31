package com.cs495.phototk;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.cs495.phototk.ui.calculator.CalculatorActivity;
import com.cs495.phototk.ui.celestial.CelestialActivity;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.login.LoginActivity;
import com.cs495.phototk.ui.management.ManagementActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.cs495.phototk.ui.weather.WeatherActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
        private static final String TAG = "MainActivity";
        private static final int ERROR_DIALOG_REQUEST = 9001;
        Button dark, light;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView topNavigationView = (BottomNavigationView) findViewById(R.id.topNavView_Bar);
        int size = topNavigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            topNavigationView.getMenu().getItem(i).setCheckable(false);
        }
        topNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_calculator:
                        Intent intent1 = new Intent(MainActivity.this, CalculatorActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_login:
                        Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_management:
                        Intent intent3 = new Intent(MainActivity.this, ManagementActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_weather:
                            Intent intent4 = new Intent(MainActivity.this, WeatherActivity.class);
                            startActivity(intent4);
                        break;
                }
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:

                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(MainActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_celestial:
                        Intent intent3 = new Intent(MainActivity.this, CelestialActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_map:
                        if(isServicesOK()) {
                            Intent intent4 = new Intent(MainActivity.this, MapsActivity.class);
                            startActivity(intent4);
                        }
                        break;
                }
                return false;
            }
        });


        dark = findViewById(R.id.dark);
        light = findViewById(R.id.light);
        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    private Boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            // Google Play Services is working
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            Log.e(TAG, "isServicesOK: A user resolvable error occurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Log.e(TAG, "isServicesOK: requests cannot be made");
            Toast.makeText(this, "Map requests cannot be made", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
