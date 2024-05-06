package com.example.webengbigproject.Utilities;
/**
 * Helper utility class which generates year from a range and picks from given keywords.
 * Primarily for omdb queries
 * @author Himanshu Bohra
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class OMDBParameterGenerator
{
    public static ArrayList<String> keywords = new ArrayList<>(Arrays.asList(
            "angel",
            "love",
            "day",
            "dead",
            "night",
            "dream",
            "heart",
            "life",
            "star",
            "home",
            "road",
            "time",
            "friend",
            "journey",
            "secret",
            "return",
            "forever",
            "memory",
            "shadow"
    ));
    public static String generateYear()
    {
        Random random = new Random();
        return "" + random.nextInt(1970, 2001);
    }

    public static String pickTitleKeyword()
    {
        Random random = new Random();
        return keywords.get(random.nextInt(0, keywords.size()));
    }
}
