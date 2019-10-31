package com.cs495.phototk.ui.management;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.celestial.CelestialActivity;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
/*READ ME: The following code in onCreate is for the navigation bar. Try not to modify it. In addition, change the activity_Management_center.xml instead of changing activity_Management.xml
 */
public class ManagementActivity extends AppCompatActivity {
    private static final String TAG = "ManagementActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

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
                        Intent intent0 = new Intent(ManagementActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(ManagementActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_celestial:
                        Intent intent3 = new Intent(ManagementActivity.this, CelestialActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_map:
                        if(isServicesOK()) {
                            Intent intent4 = new Intent(ManagementActivity.this, MapsActivity.class);
                            startActivity(intent4);
                        }
                        break;
                }
                return false;
            }
        });
    }

    private Boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ManagementActivity.this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            // Google Play Services is working
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)) {
            Log.e(TAG, "isServicesOK: A user resolvable error occurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ManagementActivity.this, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Log.e(TAG, "isServicesOK: requests cannot be made");
            Toast.makeText(this, "Map requests cannot be made", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
