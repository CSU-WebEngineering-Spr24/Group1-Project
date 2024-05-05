package com.example.webengbigproject;

import com.example.webengbigproject.Dashboard.ResultJSON;
import com.example.webengbigproject.Dashboard.ScoreResponseJSON;
import com.example.webengbigproject.DataMuse.DataMuseResponse;
import com.example.webengbigproject.DataMuse.DataMuseService;
import com.example.webengbigproject.OpenTDB.OpenTDBService;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBResponse;
import com.example.webengbigproject.Utilities.*;
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

    private final StorageHandler storageHandler = new StorageHandler();

    public APIController(OpenTDBService openTDBService, DataMuseService dataMuseService)
    {
        this._openTDBService = openTDBService;
        this._datamuseService = dataMuseService;
    }



    @GetMapping("/openTDefault")
    public OpenTriviaDBResponse getOpenTDBQuestion()
    {
        return _openTDBService.getOpenTDBObject();
    }


    @GetMapping("/openTByAmount")
    public OpenTriviaDBResponse getOpenTDBQuestion(@RequestParam(value = "amount", required = false) Integer amount)
    {
        return _openTDBService.getOpenTDBObject(amount);
    }



    @GetMapping("/openTQuestions")
    public OpenTriviaDBResponse getOpenTDBQuestion(@RequestParam(value = "amount", required = false, defaultValue = "1") Integer amount,
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

        OpenTriviaDBResponse openTDBResponse = getOpenTDBQuestion(amount, "medium", "multiple");
        return QuestionGenerator.generateQuestions(openTDBResponse);
    }


    @GetMapping("/facts")
    public ArrayList<Fact> getFacts(@RequestParam(value = "count", required = false, defaultValue = "10") Integer count)//,
    // @RequestParam(value = "amount", required = false, defaultValue = "10") Integer amount)
    {


        OpenTriviaDBResponse openTDBResponse = getOpenTDBQuestion(count, "medium", "multiple");
        return FactGenerator.generateFacts(openTDBResponse);
    }


    // TODO: Make it read from the scores.txt file. The file must be updated through submit query
    @GetMapping("/scores")
    public ArrayList<ScoreResponseJSON> getScores(@RequestParam(value = "count", required = false, defaultValue = "10") Integer count)//,
    {

        // DEMO CODE: [This must be done through retrieval from a text file]
        ResultJSON resultJSON = new ResultJSON() {{_user = "demoUser"; _score = 50;}};
        ScoreResponseJSON  scoreResponseJSON= new ScoreResponseJSON()
        {{
            _mode = "arcade"; _results = new ArrayList<>() {{add(resultJSON);}};
        }};

        return new ArrayList<ScoreResponseJSON>() {{add(scoreResponseJSON);}};
    }


    // TODO: Change the return type to a response code like success 200 etc.
    // TODO: Add a file store function.
    @GetMapping("/submit")
    public ArrayList<ScoreResponseJSON> getFacts(
                                    @RequestParam(value = "mode", required = false, defaultValue = "arcade") String mode,
                                    @RequestParam(value = "user", required = false, defaultValue = "UNKNOWN") String user,
                                    @RequestParam(value = "score", required = false, defaultValue = "0") Integer score)
    {
        ResultJSON resultJSON = new ResultJSON() {{_user = user; _score = score;}};
        ScoreResponseJSON scoreResponseJSON = new ScoreResponseJSON() {{_mode = mode; _results.add(resultJSON);}};

        try
        {
            storageHandler.updateScores(mode, user, score);
            return (ArrayList<ScoreResponseJSON>) storageHandler.readScores();
        }
        catch (Exception e)
        {
            return new ArrayList<ScoreResponseJSON>() {{add(scoreResponseJSON);}};
        }

    }



    @RequestMapping("/apiUsage")
    public String everythingElse()//,
    // @RequestParam(value = "amount", required = false, defaultValue = "10") Integer amount)
    {
        return "<div id = \"main\">" +
                "<b><i>You are seeing this page because you are not using the API call properly." +
                "<br><br>For questions please use '/questions?' followed by 'mode=' (arcade/challenge/quick)." +
                "<br><br>For fetching facts, please use '/facts?' followed by 'count='(number of facts)." +
                "<br><br>For going to ui, please use '/home'." +
                "</div>" +
                "<script> " +
                "document.getElementById(\"main\").style.border = \"thick solid #fcba03\"; " +
                "document.getElementById(\"main\").style.color = \"#ffffff\";" +
                "document.body.style.backgroundColor = \"black\";" +
                "</script>";
    }


}
