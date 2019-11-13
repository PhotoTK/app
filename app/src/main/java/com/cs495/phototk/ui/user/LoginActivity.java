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
    private EditText mEmailTextField, mPasswordTextField;
    private Button btnSignIn, btnRegister, btnContinueWithoutLoggingIn;


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
        mEmailTextField = (EditText) findViewById(R.id.email);
        mPasswordTextField = (EditText) findViewById(R.id.password);
        btnSignIn = (Button) findViewById(R.id.email_sign_in_button);
        btnRegister = (Button) findViewById(R.id.register_button);
        btnContinueWithoutLoggingIn = (Button) findViewById(R.id.continue_without_logging_in_button);
        // add OnClickListeners to buttons
        initOnClickListeners();
    }

    private void initOnClickListeners() {
        // Sign In button listener
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailTextField.getText().toString();
                String password = mPasswordTextField.getText().toString();
                if (!email.equals("") && !password.equals("")) {
                    signIn(email, password);
                }
            }
        });

        // Register button listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailTextField.getText().toString();
                String password = mPasswordTextField.getText().toString();
                createAccount(email, password);
            }
        });

        // Continue without logging in listener
        btnContinueWithoutLoggingIn.setOnClickListener(new View.OnClickListener() {
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

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount: called");
        // Validate email and password
        validateCredentials(email, password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Thank you for registering!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    
    private void signIn(String email, String password) {
        // Validate email and password
        validateCredentials(email, password);
        // Sign in with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Signed in as: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void validateCredentials(String email, String password) {
        Log.d(TAG, "validateCredentials: validating user...");
        if (!isEmailValid(email)) {
            Log.w(TAG, "validateCredentials: User entered an invalid email address!");
            Toast.makeText(this, "Invalid Email...", Toast.LENGTH_SHORT).show();
        }
        else if (!isPasswordValid(password)) {
            Log.w(TAG, "validateCredentials: User entered an invalid password!");
            Log.d(TAG, "signIn: User entered an invalid password");
            Toast.makeText(this, "Invalid Password...", Toast.LENGTH_SHORT).show();
        }
    }
    
    private Boolean isEmailValid(String email) {
        // TODO: Implement isEmailValid
        return true;
    }

    private Boolean isPasswordValid(String email) {
        // TODO: Implement isPasswordValid
        return true;
    }
}
