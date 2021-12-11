package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizMenu extends AppCompatActivity {
    List<String> setList;
    String setName;
    int displayIndex;

    TextView currentSetName;
    //Creates the view for the Quiz Menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);
        loadList();
        displayIndex = 0;
        if(setList.size() != 0) {
            setName = setList.get(displayIndex);
            currentSetName = findViewById(R.id.currentSetName);
            currentSetName.setText(setName);
        }
    }

    //Loads the list of card sets to quiz through
    public void loadList() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("SETLIST", new HashSet<>());
        setList = new ArrayList<>(set);
    }

    //Goes to the previous set in the quiz card set list
    public void prevSetName(View view){
        if(displayIndex == 0)
            displayIndex = setList.size()-1;
        else if(setList.size()!= 1)
            displayIndex--;
        setName = setList.get(displayIndex);
        TextView t = findViewById(R.id.currentSetName);
        t.setText(setName);
    }

    //Goes to the next set in the quiz card set list
    public void nextSetName(View view){
        if(displayIndex == setList.size()-1)
            displayIndex = 0;
        else if(setList.size() != 1)
            displayIndex++;
        setName = setList.get(displayIndex);
        TextView t = findViewById(R.id.currentSetName);
        t.setText(setName);
    }

    //Takes the user to the quiz view to take a quiz on the selected card set
    public void takeQuiz (View view){
        Intent intent = new Intent(this, QuizView.class);
        intent.putExtra("setname", setName);
        startActivity(intent);
    }
}