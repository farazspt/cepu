package com.kkp.application.ui.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.kkp.application.BuildConfig;
import com.kkp.application.ui.Config;
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

public class proses_login extends AsyncTask<String,Void,String> {
//    private String login_uri = "https://moprog-ubl.000webhostapp.com/php/login.php";
    private String login_uri = Config.BASE_URL+"auth/login";
    Context context;
    ProgressDialog progressDialog;
    Activity activity;
    AlertDialog.Builder builder;
    String token;

    public proses_login(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Masuk...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if (method.equals("Login")){
            try {
                URL url = new URL(login_uri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String username, password;
                username = params[1];
                password = params[2];

                String data_login = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(data_login);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

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
            String message = JO.getString("pesan");

            JSONObject jsonArrayData = JO.getJSONObject("data");
            token = jsonArrayData.getString("token");
            String level = jsonArrayData.getString("level");
//            System.out.println(token);
//            Log.d("token", token);
//            Log.d("level", level);

            //Script jika berhasil masuk
            if (code.equals("login_true")){
                if(level.matches("admin|guru" )){
                    builder.setMessage("Anda tidak bisa masuk");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            progressDialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    saveToken("token", token);
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                }
            }
            //script jika gagal masuk
            else if (code.equals("login_false")){
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

//    @Override
//    protected void onPause(){
//        super.onPause();
//        Log.d("token",getToken("token"));
//    }

    public void saveToken(String token, String value) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MyToken", Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferences = getSharedPreferences("MyToken", 0);
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        editor.putString(token, value);
        editor.apply();
    }
}

