package com.cs495.phototk.ui.management;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import android.app.AlertDialog;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.parceler.Parcels;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.celestial.CelestialActivity;
import com.cs495.phototk.ui.exif.EXIFActivity;
import com.cs495.phototk.ui.map.MapsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class ManagementActivity extends AppCompatActivity {
    private static final String TAG = "ManagementActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private DatabaseReference gearsRef;
    private List<Gears> listGear = new ArrayList<>();
    private GearListAdapter gearListAdapter;
    private ListView listView;


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
                        overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                        break;

                    case R.id.ic_exif:
                        Intent intent1 = new Intent(ManagementActivity.this, EXIFActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                        break;

                    case R.id.ic_celestial:
                        Intent intent3 = new Intent(ManagementActivity.this, CelestialActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                        break;

                    case R.id.ic_map:
                        if(isServicesOK()) {
                            Intent intent4 = new Intent(ManagementActivity.this, MapsActivity.class);
                            startActivity(intent4);
                            overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
                        }
                        break;
                }
                return false;
            }
        });

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        gearsRef = rootRef.child("gears");

        button_AddGear();
        emptyUtility();
        addChildEventListener();
        setListViewItemListener();
        setListViewLongClickListener();

    }


    public void button_AddGear() {
        Button addGearButton = (Button) findViewById(R.id.button_AddGear);
        addGearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(ManagementActivity.this, GearEdit.class);
                startActivity(inte);
                overridePendingTransition(R.anim.slide_up, R.anim.no_anim);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void emptyUtility() {
        gearListAdapter = new GearListAdapter(this, listGear);
        listView = (ListView) findViewById(R.id.list_view_gears);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        listView.setAdapter(gearListAdapter);
       // emptyView.setVisibility(View.VISIBLE);

    }

    private void addChildEventListener() {
        gearsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               Gears gears = dataSnapshot.getValue(Gears.class);
                if(gears != null){
                    gears.setKey(dataSnapshot.getKey());
                    listGear.add(dataSnapshot.getValue(Gears.class));
                    gearListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Gears gears = dataSnapshot.getValue(Gears.class);
                if(gears != null){
                    String key = dataSnapshot.getKey();
                    for(int x=0;x<listGear.size();x++){
                        Gears gears1 = listGear.get(x);
                        if(gears1.getKey().equals(key)){
                            listGear.set(x, gears);
                            gearListAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
              listGear.remove(dataSnapshot.getValue(Gears.class));
              gearListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    private void setListViewItemListener(){
        listView.setOnItemClickListener((adapterView, view, x, l) -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", true);

            bundle.putParcelable("gears", listGear.get(x));
            Intent intent = new Intent(this, GearEdit.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }


    private void setListViewLongClickListener(){
        listView.setOnItemLongClickListener((adapterView, view, x, l) -> {
            Gears gears = listGear.get(x);
            new AlertDialog.Builder(this)
                    .setTitle("Delete " + gears.getGearName())
                    .setMessage("Delete the selected gear?")
                    .setPositiveButton("Delete", (dialogInterface, i1) -> {
                        gearsRef.child(gears.getKey()).removeValue();
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i12) -> {
                        dialogInterface.dismiss();
                    })
                    .create()
                    .show();
            return true;
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
