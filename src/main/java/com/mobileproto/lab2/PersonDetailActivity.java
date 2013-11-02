package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by evan on 9/15/13.
 */



public class PersonDetailActivity extends Activity {

    private TextView personName;
    private TextView title;
    private String text = "";
    private Note note;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        Intent intent = getIntent();

        personName = (TextView)findViewById(R.id.personName);

    }
}


