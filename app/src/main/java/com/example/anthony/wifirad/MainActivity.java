package com.example.anthony.wifirad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //ImageView img;
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent in = new Intent(MainActivity.this, Page2.class);
                startActivity(in);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
        /*img=(ImageView)findViewById(R.id.imageView);
        Toast.makeText(getApplicationContext(),"Press on the Image to go Continue",Toast.LENGTH_SHORT).show();*/
    }

    /*public void getWifi(View view)
    {

        Intent in = new Intent(MainActivity.this, Page2.class);
        startActivity(in);
    }*/
}
