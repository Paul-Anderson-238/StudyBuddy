package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    public List<CardSet> cardSetList;
    private ListView lv_cardSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // public void loadSets(View view) {
    //     SharedPreferences sharedPref = getSharedPreferences("cardSetList.txt", Context.MODE_PRIVATE);
    //
    //     File txt = new File("cardSetList.txt");
    //     Scanner scan = new Scanner(txt);
    //     ArrayList<String> data = new ArrayList<String>() ;
    //     while(scan.hasNextLine()){
    //         data.add(scan.nextLine());
    //     }
    //
    //     String[] simpleArray = data.toArray(new String[]{});
    //
    //     ArrayAdapter<retrieveSet> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cardSet.getCardSet());
    //     lv_cardSet.setAdapter(adapter);
    //
    }
