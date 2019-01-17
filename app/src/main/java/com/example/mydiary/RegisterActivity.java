package com.example.mydiary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextUsername;
    EditText editTextPassword;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutPassword;

    Button buttonSignup;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHelper = new DatabaseHelper(this);
        Log.d("tag", "ashchi");
        init();
        initLogin();

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(null, editTextUsername.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString());
                if(isValid(user)) {
                    if(!databaseHelper.isEmailExists(user.getEmail())) {
                        databaseHelper.addUser(user);
                        Snackbar.make(buttonSignup, "Sign Up Successfull", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }
                    else {
                        Snackbar.make(buttonSignup, "This Email is already Registered!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean isValid(User user) {
        boolean ret = true;

        if(user.getUsername().isEmpty() || user.getUsername().length() < 5) {
            ret = false;
            textInputLayoutUsername.setError("Please Enter a Valid Username!");
        }
        else {
            textInputLayoutUsername.setError(null);
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            ret = false;
            textInputLayoutEmail.setError("Please Enter a Valid Email!");
        }
        else {
            textInputLayoutEmail.setError(null);
        }

        if(user.getPassword().isEmpty() || user.getPassword().length() < 5 || user.getPassword().length() > 30) {
            ret = false;
            textInputLayoutPassword.setError("Please Enter a Valid Password!");
        }
        else {
            textInputLayoutPassword.setError(null);
        }
        return ret;
    }

    private void initLogin() {
        TextView textView = findViewById(R.id.textViewLoginId);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        editTextEmail = findViewById(R.id.editTextEmailId);
        editTextPassword = findViewById(R.id.editTextPasswordId);
        editTextUsername = findViewById(R.id.editTextUsernameId);
        textInputLayoutEmail = findViewById(R.id.textInputEmailId);
        textInputLayoutUsername = findViewById(R.id.textInputUsernameId);
        textInputLayoutPassword = findViewById(R.id.textInputPasswordId);
        buttonSignup = findViewById(R.id.buttonSignupId);
    }
}
