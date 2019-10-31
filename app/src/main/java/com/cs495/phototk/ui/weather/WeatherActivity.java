package com.cs495.phototk.ui.weather;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.calculator.CalculatorActivity;
import com.cs495.phototk.ui.celestial.CelestialActivity;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.login.LoginActivity;
import com.cs495.phototk.ui.management.ManagementActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "ManagementActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        BottomNavigationView topNavigationView = (BottomNavigationView) findViewById(R.id.topNavView_Bar);
        Menu menu = topNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        topNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_calculator:
                        Intent intent3 = new Intent(WeatherActivity.this, CalculatorActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_login:
                        Intent intent2 = new Intent(WeatherActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_management:
                        Intent intent4 = new Intent(WeatherActivity.this, ManagementActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.ic_weather:

                        break;
                }
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        int size = bottomNavigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            bottomNavigationView.getMenu().getItem(i).setCheckable(false);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent0 = new Intent(WeatherActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(WeatherActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_celestial:
                        Intent intent3 = new Intent(WeatherActivity.this, CelestialActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_map:
                        if(isServicesOK()) {
                            Intent intent4 = new Intent(WeatherActivity.this, MapsActivity.class);
                            startActivity(intent4);
                        }
                        break;
                }
                return false;
            }
        });
    }

    private Boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(WeatherActivity.this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            // Google Play Services is working
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            Log.e(TAG, "isServicesOK: A user resolvable error occurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(WeatherActivity.this, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Log.e(TAG, "isServicesOK: requests cannot be made");
            Toast.makeText(this, "Map requests cannot be made", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
