package com.kkp.application.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kkp.application.R;
import com.kkp.application.ui.helper.proses_login;
import com.kkp.application.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnLogin;
    Context context;
//    Activity activity;

    AlertDialog.Builder builder;
//    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getToken("token");


        initialize();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Warning..!");
                    builder.setMessage("Masukan nama pengguna/password!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    proses_login backgroundTask = new proses_login(LoginActivity.this);
                    backgroundTask.execute("Login", username.getText().toString(), password.getText().toString());
                }
            }
        });

        if(getToken("token") != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class).putExtra("from", "Login");
            startActivity(intent);
        }

    }


    private String getToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", 0);
        if(sharedPreferences.contains(token)){
            String token1 = sharedPreferences.getString(token, null);
            return token1;
        }
        else{
            return null;
        }
    }

    private void initialize() {
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnSignin);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}