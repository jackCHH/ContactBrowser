package com.example.jackhou.contactlist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
    - MVP, helps w/ testing.
    - Background the cursor. 1. ContentLoader. 2. RxJava <- loook up
    - Recycler view will force the view holder pattern.
    - Reduce the extra white spaces. Include the style guide.
    - Butterknife will clean up findVIewBYId().
    - Keep methods small.

 */


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.search) EditText search;
    @BindView(R.id.contact_list) ListView contacts; // Look up RecyclerView
    private ContactsAdapter contact_adapter; // CamelCase
    private ArrayList<Contact> all_contacts = new ArrayList<Contact>(); //CamelCase


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Cursor cur = getContacts(MainActivity.this);
        traverseCursor(cur);

        contact_adapter = new ContactsAdapter(this, all_contacts);
        contacts.setAdapter(contact_adapter);

        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startDialer(all_contacts.get(position).getNumber());
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contact_adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    // clean up. Check for nullback for cursors.
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

    private void traverseCursor(Cursor cur){
        while (cur.moveToNext()) {
            String name = cur.getString(cur.getColumnIndex("display_name"));
            String phone_number = cur.getString(cur.getColumnIndex("data1"));

            Contact current_contact = new Contact(name, phone_number);
            all_contacts.add(current_contact);

        }

        cur.close();
    }


}
