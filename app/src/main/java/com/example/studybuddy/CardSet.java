package com.example.studybuddy;


import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CardSet {
    String id;
    List<Card> cards;

    public CardSet(String id){
        this.id = id;
        this.cards = new ArrayList<>();
    }

    public void addCard(Card c){
        cards.add(c);
    }

    public void addCard(String front, String back){
        Card c = new Card(front, back);
        cards.add(c);
    }

    public void save(){
        Gson gson = new Gson();
        PrintWriter out = null;
        try{
            out = new PrintWriter(id + ".txt");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        out.println(gson.toJson(this));
        out.close();
    }
}
