package com.example.keep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText number, password;
    private Button login;
    private TextView register, forgetpwd;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        receiveData();
    }
    private void init() {
        number = findViewById(R.id.number_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.button_login);
        register = findViewById(R.id.login_register);
        forgetpwd = findViewById(R.id.login_forgetpwd);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgetpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        if(id == login.getId()){
            User user = new User(number.getText().toString().trim(), password.getText().toString().trim());
            int result = mySQLiteOpenHelper.login(user);
            if(result == Result.isSuccessful){
                Bundle bundle = new Bundle();
                bundle.putString("number", number.getText().toString().trim());
                intent = new Intent(this, HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        } else if (id == register.getId()) {
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (id == forgetpwd.getId()) {

        }
    }
    private void receiveData(){
        Bundle receivedBundle = getIntent().getExtras();
        if(receivedBundle!=null){
            String number_new = receivedBundle.getString("number");
            String password_new = receivedBundle.getString("password");

            number.setText(number_new);
            password.setText(password_new);
        }
    }
}