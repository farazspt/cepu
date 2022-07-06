package com.kkp.application.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.kkp.application.ListActivity;
import com.kkp.application.R;
import com.kkp.application.ui.login.LoginActivity;
import com.kkp.application.ui.tambah.TambahLaporanActivity;

public class HomeGuru extends AppCompatActivity {
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_guru);
    }

    public void list(View view) {
        startActivity(new Intent(com.kkp.application.ui.home.HomeGuru.this, ListActivity.class));
    }

    public void logout(View view) {
        removeToken("token");
        startActivity(new Intent(com.kkp.application.ui.home.HomeGuru.this, LoginActivity.class));
        finish();
    }

    public void removeToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
