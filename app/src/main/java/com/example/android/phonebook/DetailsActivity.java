package com.example.android.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String name = intent.getExtras().getString("name");
        String number = intent.getExtras().getString("number");
        TextView nameTextView = (TextView)findViewById(R.id.details_name_text_view);
        nameTextView.setText(name);
        TextView numberTextView = (TextView)findViewById(R.id.details_number_text_view);
        numberTextView.setText(number);
    }

    public void deleteContact(View view){
//        int position = intent.getExtras().getInt("position");
//        TextView posisisi = (TextView)findViewById(R.id.posisi2);
//        posisisi.setText(position+"");

        int position = intent.getExtras().getInt("position");

        MainActivity.contactsList.remove(position);
        Intent ix = new Intent(this,MainActivity.class);
        startActivity(ix);
        finish();
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
