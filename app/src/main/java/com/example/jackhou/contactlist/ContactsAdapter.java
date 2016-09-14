package com.example.jackhou.contactlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
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

    private class ViewHolder{
        LinearLayout llContainer;
        TextView name, number;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        ViewHolder holder = null;

        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);
            holder.llContainer = (LinearLayout)convertView.findViewById(R.id.container);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView.findViewById(R.id.number);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(displayValues.get(position).name);
        holder.number.setText(displayValues.get(position).number);

        return convertView;
    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter(){

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                displayValues = (ArrayList<Contact>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Contact> FilteredArrList = new ArrayList<Contact>();

                if (originalValues == null) {
                    originalValues = new ArrayList<Contact>(displayValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = originalValues.size();
                    results.values = originalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < originalValues.size(); i++) {
                        String data = originalValues.get(i).name;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Contact(originalValues.get(i).name,originalValues.get(i).number));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }

        };

        return filter;
    }
}
