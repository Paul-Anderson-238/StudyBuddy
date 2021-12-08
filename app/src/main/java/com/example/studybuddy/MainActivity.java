package com.example.studybuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ListView lv_cardSet;
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
        Set<String> set = sharedPreferences.getStringSet("SETLIST", new HashSet<>());
        for (String x : set)
            setList.add(x);
    }
    }
