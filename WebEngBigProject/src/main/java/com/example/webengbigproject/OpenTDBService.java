package com.example.webengbigproject;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// This service annotation is important; otherwise SpringBoot will not know that this needs
// to be autowired into your main app.
@Service
public class OpenTDBService
{
    // API key from https://api.nasa.gov/
    private static final String API_KEY = "Eshc6SGaERpVafvFE71XDMWAVTzecUzPLjgY9IGG";
    private static final String BASE_URL = "https://opentdb.com/api.php?";

    private final RestTemplate restTemplate;

    public OpenTDBService(RestTemplateBuilder builder)
    {
        // In-built springboot builder for REST
        this.restTemplate = builder.build();
    }


    // Default: Get one question randomly in any category
    public OpenTriviaDBResponse getOpenTDBObject()
    {
        String url = BASE_URL;
        // Auto-gets the response object, just need to give it annotated class.
        return restTemplate.getForObject(url, OpenTriviaDBResponse.class);
    }

    /**
     *
     * @param amount The amount of questions requested
     * @return JSON response object containing the question(s) and answers
     */
    public OpenTriviaDBResponse[] getOpenTDBObject(int amount)
    {
        if (amount < 1) amount = 1;
        String url = BASE_URL + "&amount=" + amount;
        return restTemplate.getForObject(url, OpenTriviaDBResponse[].class);
    }


    public OpenTriviaDBResponse[] getOpenTDBObject(String start_date, String end_date )
    {
        String url = BASE_URL + "&start_date=" + start_date+
                "&end_date=" + end_date;
        return restTemplate.getForObject(url, OpenTriviaDBResponse[].class);
    }


}
