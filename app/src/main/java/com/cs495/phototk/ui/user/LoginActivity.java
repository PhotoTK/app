package com.cs495.phototk.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs495.phototk.MainActivity;
import com.cs495.phototk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = "LoginActivity";

    // Auth variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // UI variables
    private EditText mEmail, mPassword;
    private Button btnSignIn, btnRegister, btnContinueWihoutLoggingIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: called");
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    
    private void init() {
        Log.d(TAG, "init: called");
        // get FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        // initialize activity
        initUI();
    }

    private void initUI() {
        // initialize edit texts and buttons
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        btnSignIn = (Button) findViewById(R.id.email_sign_in_button);
        btnRegister = (Button) findViewById(R.id.register_button);
        btnContinueWihoutLoggingIn = (Button) findViewById(R.id.continue_without_logging_in_button);
        // add OnClickListeners to buttons
        initOnClickListeners();
    }

    private void initOnClickListeners() {
        // Sign In button listener
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement sign-in button OnClickListener
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (!email.equals("") && !password.equals("")) {

                }
            }
        });

        // Register button listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement register button OnClickListener
            }
        });

        // Continue without logging in listener
        btnContinueWihoutLoggingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.d(TAG, "updateUI: called");
        if (currentUser != null) {
            Log.d(TAG, "updateUI: user already signed-in");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
