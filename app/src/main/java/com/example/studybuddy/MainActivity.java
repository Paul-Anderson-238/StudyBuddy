package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private ListView lv_cardSet;
    static final String setlist = "SETLIST";
    private List<String> setList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadList();
    }

    public void displaySet(){
        setContentView(R.layout.activity_main);
        lv_cardSet = (ListView) findViewById(R.id.cardSets);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, setList);
        lv_cardSet.setAdapter(adapter);
    }

    public void EditSet(View view){
        Intent intent = new Intent(this, EditCardMenu.class);
        startActivity(intent);
    }

    public void loadList(){
        SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
        Gson gson = new Gson();
        String string = sharedPreferences.getString(setlist, "");
        setList = gson.fromJson(string, List.class);
    }
    }
