package com.kkp.application.ui.tambah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kkp.application.R;
import com.kkp.application.ui.form.FormBarangHilang;
import com.kkp.application.ui.form.FormPelanggaran;

public class TambahLaporanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_laporan);
    }

    public void pelanggaran(View view) {
        startActivity(new Intent(TambahLaporanActivity.this, FormPelanggaran.class));
    }

    public void baranghilang(View view) {
        startActivity(new Intent(TambahLaporanActivity.this, FormBarangHilang.class));
    }
}