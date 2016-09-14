package com.example.jackhou.contactlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jackhou on 9/14/16.
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {

    public ContactsAdapter(Context context,ArrayList<Contact> contacts){
        super(context,0,contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Contact contact = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView number =(TextView) convertView.findViewById(R.id.number);

        name.setText(contact.getName());
        number.setText(contact.getNumber());

        return convertView;
    }
}
