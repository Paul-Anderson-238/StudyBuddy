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

    //Creates card set
    public CardSet(String id){
        this.id = id;
        this.cards = new ArrayList<>();
    }

    //Gets list of card sets
    public List<Card> getCards() {return cards;}

    //Gets the numbers of card sets
    public int getSize() {return cards.size();}

    //Gets the id for the card sets
    public String getId() {return id;}

    //Adds a card to the card set
    public void addCard(Card c){cards.add(c);}

    //Adds cards front and back to card set list
    public void addCard(String front, String back){
        Card c = new Card(front, back);
        cards.add(c);
    }

    //Saves the card set to a file
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

    //Replaces the card set that the user edits or removes from Card Set List
    public void replace(int index, Card replacement){
        cards.remove(index);
        cards.add(index, replacement);
    }

    //Gets the cards created by the user
    public Card getCard(String front){
        for(Card c : cards){
            if (c.getFront().equals(front))
                return c;
        }
        return null;
    }

    //Removes the card set depending on the index
    public void removeCard(int index){
        if(index <= cards.size())
            cards.remove(index);
    }

    //Gets the cards to display to the screen
    public Card getCard(int index){
        if(index < 0 || index > cards.size())
            return null;
        else
            return cards.get(index);
    }

    //Randomizes the cards in a card set
    public List<Card> randomizeCards(){
        List<Card> list = new ArrayList<>();
        list = cards;
        Collections.shuffle(list);
        return list;
    }
}
