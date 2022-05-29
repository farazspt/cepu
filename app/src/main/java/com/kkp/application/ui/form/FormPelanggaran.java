package com.kkp.application.ui.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kkp.application.R;

public class FormPelanggaran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pelanggaran);
    }

    public void kembali(View view) {
        finish();
    }
}