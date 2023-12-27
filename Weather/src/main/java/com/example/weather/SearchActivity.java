package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private EditText search_text;
    private Button search_button;
    private ListView listView;
    private MyBaseAdapter2 myBaseAdapter;
    private static final String ADDRESS = "http://api.jisuapi.com/weather/city?appkey=80273443cf354b1b";
    private static final String ADDRESS2 = "https://restapi.amap.com/v3/config/district?key=2a4be315b9047e93d3ea223e5e3d3f75";
    private static String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; SLCC1; .NET CLR 2.0.50727; .NET CLR 3.0.04506; customie8)";
    private String cities_json;
    private List<String> list_city = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }
    private void init() {

        search_text = findViewById(R.id.search_text);
        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = search_text.getText().toString();
                list_city = getCity(cities_json, city);

                myBaseAdapter = new MyBaseAdapter2(SearchActivity.this, list_city);
                listView.setAdapter(myBaseAdapter);
            }
        });
        listView = findViewById(R.id.search_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                if (intent.getIntExtra("Zero", 0) == 0) {
                    intent.putExtra("city", list_city.get(i).toString());
                    setResult(Activity.RESULT_OK, intent);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                cities_json = getCities();

            }
        }).start();
    }

//    public String getCity(String cities_json, String city) {
//        String result = "";
//        String[] r = new String[]{};
//        try {
//
//            JSONObject json = new JSONObject(cities_json);
//
//            if (json.getInt("status") != 0) {
//                Log.i("xxoo", json.getString("msg"));
//            } else {
//                JSONArray resultarr = json.getJSONArray("result");
//                for (int i = 0; i < resultarr.length(); i++) {
//                    JSONObject obj = resultarr.getJSONObject(i);
//                    Log.i("xxoo", "1");
//                    String c = obj.getString("city");
//                    if(c.contains(city)){
//                        r[0] = c;
//                        Log.i("xxoo", r.toString());
//                        if(obj.getInt("parentid")!=0){
//                            JSONObject p = resultarr.getJSONObject(obj.getInt("parentid")-1);
//                            r[1] = p.getString("city");
//                            if (p.getInt("parentid")!=0){
//                                JSONObject pp = resultarr.getJSONObject(p.getInt("parentid")-1);
//                                r[2] = pp.getString("city");
//                            }
//                        }
//                        for (int j = r.length-1; j >= 0; j--) {
//                            result += " " + r[i];
//                        }
//                        return result.trim();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    public List<String> getCity(String cities_json, String city) {
        List<String> resultList = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(cities_json);
            if (json.getInt("status") != 0) {
                Log.i("xxoo", json.getString("msg"));
            } else {
                JSONArray resultarr = json.getJSONArray("result");
                for (int i = 0; i < resultarr.length(); i++) {
                    JSONObject obj = resultarr.getJSONObject(i);
                    Log.i("xxoo", "1");
                    String c = obj.getString("city");
                    if(c.contains(city)){
                        String s = c + " ";
                        if (obj.getInt("parentid")!=0){
                            JSONObject p = resultarr.getJSONObject(obj.getInt("parentid")-2);
                            s += p.getString("city") + " ";
                            if (p.getInt("parentid")!=0){
                                JSONObject pp = resultarr.getJSONObject(p.getInt("parentid")-1);
                                s += pp.getString("city");
                            }
                        }
                        String[] ss = s.split(" ");
                        String s1 = "";
                        for (int j = ss.length-1; j >= 0; j--) {
                            s1 += ss[j] + " ";
                        }
                        resultList.add(s1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private String getCities(){

        String result = "";
        try{
            URL url = new URL(ADDRESS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.connect();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String inputLine;
            StringBuffer stringBuffer = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                stringBuffer.append(inputLine);
                stringBuffer.append("\r\n");
            }
            result = stringBuffer.toString();
            reader.close();
            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}