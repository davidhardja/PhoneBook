package com.example.android.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class AddActivity extends Activity {
    public static int REQUEST_IMAGE_CAPTURE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void cancel(View view){
        finish();

    }

    public void submitAdd(View view){
        EditText name = (EditText)findViewById(R.id.add_name_edit_view);
        EditText number = (EditText)findViewById(R.id.add_number_edit_view);
        Intent intent = new Intent(this,MainActivity.class);

        REQUEST_IMAGE_CAPTURE = 0;
        ImageView imageView = (ImageView) findViewById(R.id.add_image_view);
        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        MainActivity.addContact(view,name.getText().toString(),number.getText().toString(),bm);
        startActivity(intent);
        finish();
    }

    Bitmap photo;
    File photofile;

    public void addImage(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==REQUEST_IMAGE_CAPTURE){
            try{
                photo = (Bitmap) data.getExtras().get("data");

            }catch(NullPointerException ex){
                photo = BitmapFactory.decodeFile(photofile.getAbsolutePath());
            }
            Save s = new Save();
            if(photo!=null){

                ImageView iv = (ImageView)findViewById(R.id.add_image_view);

                iv.setImageBitmap(photo);
                s.SaveImage(this, photo);
            }else{

                Toast.makeText(this, "oops cant save photo", Toast.LENGTH_LONG).show();

            }
        }


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
}
