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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class EditActivity extends Activity {
    static int REQUEST_IMAGE_CAPTURE;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText tname = (EditText)findViewById(R.id.name_edit_view);
        EditText tnumber = (EditText)findViewById(R.id.number_edit_view);
        tname.setText(intent.getExtras().getString("name"));
        tnumber.setText(intent.getExtras().getString("number"));
        int position = intent.getExtras().getInt("posisi");
        Bitmap x = MainActivity.contactsList.get(position).getFoto();


        if(x!=null){


            ImageView photoImageView = (ImageView)findViewById(R.id.edit_image_view);
            photoImageView.setImageBitmap(x);

        }

    }

    public void cancelButton(View view){
//        int position = intent.getExtras().getInt("posisi");
//        TextView posisisi = (TextView)findViewById(R.id.posisi);
//        posisisi.setText(position + "");
        finish();
    }

    public void submitEdit(View view){
        int position = intent.getExtras().getInt("posisi");
        REQUEST_IMAGE_CAPTURE = position;
        EditText name = (EditText)findViewById(R.id.name_edit_view);
        EditText number = (EditText)findViewById(R.id.number_edit_view);
        ImageView imageView = (ImageView) findViewById(R.id.edit_image_view);
        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();

        MainActivity.editContact(view, position, name.getText().toString(), number.getText().toString(),bm);
        Intent ix = new Intent(this,MainActivity.class);
        startActivity(ix);
        finish();
    }

    public void editImage(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    Bitmap photo;
    File photofile;

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

                ImageView iv = (ImageView)findViewById(R.id.edit_image_view);

                iv.setImageBitmap(photo);
                s.SaveImage(this, photo);
            }else{

                Toast.makeText(this, "oops cant save photo", Toast.LENGTH_LONG).show();

            }
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
}
