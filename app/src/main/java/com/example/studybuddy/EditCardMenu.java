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

import java.io.Serializable;
import java.util.List;

public class EditCardMenu extends AppCompatActivity {
    List<String> setList;
    public String setName;

    //data for creating a popup screen
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newSetName;
    private Button saveNewSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_menu);

        Intent intent = getIntent();
        setList = (List<String>) intent.getSerializableExtra("SETLIST");
    }

    //Create shared preferences for the name of lists
    public void listName(View view) {
       SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
        Gson gson = new Gson();
        String string = sharedPreferences.getString(setName, "setname");
        setList = gson.fromJson(string, List.class);
    }

    //Create Intent to connect pass set list to edit card view. See main, pass the set name we are trying to edit.
    public void editSet(View view){
        Intent intent = new Intent(this, EditCardMenu.class);
        intent.putExtra("setname", (Serializable) setName);
        startActivity(intent);
    }

    //Add a New Set
    public void newSet(View view) {
        Intent intent = new Intent(this, EditCardMenu.class);
        dialogBuilder = new AlertDialog.Builder(this);
        final View newSetPopupView = getLayoutInflater().inflate(R.layout.newsetpopup, null);
        newSetName = (EditText) findViewById(R.id.newSetName);
        saveNewSet = (Button) findViewById(R.id.saveNewSet);

        dialogBuilder.setView(newSetPopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        saveNewSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editor = (EditText) findViewById(R.id.newSetName);
                setName = editor.getText().toString();
            }
        });
    }
}