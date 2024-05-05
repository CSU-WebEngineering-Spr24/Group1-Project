package com.example.webengbigproject.DataMuse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

// This service annotation is important; otherwise SpringBoot will not know that this needs
// to be autowired into your main app.

// TODO: Make these services extend a APIService abstract class, each must have getResponseObject()

/**
 * This class will allow the program/game to generate 2 types of questions: guess definition by word
 * or guess word by definition.
 * @author Himanshu Bohra
 */
@Service
public class DataMuseService
{
    // API key from https://api.nasa.gov/
    //private static final String API_KEY = "Eshc6SGaERpVafvFE71XDMWAVTzecUzPLjgY9IGG";

    // API EXAMPLE: https://api.datamuse.com/words?ml=ringing+in+the+ears
    private static final String BASE_URL = "https://api.datamuse.com/words?";
    // How many results returned at maximum. Hard auto-limit is 1000.
    private static final String AMOUNT = "&max=";
    private static final String MEANING = "&ml=";
    private static final String SOUNDS_LIKE = "&sl=";

    // This is for generating a random word to generate a question with.
    private static final String CHAR_START = "&sp=*";
    private static final String CHAR_END = "*";

    // THIS WAS THE CULPRIT FOR NOT CATCHING DEFS ALL ALONG, DIDN'T IMPLEMENT THIS!
    private static final String DEFINITIONS_ENABLED = "&md=d";



    // TODO: Need to figure something better out for the default response.
    private static final String DEFAULT_REQUEST = "sp=*a*&md=d&max=1";

    private final RestTemplate restTemplate;

    public DataMuseService(RestTemplateBuilder builder)
    {
        // In-built springboot builder for REST
        this.restTemplate = builder.build();
    }


    /**
     * This is not to be used, just a placeholder function.
     * @return Returns a word with 'a' in it.
     */
    public DataMuseResponse getDataMuseObject()
    {
        String url = BASE_URL + DEFAULT_REQUEST;
        // Auto-gets the response object, just need to give it annotated class.
        return restTemplate.getForObject(url, DataMuseResponse.class);
    }


    /**
     * Returns a list of words based on the given character. Random pick is handled by QuestionGenerator.
     * @param randomCharacter
     * @param amount
     * @return
     */
    public DataMuseResponse[] getDataMuseObject(String randomCharacter, int amount)
    {
        if (amount < 1) amount = 1;
        if (randomCharacter.isEmpty()) randomCharacter = "a";

        String url = BASE_URL + CHAR_START + randomCharacter + CHAR_END + AMOUNT + amount + DEFINITIONS_ENABLED;
        ArrayList<DataMuseResponse> returnList = new ArrayList<DataMuseResponse>();
        //returnList.add(restTemplate.getForObject(url, DataMuseResponse.class));
        return restTemplate.getForObject(url, DataMuseResponse[].class);
    }


}
