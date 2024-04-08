package com.example.webengbigproject.DataMuse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

// TODO: DETERMINE IF THIS CLASS WILL BE NEEDED OR DATAMUSERESPONSE DEFINITIONS CAN BE PARSED WITHOUT IT.
/**
 @author Himanshu Bohra
 This class contains the json response for OpenTDB API query.
 Example: https://opentdb.com/api.php?amount=1&difficulty=medium&type=multiple&encode=url3986
 */
public class DataMuseWordDefinitions
{
    // We are only interested in the word itself, and the definition of that word.
    //@JsonProperty("word")
    //private ArrayList<String> _word;

    @JsonProperty("defs")
    private List<String> _definitions;


    // Getters
    /*
    public ArrayList<String> getWord()
    {
        return _word;
    }



    public ArrayList<String> getDefinition()
    {
        return _definitions;
    }

     */


}
