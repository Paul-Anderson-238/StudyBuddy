package com.example.studybuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EditCardView extends AppCompatActivity {
    static final String setlist = "SETLIST";
    CardSet set;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newFront, newBack;
    private Button saveButton, cancelButton;


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

    public void createNewCard(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View cardPopupView = getLayoutInflater().inflate(R.layout.addcardpopup, null);
        newFront = (EditText) cardPopupView.findViewById(R.id.newFront);
        newBack = (EditText) cardPopupView.findViewById(R.id.newBack);
        saveButton = (Button) cardPopupView.findViewById(R.id.saveButton);
        cancelButton = (Button) cardPopupView.findViewById(R.id.cancelButton);

        dialogBuilder.setView(cardPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editor = (EditText) findViewById(R.id.newFront);
                String front = editor.getText().toString();
                editor = (EditText) findViewById(R.id.newBack);
                String back = editor.getText().toString();
                set.addCard(front, back);
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}