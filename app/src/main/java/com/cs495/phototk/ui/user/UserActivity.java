package com.cs495.phototk.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.cs495.phototk.ui.weather.Model.Main;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = "UserActivity";

    // Member Variables
    private TextView mWhoSignedInTextView;
    private Button btnProfile, btnSignIn, btnSignOut;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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
        mWhoSignedInTextView = (TextView) findViewById(R.id.whoSignedInTextView);
        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignOut = (Button) findViewById(R.id.btnSignOut);
        // set who is signed in for the text view
        if (mCurrentUser != null) {
            mWhoSignedInTextView.setText("Signed in as: " + mCurrentUser.getEmail());
        }
        initOnClickListeners();
    }

    private void initOnClickListeners() {
        Log.d(TAG, "initOnClickListeners: called");

        // btnProfile OnClickListener
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if there is a user signed in, send them to the profile activity
                if (mCurrentUser != null) {
                    Intent intent = new Intent(UserActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(UserActivity.this, "Not Signed In...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // btnSignIn OnClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if there is no user already signed in, send user to sign in activity
                if (mCurrentUser == null) {
                    Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(UserActivity.this, "Already Signed In as: " + mCurrentUser.getEmail(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // btnSignOut OnClickListener
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if there is a user signed in, sign them out
                if (mCurrentUser != null) {
                    signOut();
                    Toast.makeText(UserActivity.this, "Signing out...", Toast.LENGTH_SHORT).show();
                    // send user back to home screen
                    Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(UserActivity.this, "Not Signed in...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signOut() {
        Log.d(TAG, "signOut: called");
        mAuth.signOut();
    }
}
