package com.cs495.phototk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CelestialActivity extends AppCompatActivity {
    private Button btnSolar_Eclipse;
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
                Intent intent = new Intent(CelestialActivity.this,SolarEclipse.class);
                startActivity(intent);
            }
        });
    }
}
