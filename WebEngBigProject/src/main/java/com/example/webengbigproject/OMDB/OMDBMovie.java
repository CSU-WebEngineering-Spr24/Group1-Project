package com.example.webengbigproject.OMDB;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 @author Himanshu Bohra
 This class represents the json object for OMDB API movie.
 */
public class OMDBMovie
{
    @JsonProperty("Title")
    public String _title;

    @JsonProperty("Year")
    public String _year;

}
