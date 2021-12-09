package com.example.studybuddy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditCardView extends AppCompatActivity {
    static final String setlist = "SETLIST";
    String filename;
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

        //Displays the name of the set
        TextView t = findViewById(R.id.setName);
        t.setText(setName);

        //Begins decoding the file
        filename = setName + ".txt";

        FileInputStream fis = null;

        try {
            fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String inputFromFile;

            while((inputFromFile = br.readLine()) != null){
                sb.append(inputFromFile);
            }

            set = gson.fromJson(inputFromFile, CardSet.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //If the set doesn't currently exist
        if(cards == null){
            set = new CardSet(setName);
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
        currentCard = set.getCard(currentCardIndex);
        displayCard();
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
        currentCard = set.getCard(currentCardIndex);
        displayCard();
    }

    public void saveSet(View view){
        FileOutputStream fos = null;
        Gson gson = new Gson();
        String saveData = gson.toJson(set);
        try {
            fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(saveData.getBytes());
            Toast.makeText(getApplicationContext(), "Saved to " + getFilesDir() + "/" + filename, Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
                EditText editor = (EditText) cardPopupView.findViewById(R.id.newFront);
                String front = editor.getText().toString();
                editor = (EditText) cardPopupView.findViewById(R.id.newBack);
                String back = editor.getText().toString();
                set.addCard(front, back);
                Toast.makeText(getApplicationContext(), "Saved Card", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                currentCard = set.getCard(set.getSize()-1);
                currentCardIndex = set.getSize()-1;
                displayCard();
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

    void displayCard(){
        EditText e = findViewById(R.id.cardFront);
        e.setText(currentCard.getFront());
        e = findViewById(R.id.cardBack);
        e.setText(currentCard.getBack());
    }
}