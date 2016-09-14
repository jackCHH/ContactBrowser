package com.example.jackhou.contactlist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


//ContactsContract.CommonDataKinds.Phone.NUMBER

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter adapter;
    private ContactsAdapter contact_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Contact> all_contacts = new ArrayList<Contact>();
        Cursor cur = getContacts(MainActivity.this);


        ListView contacts = (ListView) findViewById(R.id.contact_list);



        while (cur.moveToNext()) {
            String name = cur.getString(cur.getColumnIndex("display_name"));
            String phone_number = cur.getString(cur.getColumnIndex("data1"));

            Contact current_contact = new Contact(name, phone_number);
            all_contacts.add(current_contact);

        }

        cur.close();

        contact_adapter = new ContactsAdapter(this, all_contacts);
        contacts.setAdapter(contact_adapter);


        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startDialer(all_contacts.get(position).getNumber());
            }
        });

    }


    private Cursor getContacts(Context context) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection =
                new String[]{ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER};

        String selection = ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        return context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);


    }

    private void startDialer(String current_number){

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+current_number));
        startActivity(intent);

    }


}
