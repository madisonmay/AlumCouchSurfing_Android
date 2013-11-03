package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

        Bundle b = getIntent().getExtras();
        final double lat = b.getDouble("lat");
        final double lng = b.getDouble("lng");
        final String location_word = b.getString("location");


        TextView nameField = (TextView) this.findViewById(R.id.personName);
        final String name = b.getString("name");
        nameField.setText(name);

        TextView emailField = (TextView) this.findViewById(R.id.email);
        final String email = b.getString("email");
        emailField.setText(email);

        TextView yearField = (TextView) this.findViewById(R.id.yearclass);
        final String year = b.getString("class");
        yearField.setText("Class of " + year);

        TextView addressField = (TextView) this.findViewById(R.id.address);
        final String address = b.getString("address");
        addressField.setText(address);

        ImageView imageField = (ImageView) this.findViewById(R.id.imageView);
        final String gender = b.getString("gender");
        if (gender == "male") {
            imageField.setImageResource(R.drawable.ic_male);
        } else {
            imageField.setImageResource(R.drawable.ic_female);
        }



        Button backButton = (Button)findViewById(R.id.button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PersonDetailActivity.this, MapActivity.class);
                Bundle b = new Bundle();
                b.putDouble("lat", lat);
                b.putDouble("lng", lng);
                b.putString("location",location_word);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

    }
}



