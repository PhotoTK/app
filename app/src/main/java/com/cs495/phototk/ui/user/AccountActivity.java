package com.cs495.phototk.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class AccountActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = "AccountActivity";

    // Member Variables
    EditText mCurrentPassword, mNewPassword, mConfirmNewPassword;
    Button mChangePasswordButton, mDeleteAccountButton;
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
        mCurrentPassword = (EditText) findViewById(R.id.current_password);
        mNewPassword = (EditText) findViewById(R.id.new_password);
        mConfirmNewPassword = (EditText) findViewById(R.id.confirm_new_password);
        mChangePasswordButton = (Button) findViewById(R.id.change_password_button);
        mDeleteAccountButton = (Button) findViewById(R.id.delete_account_button);
        initOnClickListeners();
    }

    private void initOnClickListeners() {
        Log.d(TAG, "initOnClickListeners: called");

        // change password button OnClickListener
        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPwd = mNewPassword.getText().toString();
                String confirmPwd = mConfirmNewPassword.getText().toString();

                // Re-authenticate user
                if (isPasswordValid(mCurrentPassword.getText().toString())) {
                    reauthenticateUser();
                    // if passwords are valid passwords and they are equal, allow user to change password
                    if (isPasswordValid(newPwd)) {
                        if (Objects.equals(newPwd, confirmPwd)) {
                            // Change password
                            mCurrentUser.updatePassword(newPwd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "onComplete: User password updated");
                                        Toast.makeText(AccountActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AccountActivity.this, UserActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        // User password NOT updated
                                        Log.w(TAG, "onComplete: Password update FAILED!");
                                        Toast.makeText(AccountActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Log.d(TAG, "onClick: passwords do not match");
                            Toast.makeText(AccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Log.d(TAG, "onClick: invalid password");
                        Toast.makeText(AccountActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.d(TAG, "onClick: Password entered for Current Password is invalid");
                    Toast.makeText(AccountActivity.this, "Password entered for Current Password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // change password button OnClickListener
        mDeleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordValid(mCurrentPassword.getText().toString())) {
                    reauthenticateUser();
                    mCurrentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: User's account successfully deleted.");
                                Toast.makeText(AccountActivity.this, "Account Deleted...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else {
                    Log.d(TAG, "onClick: Current Password is NOT valid");
                    Toast.makeText(AccountActivity.this, "Current Password is NOT valid...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reauthenticateUser() {
        // get user's email and password
        String email = mCurrentUser.getEmail();
        String pwd = mCurrentPassword.getText().toString();

        // generate user's credentials
        AuthCredential credential = EmailAuthProvider.getCredential(email, pwd);

        // Re-authenticate user
        mCurrentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: User re-authenticated");
            }
        });
    }

    private Boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }
}
