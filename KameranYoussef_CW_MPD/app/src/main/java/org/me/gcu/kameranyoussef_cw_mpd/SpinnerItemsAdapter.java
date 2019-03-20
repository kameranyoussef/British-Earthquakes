package org.me.gcu.kameranyoussef_cw_mpd;

// Name                 Kameran Youssef
// Student ID           S1038287
// Programme of Study   Computing
//this class is the adapter for listView items

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
//the class extand ArrayAdapter to have a collection of objects to store <SpinnerItems> type of objects
public class SpinnerItemsAdapter extends ArrayAdapter<SpinnerItems>  {

    //class constructor
    public SpinnerItemsAdapter(Context context, ArrayList<SpinnerItems> spinnerItems) {
        super(context, 0, spinnerItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    private View initView(int position, View convertView, ViewGroup parent) {
        //To inflate the our new custome spinner view layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinnerlayout, parent, false
            );
        }

        //getting the elements form the Spinnerlayout
        ImageView imageViewFlag = convertView.findViewById(R.id.imageView_Spinner_icon);
        TextView textViewName = convertView.findViewById(R.id.Spinner_text);

        SpinnerItems currentItem = getItem(position);

        //adding elements.
        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getmSpinnerItemsIcon());
            textViewName.setText(currentItem.getmSpinnerItemsTitle());
        }

        return convertView;
    }
}
