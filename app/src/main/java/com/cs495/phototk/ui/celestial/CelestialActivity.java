package com.cs495.phototk.ui.celestial;

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
import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.calculator.CalculatorActivity;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.login.LoginActivity;
import com.cs495.phototk.ui.management.ManagementActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.cs495.phototk.ui.weather.WeatherActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
/*READ ME: The following code in onCreate is for the navigation bar. Try not to modify it. In addition, change the activity_celestial_center.xml instead of changing activity_celestial.xml
 */
public class CelestialActivity extends AppCompatActivity {
    private Button btnSolar_Eclipse;
    private Button btnMoon_Eclipse;
    private Button btnMeteor_Shower;
    private static final String TAG = "CelestialActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celestial);

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
                        Intent intent1 = new Intent(CelestialActivity.this, CalculatorActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_login:
                        Intent intent2 = new Intent(CelestialActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_management:
                        Intent intent3 = new Intent(CelestialActivity.this, ManagementActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_weather:
                            Intent intent4 = new Intent(CelestialActivity.this, WeatherActivity.class);
                            startActivity(intent4);
                        break;
                }
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent0 = new Intent(CelestialActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(CelestialActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_celestial:

                        break;

                    case R.id.ic_map:
                        if(isServicesOK()) {
                            Intent intent4 = new Intent(CelestialActivity.this, MapsActivity.class);
                            startActivity(intent4);
                        }
                        break;

                }
                return false;
            }
        });

        Button btnSolar_Eclipse = findViewById(R.id.btnSolar_Eclipse);
        btnSolar_Eclipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CelestialActivity.this, SolarEclipse.class);
                startActivity(intent);
            }
        });
        buttonMoon_Eclipse();
        buttonMeteor_shower();
    }

    private void buttonMoon_Eclipse() {
        Log.d(TAG, "buttonMoon_Eclipse: called");
        Button btnMoon_Eclipse = findViewById(R.id.btnMoon_Eclipse);
        btnMoon_Eclipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CelestialActivity.this, MoonEclipse.class);
                startActivity(intent);
            }
        });
    }

    private void buttonMeteor_shower() {
        Log.d(TAG, "buttonMeteor_shower: called");
        Button btnMeteor_Shower = findViewById(R.id.btnMeteor_Shower);
        btnMeteor_Shower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CelestialActivity.this, MeteorShower.class);
                startActivity(intent);
            }
        });
    }
    private Boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(CelestialActivity.this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            // Google Play Services is working
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            Log.e(TAG, "isServicesOK: A user resolvable error occurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(CelestialActivity.this, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Log.e(TAG, "isServicesOK: requests cannot be made");
            Toast.makeText(this, "Map requests cannot be made", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
