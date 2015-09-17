package com.example.android.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

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

//        String ser = SerializeObject.objectToString(MainActivity.contactsList);
//        if (ser != null && !ser.equalsIgnoreCase("")) {
//            SerializeObject.WriteSettings(this, ser, "contactData.dat");
//        } else {
//            SerializeObject.WriteSettings(this, "", "contactData.dat");
//        }

        MainActivity.addContact(view,name.getText().toString(),number.getText().toString());

        startActivity(intent);
        finish();
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
