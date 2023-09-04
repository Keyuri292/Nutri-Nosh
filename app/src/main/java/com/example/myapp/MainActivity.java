package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,mobile_no,password;
    Button login,register;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText) findViewById(R.id.username);
        mobile_no=(EditText)findViewById(R.id.mobileno);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.loginbtn);
        register=(Button) findViewById(R.id.registerbtn);
        mydb=new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String mobile=mobile_no.getText().toString();
                String pass=password.getText().toString();

                if(user.equals("") || mobile.equals("") || pass.equals(""))
                {
                    Toast.makeText(MainActivity.this, "fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean usercheckresult=mydb.checkusername(user);
                    if(usercheckresult == false)
                    {
                        Boolean regresult=mydb.insertData(user,mobile,pass);
                        if(regresult == true)
                        {
                            Toast.makeText(MainActivity.this, "Registration succesfull", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "User Already exists \n please log in", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}