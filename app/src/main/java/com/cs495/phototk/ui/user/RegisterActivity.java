package com.cs495.phototk.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

public class RegisterActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = "RegisterActivity";

    // Member Variables
    FirebaseAuth mAuth;
    EditText mEmail, mPassword, mConfirmPassword;
    Button mCancelButton, mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }
    
    private void init() {
        Log.d(TAG, "init: called");
        // get FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        // initialize the activity's UI
        initUI();
    }
    
    private void initUI() {
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        initOnClickListeners();
    }
    
    private void initOnClickListeners() {
        // Cancel Button OnClickListener
        // this button returns the user to the login screen
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Register Button OnClickListener
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                createAccount(email, password, confirmPassword);
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.d(TAG, "updateUI: called");
        if (currentUser != null) {
            Log.d(TAG, "updateUI: user already signed-in");
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void createAccount(String email, String password, String confirmPassword) {
        Log.d(TAG, "createAccount: called");
        // if credentials are valid, attempt to register user
        if (validateCredentials(email, password, confirmPassword)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail: success");
                                Toast.makeText(RegisterActivity.this, "Thank you for registering!", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail: failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private Boolean validateCredentials(String email, String password, String confirmPassword) {
        Log.d(TAG, "validateCredentials: validating user...");
        if (!isEmailValid(email)) {
            Log.w(TAG, "validateCredentials: User entered an invalid email address!");
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!isPasswordValid(password)) {
            Log.w(TAG, "validateCredentials: User entered an invalid password!");
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!password.equals(confirmPassword)) {
            Log.w(TAG, "validateCredentials: passwords do not match");
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean isEmailValid(String email) {
        // check that email isn't null and that email is of the correct character pattern for an email address
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private Boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

}
