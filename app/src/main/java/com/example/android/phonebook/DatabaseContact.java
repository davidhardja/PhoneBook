package com.example.android.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.sql.Blob;

/**
 * Created by bocist-8 on 18/09/15.
 */
public class DatabaseContact extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "databasecontact";
    public static final String CONTACT_NAME = "name";
    public static final String CONTACT_NUMBER = "number";
    public static final String CONTACT_PHOTO = "photo";
    public static boolean dummy = false;
    public DatabaseContact(Context context){
        super(context, DATABASE_NAME, null, 1);


    }

    public void createTable(SQLiteDatabase sqLiteDatabase){
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contactList");
        sqLiteDatabase.execSQL("CREATE TABLE if not exists contactList (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT, photo BLOB);");
//        if(dummy==false){
//
//            crateDummyData(sqLiteDatabase);
//            dummy = true;
//        }

    }

    public void crateDummyData(SQLiteDatabase sqLiteDatabase){
        ContentValues cv = new ContentValues();
        cv.put(CONTACT_NAME,"Acong");
        cv.put(CONTACT_NUMBER,"08112345678");
        sqLiteDatabase.insert("contactList", CONTACT_NAME,cv);
        cv.put(CONTACT_NAME,"Burton");
        cv.put(CONTACT_NUMBER,"08112345679");
        sqLiteDatabase.insert("contactList", CONTACT_NAME,cv);
        cv.put(CONTACT_NAME,"Cangcimen");
        cv.put(CONTACT_NUMBER,"08112345680");
        sqLiteDatabase.insert("contactList", CONTACT_NAME,cv);
        cv.put(CONTACT_NAME,"Dono");
        cv.put(CONTACT_NUMBER,"08112345681");
        sqLiteDatabase.insert("contactList", CONTACT_NAME,cv);
        cv.put(CONTACT_NAME,"Encok");
        cv.put(CONTACT_NUMBER,"08112345682");
        sqLiteDatabase.insert("contactList", CONTACT_NAME,cv);

    }

    public static void removeData(SQLiteDatabase sqLiteDatabase, String name)throws SQLiteException{
        System.out.println(name);
        sqLiteDatabase.delete("contactList", "name = '"+ name+"'",null);
    }


    public static void addData(SQLiteDatabase sqLiteDatabase, String name, String number, byte[] photo) throws SQLiteException{
        ContentValues cv = new ContentValues();
        cv.put(CONTACT_NAME,name);
        cv.put(CONTACT_NUMBER,number);
        cv.put(CONTACT_PHOTO, photo);
        sqLiteDatabase.insert("contactList", CONTACT_NAME, cv);



    }



    public static void editData(SQLiteDatabase sqLiteDatabase, String oldName, String name, String number, byte[] photo) throws SQLiteException{
        ContentValues cv = new ContentValues();
        cv.put(CONTACT_NAME,name);
        cv.put(CONTACT_NUMBER,number);
        cv.put(CONTACT_PHOTO, photo);
        //String sql = "UPDATE contactList SET name='"+name+"', number='"+number+"', photo='"+photo+"' WHERE id = "+position+";";
        sqLiteDatabase.update("contactList", cv, "name " + "="+oldName, null);
        //sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
