package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class EditCardView extends AppCompatActivity {
    static final String setlist = "SETLIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_view);
    }

    public void returnToPrev(){
        SharedPreferences sharedPreferences = getSharedPreferences(setlist, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }
}