package com.example.webengbigproject.Utilities;

/**
 * @author Himanshu Bohra
 * Generates Facts by pulling data from multiple APIs
 */
import com.example.webengbigproject.DataMuse.DataMuseResponse;
import com.example.webengbigproject.DataMuse.DataMuseService;
import com.example.webengbigproject.DataMuse.DataMuseWordDefinitions;
import com.example.webengbigproject.OMDB.OMDBMovie;
import com.example.webengbigproject.OMDB.OMDBResponse;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBQuestion;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class FactGenerator
{
    private static final Random random = new Random();


    // TODO: Add datamuse and movie db to this; randomize it
    public static ArrayList<Fact> generateFacts(OpenTriviaDBResponse openTrivia, DataMuseResponse[] dataMuseResponses,
                                                OMDBResponse omdbResponse)
    {

        ArrayList<OpenTriviaDBQuestion> openTDBQuestions = openTrivia.getResults();
        ArrayList<OMDBMovie> omdbMovies = omdbResponse._searchedMovies;
        ArrayList<Fact> facts = new ArrayList<Fact>();
        Fact fact;

        // We will iterate over all the questions generated by opentriviaDB query and add them.
        for ( OpenTriviaDBQuestion eachQuestion : openTDBQuestions)
        {
            fact = new Fact();
            String q = eachQuestion.getQuestion();
            q = Character.toLowerCase(q.charAt(0)) + q.substring(1);

            if(!q.substring(q.length()-1).equals("?"))
            {
                q = q.substring(q.length()-2) + ":";
            }
            fact._fact = "Did you know " + q + " " + eachQuestion.getCorrectAnswer() + "!";
            fact._fact = org.jsoup.parser.Parser.unescapeEntities(fact._fact, true);

            facts.add(fact);
        }

        facts.addAll(handleDataMuseResponse(dataMuseResponses));
        facts.addAll(handleOMDBResponse(omdbResponse));
        Collections.shuffle(facts);
        return facts;
    }


    private static ArrayList<Fact> handleDataMuseResponse(DataMuseResponse[] dataMuseResponses)
    {
        ArrayList<Fact> facts = new ArrayList<>();
        Fact fact;

        for (DataMuseResponse eachResponse : dataMuseResponses)
        {
            String word = eachResponse._word;

            ArrayList<String> defs = eachResponse._defs;

            fact = new Fact();
            int randomIndex = random.nextInt(0, defs.size());
            fact._fact = "Did you know " + word + " also means " + defs.get(randomIndex) + "?";
            fact._fact = org.jsoup.parser.Parser.unescapeEntities(fact._fact, true);

            facts.add(fact);
        }

        return facts;
    }

    private static ArrayList<Fact> handleOMDBResponse(OMDBResponse omdbResponse)
    {
        ArrayList<Fact> facts = new ArrayList<>();
        Fact fact;


        ArrayList<OMDBMovie> movies = omdbResponse._searchedMovies;

        // Dirty patch check
        if (movies == null)
        {
            facts.add(new Fact(){{_fact = "Did you know that sometimes you get rate-limited when fetching API responses?";}});
            return  facts;
        }

        if( omdbResponse._count > movies.size())
        {
            omdbResponse._count = movies.size()-1;
        }

        else if(omdbResponse._count < 1)
        {
            return facts;
        }

        int counter = 1;
        for (OMDBMovie eachMovie : movies)
        {
            if(counter > omdbResponse._count)
            {
                break;
            }
            String movieTitle = eachMovie._title;

            String year = eachMovie._year;

            fact = new Fact();
            fact._fact = "Did you know the movie " + movieTitle + " was released in " + year + "?";
            fact._fact = org.jsoup.parser.Parser.unescapeEntities(fact._fact, true);

            facts.add(fact);
            counter++;
        }

        return facts;
    }


    // Helper method:
    public String parseJsonDefs(String jsonResponse)
    {
        try {
            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Extract the "defs" array
            JsonNode defsArray = jsonNode.get(0).get("defs");

            // Convert the array to a single string
            StringBuilder defsString = new StringBuilder();
            for (JsonNode definition : defsArray) {
                defsString.append(definition.asText()).append("\n");
            }

            // Now you can manually process the defsString as needed
            System.out.println(defsString.toString());

            return defsString.toString();
        }
        catch (Exception e) {System.out.println(e.toString()); return e.toString();}
    }
}
