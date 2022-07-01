package com.kkp.application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kkp.application.ui.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    private String JSON_STRING;
    private ListView listView;
    ImageView ivGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.listlaporan);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showAllLaporan(){
        JSONObject jo1 = null;
        ArrayList<HashMap<String,Object>> list = new ArrayList<>();
        try {
            jo1 = new JSONObject(JSON_STRING);
            JSONArray result = jo1.getJSONArray("Server");
            JSONObject jo2 = result.getJSONObject(0);
            JSONArray data = jo2.getJSONArray("data");

            for(int i=0;i<data.length();i++){
                JSONObject jo3 = data.getJSONObject(i);
                int id = jo3.getInt("id");
                String judul = jo3.getString("judul");
                String nama = jo3.getString("nama");
                String notlpn = jo3.getString("notlpn");
                String detail = jo3.getString("detail");
                String file = jo3.getString("file");
                String cleanImage = file.replace("data:image/png;base64,", "").replace("data:image/jpg;base64,", "").replace("data:image/jpeg;base64,", "");
                byte[] byteImg = Base64.decode(cleanImage, Base64.DEFAULT);
//                String gambar = new String(byteImg);
                Bitmap decodebyte = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);

                HashMap<String,Object> lapor = new HashMap<>();
                lapor.put("id",id);
                lapor.put("judul",judul);
                lapor.put("nama",nama);
                lapor.put("notlpn",notlpn);
                lapor.put("detail",detail);
                lapor.put("file", decodebyte);
                list.add(lapor);
                Log.d("isilis",list.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleAdapter adapter = new SimpleAdapter(
                ListActivity.this, list,R.layout.list_item,
                new String[]{"judul", "nama", "notlpn", "detail","file"},
                new int[]{R.id.listjudul, R.id.listnama, R.id.listnotlpn, R.id.listdetail, R.id.listfile}
        );

        adapter.setViewBinder(new SimpleAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if((view instanceof ImageView) & (data instanceof Bitmap) ) {
                    ImageView iv = (ImageView) view;
                    Bitmap bm = (Bitmap) data;
                    iv.setImageBitmap(bm);
                    return true;
                }
                return false;
            }
        });

        Log.d("isiadapter",adapter.toString());
        listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListActivity.this,"Mengambil data...","Mohon Tunggu...",false,false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showAllLaporan();
                Log.d("ambil",s);
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    URL url = new URL(Config.BASE_URL +"pelaporan");
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
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailListActivity.class);
        HashMap<String,String> map = (HashMap)parent.getItemAtPosition(position);
        String id1 = map.get("id").toString();
        intent.putExtra("id",id1);
        startActivity(intent);
    }
}