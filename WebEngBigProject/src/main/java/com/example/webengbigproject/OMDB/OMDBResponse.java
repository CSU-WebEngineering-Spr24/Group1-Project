package com.example.webengbigproject.OMDB;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 @author Himanshu Bohra
 This class contains the json response for OMDB API query.
 Example: https://www.omdbapi.com/?apikey=d1072e98&y=1970&s=day
 (fetches a list of movies from the year 1970 with 'day' in the title
 */
public class OMDBResponse
{
    @JsonProperty("Search")
    public ArrayList<OMDBMovie> _searchedMovies;

    @JsonProperty("count")
    public int _count;

}
