package com.my.cafeoder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGIN = "login";
    public static final String PASSWORD = "pass";

    private EditText mEditTextLogin;
    private EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditTextLogin = findViewById(R.id.editTextLogin);
        mEditTextPassword = findViewById(R.id.editTextPass);
    }

    public void onClickCreateOrder(View view) {
        String login =  mEditTextLogin.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();
        if(!login.isEmpty() && !password.isEmpty()) {
            Intent intent = new Intent(this, CreateOrderActivity.class);
            intent.putExtra(PASSWORD, login);
            intent.putExtra(LOGIN, password);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.write_fields), Toast.LENGTH_SHORT).show();
        }

    }
}
