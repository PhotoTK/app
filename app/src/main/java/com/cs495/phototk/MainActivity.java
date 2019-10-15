package com.cs495.phototk;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    private Button btnEXIF;

    // Constants
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isServicesOK()) {
            init();
        }
        // exif botton
        Button btnEXIF = (Button) findViewById(R.id.btnEXIF);
        btnEXIF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEXIF();
            }
        });

        initWeatherButton();
        initManagementButton();
        initClestialButton();

    }

    public void openEXIF() {
        Intent intent = new Intent(this,EXIFActivity.class);
        startActivity(intent);
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

    private void init() {
        Log.d(TAG, "init: called");
        initMapButton();
        // initWeatherButton(); // TODO: uncomment when weather activity is ready
    }

    private void initMapButton() {
        Log.d(TAG, "initMapButton: called");
        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initWeatherButton() {
        Log.d(TAG, "initWeatherButton: called");
        Button btnWeather = findViewById(R.id.btnWeather);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initManagementButton() {
        Log.d(TAG, "initManagementButton: called");
        Button btnManagement = findViewById(R.id.btnManagement);
        btnManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initClestialButton() {
        Log.d(TAG, "initClestialButton: called");
        Button btnCelestial = findViewById(R.id.btnCelestial);
        btnCelestial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CelestialActivity.class);
                startActivity(intent);
            }
        });
    }

}
