package com.example.webengbigproject.OpenTDB;

/**
 * @author Himanshu Bohra
 * Service class for the open trivia database API
 */

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// This service annotation is important; otherwise SpringBoot will not know that this needs
// to be autowired into your main app.
@Service
public class OpenTDBService
{
    // API key from https://api.nasa.gov/
    //private static final String API_KEY = "Eshc6SGaERpVafvFE71XDMWAVTzecUzPLjgY9IGG";
    private static final String BASE_URL = "https://opentdb.com/api.php?";
    private static final String AMOUNT = "&amount=";
    private static final String DIFFICULTY = "&difficulty=";
    private static final String TYPE = "&type=";

    private static final String DEFAULT_REQUEST = "amount=1&difficulty=medium&type=multiple";
    private static final String ENCODING = "&encode=url3986";

    private final RestTemplate restTemplate;

    public OpenTDBService(RestTemplateBuilder builder)
    {
        // In-built springboot builder for REST
        this.restTemplate = builder.build();
    }


    /**
     * This function should not be used, temporary test function.
     * @return Returns a medium difficulty, multi-choice question from any category.
     */
    public OpenTriviaDBResponse getOpenTDBObject()
    {
        String url = BASE_URL + DEFAULT_REQUEST + ENCODING;
        // Auto-gets the response object, just need to give it annotated class.
        return restTemplate.getForObject(url, OpenTriviaDBResponse.class);
    }

    /**
     * This function should not be used. Temporary test function.
     * @param amount The amount of questions requested
     * @return JSON response object containing the question(s) and answers (med,multi-choice)
     */
    public OpenTriviaDBResponse getOpenTDBObject(int amount)
    {
        if (amount < 1) amount = 1;
        String url = BASE_URL + "&amount=" + amount + ENCODING;
        return restTemplate.getForObject(url, OpenTriviaDBResponse.class);
    }


    public OpenTriviaDBResponse getOpenTDBObject(int amount, String difficulty, String type )
    {
        if (amount < 1) amount = 1;
        if (difficulty == null) difficulty = "medium";
        if (type == null) type = "multiple";
        String url = BASE_URL + AMOUNT + amount + DIFFICULTY + difficulty + TYPE + type;
        return restTemplate.getForObject(url, OpenTriviaDBResponse.class);
    }


}
