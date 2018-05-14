package com.vramakanth.calculator;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.net.URL;

/**
 * Created by Vallabh on 11/15/2015.
 */
public class Activity2 extends AppCompatActivity {


    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);
        setContentView(R.layout.activity2);


    }
    public void click(View v){
        String url = "http://www.facebook.com/vallabhr1/";
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.i("THIS","SHOULD WORK!!");
            startActivity(intent);
        }
    }
}
