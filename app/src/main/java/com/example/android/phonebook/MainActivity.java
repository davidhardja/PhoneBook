package com.example.android.phonebook;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    MyCustomAdapter dataAdapter=null;


    static ArrayList<Contact> contactsList = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayListView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayListView(){
        if(contactsList.isEmpty()){
            Contact Acong = new Contact("Acong","08112345678",null);
            contactsList.add(Acong);
            Contact Burton = new Contact("Burton","08212345679",null);
            contactsList.add(Burton);
            Contact Cangcimen = new Contact("Cangcimen","08312345680",null);
            contactsList.add(Cangcimen);
            Contact Dodo = new Contact("Dodo","08412345681",null);
            contactsList.add(Dodo);
            Contact Encok = new Contact("Encok","08512345682",null);
            contactsList.add(Encok);
        }

        dataAdapter = new MyCustomAdapter(this,R.layout.list_view_layout,contactsList);
        ListView lv = (ListView)findViewById(R.id.contact_list_view);
        lv.setAdapter(dataAdapter);

        lv.setOnItemClickListener(mMessageClickedHandler);

    }

    public static void editContact(View view, int position, String name, String number, Bitmap foto){
        Contact x = contactsList.get(position);
        x.setName(name);
        x.setNumber(number);
        x.setFoto(foto);


    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // Do something in response to the click

            goDetails(v, position);


        }
    };

    public void add(View view){
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
        finish();


    }

    public static void addContact(View view, String name, String number, Bitmap b){

        Contact Contact = new Contact(name,number,b);
        contactsList.add(Contact);

    }

    private void goDetails(View view, int position){
        Intent intent = new Intent(this,DetailsActivity.class);
        Contact c = dataAdapter.contactList.get(position);
        String name = c.getName();
        String number = c.getNumber();
        intent.putExtra("name",name);
        intent.putExtra("number",number);
        intent.putExtra("position",position);
        startActivity(intent);
    }



}
