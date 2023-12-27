package com.example.keep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText number, password1, password2;
    private Button register;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    private  void init(){
        number = findViewById(R.id.number_register);
        password1 = findViewById(R.id.password_register1);
        password2 = findViewById(R.id.password_register2);
        register = findViewById(R.id.button_register);

        register.setOnClickListener(this);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
    }

    @Override
    public void onClick(View view) {
        String number_new = number.getText().toString().trim();
        String password_new = password1.getText().toString().trim();
        String password_new2 = password2.getText().toString().trim();

        if(password_new.equals(password_new2)){
            //密码确认
            Bundle bundle = new Bundle();
            bundle.putString("number", number_new);
            bundle.putString("password", password_new);
            User user = new User(number_new, password_new);
            int result = mySQLiteOpenHelper.register(user);
            if(result == Result.isSuccessful){
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (result == Result.isHaved) {
                Toast.makeText(this, "账号已存在", Toast.LENGTH_SHORT).show();
            } else if (result == Result.isFailed) {
                Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
        }
    }
}