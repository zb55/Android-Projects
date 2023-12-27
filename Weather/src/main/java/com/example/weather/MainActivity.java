package com.example.weather;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {
    private ImageButton city_menu;
    private TextView lowest, now, highest, info_left, info_right, info_bottom1, info_bottom2, info_bottom3, info_bottom4;
    private static final String APPKEY = "3f623e4a3dc98e87";
    private static final String ADDRESS = "https://api.jisuapi.com/weather/query?appkey=80273443cf354b1b&city=";
    private static String defaultCity;
    private static String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        SwipeRefresh();

        IntentFilter inf = new IntentFilter();
        inf.addAction("com.weather.refresh");
        registerReceiver(broadcastReceiver, inf);
        startService(new Intent(this, MyService.class));

    }
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
        unregisterReceiver(broadcastReceiver);
    }
    private void init(){
        city_menu = findViewById(R.id.city_button);
        city_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                intent.putExtra("single", 1);
                resultLauncher.launch(intent);
            }
        });

        lowest = findViewById(R.id.lowest_text);
        now = findViewById(R.id.now_text);
        highest = findViewById(R.id.highest_text);
        info_left = findViewById(R.id.info_left_text);
        info_right = findViewById(R.id.info_right_text);
        info_bottom1 = findViewById(R.id.info_bottom1);
        info_bottom2 = findViewById(R.id.info_bottom2);
        info_bottom3 = findViewById(R.id.info_bottom3);
        info_bottom4 = findViewById(R.id.info_bottom4);

        CityDB cityDB = new CityDB(this);

        defaultCity = cityDB.getFirstCity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String openconnect= query(ADDRESS, defaultCity);
                Message message = new Message();
                message.what = 0;
                message.obj = openconnect;

                mHandler.sendMessage(message);
            }
        }).start();
    }
    private String query(String address, String city){
        String result = "";
        try{
            URL url = new URL(address + URLEncoder.encode(city, "utf-8"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = "";
            while((line = reader.readLine()) != null){
                result += line;
            }
            reader.close();
            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("test", result);
        return result;
    }

    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);
            if(msg.what == 0){
                String strData = (String) msg.obj;
                parseJSONData(strData);
            }
        }
    };
    @SuppressLint("SetTextI18n")
    private void parseJSONData(String result){
        try{
            JSONObject object = new JSONObject(result);
            JSONObject today = (JSONObject) object.get("result");

            highest.setText("最高："+today.getString("temphigh"));
            lowest.setText("最低："+today.getString("templow"));
            now.setText(today.getString("temp")+"℃");
            info_left.setText(today.getString("city") +"\n\n" +today.getString("week") + "\n\n" +today.getString("weather"));
            info_right.setText(today.getString("date")+"\n\n"+"湿度："+today.getString("humidity")+"\n\n"+"气压："+today.getString("pressure"));

            JSONArray forecast = (JSONArray) today.get("daily");
            info_bottom1.setText((((JSONObject) forecast.get(1)).getJSONObject("day")).getString("weather") + "\n\n" + ((((JSONObject) forecast.get(1)).getJSONObject("day")).getString("temphigh") + "/" + ((((JSONObject) forecast.get(1)).getJSONObject("night")).getString("templow")))
            +"\n\n"+((JSONObject) forecast.get(1)).getString("date"));

            info_bottom2.setText((((JSONObject) forecast.get(2)).getJSONObject("day")).getString("weather") + "\n\n" + ((((JSONObject) forecast.get(2)).getJSONObject("day")).getString("temphigh") + "/" + ((((JSONObject) forecast.get(2)).getJSONObject("night")).getString("templow")))
            +"\n\n"+((JSONObject) forecast.get(2)).getString("date"));

            info_bottom3.setText((((JSONObject) forecast.get(3)).getJSONObject("day")).getString("weather") + "\n\n" + ((((JSONObject) forecast.get(3)).getJSONObject("day")).getString("temphigh") + "/" + ((((JSONObject) forecast.get(3)).getJSONObject("night")).getString("templow")))
            +"\n\n"+((JSONObject) forecast.get(3)).getString("date"));

            info_bottom4.setText((((JSONObject) forecast.get(4)).getJSONObject("day")).getString("weather") + "\n\n" + ((((JSONObject) forecast.get(4)).getJSONObject("day")).getString("temphigh") + "/" + ((((JSONObject) forecast.get(4)).getJSONObject("night")).getString("templow")))
            +"\n\n"+((JSONObject) forecast.get(4)).getString("date"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private ActivityResultLauncher resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        city = intent.getStringExtra("city");
                        Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.weather.refresh")){
                Toast.makeText(getApplication(), "refresh", Toast.LENGTH_SHORT).show();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String openconnect= query(ADDRESS, city);
//                        Message message = new Message();
//                        message.what = 0;
//                        message.obj = openconnect;
//
//                        mHandler.sendMessage(message);
//                    }
//                }).start();
            }
        }
    };
    public void SwipeRefresh(){
        SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeRefresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String openconnect= query(ADDRESS, city);
                                Message message = new Message();
                                message.what = 0;
                                message.obj = openconnect;

                                mHandler.sendMessage(message);
                            }
                        }).start();
                        refreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }
}