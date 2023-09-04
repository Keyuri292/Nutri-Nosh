package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText usernamelog,passwordlog;
    Button loginbtn1;
    DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernamelog=(EditText) findViewById(R.id.usernameLog);
        passwordlog=(EditText) findViewById(R.id.passwordLog);
        loginbtn1=(Button) findViewById(R.id.loginbtn1);

        mydb=new DBHelper(this);

        loginbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=usernamelog.getText().toString();
                String password=passwordlog.getText().toString();

                if(user.equals("") || password.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean result=mydb.checkusernamepassword(user,password);
                    if(result==true)
                    {
                        Intent intent=new Intent(getApplicationContext(),OrderActivity.class);
                        intent.putExtra("keyuser",user); // to pass value to the next page
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}