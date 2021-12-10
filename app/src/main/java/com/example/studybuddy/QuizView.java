package com.example.studybuddy;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class QuizView extends AppCompatActivity {
    CardSet set;
    Card currentCard;
    int currentCardIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_view);

//Name of Set we'll need from the incoming intent to read each card in the list
        Intent intent = getIntent();
        Gson gson = new Gson();

        //Make sure the set has at least 1 card and display the front
        if (set.getSize() > 0){
            Card front = set.getCard(currentCardIndex);
            TextView text = (TextView) findViewById(R.id.numberOfCards);
            text.setText((currentCardIndex + 1) + "/" + (set.getSize()));
        }

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
        EditText e = findViewById(R.id.cardFront);
        e.setText(currentCard.getFront());
    }
    public void flipCard(){

    }
    }