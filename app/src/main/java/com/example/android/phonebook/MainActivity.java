package com.example.android.phonebook;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {
    MyCustomAdapter dataAdapter=null;
    static SQLiteDatabase sqLiteDatabase = null;
    Cursor cursor = null;
    static DatabaseContact databaseContact = null;


    static ArrayList<Contact> contactsList = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseContact = new DatabaseContact(this);

        sqLiteDatabase = databaseContact.getWritableDatabase();
        databaseContact.createTable(sqLiteDatabase);
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

//        Contact Acong = new Contact("Acong","08112345678",null);
//        contactsList.add(Acong);
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM contactList", null);
        if(contactsList.isEmpty()){
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                String name = cursor.getString(1);
                String number = cursor.getString(2);
                Bitmap photo = DbBitmapUtility.getImage(cursor.getBlob(3));
                Contact c = new Contact(name,number,photo);
                contactsList.add(c);
            }
        }


        dataAdapter = new MyCustomAdapter(this,R.layout.list_view_layout,contactsList);
        ListView lv = (ListView)findViewById(R.id.contact_list_view);
        lv.setAdapter(dataAdapter);

        lv.setOnItemClickListener(mMessageClickedHandler);

    }

    public static void editContact(View view, int position, String name, String number, Bitmap foto){
        Contact x = contactsList.get(position);
        String oldName = x.getName();
        x.setName(name);
        x.setNumber(number);
        x.setFoto(foto);

        byte[] image = DbBitmapUtility.getBytes(foto);
        databaseContact.editData(sqLiteDatabase, oldName, name, number, image);

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

    public static void removeContact(View v, String name, int posisi){

        databaseContact.removeData(sqLiteDatabase,name);
        contactsList.remove(posisi);
    }

    public static void addContact(View view, String name, String number, Bitmap b){

        Contact Contact = new Contact(name,number,b);
        contactsList.add(Contact);

        byte[] image = DbBitmapUtility.getBytes(b);
        databaseContact.addData(sqLiteDatabase,name,number,image);

    }

    private void goDetails(View view, int position){

        Intent intent = new Intent(this,DetailsActivity.class);
        Contact c = dataAdapter.contactList.get(position);
        String name = c.getName();
        String number = c.getNumber();
        intent.putExtra("name",name);
        intent.putExtra("number",number);
        intent.putExtra("position", position);
        startActivity(intent);
        finish();
    }



}
