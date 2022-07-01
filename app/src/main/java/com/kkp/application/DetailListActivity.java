package com.kkp.application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kkp.application.ui.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailListActivity extends AppCompatActivity {

    TextView tvJudul,tvDetail,tvNama,tvTelp;
    ImageView ivGambar;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        Intent intent = getIntent();
        id=intent.getStringExtra("id");

        tvJudul = findViewById(R.id.tvJudul);
        tvDetail = findViewById(R.id.tvDetail);
        tvNama = findViewById(R.id.tvDetailNama);
        tvTelp = findViewById(R.id.tvDetailTelp);
        ivGambar = findViewById(R.id.ivgambardetail);

        getLaporan();
    }

    private void getLaporan(){
        class GetKucing extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailListActivity.this, "Mengambil Data", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showLaporan(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    URL url = new URL(Config.BASE_URL +"pelaporan/" +id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("token", "8d745146006dfe270e241a1acf3b8ca8969c90a50f5b21c1ad4b368272e144b3caf651c0daa8f2fa");
//                    connection.setDoOutput(true);
//                    connection.setDoInput(true);
//                    InputStream
//                    OutputStream outputStream = connection.getOutputStream();
//                    outputStream.close();

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line + "\n");
                    }

                    connection.disconnect();
                    Thread.sleep(1000);
                    return stringBuilder.toString().trim();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetKucing gl = new GetKucing();
        gl.execute();
    }

    private void showLaporan(String json){
        try{
            JSONObject jo = new JSONObject(json);
            JSONArray result = jo.getJSONArray("Server");
            JSONObject jo2 = result.getJSONObject(0);
            JSONObject data = jo2.getJSONObject("data");

            String judul = data.getString("judul");
            String nama = data.getString("nama");
            String notlpn = data.getString("notlpn");
            String detail = data.getString("detail");
            String file = data.getString("file");

            String cleanImage = file.replace("data:image/png;base64,", "").replace("data:image/jpg;base64,", "").replace("data:image/jpeg;base64,", "");
            byte[] byteImg = Base64.decode(cleanImage, Base64.DEFAULT);
//                String gambar = new String(byteImg);
            Bitmap decodebyte = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);

            tvJudul.setText(judul);
            tvNama.setText(nama);
            tvTelp.setText(notlpn);
            tvDetail.setText(detail);
            ivGambar.setImageBitmap(decodebyte);


        }catch(JSONException e){
            e.printStackTrace();
        }
    }



    public void panahKiridetail1(View view) {
        finish();
    }
}