package com.kkp.application.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.kkp.application.ListActivity;
import com.kkp.application.R;
import com.kkp.application.ui.login.LoginActivity;
import com.kkp.application.ui.tambah.TambahLaporanActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    Activity activity;
    TextView tvakun;
    ImageView logout;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvakun = findViewById(R.id.tvAkun);
        logout = findViewById(R.id.logoutsiswa);

        tvakun.setText(showNama("nama"));

        logout.setOnClickListener(new View.OnClickListener() {
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

                        startActivity(new Intent(com.kkp.application.ui.home.HomeActivity.this, LoginActivity.class));
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
        startActivity(new Intent(HomeActivity.this, ListActivity.class));
    }

    public void add(View view) {
        startActivity(new Intent(HomeActivity.this, TambahLaporanActivity.class));
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
//        Intent intent = getIntent();
//        String activity = intent.getStringExtra("from");
//        if(activity.equals("Login")){
//            finishAffinity();
//        }
        finishAffinity();
        super.onBackPressed();
    }
}