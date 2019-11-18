package com.cs495.phototk.ui.celestial;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
/*READ ME: The following code in onCreate is for the navigation bar. Try not to modify it. In addition, change the activity_celestial_center.xml instead of changing activity_celestial.xml
 */
public class CelestialActivity extends AppCompatActivity {
    private ImageView btnSolar_Eclipse;
    private ImageView btnMoon_Eclipse;
    private ImageView btnMeteor_Shower;
    private static final String TAG = "CelestialActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celestial);


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
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(CelestialActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;

                    case R.id.ic_celestial:
                        break;

                    case R.id.ic_map:
                        if(isServicesOK()) {
                            Intent intent4 = new Intent(CelestialActivity.this, MapsActivity.class);
                            startActivity(intent4);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                        break;

                }
                return false;
            }
        });

        ImageView btnSolar_Eclipse = findViewById(R.id.btnSolar_Eclipse);
        btnSolar_Eclipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CelestialActivity.this, SolarEclipse.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
            }
        });
        buttonMoon_Eclipse();
        buttonMeteor_shower();
        buttonSearch_Celestial();
    }

    private void buttonMoon_Eclipse() {
        Log.d(TAG, "buttonMoon_Eclipse: called");
        ImageView btnMoon_Eclipse = findViewById(R.id.btnMoon_Eclipse);
        btnMoon_Eclipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CelestialActivity.this, MoonEclipse.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
            }
        });
    }

    private void buttonMeteor_shower() {
        Log.d(TAG, "buttonMeteor_shower: called");
        ImageView btnMeteor_Shower = findViewById(R.id.btnMeteor_Shower);
        btnMeteor_Shower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CelestialActivity.this, MeteorShower.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
            }
        });
    }

    private void buttonSearch_Celestial() {
        Log.d(TAG, "buttonSearch_Celestial: called");
        ImageView btnSearch_Celestial = findViewById(R.id.btnSearch_Celestial);
        btnSearch_Celestial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CelestialActivity.this, SearchCelestial.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
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
