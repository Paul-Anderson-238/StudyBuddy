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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditCardMenu extends AppCompatActivity {
    List<String> setList;
    String setName;
    SharedPreferences sharedpreferences;
    int displayIndex;

    //data for creating a popup screen
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText newSetName;
    Button saveNewSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_menu);
        loadList();
        displayIndex = 0;
        if(setList.size() != 0){
            setName = setList.get(displayIndex);
            TextView t = findViewById(R.id.currentSet);
            t.setText(setName);
        }
    }

    public void loadList() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("SETLIST", new HashSet<>());
        if (set != null)
            setList.addAll(set);
        else
            setList = new ArrayList<>();
    }

    //Create Intent to connect pass set list to edit card view. See main, pass the set name we are trying to edit.
    public void editSet(View view) {
        Intent intent = new Intent(this, EditCardView.class);
        intent.putExtra("setname", setName);
        startActivity(intent);
    }

    public void createNewSet(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View newSetPopupView = getLayoutInflater().inflate(R.layout.newsetpopup, null);
        newSetName = newSetPopupView.findViewById(R.id.newSetNameText);
        saveNewSet = newSetPopupView.findViewById(R.id.saveNewSetButton);

        dialogBuilder.setView(newSetPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        saveNewSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editor = newSetPopupView.findViewById(R.id.newSetNameText);
                setName = editor.getText().toString();
                String filename = setName + ".txt";

                FileOutputStream fos = null;

                if (!setName.equals("")) {
                    try {
                        fos = openFileOutput(filename, MODE_PRIVATE);
                        fos.write("".getBytes());
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

                    //add the new setName to the List of setnames
                    setList.add(setName);

                    //update the shared preferences
                    sharedpreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedpreferences.edit();
                    setList.add(setName);

                    Set<String> s = new HashSet<>(setList);

                    edit.putStringSet("SETLIST", s);
                    edit.apply();
                }

                dialog.dismiss();

                TextView temp = findViewById(R.id.currentSet);
                temp.setText(setName);
            }
        });
    }

    public void prevSetName(View view){
        if(displayIndex == 0)
            displayIndex = setList.size()-1;
        else if(setList.size()!= 1)
            displayIndex--;
        setName = setList.get(displayIndex);
        TextView t = findViewById(R.id.currentSet);
        t.setText(setName);
    }

    public void nextSetName(View view){
        if(displayIndex == setList.size()-1)
            displayIndex = 0;
        else if(setList.size() != 1)
            displayIndex++;
        setName = setList.get(displayIndex);
        TextView t = findViewById(R.id.currentSet);
        t.setText(setName);
    }
}