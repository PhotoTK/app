package com.cs495.phototk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

    public class MoonEclipse extends AppCompatActivity {

        private CheckBox check;
        //private CheckBox check1;
        private SharedPreferences sp;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.moon_eclipse);
            check = (CheckBox) findViewById(R.id.btntwo);
            sp = getSharedPreferences("isChecked", 0);
            boolean result = sp.getBoolean("choose", false);
            check.setChecked(result);
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    boolean ischecked = check.isChecked();
                    if (isChecked) {
                        check.setText("Selected");
                        Toast.makeText(MoonEclipse.this, "Selected", 2).show();
                        sp = getSharedPreferences("isChecked", 0);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("choose", true);
                        edit.apply();
                    } else {
                        check.setText("No Selected");
                        Toast.makeText(MoonEclipse.this, "No Selected", 2).show();
                        sp = getSharedPreferences("isChecked", 0);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("choose", false);
                        edit.apply();
                    }
                }
            });
        }
}
