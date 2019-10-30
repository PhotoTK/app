package com.cs495.phototk.ui.celestial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CelestialActivity extends AppCompatActivity {
    private Button btnSolar_Eclipse;
    private Button btnMoon_Eclipse;
    private Button btnMeteor_Shower;
    private static final String TAG = "CelestialActivity";

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
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(CelestialActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_celestial:

                        break;

                    case R.id.ic_map:
                        Intent intent4 = new Intent(CelestialActivity.this, MapsActivity.class);
                        startActivity(intent4);
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

}
