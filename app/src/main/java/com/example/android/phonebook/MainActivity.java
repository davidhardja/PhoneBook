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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private TextView info;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
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

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        displayListView();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        displayListView();
    }

    @Override
    public void recreate() {
        super.recreate();
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

    public void big(View view){
        ListView image = (ListView)findViewById(R.id.contact_list_view);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.biganimation);
        image.startAnimation(animation);
    }

    public void small(View view){
        ListView image = (ListView)findViewById(R.id.contact_list_view);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.smallanimation);
        image.startAnimation(animation);
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

    }



}
