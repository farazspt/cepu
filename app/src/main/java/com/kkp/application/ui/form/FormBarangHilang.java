package com.kkp.application.ui.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kkp.application.R;

public class FormBarangHilang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_barang_hilang);
    }

    public void kembali(View view) {
        finish();
    }
}