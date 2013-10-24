package com.mobileproto.lab2;

/**
 * Created by mmay on 9/17/13.
 */
public class Note {
    private int _id;
    private String noteTitle;
    private String noteText;

    public Note () {

    }

    public Note (int id, String noteTitle, String noteText) {
        this._id = id;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }

    public Note (String noteTitle, String noteText) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteTitle() {
        return this.noteTitle;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText() {
        return this.noteText;
    }
}