package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class EditCardMenu extends AppCompatActivity {
    List<String> setList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_menu);

        Intent intent = getIntent();
        setList = (List<String>) intent.getSerializableExtra("SETLIST");
    }
}