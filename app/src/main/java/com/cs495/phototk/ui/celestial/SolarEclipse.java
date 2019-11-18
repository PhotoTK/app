package com.cs495.phototk.ui.celestial;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.R;

public class SolarEclipse extends AppCompatActivity {
    private CheckBox check, check1, check2, check3, check4, check5, check6, check7, check8;
    private SharedPreferences sp, sp1, sp2, sp3, sp4, sp5, sp6, sp7, sp8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solar_eclipse);
        check = findViewById(R.id.btnone);
        check1 = findViewById(R.id.btnone1);
        check2 = findViewById(R.id.btnone2);
        check3 = findViewById(R.id.btnone3);
        check4 = findViewById(R.id.btnone4);
        check5 = findViewById(R.id.btnone5);
        check6 = findViewById(R.id.btnone6);
        check7 = findViewById(R.id.btnone7);
        check8 = findViewById(R.id.btnone8);
        sp = getSharedPreferences("isChecked", 0);
        sp1 = getSharedPreferences("isChecked1", 0);
        sp2 = getSharedPreferences("isChecked2", 0);
        sp3 = getSharedPreferences("isChecked3", 0);
        sp4 = getSharedPreferences("isChecked4", 0);
        sp5 = getSharedPreferences("isChecked5", 0);
        sp6 = getSharedPreferences("isChecked6", 0);
        sp7 = getSharedPreferences("isChecked7", 0);
        sp8 = getSharedPreferences("isChecked8", 0);
        boolean result = sp.getBoolean("choose", false);
        boolean result1 = sp1.getBoolean("choose", false);
        boolean result2 = sp2.getBoolean("choose", false);
        boolean result3 = sp3.getBoolean("choose", false);
        boolean result4 = sp4.getBoolean("choose", false);
        boolean result5 = sp5.getBoolean("choose", false);
        boolean result6 = sp6.getBoolean("choose", false);
        boolean result7 = sp7.getBoolean("choose", false);
        boolean result8 = sp8.getBoolean("choose", false);
        check.setChecked(result);
        check1.setChecked(result1);
        check2.setChecked(result2);
        check3.setChecked(result3);
        check4.setChecked(result4);
        check5.setChecked(result5);
        check6.setChecked(result6);
        check7.setChecked(result7);
        check8.setChecked(result8);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //boolean ischecked = check.isChecked();
                if (isChecked) {
                    check.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp = getSharedPreferences("isChecked", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp = getSharedPreferences("isChecked", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check1.isChecked()) {
                    check1.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp1 = getSharedPreferences("isChecked1", 0);
                    SharedPreferences.Editor edit = sp1.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check1.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp1 = getSharedPreferences("isChecked1", 0);
                    SharedPreferences.Editor edit = sp1.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check2.isChecked()) {
                    check2.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp2 = getSharedPreferences("isChecked2", 0);
                    SharedPreferences.Editor edit = sp2.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check2.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp2 = getSharedPreferences("isChecked2", 0);
                    SharedPreferences.Editor edit = sp2.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check3.isChecked()) {
                    check3.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp3 = getSharedPreferences("isChecked3", 0);
                    SharedPreferences.Editor edit = sp3.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check3.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp3 = getSharedPreferences("isChecked3", 0);
                    SharedPreferences.Editor edit = sp3.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check4.isChecked()) {
                    check4.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp3 = getSharedPreferences("isChecked4", 0);
                    SharedPreferences.Editor edit = sp4.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check4.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp4 = getSharedPreferences("isChecked4", 0);
                    SharedPreferences.Editor edit = sp4.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check5.isChecked()) {
                    check5.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp5 = getSharedPreferences("isChecked5", 0);
                    SharedPreferences.Editor edit = sp5.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check5.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp5 = getSharedPreferences("isChecked5", 0);
                    SharedPreferences.Editor edit = sp5.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check6.isChecked()) {
                    check6.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp3 = getSharedPreferences("isChecked6", 0);
                    SharedPreferences.Editor edit = sp6.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check6.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp6 = getSharedPreferences("isChecked6", 0);
                    SharedPreferences.Editor edit = sp6.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check7.isChecked()) {
                    check7.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp7 = getSharedPreferences("isChecked7", 0);
                    SharedPreferences.Editor edit = sp7.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check7.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp7 = getSharedPreferences("isChecked7", 0);
                    SharedPreferences.Editor edit = sp7.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check8.isChecked()) {
                    check8.setText("Selected");
                    Toast.makeText(SolarEclipse.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp8 = getSharedPreferences("isChecked8", 0);
                    SharedPreferences.Editor edit = sp8.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check8.setText("No Selected");
                    Toast.makeText(SolarEclipse.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp8 = getSharedPreferences("isChecked8", 0);
                    SharedPreferences.Editor edit = sp8.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });
    }
}



