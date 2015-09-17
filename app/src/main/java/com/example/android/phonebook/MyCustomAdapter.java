package com.example.android.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bocist-8 on 16/09/15.
 */
public class MyCustomAdapter extends ArrayAdapter<Contact> {
    ArrayList<Contact> contactList;
    Context context ;

    public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Contact> contactList){

        super(context,textViewResourceId,contactList);

        this.context = context;
        this.contactList = new ArrayList<Contact>();
        this.contactList.addAll(contactList);

    }

    private class ViewHolder{
        TextView name;
        TextView number;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );;
            convertView = inflater.inflate(R.layout.list_view_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name_text_view);
            viewHolder.number = (TextView) convertView.findViewById(R.id.number_text_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Contact contact = contactList.get(position);
        viewHolder.name.setText(contact.getName());
        viewHolder.number.setText(contact.getNumber());

        return convertView;

    }


}
