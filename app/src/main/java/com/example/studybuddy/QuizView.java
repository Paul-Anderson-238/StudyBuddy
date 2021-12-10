package com.example.studybuddy;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
            intent = new Intent(this, QuizMenu.class);
            Toast.makeText(this, "ERROR: MUST HAVE AT LEAST ONE CARD IN SET", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }

        //Shuffle the cards and set the display to the new first card
        set.randomizeCards();
        currentCard = set.getCard(0);
        displayCard();
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

    public void nextCard(View view){
        if(currentCardIndex == set.getSize()-1)
            currentCardIndex = 0;
        else if(set.getSize() != 1)
            currentCardIndex++;
        //setName = setList.get(displayIndex);
        //TextView t = findViewById(R.id.currentSet);
        //t.setText(setName);
    }
    public void removeCard(View view){
        //set.remove(currentCardIndex);
    }
    void displayCard() {
        TextView e = findViewById(R.id.cFront);
        e.setText(currentCard.getFront());
    }
    public void flipCard(){

    }
    }