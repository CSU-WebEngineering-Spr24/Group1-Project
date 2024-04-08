package com.example.webengbigproject.DataMuse;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBQuestion;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 @author Himanshu Bohra
 This class contains the json response for DataMuse API query.
 Example: https://api.datamuse.com/words?sp=*a*&md=d  (fetches a list of words with 'a' in them (between)
 Example: https://api.datamuse.com/words?ml=ringing+in+the+ears&md=d  (list of words with similar meaning)
 */
public class DataMuseResponse
{
    // We are only interested in the word itself, and the definition(s) of that word.
    @JsonProperty("word")
    public String _word;

    @JsonProperty("score")
    public int _score;

    // FIXME: ISSUES WITH PARSING THIS ATTRIBUTE; RETURNS NULL.
    @JsonProperty("defs")
    public ArrayList<String> _defs;


    // Getters
    public String getWord()
    {
        return _word;
    }

    public ArrayList<String> getDefs()
    {
        return _defs;
    }
}