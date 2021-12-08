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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

public class EditCardMenu extends AppCompatActivity {
    Set<String> setList;
    public String setName;
    SharedPreferences sharedpreferences;

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
        loadList();
    }

    public void loadList() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
        Gson gson = new Gson();
        String string = sharedPreferences.getString( "SETLIST", "" );
        setList = gson.fromJson(string, Set.class);
    }

    //Create Intent to connect pass set list to edit card view. See main, pass the set name we are trying to edit.
    public void editSet(View view) {
        Intent intent = new Intent(this, EditCardView.class);
        intent.putExtra("setname", (String) setName);
        startActivity(intent);
    }

    public void createNewSet(View view) {
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

                    //create an empty file for the new set
                    Gson gson = new Gson();
                    PrintWriter out = null;
                    try{
                        out = new PrintWriter(setName + ".txt");
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    out.println("");
                    out.close();

                    //add the new setName to the List of setnames
                    setList.add(setName);

                    //update the shared preferences
                    sharedpreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    edit.putStringSet("SETLIST", setList);

                    dialog.dismiss();
                }
        });
    }

    //Add a New Set
    //public void newSet(View view) {
        //Intent intent = new Intent(this, EditCardView.class);
        //intent.putExtra("setname", (String) setName);
        //startActivity(intent);
    //}
}