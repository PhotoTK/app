package com.cs495.phototk.ui.calculator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.celestial.CelestialActivity;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.management.GearEdit;
import com.cs495.phototk.ui.management.ManagementActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/*READ ME: The following code in onCreate is for the navigation bar. Try not to modify it. In addition, change the activity_calculator_center.xml instead of changing activity_calculator.xml
 */




public class CalculatorActivity extends AppCompatActivity {
    private static final String TAG = "CalculatorActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    TextView Exposure;
    EditText Aperture;
    EditText Speed;
    EditText Iso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        int size = bottomNavigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            bottomNavigationView.getMenu().getItem(i).setCheckable(false);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intent0 = new Intent(CalculatorActivity.this, MainActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(CalculatorActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                        break;

                    case R.id.ic_celestial:
                        Intent intent3 = new Intent(CalculatorActivity.this, CelestialActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                        break;

                    case R.id.ic_map:
                        if(isServicesOK()) {
                            Intent intent4 = new Intent(CalculatorActivity.this, MapsActivity.class);
                            startActivity(intent4);
                            overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                        }
                        break;
                }
                return false;
            }
        });
        button_Calculate();
    }


    public void button_Calculate() {
        Button addGearButton = (Button) findViewById(R.id.btnCalculate);
        addGearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Exposure = (TextView) findViewById(R.id.EV);
                Aperture = (EditText) findViewById(R.id.aperture);
                if (Aperture.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter aperture", Toast.LENGTH_LONG).show();
                    return;
                }
                Speed = (EditText) findViewById(R.id.speed);
                if (Speed.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter speed", Toast.LENGTH_LONG).show();
                    return;
                }
                Iso = (EditText) findViewById(R.id.iso);
                if (Iso.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter iso", Toast.LENGTH_LONG).show();
                    return;
                }

                float N = Float.valueOf(Aperture.getText().toString());
                float t = Float.valueOf(Speed.getText().toString());
                float ISO =Float.valueOf(Iso.getText().toString());
                float EV = (float) ((Math.log(N * N) / Math.log(2)) + (Math.log(1 / t) / Math.log(2)) -
                        (Math.log(ISO / 100) / Math.log(2)));
                String tmp = String.format("EV = %.2f", EV);
                Exposure.setText(tmp);
            }
        });
    }

    private Boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(CalculatorActivity.this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            // Google Play Services is working
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            Log.e(TAG, "isServicesOK: A user resolvable error occurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(CalculatorActivity.this, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Log.e(TAG, "isServicesOK: requests cannot be made");
            Toast.makeText(this, "Map requests cannot be made", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
