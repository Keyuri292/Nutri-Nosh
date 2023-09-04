package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {

    public static String Details="Details";
    public static String User="User";

    public static String Totalsum="Totalsum";

    TextView details;
    Button confirmbtn;
    private String detailsor;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        details=(TextView) findViewById(R.id.details);
        confirmbtn=(Button) findViewById(R.id.confirmorder);

        Intent intent=getIntent();
        detailsor=intent.getStringExtra(Details);
        username=intent.getStringExtra(User);
        Totalsum=intent.getStringExtra(Totalsum);




        details.setText("Your order items:" + detailsor + "\n Total price:" + Totalsum);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU)
        {
            if(ContextCompat.checkSelfPermission(ConfirmActivity.this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ConfirmActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ConfirmActivity.this, "Yay!" + username + "\n Your order has been placed!", Toast.LENGTH_SHORT).show();
                String chanelID = "CHANEL_ID_NOTIFICATION";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),chanelID);
                builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                        .setContentTitle("MyApp")
                        .setContentText(detailsor + "are ordered!")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("data","Dear customer, your order is successfully placed!");
                PendingIntent pendingIntent= PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
                builder.setContentIntent(pendingIntent);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    NotificationChannel notificationChannel = notificationManager.getNotificationChannel(chanelID);
                    if(notificationChannel == null)
                    {
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        notificationChannel = new NotificationChannel(chanelID, "Some description", importance);
                        //notificationChannel.setLightColor(Color.GREEN);
                        notificationChannel.enableVibration(true);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                }
                notificationManager.notify(0,builder.build());
            }
        });

    }
}