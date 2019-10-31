package com.cs495.phototk.ui.calculator;

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
import com.cs495.phototk.ui.celestial.CelestialActivity;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.login.LoginActivity;
import com.cs495.phototk.ui.management.ManagementActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.cs495.phototk.ui.weather.WeatherActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalculatorActivity extends AppCompatActivity {
    private static final String TAG = "CalculatorActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        BottomNavigationView topNavigationView = (BottomNavigationView) findViewById(R.id.topNavView_Bar);
        Menu menu = topNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        topNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_calculator:

                        break;

                    case R.id.ic_login:
                        Intent intent2 = new Intent(CalculatorActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_management:
                        Intent intent3 = new Intent(CalculatorActivity.this, ManagementActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_weather:
                        Intent intent4 = new Intent(CalculatorActivity.this, WeatherActivity.class);
                        startActivity(intent4);
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
                        Intent intent0 = new Intent(CalculatorActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(CalculatorActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_celestial:
                        Intent intent3 = new Intent(CalculatorActivity.this, CelestialActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_map:
                        if(isServicesOK()) {
                            Intent intent4 = new Intent(CalculatorActivity.this, MapsActivity.class);
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
        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(CalculatorActivity.this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            // Google Play Services is working
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            Log.e(TAG, "isServicesOK: A user resolvable error occurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(CalculatorActivity.this, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Log.e(TAG, "isServicesOK: requests cannot be made");
            Toast.makeText(this, "Map requests cannot be made", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
