package com.example.webengbigproject;

import com.example.webengbigproject.Dashboard.ResultJSON;
import com.example.webengbigproject.Dashboard.ScoreResponseJSON;
import com.example.webengbigproject.DataMuse.DataMuseResponse;
import com.example.webengbigproject.DataMuse.DataMuseService;
import com.example.webengbigproject.OpenTDB.OpenTDBService;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBResponse;
import com.example.webengbigproject.Utilities.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
    public ArrayList<Question> getQuestions(@RequestParam(value = "mode", required = false) String gameMode,
                                            @RequestParam(value = "amount", required = false, defaultValue = "1") Integer amount)
    {
        if(gameMode.equalsIgnoreCase("arcade")) amount = 10;
        else if(gameMode.equalsIgnoreCase("timed")) amount = 10;
        else if(gameMode.equalsIgnoreCase("quick")) amount = 5;
        else if(gameMode.equalsIgnoreCase("marathon")) amount = 50;

        OpenTriviaDBResponse openTDBResponse = getOpenTDBQuestion(amount, "medium", "multiple");
        return QuestionGenerator.generateQuestions(openTDBResponse);
    }


    @GetMapping("/facts")
    public ArrayList<Fact> getFacts(@RequestParam(value = "count", required = false, defaultValue = "10") Integer count)//,
    // @RequestParam(value = "amount", required = false, defaultValue = "10") Integer amount)
    {
        Random random = new Random();
        int randomDistribution = random.nextInt(0, count);

        // For datamuse random character word:
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');


        OpenTriviaDBResponse openTDBResponse = getOpenTDBQuestion(randomDistribution, "medium", "multiple");
        DataMuseResponse[] dataMuseResponseArray = getDataMuse(String.valueOf(c), count-randomDistribution);
        return FactGenerator.generateFacts(openTDBResponse, dataMuseResponseArray);
    }


    @GetMapping("/submit")
    public ResponseEntity<String> getFacts(
                                    @RequestParam(value = "mode", required = false, defaultValue = "arcade") String mode,
                                    @RequestParam(value = "user", required = false, defaultValue = "UNKNOWN") String user,
                                    @RequestParam(value = "score", required = false, defaultValue = "0") Integer score)
    {
        try
        {
            storageHandler.updateScores(mode, user, score);
            //return (ArrayList<ScoreResponseJSON>) storageHandler.readScores();
            return ResponseEntity.ok("Submission Successful");
        }
        catch (Exception e)
        {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Submission Failed: Internal Server Error");
        }

    }

    @GetMapping("/readscores")
    public ArrayList<ScoreResponseJSON> readScoreFile()
    {
        try {
            return storageHandler.readScores();
        }
        catch (Exception e)
        {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return new ArrayList<ScoreResponseJSON>();
        }
    }

    @GetMapping("/resetscores")
    public ResponseEntity<String> resetScores()
    {
        try
        {
            storageHandler.resetScores();
            return ResponseEntity.ok("Scores were successfully reset");
        }
        catch (Exception e)
        {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("RESET FAILED: Internal Server Error");
        }
    }


    @GetMapping("/apiusage")
    public String apiUsage()//,
    // @RequestParam(value = "amount", required = false, defaultValue = "10") Integer amount)
    {
        return "<div id = \"main\">" +
                "<b><i>Following are the API usage examples:" +
                "<br><br>For questions please use '/questions?' followed by 'mode=' (arcade/challenge/quick)." +
                "<br><br>For fetching facts, please use '/facts?' followed by 'count='(number of facts)." +
                "<br><br>For fetching scores, please use '/readscores?'." +
                "<br><br>For submitting scores, please use '/submit?' followed by 'user='(user name), '&mode='(modename) and '&score='(integer score)." +
                "<br><br>For resetting scores, please use '/resetscores?'." +
                "<br><br>For going to ui, please use '/home'." +
                "</div>" +
                "<script> " +
                "document.getElementById(\"main\").style.border = \"thick solid #fcba03\"; " +
                "document.getElementById(\"main\").style.color = \"#ffffff\";" +
                "document.body.style.backgroundColor = \"black\";" +
                "</script>";
    }


}
