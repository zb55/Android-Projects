package com.example.weather;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityActivity extends AppCompatActivity {
    private Button add_button, delete_button;
    private ListView listView;
    private List<String> cities;
    private CityDB cityDB;
    private MyBaseAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        init();
    }
    private void init(){
        cityDB = new CityDB(this);

//        cityDB.addCity("陕西 汉中 西乡县");
//        cityDB.deleteCity("陕西 汉中 西乡县");
        cities = cityDB.queryAll();
        myAdapter = new MyBaseAdapter(this, cities);
        add_button = findViewById(R.id.add_city_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CityActivity.this, SearchActivity.class);
                intent.putExtra("Zero", 0);
                resultLauncher.launch(intent);
            }
        });
        delete_button = findViewById(R.id.delete_city_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> removedCities = new ArrayList<>();
                for (int i = 0; i < listView.getChildCount(); i++) {
                    View view = listView.getChildAt(i);
                    CheckBox checkBox = view.findViewById(R.id.item_checkbox);
                    if (checkBox.isChecked()) {
                        String city = ((TextView) view.findViewById(R.id.item_city)).getText().toString();
                        removedCities.add(city);

                    }
                }
                if(listView.getChildCount() == removedCities.size()){
                    Toast.makeText(CityActivity.this, "至少保留一个城市", Toast.LENGTH_SHORT).show();
                }else {
                    cityDB.deleteCities(removedCities);
                    listView.setAdapter(new MyBaseAdapter(CityActivity.this, cityDB.queryAll()));
                }
            }
        });

        listView = findViewById(R.id.city_listview);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                if(intent.getIntExtra("single", 0) ==1){
                    cities = cityDB.queryAll();
                    String[] c = cities.get(i).split(" ");
                    String city = c[c.length-1];
                    intent.putExtra("city", city);
                    setResult(Activity.RESULT_OK, intent);
                }
            }
        });
    }
    private ActivityResultLauncher resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        String city = intent.getStringExtra("city");
                        cityDB.addCity(city);
                        listView.setAdapter(new MyBaseAdapter(CityActivity.this, cityDB.queryAll()));
                    }
                }
            });

}