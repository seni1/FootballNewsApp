//package com.example.android.footballnewsapp;
//
//import android.content.Context;
//import android.graphics.drawable.GradientDrawable;
//import android.support.v4.content.ContextCompat;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * {@link FootballAdapter} knows how to create a list item layout for each earthquake
// * in the data source (a list of {@link Football} objects).
// *
// * These list item layouts will be provided to an adapter view like ListView
// * to be displayed to the user.
// */
//
//public class FootballAdapter extends ArrayList<Football>{
//
////    /**
////     * The part of the location string from the USGS service that we use to determine
////     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
////     */
////    private static final String LOCATION_SEPARATOR = " of ";
//
//
//    /**
//     * Constructs a new {@link FootballAdapter}.
//     *
//     * @param context of the app
//     * @param footballs is the list of earthquakes, which is the data source of the adapter
//     */
//
//
//    public FootballAdapter(Context context, List<Football> footballs) {
//        //Here I initialize the ArrayAdapter's internal storage for the context and the list.
//        //The second argument is used when the ArrayAdapter is populating a single TextView.
//        //Because, this is a custom adapter for two TextViews, the adapter is not going to
//        //use this second argument, so it can be any value.  Here I use 0.
//        super(context, 0, footballs);
//    }
//
//    /**
//     * Provides a View of an AdapterView (ListView, GridView, etc.)
//     * @param position The position is the list of data that should be displayed
//     *                 in the listItemView
//     * @param convertView The recycledView to populate
//     * @param parent The parent ViewGroup that is used for inflation
//     * @return The View for the position in the AdapterView.
//     */
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        //Check if the existing View is being reused, otherwise inflate the View
//        View listItemView = convertView;
//
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.football_list_item, parent,
//                    false);
//        }
//
//        //Get the {@link Football} object located at this position in the list
//        Football currentFootball = getItem(position);
//
//        //Find the TextView in the earthquake_list_item.xmlst_item.xml layout with the ID magnitude_tv
//        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitude_tv);
//        //Get the Earthquake from the current Earthquake object and set this text
//        //on the magnitudeTextView
////        magnitudeTextView.setText(currentEarthquake.getEarthquakeMagnitude());
//        // Format the magnitude to show 1 decimal place
//        String formattedMagnitude = formatMagnitude(currentFootball.getEarthquakeMagnitude());
//        // Display the magnitude of the current earthquake in that TextView
//        magnitudeTextView.setText(formattedMagnitude);
//
//
//        // Set the proper background color on the magnitude circle.
//        // Fetch the background from the TextView, which is a GradientDrawable.
//        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
//        // Get the appropriate background color based on the current earthquake magnitude
//        int magnitudeColor = getMagnitudeColor(currentFootball.getEarthquakeMagnitude());
//        // Set the color on the magnitude circle
//        magnitudeCircle.setColor(magnitudeColor);
//
//
//        // Get the original location string from the Earthquake object,
//        // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
//        String originalLocation = currentFootball.getEarthquakeLocation();
//
//
//
//        // If the original location string (i.e. "5km N of Cairo, Egypt") contains
//        // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
//        // then store the primary location separately from the location offset in 2 Strings,
//        // so they can be displayed in 2 TextViews.
//        String primaryLocation;
//        String locationOffset;
//
//        // Check whether the originalLocation string contains the " of " text
//        if (originalLocation.contains(LOCATION_SEPARATOR)) {
//            // Split the string into different parts (as an array of Strings)
//            // based on the " of " text. We expect an array of 2 Strings, where
//            // the first String will be "5km N" and the second String will be "Cairo, Egypt".
//            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
//            // Location offset should be "5km N " + " of " --> "5km N of"
//            locationOffset = parts[0] + LOCATION_SEPARATOR;
//            // Primary location should be "Cairo, Egypt"
//            primaryLocation = parts[1];
//        } else {
//            // Otherwise, there is no " of " text in the originalLocation string.
//            // Hence, set the default location offset to say "Near the".
//            locationOffset = getContext().getString(R.string.near_the);
//            // The primary location will be the full location string "Pacific-Antarctic Ridge".
//            primaryLocation = originalLocation;
//        }
//
//        // Find the TextView with view ID location
//        TextView primaryLocationView = listItemView.findViewById(R.id.primary_location);
//        // Display the location of the current earthquake in that TextView
//        primaryLocationView.setText(primaryLocation);
//
//        // Find the TextView with view ID location offset
//        TextView locationOffsetView = listItemView.findViewById(R.id.location_offset);
//        // Display the location offset of the current earthquake in that TextView
//        locationOffsetView.setText(locationOffset);
//
////        //Find the TextView in the earthquake_list_item.xmlst_item.xml layout with the ID location_tv
////        TextView locationTextView = listItemView.findViewById(R.id.location_tv);
////        //Get the Earthquake from the current Earthquake object and set this text
////        //on the magnitudeTextView
////        locationTextView.setText(currentEarthquake.getEarthquakeLocation());
//
//        // Create a new Date object from the time in milliseconds of the earthquake
//        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
//
//        //Find the TextView in the earthquake_list_iteme_list_item.xml layout with the ID location_tv
//        TextView dateTextView = listItemView.findViewById(R.id.date_tv);
//        // Format the date string (i.e. "Mar 3, 1984")
//        String formattedDate = formatDate(dateObject);
//        // Display the date of the current earthquake in that TextView
//        dateTextView.setText(formattedDate);
//
//        // Find the TextView with view ID time
//        TextView timeView = listItemView.findViewById(R.id.time_tv);
//        // Format the time string (i.e. "4:30PM")
//        String formattedTime = formatTime(dateObject);
//        // Display the time of the current earthquake in that TextView
//        timeView.setText(formattedTime);
//
//
//
//        //Return the whole listItem layout (containing 4 TextViews)
//        //so that it can be shown in the ListView
//        return listItemView;
//
//    }
//
//
//    /**
//     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
//     */
//    private String formatDate(Date dateObject) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
//        return dateFormat.format(dateObject);
//    }
//
//    /**
//     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
//     */
//    private String formatTime(Date dateObject) {
//        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
//        return timeFormat.format(dateObject);
//    }
//
//    /**
//     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
//     * from a decimal magnitude value.
//     */
//    private String formatMagnitude(double magnitude) {
//        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
//        return magnitudeFormat.format(magnitude);
//    }
//
//    /**
//     * Return the color for the magnitude circle based on the intensity of the earthquake.
//     *
//     * @param magnitude of the earthquake
//     */
////    private int getMagnitudeColor(double magnitude) {
////        int magnitudeColorResourceId;
////        int magnitudeFloor = (int) Math.floor(magnitude);
////        switch (magnitudeFloor) {
////            case 0:
////            case 1:
////                magnitudeColorResourceId = R.color.magnitude1;
////                break;
////            case 2:
////                magnitudeColorResourceId = R.color.magnitude2;
////                break;
////            case 3:
////                magnitudeColorResourceId = R.color.magnitude3;
////                break;
////            case 4:
////                magnitudeColorResourceId = R.color.magnitude4;
////                break;
////            case 5:
////                magnitudeColorResourceId = R.color.magnitude5;
////                break;
////            case 6:
////                magnitudeColorResourceId = R.color.magnitude6;
////                break;
////            case 7:
////                magnitudeColorResourceId = R.color.magnitude7;
////                break;
////            case 8:
////                magnitudeColorResourceId = R.color.magnitude8;
////                break;
////            case 9:
////                magnitudeColorResourceId = R.color.magnitude9;
////                break;
////            default:
////                magnitudeColorResourceId = R.color.magnitude10plus;
////                break;
////        }
////
////        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
////    }
//}
