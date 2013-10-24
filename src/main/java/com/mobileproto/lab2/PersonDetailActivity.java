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

    private TextView noteText;
    private TextView title;
    private String text = "";
    private Note note;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Intent intent = getIntent();


        String noteTitle = intent.getStringExtra("noteTitle");
        title = (TextView) findViewById(R.id.noteTitle);
        noteText = (TextView) findViewById(R.id.noteText);
        title.setText(noteTitle);

        noteText.requestFocus();

        DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);
        note = dbHandler.findNote(noteTitle);
        text = note.getNoteText();
        noteText.setText(text);

        Button save = (Button)findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteTitle = title.getText().toString();
                text = noteText.getText().toString();
                if (noteTitle != null && text != null){
                    try{
                        DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);

                        Note new_note = new Note(noteTitle, text);

                        dbHandler.updateNote(note, new_note);

                        Intent in = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(in);

                    } catch (Exception e){
                        Log.e("Exception", e.getMessage());
                    }
                }
            }
        });

    }
}


