package com.example.android.footballnewsapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * {@link FootballAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Football} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class FootballAdapter extends ArrayAdapter<Football> {


    /**
     * Constructs a new {@link FootballAdapter}.
     *
     * @param context of the app
     * @param allFootball is the list of earthquakes, which is the data source of the adapter
     */


    public FootballAdapter(Context context, List<Football> allFootball) {

        //Here I initialize the ArrayAdapter's internal storage for the context and the list.
        //The second argument is used when the ArrayAdapter is populating a single TextView.
        //Because, this is a custom adapter for two TextViews, the adapter is not going to
        //use this second argument, so it can be any value.  Here I use 0.
        super(context, 0, allFootball);
    }

    /**
     * Provides a View of an AdapterView (ListView, GridView, etc.)
     * @param position The position is the list of data that should be displayed
     *                 in the listItemView
     * @param convertView The recycledView to populate
     * @param parent The parent ViewGroup that is used for inflation
     * @return The View for the position in the AdapterView.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.football_list_item, parent, false);
        }

        //Get the {@link Football} object located at this position in the list
        Football currentFootball = getItem(position);

        //Find the TextView with the view ID type_tv
        TextView typeView = listItemView.findViewById(R.id.type_tv);
        //Display the type of the current News in that TextView
        typeView.setText(currentFootball.getType());

        //Find the TextView with the view ID section_tv
        TextView sectionView = listItemView.findViewById(R.id.section_tv);
        //Display the section of the current News in that TextView
        sectionView.setText(currentFootball.getSection());

        //Find the TextView with the view ID title_tv
        TextView titleView = listItemView.findViewById(R.id.title_tv);
        //Display the title of the current News in that TextView
        titleView.setText(currentFootball.getTitle());

        //Find the TextView with the view ID date_tv
        TextView dateView = listItemView.findViewById(R.id.date_tv);
        //Display the date of the current News in the TextView
        dateView.setText(currentFootball.getDate());

        //Return the whole listItem layout (containing 4 TextViews)
        //so that it can be shown in the ListView
        return listItemView;

    }

}
