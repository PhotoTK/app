package com.cs495.phototk.ui.celestial;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.R;

public class MeteorShower extends AppCompatActivity {
    private CheckBox check, check1, check2, check3;
    private SharedPreferences sp, sp1, sp2, sp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meteor_shower);
        check = findViewById(R.id.btnthree);
        check1 = findViewById(R.id.btnthree1);
        check2 = findViewById(R.id.btnthree2);
        check3 = findViewById(R.id.btnthree3);
        sp = getSharedPreferences("isCheckedThree", 0);
        sp1 = getSharedPreferences("isCheckedThree1", 0);
        sp2 = getSharedPreferences("isCheckedThree2", 0);
        sp3 = getSharedPreferences("isCheckedThree3", 0);
        boolean result = sp.getBoolean("choose", false);
        boolean result1 = sp1.getBoolean("choose", false);
        boolean result2 = sp2.getBoolean("choose", false);
        boolean result3 = sp3.getBoolean("choose", false);
        check.setChecked(result);
        check1.setChecked(result1);
        check2.setChecked(result2);
        check3.setChecked(result3);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check.setText("Selected");
                    Toast.makeText(MeteorShower.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp = getSharedPreferences("isCheckedThree", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check.setText("No Selected");
                    Toast.makeText(MeteorShower.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp = getSharedPreferences("isCheckedThree", 0);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check1.setText("Selected");
                    Toast.makeText(MeteorShower.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp1 = getSharedPreferences("isCheckedThree1", 0);
                    SharedPreferences.Editor edit = sp1.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check1.setText("No Selected");
                    Toast.makeText(MeteorShower.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp1 = getSharedPreferences("isCheckedThree1", 0);
                    SharedPreferences.Editor edit = sp1.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check2.setText("Selected");
                    Toast.makeText(MeteorShower.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp2 = getSharedPreferences("isCheckedThree2", 0);
                    SharedPreferences.Editor edit = sp2.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check2.setText("No Selected");
                    Toast.makeText(MeteorShower.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp2 = getSharedPreferences("isCheckedThree2", 0);
                    SharedPreferences.Editor edit = sp2.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });

        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check3.setText("Selected");
                    Toast.makeText(MeteorShower.this, "Selected", Toast.LENGTH_SHORT).show();
                    sp3 = getSharedPreferences("isCheckedThree3", 0);
                    SharedPreferences.Editor edit = sp3.edit();
                    edit.putBoolean("choose", true);
                    edit.apply();
                } else {
                    check3.setText("No Selected");
                    Toast.makeText(MeteorShower.this, "No Selected", Toast.LENGTH_SHORT).show();
                    sp3 = getSharedPreferences("isCheckedThree3", 0);
                    SharedPreferences.Editor edit = sp3.edit();
                    edit.putBoolean("choose", false);
                    edit.apply();
                }
            }
        });
    }
}
