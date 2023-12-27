package com.example.keep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread()  {
            public void run(){
                super.run();
                boolean start = false;
                try{
                    Thread.sleep(3000);
                    start = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(start){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();


    }


}