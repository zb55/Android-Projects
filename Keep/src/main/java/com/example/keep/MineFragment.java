package com.example.keep;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment {
    String numberNow;
    public void setNumber(String number){
        numberNow = number;
    }
    List<Course> courseList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        TextView textView = view.findViewById(R.id.mine_number);
        textView.setText(numberNow);
        Button button = view.findViewById(R.id.mine_courselevel);
        ListView listView = (ListView) view.findViewById(R.id.mine_listview);
        View popup_view = getLayoutInflater().inflate(R.layout.popupwindow,null);
        PopupWindow popupWindow = new PopupWindow(popup_view, ViewGroup.LayoutParams.WRAP_CONTENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);

        courseList = getCourseList("全部课程");
        if (!courseList.get(0).getName().equals("0"))
            listView.setAdapter(new ListViewAdapterCourse(MineFragment.this.getActivity(), R.layout.item_course, courseList));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAsDropDown(view);
            }
        });

        TextView all = popup_view.findViewById(R.id.all);
        TextView level1 = popup_view.findViewById(R.id.level1);
        TextView level2 = popup_view.findViewById(R.id.level2);
        TextView level3 = popup_view.findViewById(R.id.level3);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText("全部课程");
                courseList = getCourseList("全部课程");
                popupWindow.dismiss();
                listView.setAdapter(new ListViewAdapterCourse(MineFragment.this.getActivity(), R.layout.item_course, courseList));

            }
        });
        level1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  button.setText(level1.getText());
                  courseList = getCourseList(level1.getText().toString());
                  popupWindow.dismiss();
                  listView.setAdapter(new ListViewAdapterCourse(MineFragment.this.getActivity(), R.layout.item_course, courseList));
              }
          });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText(level2.getText());
                courseList = getCourseList(level2.getText().toString());
                popupWindow.dismiss();
                listView.setAdapter(new ListViewAdapterCourse(MineFragment.this.getActivity(), R.layout.item_course, courseList));
            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText(level3.getText());
                courseList = getCourseList(level3.getText().toString());
                listView.setAdapter(new ListViewAdapterCourse(MineFragment.this.getActivity(), R.layout.item_course, courseList));
                popupWindow.dismiss();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course course = (Course) adapterView.getItemAtPosition(i);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("确认删除该课程？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());

                                if (mySQLiteOpenHelper.courseExist(numberNow, course)) {
                                    mySQLiteOpenHelper.deleteCourse(numberNow, course);
                                    Toast.makeText(getContext(), "该课程已删除", Toast.LENGTH_SHORT).show();
                                }else{

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

    public List<Course> getCourseList(String levelChoose){
        List<Course> courseList = new ArrayList<>();
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(MineFragment.this.getActivity());
        String courseMassage = mySQLiteOpenHelper.getCourse(numberNow);
        if(!courseMassage.equals("")) {
            String[] cm = courseMassage.split(";");
            for (int i = 0; i < cm.length; i += 3) {
                if (cm[i + 2].equals(levelChoose) || "全部课程".equals(levelChoose))
                    courseList.add(new Course(Integer.parseInt(cm[i]), cm[i + 1], cm[i + 2]));
            }
        }else{
            courseList.add(new Course(0, "0", "0"));
        }
        return courseList;
    }
}