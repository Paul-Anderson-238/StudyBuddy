package com.example.studybuddy;


import java.util.ArrayList;
import java.util.List;

public class CardSet {
    String id;
    List<Card> cards;

    public CardSet(String id){
        this.id = id;
        this.cards = new ArrayList<Card>();
    }

    public void addCard(Card c){
        cards.add(c);
    }

    public void addCard(String front, String back){
        Card c = new Card(front, back);
        cards.add(c);
    }
}
