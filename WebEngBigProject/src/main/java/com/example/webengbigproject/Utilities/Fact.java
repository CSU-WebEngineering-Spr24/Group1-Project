package com.example.webengbigproject.Utilities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * A class that represents a Fact in the form of a JSON Object. Has the fact string.
 * @author Himanshu Bohra
 */
public class Fact
{
    @JsonProperty("fact")
    public String _fact;
}
