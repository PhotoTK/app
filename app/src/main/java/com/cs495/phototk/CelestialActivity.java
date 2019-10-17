package com.cs495.phototk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
