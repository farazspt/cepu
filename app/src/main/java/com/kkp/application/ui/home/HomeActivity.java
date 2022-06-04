package com.kkp.application.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kkp.application.ListActivity;
import com.kkp.application.R;
import com.kkp.application.ui.tambah.TambahLaporanActivity;

public class HomeActivity extends AppCompatActivity {

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
}