package com.mobileproto.lab2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private TextView title;
    private TextView note;
    private List<String> noteTitles;
    private NoteListAdapter aa;
    private ListView notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);

        title = (TextView) findViewById(R.id.titleField);
        note = (EditText) findViewById(R.id.noteField);
        noteTitles = new ArrayList<String>(dbHandler.allNotes());
        aa  = new NoteListAdapter(this, android.R.layout.simple_list_item_1, noteTitles);
        notes = (ListView) findViewById(R.id.noteList);
        notes.setAdapter(aa);
        Button save = (Button)findViewById(R.id.saveButton);

        note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    note.setLines(15);
                } else {
                    note.setLines(2);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteTitle = title.getText().toString();
                String noteText = note.getText().toString();
                if (noteTitle != null && noteText != null){
                    try{
                        DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);

                        Note new_note = new Note(noteTitle, noteText);

                        dbHandler.addNote(new_note);

                        title.setText("");
                        note.setText("");
                        aa.insert(noteTitle,0);
                        aa.notifyDataSetChanged();
                        note.clearFocus();
                    } catch (Exception e){
                        Log.e("Exception", e.getMessage());
                    }
                }
            }
        });

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final TextView rowTitle = (TextView) view.findViewById(R.id.titleTextView);
                String localTitle = rowTitle.getText().toString();
                Intent in = new Intent(getApplicationContext(), NoteDetailActivity.class);
                in.putExtra("noteTitle", localTitle);
                startActivity(in);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
