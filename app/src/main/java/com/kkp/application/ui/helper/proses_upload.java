package com.kkp.application.ui.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import com.kkp.application.ui.home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class proses_upload extends AsyncTask<String,Void, String> {

    //Link
    private String url_proses_login = "https://6abd-101-255-11-228.ngrok.io/pelaporan";

    Context context;
    ProgressDialog progressDialog;
    Activity activity;
    AlertDialog.Builder builder;

    public proses_upload(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menyimpan Data..");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if (method.equals("Upload")) {
            try {
                URL url = new URL(url_proses_login);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("token", "8d745146006dfe270e241a1acf3b8ca8969c90a50f5b21c1ad4b368272e144b3caf651c0daa8f2fa");
                connection.setUseCaches(false);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String judul, kategori, notlpn, detail, gambar;

                kategori = params[1];
                judul = params[2];
                notlpn = params[3];
                detail = params[4];
                gambar= params[5];


                String data_upload =
                        URLEncoder.encode("judul", "UTF-8") + "=" + URLEncoder.encode(judul, "UTF-8") + "&" +
                                URLEncoder.encode("notlpn", "UTF-8") + "=" + URLEncoder.encode(notlpn, "UTF-8") +
                                URLEncoder.encode("kategori", "UTF-8") + "=" + URLEncoder.encode(kategori, "UTF-8") +"&" +
                                URLEncoder.encode("detail", "UTF-8") + "=" + URLEncoder.encode(detail, "UTF-8") + "&" +
                                URLEncoder.encode("gambar", "UTF-8") + "=" + URLEncoder.encode(gambar, "UTF-8");

                bufferedWriter.write(data_upload);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                connection.disconnect();
                Thread.sleep(3000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {

            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("Server");

            JSONObject JO = jsonArray.getJSONObject(0);
            String code = JO.getString("code");
            String message = JO.getString("message");
            //Script jika berhasil kirim
            if (code.equals("addLaporan_true")) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            }
            //script jika gagal kirim
            else if (code.equals("getLaporan_false")) {
                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
