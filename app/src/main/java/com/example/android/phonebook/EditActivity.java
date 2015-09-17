package com.example.android.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

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

    }

    public void cancelButton(View view){
//        int position = intent.getExtras().getInt("posisi");
//        TextView posisisi = (TextView)findViewById(R.id.posisi);
//        posisisi.setText(position + "");
        finish();
    }

    public void submitEdit(View view){
        int position = intent.getExtras().getInt("posisi");
        EditText name = (EditText)findViewById(R.id.name_edit_view);
        EditText number = (EditText)findViewById(R.id.number_edit_view);
        MainActivity.editContact(view, position, name.getText().toString(), number.getText().toString());
        Intent ix = new Intent(this,MainActivity.class);
        startActivity(ix);
        finish();
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
