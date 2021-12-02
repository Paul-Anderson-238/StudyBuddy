package com.example.studybuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class EditCardView extends AppCompatActivity {
    static final String setlist = "SETLIST";
    CardSet set;
    Card currentCard;
    int currentCardIndex;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newFront, newBack;
    private Button saveButton, cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_view);

        //Stuff we'll need for the incoming intent
        Intent intent = getIntent();
        Gson gson = new Gson();

        //decodes the incoming Json into a Cardset, or nothing if the file doesn't exist
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

        //If the set doesn't currently exist
        if(cards == null){
            set = new CardSet(setName);
        }
        //Sets our temporary set to the incoming set
        else{
            set = gson.fromJson(String.valueOf(set), CardSet.class);
        }
        //defaults the view to the current card. displays proper set name
        currentCardIndex = 0;
        TextView text = (TextView) findViewById(R.id.setName);
        text.setText(setName);

        //displays the first card if there is a first card
        if (set.getSize() > 0){
            Card temp = set.getCard(currentCardIndex);
            EditText edit = (EditText) findViewById(R.id.cardFront);
            edit.setText(temp.getFront());
            edit = (EditText) findViewById(R.id.cardBack);
            edit.setText(temp.getBack());
            text = (TextView) findViewById(R.id.cardNumber);
            text.setText(currentCardIndex+1);
        }
    }

    public void nextCard(View view){
        //save current card
        EditText editor = (EditText) findViewById(R.id.cardFront);
        String front = editor.getText().toString();
        editor = (EditText) findViewById(R.id.cardBack);
        String back = editor.getText().toString();
        Card t = new Card(front, back);
        set.replace(currentCardIndex, t);

        //Make sure we aren't at the end of the card list
        if(currentCardIndex < (set.getSize()-1))
            currentCardIndex++;
        else
            currentCardIndex = 0;

        //set the display to the current card
        Card temp = set.getCard(currentCardIndex);
        editor = (EditText) findViewById(R.id.cardFront);
        editor.setText(temp.getFront());
        editor = (EditText) findViewById(R.id.cardBack);
        TextView edit = (TextView) findViewById(R.id.cardNumber);
        edit.setText(currentCardIndex+1);
    }

    public void prevCard(View view){
        //save current card
        EditText editor = (EditText) findViewById(R.id.cardFront);
        String front = editor.getText().toString();
        editor = (EditText) findViewById(R.id.cardBack);
        String back = editor.getText().toString();
        Card t = new Card(front, back);
        set.replace(currentCardIndex, t);

        //Make sure we aren't at the start of the card list
        if(currentCardIndex == 0)
            currentCardIndex = set.getSize()-1;
        else
            currentCardIndex--;

        //set the display to the current card
        Card temp = set.getCard(currentCardIndex);
        editor = (EditText) findViewById(R.id.cardFront);
        editor.setText(temp.getFront());
        editor = (EditText) findViewById(R.id.cardBack);
        TextView edit = (TextView) findViewById(R.id.cardNumber);
        edit.setText(currentCardIndex+1);
    }

    public void saveSet(View view){
        //SharedPreferences sharedPreferences = getSharedPreferences(setlist, MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();

        PrintWriter out = null;
        try{
            out = new PrintWriter(set.getId() + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        out.println(gson.toJson(set));
        out.close();
    }

    public void createNewCard(View view){
        //Code for creating a Popup window to add a new card. The dialog stuff creates the view
        dialogBuilder = new AlertDialog.Builder(this);
        final View cardPopupView = getLayoutInflater().inflate(R.layout.addcardpopup, null);
        newFront = (EditText) cardPopupView.findViewById(R.id.newFront);
        newBack = (EditText) cardPopupView.findViewById(R.id.newBack);
        saveButton = (Button) cardPopupView.findViewById(R.id.saveButton);
        cancelButton = (Button) cardPopupView.findViewById(R.id.cancelButton);

        dialogBuilder.setView(cardPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        //Saves the new card
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

        //deletes the new card
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}