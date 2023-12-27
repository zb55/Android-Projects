package com.example.keep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CourseFragment extends Fragment {
    String numberNow;
    public void setNumber(String number){
        numberNow = number;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        int id[] = new int[]{R.drawable.course_fuwocheng, R.drawable.course_run,
                R.drawable.course_juanfu, R.drawable.course_lashen,
                R.drawable.course_fuji, R.drawable.course_yingla};
        String name[] = new String[]{"Tabata全身暴汗燃脂", "10公里法莱特跑",
                "中等强度腹部进阶训练", "全身拉伸",
                "HIIT腹肌强化瘦腰围", "传统硬拉"};
        String level[] = new String[]{"K1 零基础","K2 进阶","K3 挑战"};
        List<Course> courseList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            courseList.add(new Course(id[i], name[i], level[i%3]));
        }

        ListView listView = view.findViewById(R.id.course_listview);
        listView.setAdapter(new ListViewAdapterCourse(this.getActivity(), R.layout.item_course, courseList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course course = (Course) adapterView.getItemAtPosition(i);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("确认添加该课程？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());

                                    if (!mySQLiteOpenHelper.courseExist(numberNow, course)) {
                                        mySQLiteOpenHelper.addCourse(numberNow, course);
                                    }else{
                                        Toast.makeText(getContext(), "该课程已存在", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
            }
        });
        return view;
    }
}