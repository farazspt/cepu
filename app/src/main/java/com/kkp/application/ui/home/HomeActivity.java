package com.kkp.application.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvakun = findViewById(R.id.tvAkun);

        showNama("nama");
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

    private void showNama(String json){
        try{
            JSONObject jo = new JSONObject(json);
            JSONArray result = jo.getJSONArray("Server");
            JSONObject jo2 = result.getJSONObject(0);
            JSONObject data = jo2.getJSONObject("data");

            String nama = data.getString("nama");

            tvakun.setText(nama);
        }catch(JSONException e){
            e.printStackTrace();
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