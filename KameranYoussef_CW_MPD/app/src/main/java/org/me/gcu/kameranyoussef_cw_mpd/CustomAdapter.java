package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing
//this class is the adapter for listView items

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//the class extand ArrayAdapter to have a collection of objects to store <ListItemObject> type of objects
public class CustomAdapter extends ArrayAdapter<ListItemObject> {
    ListItemObject singleitem;

    //class constructor
    public CustomAdapter(Context context, ArrayList<ListItemObject> item) {
        super(context, R.layout.list_view, item);
    }

    @Override
    public View getView(int position, View view,ViewGroup parent) {
        //To inflate the our new custome list view layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View List_view = inflater.inflate(R.layout.list_view,parent,false);

        //getting the list view items
        singleitem = getItem(position);

        //getting the elements form the List_view layout
        TextView line1 = (TextView) List_view.findViewById(R.id.line1);
        TextView line2 = (TextView) List_view.findViewById(R.id.line2);
        TextView line3 = (TextView) List_view.findViewById(R.id.line3);
        TextView line4 = (TextView) List_view.findViewById(R.id.line4);

        //setting the text for each item in the listView
        line1.setText(singleitem.getTitle());
        line2.setText(singleitem.getDate());
        line3.setText("Magnitude: "+ String.valueOf(singleitem.getMagnitude()));
        line4.setText("Depth: "+ String.valueOf(singleitem.getDepth())+" km");

        return List_view;
    }

}
