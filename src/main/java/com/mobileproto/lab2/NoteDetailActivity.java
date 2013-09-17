package com.mobileproto.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by evan on 9/15/13.
 */



public class NoteDetailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        Intent intent = getIntent();

        String noteTitle = intent.getStringExtra("noteTitle");

        TextView title = (TextView) findViewById(R.id.noteTitle);
        TextView noteText = (TextView) findViewById(R.id.noteText);

        title.setText(noteTitle);
        DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);
        String text = dbHandler.findNote(noteTitle).getNoteText();
        noteText.setText(text);
    }
}


