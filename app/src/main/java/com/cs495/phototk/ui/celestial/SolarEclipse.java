package com.cs495.phototk.ui.celestial;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.R;

public class SolarEclipse extends AppCompatActivity {
    private CheckBox check;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solar_eclipse);
        check = findViewById(R.id.btnone);
        sp = getSharedPreferences("isChecked", 0);
        boolean result = sp.getBoolean("choose", false);
        check.setChecked(result);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean ischecked = check.isChecked();
                if (isChecked) {
                    check.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp = getSharedPreferences("isChecked", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                }
                else {
                    check.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp = getSharedPreferences("isChecked", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });
    }
}



