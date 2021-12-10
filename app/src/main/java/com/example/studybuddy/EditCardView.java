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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditCardView extends AppCompatActivity {
    String filename;
    String setName;
    CardSet set;
    Card currentCard;
    int currentCardIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_view);

        //Stuff we'll need for the incoming intent
        Intent intent = getIntent();
        Gson gson = new Gson();

        //Displays the name of the set
        setName = intent.getStringExtra("setname");
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
                sb.append(inputFromFile).append("\n");
            }

            set = gson.fromJson(sb.toString(), CardSet.class);

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
        if(set == null)
            set = new CardSet(setName);

        //If there are no cards, default to creating a new card view
        if (set.getSize() == 0)
            createNewCard(null);

        //sets the currentCardIndex to 0, starts on the first card if it exists
        currentCardIndex = 0;

        //displays the first card if there is a first card
        if (set.getSize() > 0){
            currentCard = set.getCard(currentCardIndex);
            EditText edit = findViewById(R.id.cardFront);
            edit.setText(currentCard.getFront());
            edit = findViewById(R.id.cardBack);
            edit.setText(currentCard.getBack());
        }
    }

    public void nextCard(View view){
        //save current card
        EditText editor = findViewById(R.id.cardFront);
        String front = editor.getText().toString();
        editor = findViewById(R.id.cardBack);
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
        EditText editor = findViewById(R.id.cardFront);
        String front = editor.getText().toString();
        editor = findViewById(R.id.cardBack);
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View cardPopupView = getLayoutInflater().inflate(R.layout.addcardpopup, null);
        Button saveButton = cardPopupView.findViewById(R.id.saveButton);
        Button cancelButton = cardPopupView.findViewById(R.id.cancelButton);

        dialogBuilder.setView(cardPopupView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        //Saves the new card
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editor = cardPopupView.findViewById(R.id.newFront);
                String front = editor.getText().toString();
                editor = cardPopupView.findViewById(R.id.newBack);
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