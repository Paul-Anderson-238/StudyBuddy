package com.example.studybuddy;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        //Make sure the set has at least 1 card
        if (set.getSize() > 0){
            Card temp = set.getCard(currentCardIndex);
            TextView text = (TextView) findViewById(R.id.cardFront);
            text.setText(currentCardIndex+1);
        }

    }

}