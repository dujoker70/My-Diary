package com.example.mydiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    Button buttonLogin;
    Button buttonSignup;

    DatabaseHelper databaseHelper;

    AppCompatCheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPreviousLog();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);
        init();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(null, null, editTextEmail.getText().toString(), editTextPassword.getText().toString());
                if(isValid(user)) {
                    User current = databaseHelper.findUser(user);
                    if(current != null) {
                        SaveSharedPreference.setPrefUser(LoginActivity.this, user.getEmail());
                        Intent intent = new Intent(LoginActivity.this, ListViewActivity.class);
                        intent.putExtra("email", user.getEmail());
                        startActivity(intent);
                    }
                    else {
                        Snackbar.make(buttonLogin, "Log In Failed! Try Again!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkPreviousLog() {
        if(SaveSharedPreference.getPrefUser(LoginActivity.this).length() != 0) {
            startActivity(new Intent(LoginActivity.this, ListViewActivity.class));
        }
    }

    private boolean isValid(User user) {
        boolean ret = true;

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

    private void init() {
        editTextEmail = findViewById(R.id.editTextEmailId);
        editTextPassword = findViewById(R.id.editTextPasswordId);
        textInputLayoutEmail = findViewById(R.id.textInputEmailId);
        textInputLayoutPassword = findViewById(R.id.textInputPasswordId);
        buttonLogin = findViewById(R.id.buttonLoginId);
        buttonSignup = findViewById(R.id.buttonSignupId);
        checkBox = findViewById(R.id.checkBoxId);
    }
}
