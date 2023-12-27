package com.example.keep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home_image, course_image, mine_image, current_image;
    private TextView home_text, course_text, mine_text, current_text;
    private ViewPager2 viewPager2;
    private List<Fragment> lf;
    private Pager2Adapter_home pager2AdapterHome;
    String number ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        receiveData();
        init();
    }
    public void init(){
        home_image = findViewById(R.id.bottom_image_home);
        course_image = findViewById(R.id.bottom_image_course);
        mine_image = findViewById(R.id.bottom_image_mine);
        home_text = findViewById(R.id.bottom_text_home);
        course_text = findViewById(R.id.bottom_text_course);
        mine_text = findViewById(R.id.bottom_text_mine);
        viewPager2 = findViewById(R.id.viewpager2_home);

        lf = new ArrayList<>();
        lf.add(new SportFragment());
        CourseFragment courseFragment = new CourseFragment();
        courseFragment.setNumber(number);
        lf.add(courseFragment);
        MineFragment mineFragment = new MineFragment();
        mineFragment.setNumber(number);
        lf.add(mineFragment);
        pager2AdapterHome = new Pager2Adapter_home(lf, getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(pager2AdapterHome);

        home_image.setSelected(true);
        home_text.setTypeface(null, Typeface.BOLD);
        current_image = home_image;
        current_text = home_text;
        home_image.setOnClickListener(this);
        course_image.setOnClickListener(this);
        mine_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == home_image.getId()){
            current_image.setSelected(false);
            current_text.setTypeface(null, Typeface.NORMAL);
            home_image.setSelected(true);
            home_text.setTypeface(null, Typeface.BOLD);
            current_image = home_image;
            current_text = home_text;
            viewPager2.setCurrentItem(0);
        } else if (id == course_image.getId()) {
            current_image.setSelected(false);
            current_text.setTypeface(null, Typeface.NORMAL);
            course_image.setSelected(true);
            course_text.setTypeface(null, Typeface.BOLD);
            current_image = course_image;
            current_text = course_text;
            viewPager2.setCurrentItem(1);
        } else if (id == mine_image.getId()) {
            current_image.setSelected(false);
            current_text.setTypeface(null, Typeface.NORMAL);
            mine_image.setSelected(true);
            mine_text.setTypeface(null, Typeface.BOLD);
            current_image = mine_image;
            current_text = mine_text;
            viewPager2.setCurrentItem(2);
        }
    }
    private void receiveData(){
        Bundle receivedBundle = getIntent().getExtras();
        if(receivedBundle!=null){
            number = receivedBundle.getString("number");
        }
    }
}