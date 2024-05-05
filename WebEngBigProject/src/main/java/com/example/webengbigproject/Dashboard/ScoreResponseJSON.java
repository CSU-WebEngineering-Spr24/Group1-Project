package com.example.webengbigproject.Dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ScoreResponseJSON
{
    @JsonProperty
    public String _mode;

    // Early instantiation for ease of use later
    @JsonProperty
    public ArrayList<ResultJSON> _results = new ArrayList<>();

}
