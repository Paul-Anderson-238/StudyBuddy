package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class EditCardMenu extends AppCompatActivity {
    List<String> setList;
    public String setName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_menu);

        Intent intent = getIntent();
        setList = (List<String>) intent.getSerializableExtra("SETLIST");
    }

    //Create shared preferences for the name of lists
    public void listName(View view) {
       SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
        Gson gson = new Gson();
        String string = sharedPreferences.getString(setName, "setname");
        setList = gson.fromJson(string, List.class);
    }

    //Create Intent to connect pass set list to edit card view. See main, pass the set name we are trying to edit.
    public void setName(View view){
        Intent intent = new Intent(this, EditCardMenu.class);
        intent.putExtra("setname", (Serializable) setName);
        startActivity(intent);
    }
}