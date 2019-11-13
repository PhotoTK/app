package com.cs495.phototk.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs495.phototk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = "AccountActivity";

    // Member Variables
    EditText mFirstName, mLastName;
    Button btnCancel, btnSubmit;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // initialize the activity
        init();
    }

    private void init() {
        Log.d(TAG, "init: called");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        initUI();
    }

    private void initUI() {
        Log.d(TAG, "initUI: called");
        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        btnCancel = (Button) findViewById(R.id.cancel_button);
        btnSubmit = (Button) findViewById(R.id.submit_button);
        initOnClickListeners();
    }

    private void initOnClickListeners() {
        Log.d(TAG, "initOnClickListeners: called");

        // btnCancel OnClickListener
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        // btnSubmit OnClickListener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: implement btnSubmit OnClickListener
                Toast.makeText(AccountActivity.this, "Updated Account Information", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
