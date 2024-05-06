package com.example.webengbigproject.OMDB;
/**
 * This class will fetch omdb data and store it into omdb response json
 * @author Himanshu Bohra
 */
import com.example.webengbigproject.DataMuse.DataMuseResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OMDBService {
    // Keywords to fetch movie title through


    // API key from OMDB's email (upon request)
    private static final String API_KEY = "d1072e98";

    // API EXAMPLE: https://www.omdbapi.com/?apikey=d1072e98&y=1970&s=dream
    private static final String BASE_URL = "https://www.omdbapi.com/?apikey=";
    private static final String TITLE = "&s=";
    private static final String YEAR = "&y=";


    private final RestTemplate restTemplate;

    public OMDBService(RestTemplateBuilder builder)
    {
        // In-built springboot builder for REST
        this.restTemplate = builder.build();
    }


    /**
     * This is not to be used, just a placeholder function.
     * @return Returns a word with 'a' in it.
     */
    public OMDBResponse getOMDBObject()
    {
        String url = BASE_URL + API_KEY;
        // Auto-gets the response object, just need to give it annotated class.
        return restTemplate.getForObject(url, OMDBResponse.class);
    }


    /**
     * Returns a list of words based on the given character. Random pick is handled by QuestionGenerator.
     * @param titleKeyword Keyword for the title to contain
     * @param year The year (must be pre-generated)
     * @return Omdb response object with the set count variable (used by question generator)
     */
    public OMDBResponse getOMDBObject(String titleKeyword, String year, int count)
    {
        String url = BASE_URL + API_KEY + TITLE + titleKeyword + YEAR + year;
        OMDBResponse omdbResponse = restTemplate.getForObject(url, OMDBResponse.class);
        assert omdbResponse != null;
        omdbResponse._count = count;
        return omdbResponse;
    }


}