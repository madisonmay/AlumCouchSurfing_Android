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

        //fake data
        String Name = "Natalie Mattison";
        String Address = "51 Perkins Street";
        String email = "natalie.mattison@students.olin.edu";

        Button backButton = (Button)findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PersonDetailActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}


