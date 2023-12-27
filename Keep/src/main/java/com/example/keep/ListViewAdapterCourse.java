package com.example.keep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ListViewAdapterCourse extends ArrayAdapter<Course> {

    public ListViewAdapterCourse(@NonNull Context context, int resource, @NonNull List<Course> objects) {
        super(context, resource, objects);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);

        View view= LayoutInflater.from(getContext()).inflate(R.layout.item_course,parent,false);
        ImageView courseImage =view.findViewById(R.id.course_image);
        TextView courseName =view.findViewById(R.id.course_name);
        TextView courseLevel=view.findViewById(R.id.course_level);

        courseImage.setImageResource(course.getImageId());
        courseName.setText(course.getName());
        courseLevel.setText(course.getLevel());

        return view;
    }

}
