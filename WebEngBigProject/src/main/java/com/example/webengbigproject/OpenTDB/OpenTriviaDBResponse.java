package com.example.webengbigproject.OpenTDB;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBQuestion;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
    @author Himanshu Bohra
    This class contains the json response for OpenTDB API query.
    Example: https://opentdb.com/api.php?amount=1&difficulty=medium&type=multiple&encode=url3986
 */
public class OpenTriviaDBResponse
{
    // Making a temporary fix before refactoring
    @JsonProperty("results")
    private ArrayList<OpenTriviaDBQuestion> _results;

    @JsonProperty("response_code")
    private String _responseCode;



    // Getters
    public ArrayList<OpenTriviaDBQuestion> getResults()
    {
        return _results;
    }

    public String getResponseCode()
    {
        return _responseCode;
    }

}
