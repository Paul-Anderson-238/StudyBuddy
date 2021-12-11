package com.example.studybuddy;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuizView extends AppCompatActivity {
    CardSet set;
    Card currentCard;
    int currentCardIndex;
    String filename;
    String setName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_view);

        //Name of Set we'll need from the incoming intent to read each card in the list
        Intent intent = getIntent();
        setName = intent.getStringExtra("setname");

        //read the File
        readFile();

        //Check to make sure there is at least one card in the set
        if(set.getSize() == 0){
            Intent i = new Intent(this, QuizMenu.class);
            Toast.makeText(this, "ERROR: MUST HAVE AT LEAST ONE CARD IN SET", Toast.LENGTH_LONG).show();
            startActivity(i);
        }
        else {
            //Shuffle the cards and set the display to the new first card
            set.randomizeCards();
            currentCard = set.getCard(0);
            displayCard();
        }
    }

    public void readFile(){
        //A few preliminary set up variables
        filename = setName + ".txt";
        Gson gson = new Gson();
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
    }
    public void removeCard(){
        set.removeCard(currentCardIndex);
    }
    void displayCard() {
        TextView e = findViewById(R.id.cFront);
        e.setText(currentCard.getFront());
    }

    public void flipCard(View view) {
        //Code for creating a Popup window to add a new card. The dialog stuff creates the view
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View backPopupView = getLayoutInflater().inflate(R.layout.backofcardpopup, null);
        TextView backOfCard = backPopupView.findViewById(R.id.cBack);
        backOfCard.setText(currentCard.getBack());
        Button passCard = backPopupView.findViewById(R.id.passButton);
        Button retryCard = backPopupView.findViewById(R.id.retryButton);

        dialogBuilder.setView(backPopupView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        //Puts card back into quiz list
        retryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Putting Card Back in Set", Toast.LENGTH_SHORT).show();
                removeCard();
                set.addCard(currentCard);
                currentCard = set.getCard(0);
                dialog.dismiss();
            }
        });

        //deletes the new card
        passCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Nice Work!", Toast.LENGTH_SHORT).show();
                removeCard();
                if (set.getSize() == 0) {
                    Toast.makeText( getApplicationContext(), "Congratulations!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    dialog.dismiss();
                    startActivity(intent);
                }
                else {
                    currentCard = set.getCard(0);
                    displayCard();
                    dialog.dismiss();
                }
            }
        });
    }

}