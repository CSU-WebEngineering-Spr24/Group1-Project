package com.example.webengbigproject;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// This service annotation is important; otherwise SpringBoot will not know that this needs
// to be autowired into your main app.
@Service
public class APODService
{
    // API key from https://api.nasa.gov/
    private static final String API_KEY = "Eshc6SGaERpVafvFE71XDMWAVTzecUzPLjgY9IGG";
    private static final String BASE_URL = "https://api.nasa.gov/planetary/apod";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final RestTemplate restTemplate;

    public APODService(RestTemplateBuilder builder)
    {
        // In-built springboot builder for REST
        this.restTemplate = builder.build();
    }


    // Default, picture of the day
    public OpenTriviaDBResponse getAPODObject()
    {
        String url = BASE_URL + "?api_key=" + API_KEY;
        // Auto-gets the response object, just need to give it annotated class.
        return restTemplate.getForObject(url, OpenTriviaDBResponse.class);
    }

    public OpenTriviaDBResponse getAPODObject(LocalDate date)
    {
        String url = BASE_URL + "?api_key=" + API_KEY + "&date=" + date.format(DATE_FORMAT);
        return restTemplate.getForObject(url, OpenTriviaDBResponse.class);
    }

    public OpenTriviaDBResponse[] getAPODObject(int count)
    {
        String url = BASE_URL + "?api_key=" + API_KEY + "&count=" + count;
        return restTemplate.getForObject(url, OpenTriviaDBResponse[].class);
    }


    public OpenTriviaDBResponse[] getAPODObject(String start_date, String end_date )
    {
        String url = BASE_URL + "?api_key=" + API_KEY + "&start_date=" + start_date+
                "&end_date=" + end_date;
        return restTemplate.getForObject(url, OpenTriviaDBResponse[].class);
    }


    //&start_date=2024-01-01&end_date=2024-02-01

}
