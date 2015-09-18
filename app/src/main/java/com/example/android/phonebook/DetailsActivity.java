package com.example.android.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String name = intent.getExtras().getString("name");
        String number = intent.getExtras().getString("number");
        int position = intent.getExtras().getInt("position");
        System.out.println(position);
        Bitmap x = MainActivity.contactsList.get(position).getFoto();
        if(x!=null){
            ImageView photoImageView = (ImageView)findViewById(R.id.details_image_view);
            photoImageView.setImageBitmap(x);

        }

        TextView nameTextView = (TextView)findViewById(R.id.details_name_text_view);
        nameTextView.setText(name);
        TextView numberTextView = (TextView)findViewById(R.id.details_number_text_view);
        numberTextView.setText(number);
    }

    public void deleteContact(View view){
        System.out.println("OIIII"+intent.getExtras().getInt("position"));
        String name = intent.getExtras().getString("name");
        MainActivity.removeContact(view, name, intent.getExtras().getInt("position"));
        Intent ix = new Intent(this,MainActivity.class);
        startActivity(ix);
    }

    public void goEditActivity(View view){
        String name = intent.getExtras().getString("name");
        String number = intent.getExtras().getString("number");
        int position = intent.getExtras().getInt("position");
        intent = new Intent(this,EditActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("number", number);
        intent.putExtra("posisi",position);

        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
