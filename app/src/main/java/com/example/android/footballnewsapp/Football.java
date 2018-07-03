package com.example.android.footballnewsapp;

/**
 * Created by SeniKamara on 03.06.2018.
 * {@link Football} represents a news feed about football articles and liveblog
 * the Guardian News paper by means of API
 */

public class Football {

    /** Type of the news: article of liveblog */
    private String type;

    /** Name of the Section of the news */
    private String section;

    /** Date of the news */
    private String date;

    /** Title of the aritcle or liveblog */
    private String title;

    /** Website URL of the earthquake */
    private String url;


    /**
     * Create a new Football object.
     * @param typeNews is the magnitude (size) of the earthquake
     * @param sectionName is the city location of the earthquake
     * @param newsDate is the time in milliseconds (from the Epoch) when the
     */

    public Football(String typeNews, String sectionName, String newsDate, String webTitle, String Url) {

        //Initializing the variables
        type = typeNews;
        section = sectionName;
        date = newsDate;
        title = webTitle;
        url = Url;
    }

    /**
     * Get the TypeNews method
     */

    public String getType() {
        return type;
    }

    /**
     * Get the EarthquakeLocation method
     */

    public String getSection() {
        return section;
    }

    /**
     * Get the TimeInMilliseconds method
     */

    public String getDate() {
        return date;
    }

    /**
     * Get the WebTitle method
     */

    public String getTitle() {
        return title;
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return url;
    }
}


