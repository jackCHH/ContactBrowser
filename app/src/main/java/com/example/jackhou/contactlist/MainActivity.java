package com.example.jackhou.contactlist;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


//ContactsContract.CommonDataKinds.Phone.NUMBER

public class MainActivity extends AppCompatActivity{

    private SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Cursor cur = getContacts(MainActivity.this);
        ListView contacts = (ListView) findViewById(R.id.contact_list);
        String[] bindFrom = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        int[] bindTo = {R.id.name, R.id.number};

        adapter = new SimpleCursorAdapter(this,R.layout.list_item, cur, bindFrom
                ,bindTo,0);
        contacts.setAdapter(adapter);



    }


    private Cursor getContacts(Context context){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection =
                    new String[]{ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

        String selection = ContactsContract.CommonDataKinds.Phone.TYPE+"="+ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME +
                    " COLLATE LOCALIZED ASC";
        return context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);





    }




}
