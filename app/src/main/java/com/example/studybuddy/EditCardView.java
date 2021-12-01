package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EditCardView extends AppCompatActivity {
    static final String setlist = "SETLIST";
    CardSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_view);

        Intent intent = getIntent();
        Gson gson = new Gson();

        String cards = null;
        String setName = intent.getStringExtra("setname");
        File inputFile = new File(setName + ".txt");
        Scanner input = null;
        try{
            input = new Scanner(inputFile);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(input.hasNext()){
            cards = input.nextLine();
        }

        if(cards == null){
            set = new CardSet(setName);
        }
        else{
            set = gson.fromJson(String.valueOf(set), CardSet.class);
        }
    }

    public void returnToPrev(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(setlist, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }
}