package com.example.android.phonebook;

import android.graphics.Bitmap;

/**
 * Created by bocist-8 on 15/09/15.
 */
public class Contact {
    String name="";
    String number="";
    Bitmap foto;

    public Contact(String name, String number, Bitmap b){
        super();
        this.name = name;
        this.number = number;
        this.foto = b;

    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public Bitmap getFoto() {
        return foto;
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
