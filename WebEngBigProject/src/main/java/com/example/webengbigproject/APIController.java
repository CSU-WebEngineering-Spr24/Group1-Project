package com.example.webengbigproject;

import com.example.webengbigproject.DataMuse.DataMuseResponse;
import com.example.webengbigproject.DataMuse.DataMuseService;
import com.example.webengbigproject.OpenTDB.OpenTDBService;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBResponse;
import com.example.webengbigproject.Utilities.Question;
import com.example.webengbigproject.Utilities.QuestionGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/*
    TODO: Refactor this code to generate a custom API such that frontend only needs to ask a question
    LATER: There will be a random generator class which will fetch from random API.
 */

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // Allow all? (at least gets rid of CORS)
public class APIController
{
    private OpenTDBService _openTDBService = null;
    private DataMuseService _datamuseService = null;

    public APIController(OpenTDBService openTDBService, DataMuseService dataMuseService)
    {
        this._openTDBService = openTDBService;
        this._datamuseService = dataMuseService;
    }



    @GetMapping("/getDefault")
    public OpenTriviaDBResponse getQuestion()
    {
        return _openTDBService.getOpenTDBObject();
    }


    @GetMapping("/getByAmount")
    public OpenTriviaDBResponse getQuestion(@RequestParam(value = "amount", required = false) Integer amount)
    {
        return _openTDBService.getOpenTDBObject(amount);
    }



    @GetMapping("/get")
    public OpenTriviaDBResponse getQuestion(@RequestParam(value = "amount", required = false, defaultValue = "1") Integer amount,
                                          @RequestParam(value = "difficulty", required = false, defaultValue = "easy") String difficulty,
                                              @RequestParam(value = "type", required = false, defaultValue = "multiple") String type)
    {
        return _openTDBService.getOpenTDBObject(amount, difficulty, type);
    }


    @GetMapping("/datamuse")
    public DataMuseResponse[] getDataMuse(@RequestParam(value = "character", required = false, defaultValue = "a") String character,
                                                   @RequestParam(value = "amount", required = false, defaultValue = "1") Integer amount)
    {
        return _datamuseService.getDataMuseObject(character, amount);
    }


    // TODO: MAP THE PARAMETERS PROPERLY! ARCADE (must have some unique attributes; use ENUMS?) W.I.P
    @GetMapping("/questions")
    public ArrayList<Question> getQuestions(@RequestParam(value = "mode", required = false, defaultValue = "arcade") String gameMode)//,
                                           // @RequestParam(value = "amount", required = false, defaultValue = "10") Integer amount)
    {
        int amount = 1;
        if(gameMode.equalsIgnoreCase("arcade")) amount = 10;
        if(gameMode.equalsIgnoreCase("challenge")) amount = 20;
        if(gameMode.equalsIgnoreCase("quick")) amount = 5;

        OpenTriviaDBResponse openTDBResponse = getQuestion(amount, "medium", "multiple");
        return QuestionGenerator.generateQuestions(openTDBResponse);
    }


}
