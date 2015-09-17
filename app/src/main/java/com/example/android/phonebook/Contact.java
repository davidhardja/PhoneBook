package com.example.android.phonebook;

/**
 * Created by bocist-8 on 15/09/15.
 */
public class Contact {
    String name="";
    String number="";

    public Contact(String name, String number){
        super();
        this.name = name;
        this.number = number;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
