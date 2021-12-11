package com.example.studybuddy;

public class Card {
    String front;
    String back;
    String notes;

    //Creates the card with a front and back side
    public Card(String front, String back){
        this.front = front;
        this.back = back;
    }

    //Gets the user edited front side
    public String getFront(){return front;}

    //Sets that customized front side
    public void setFront(String front){this.front = front;}

    //Gets the user edited back side
    public String getBack(){return back;}

    //Sets that customized back side
    public void setBack(String back) { this.back = back;}
}
