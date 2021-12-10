package com.example.studybuddy;


import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class CardSet {
    String id;
    List<Card> cards;

    public CardSet(String id){
        this.id = id;
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {return cards;}

    public int getSize() {return cards.size();}

    public String getId() {return id;}

    public void addCard(Card c){cards.add(c);}

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

    public void replace(int index, Card replacement){
        cards.remove(index);
        cards.add(index, replacement);
    }

    public Card getCard(String front){
        for(Card c : cards){
            if (c.getFront().equals(front))
                return c;
        }
        return null;
    }

    public void removeCard(int index){
        if(index <= cards.size())
            cards.remove(index);
    }

    public Card getCard(int index){
        if(index < 0 || index > cards.size())
            return null;
        else
            return cards.get(index);
    }

    public List<Card> randomizeCards(){
        List<Card> list = new ArrayList<>();
        list = cards;
        Collections.shuffle(list);
        return list;
    }
}
