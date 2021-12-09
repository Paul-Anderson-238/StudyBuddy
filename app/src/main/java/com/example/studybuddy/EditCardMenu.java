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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public class EditCardMenu extends AppCompatActivity {
    Set<String> setList;
    String setName;
    SharedPreferences sharedpreferences;

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
    }

    public void loadList() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHAREDPREF", MODE_PRIVATE);
        setList = sharedPreferences.getStringSet("SETLIST", new HashSet<>());
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
                    edit.putStringSet("SETLIST", setList);
                    edit.commit();
                }

                dialog.dismiss();

                TextView temp = findViewById(R.id.currentSet);
                temp.setText(setName);
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