package com.example.myapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.DBHelper;
import com.example.myapp.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String tableName = "Users"; // Replace with your table name

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        TextView detailsTextView = findViewById(R.id.detailsTextView);

        StringBuilder detailsBuilder = new StringBuilder();

        while (cursor.moveToNext()) {
            // Retrieve data from cursor
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String mobile_no = cursor.getString(cursor.getColumnIndexOrThrow("mobile_no"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String orderitem = cursor.getString(cursor.getColumnIndexOrThrow("orderitem"));
            int total_price=cursor.getInt(cursor.getColumnIndexOrThrow("total_price"));
            String date_info = cursor.getString(cursor.getColumnIndexOrThrow("date_info"));
            String time_slot = cursor.getString(cursor.getColumnIndexOrThrow("time_slot"));

            // Append the retrieved data to the StringBuilder
            detailsBuilder.append("Username: ").append(username).append("\n")
                    .append("Mobile Number: ").append(mobile_no).append("\n")
                    .append("Password: ").append(password).append("\n")
                    .append("Order Item: ").append(orderitem).append("\n")
                    .append("Total price: ").append(total_price).append("\n")
                    .append("Date: ").append(date_info).append("\n")
                    .append("TimeSlot: ").append(time_slot).append("\n\n");
        }

        cursor.close();

        // Update the TextView with the retrieved data
        detailsTextView.setText(detailsBuilder.toString());
    }
}
