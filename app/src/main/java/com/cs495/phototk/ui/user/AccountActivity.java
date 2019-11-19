package com.cs495.phototk.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cs495.phototk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = "AccountActivity";

    // Member Variables
    EditText mCurrentPassword, mNewPassword, mConfirmNewPassowrd;
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
        mConfirmNewPassowrd = (EditText) findViewById(R.id.confirm_new_password);
        mChangePasswordButton = (Button) findViewById(R.id.change_password_button);
        mDeleteAccountButton = (Button) findViewById(R.id.delete_account_button);
        initOnClickListeners();
    }

    private void initOnClickListeners() {
        Log.d(TAG, "initOnClickListeners: called");

        // TODO: implement change password OnClickListener

        // TODO: implement delete button OnClickListener
    }
}
