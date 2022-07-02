package com.kkp.application.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kkp.application.ListActivity;
import com.kkp.application.R;
import com.kkp.application.ui.login.LoginActivity;
import com.kkp.application.ui.tambah.TambahLaporanActivity;

public class HomeActivity extends AppCompatActivity {
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void list(View view) {
        startActivity(new Intent(HomeActivity.this, ListActivity.class));
    }

    public void add(View view) {
        startActivity(new Intent(HomeActivity.this, TambahLaporanActivity.class));
    }

    public void logout(View view) {
        removeToken("token");
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }

    public void removeToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }
}