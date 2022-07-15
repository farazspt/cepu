package com.kkp.application.ui.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkp.application.ListActivity;
import com.kkp.application.R;
import com.kkp.application.ui.helper.proses_login;
import com.kkp.application.ui.login.LoginActivity;
import com.kkp.application.ui.tambah.TambahLaporanActivity;

public class HomeGuru extends AppCompatActivity {
    Activity activity;
    TextView tvakun;
    ImageView btnLogout;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_guru);
        tvakun = findViewById(R.id.tvAkun);
        btnLogout = findViewById(R.id.logoutguru);

        tvakun.setText(showNama("nama"));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Logout");
                builder.setMessage("Anda yakin ingin logout?");
//                builder.setMessage("Test");
                builder.setPositiveButton("yakin", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeToken("token");
                        removeNama("nama");

                        startActivity(new Intent(com.kkp.application.ui.home.HomeGuru.this, LoginActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void list(View view) {
        startActivity(new Intent(com.kkp.application.ui.home.HomeGuru.this, ListActivity.class));
    }

    public void removeToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }

    public void removeNama(String nama) {
        SharedPreferences sharedPreferences = getSharedPreferences("Nama", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }

    private String showNama(String nama){
        SharedPreferences sharedPreferences = getSharedPreferences("Nama", 0);
        if(sharedPreferences.contains(nama)){
            String nama1 = sharedPreferences.getString(nama, null);
            return nama1;
        }
        else{
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
