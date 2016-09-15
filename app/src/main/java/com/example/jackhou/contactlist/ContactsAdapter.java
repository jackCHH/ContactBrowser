package com.example.jackhou.contactlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jackhou on 9/14/16.
 */
public class ContactsAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Contact> originalValues;
    private ArrayList<Contact> displayValues;
    LayoutInflater inflater;

    public ContactsAdapter(Context context,ArrayList<Contact> contacts){
        this.originalValues = contacts;
        this.displayValues = contacts;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        TextView name, number;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        ViewHolder holder = null;

        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(displayValues.get(position).getName());
        holder.number.setText(displayValues.get(position).getNumber());

        return convertView;
    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter(){

            @Override
            protected void publishResults(CharSequence search_query,FilterResults results) {

                displayValues = (ArrayList<Contact>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence search_query) {
                FilterResults results = new FilterResults();
                ArrayList<Contact> FilteredArrList = new ArrayList<Contact>();

                if (originalValues == null) {
                    originalValues = new ArrayList<Contact>(displayValues);
                }

                if (search_query == null || search_query.length() == 0) {

                    results.count = originalValues.size();
                    results.values = originalValues;

                } else {
                    search_query = search_query.toString().toLowerCase();

                    for (int i = 0; i < originalValues.size(); i++) {
                        String data = originalValues.get(i).getName();
                        if (data.toLowerCase().contains(search_query.toString())) {
                            FilteredArrList.add(new Contact(originalValues.get(i).getName(),originalValues.get(i).getNumber()));
                        }
                        String num_data = originalValues.get(i).getNumber();
                        if(num_data.contains(search_query.toString())){
                            FilteredArrList.add(new Contact(originalValues.get(i).getName(),originalValues.get(i).getNumber()));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }

        };

        return filter;
    }

    @Override
    public int getCount(){
        return displayValues.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

}
