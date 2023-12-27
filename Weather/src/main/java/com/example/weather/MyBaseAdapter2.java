package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class MyBaseAdapter2 extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<String> cities;

    public MyBaseAdapter2(Context context, List<String> cities){
        layoutInflater = LayoutInflater.from(context);
        this.cities = cities;

    }
    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int i) {
        return cities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.city_item_s, parent, false);
        }

        TextView cityTextView = convertView.findViewById(R.id.item_city);

        if (cities != null) {
            cityTextView.setText(cities.get(position));

        }

        return convertView;
    }

}
