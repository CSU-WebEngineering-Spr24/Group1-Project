package com.example.webengbigproject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/*
    TODO: Refactor this code for open trivia DB API (TASK 1 for backend)
    LATER: There will be a random generator class which will fetch from random API.
 */

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // Allow all? (at least gets rid of CORS)
public class APIController
{
    private OpenTDBService _openTDBService = null;

    public APIController(OpenTDBService apodService)
    {
        this._openTDBService = apodService;
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


}
