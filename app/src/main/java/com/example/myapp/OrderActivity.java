package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    CheckBox ch1,ch2,ch3,ch4,ch5;
    Button orderbtn;
    DBHelper mydb1;
    EditText date1,time1;
    public static int  sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ch1=(CheckBox) findViewById(R.id.sproutsCheckbox);
        ch2=(CheckBox) findViewById(R.id.sproutsmixCheckbox);
        ch3=(CheckBox) findViewById(R.id.fruitsmallCheckbox);
        //ch4=(CheckBox) findViewById(R.id.fruitmediumCheckbox);
        //ch5=(CheckBox) findViewById(R.id.fruitlargeCheckbox);
        date1=(EditText)findViewById(R.id.dateslot);
        time1=(EditText)findViewById(R.id.timeslot);


        orderbtn=(Button) findViewById(R.id.orderButton);
        mydb1=new DBHelper(this);
       String username= getIntent().getStringExtra("keyuser");// to get the username from previous page



        orderbtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {


                StringBuilder order=new StringBuilder();
                order.append(" ");
                if(ch1.isChecked())
                {
                    order.append("\n Sprouts,");
                    sum=sum+150;
                }
                if(ch2.isChecked())
                {
                    order.append("\n Sprouts Mix,");
                    sum=sum+200;
                }
                if(ch3.isChecked())
                {
                    order.append("\n Fruit Salad(small),");
                    sum=sum+250;
                }
               /* if(ch4.isChecked())
                {
                    order.append("\n Fruit Salad(Medium)");
                    sum=sum+300;
                }
                if(ch5.isChecked())
                {
                    order.append("\n Fruit Salad(Large)");
                    sum=sum+350;
                }*/

                String sumstring=String.valueOf(sum);
                String finalorder=order.toString();

                String date_info=date1.getText().toString();
                if(date_info.length() == 0)
                {
                    date1.setError("please enter date");
                }

                String time_info=time1.getText().toString();
                if(time_info.length() == 0)
                {
                    time1.setError("please enter time");
                }

                Boolean result1=mydb1.updateOrderitem(finalorder,username,sum,date_info,time_info);

                    if(result1 == true)
                    {
                        Toast.makeText(OrderActivity.this, "Placing order...", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(OrderActivity.this, "Error in ordering...", Toast.LENGTH_SHORT).show();
                    }


                Intent intent=new Intent(getApplicationContext(), ConfirmActivity.class);
                intent.putExtra(ConfirmActivity.User,username);
                intent.putExtra(ConfirmActivity.Details,finalorder);
                intent.putExtra(ConfirmActivity.Totalsum,sumstring);
                startActivity(intent);

            }
        });


    }
}