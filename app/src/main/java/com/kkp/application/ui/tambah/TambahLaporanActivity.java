package com.kkp.application.ui.tambah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kkp.application.R;
import com.kkp.application.ui.form.FormBarangHilang;
import com.kkp.application.ui.form.FormPelanggaran;
import com.kkp.application.ui.home.HomeActivity;

public class TambahLaporanActivity extends AppCompatActivity {
    TextView tvakun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_laporan);

        tvakun = findViewById(R.id.tvAkun);

        tvakun.setText(showNama("nama"));
    }

    public void pelanggaran(View view) {
        startActivity(new Intent(TambahLaporanActivity.this, FormPelanggaran.class));
    }

    public void baranghilang(View view) {
        startActivity(new Intent(TambahLaporanActivity.this, FormBarangHilang.class));
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


    public void panahKiridetail1(View view) {
        finish();
    }

    public void kembali(View view) {
        startActivity(new Intent(TambahLaporanActivity.this, HomeActivity.class));
    }
}