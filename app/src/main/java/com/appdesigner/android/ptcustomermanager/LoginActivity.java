package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mPasswordView, mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserName = (EditText) findViewById(R.id.user_name);
        mPasswordView = (EditText) findViewById(R.id.password);


        // Validate username and password and display appropriate Toast
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUserName.getText().toString();
                String password = mPasswordView.getText().toString();
                if (userName.equals("jdoe") && password.equals("welcome1")) {
                    Toast.makeText(LoginActivity.this, R.string.success_toast, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, CustomersActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, R.string.unsuccessful_toast, Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


}

