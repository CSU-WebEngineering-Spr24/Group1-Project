package com.example.webengbigproject;
/**
 * @author Himanshu Bohra
 * Main API controller with all the API endpoint mappings.
 */

import com.example.webengbigproject.Dashboard.ResultJSON;
import com.example.webengbigproject.Dashboard.ScoreResponseJSON;
import com.example.webengbigproject.DataMuse.DataMuseResponse;
import com.example.webengbigproject.DataMuse.DataMuseService;
import com.example.webengbigproject.OMDB.OMDBResponse;
import com.example.webengbigproject.OMDB.OMDBService;
import com.example.webengbigproject.OpenTDB.OpenTDBService;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBResponse;
import com.example.webengbigproject.Utilities.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // Allow all? (at least gets rid of CORS)
public class APIController
{
    private OpenTDBService _openTDBService = null;
    private DataMuseService _datamuseService = null;
    private OMDBService _omdbService = null;

    private final StorageHandler storageHandler = new StorageHandler();

    public APIController(OpenTDBService openTDBService, DataMuseService dataMuseService, OMDBService omdbService)
    {
        this._openTDBService = openTDBService;
        this._datamuseService = dataMuseService;
        this._omdbService = omdbService;
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

    @GetMapping("/omdb")
    public OMDBResponse getOMDB(@RequestParam(value = "keyword", required = false, defaultValue = "day") String keyword,
                                    @RequestParam(value = "year", required = false, defaultValue = "1970") String year,
                                @RequestParam(value = "count", required = false, defaultValue = "1") Integer count)
    {
        return _omdbService.getOMDBObject(keyword, year, count);
    }


    @GetMapping("/questions")
    public ArrayList<Question> getQuestions(@RequestParam(value = "mode", required = false, defaultValue = "_") String gameMode,
                                            @RequestParam(value = "amount", required = false, defaultValue = "1") Integer amount)
    {
        if(gameMode.equalsIgnoreCase("arcade")) amount = 10;
        else if(gameMode.equalsIgnoreCase("timed")) amount = 10;
        else if(gameMode.equalsIgnoreCase("quick")) amount = 5;
        else if(gameMode.equalsIgnoreCase("marathon")) amount = 50;

        OpenTriviaDBResponse openTDBResponse = getOpenTDBQuestion(amount, "medium", "multiple");
        return QuestionGenerator.generateQuestions(openTDBResponse);
    }


    // Quick patch: The count will be passed to the fact generator along with responses. FG will decide
    // which response gets how many from the total count. Randomly. [Patch for count=1;fact=2]
    @GetMapping("/facts")
    public ArrayList<Fact> getFacts(@RequestParam(value = "count", required = false, defaultValue = "10") Integer count)//,
    // @RequestParam(value = "amount", required = false, defaultValue = "10") Integer amount)
    {
        try {
            //System.out.println("DISTR = " + count + "/" + 3);
            int distr = count / 3;
            int total = distr * 3;
            System.out.println("DISTR: " + distr + " TOTAL: " + total);

            int openTDBDistr, dataMuseDistr, openMovieDistr = 0;

            if (distr < 1) {
                // Get one open trivia response and make the others blank
                OpenTriviaDBResponse openTDBResponse = getOpenTDBQuestion(count, "medium", "multiple");
                DataMuseResponse[] emptyDataMuseResponseArray = {};
                OMDBResponse emptyOMDBResponse = new OMDBResponse() {{
                    _searchedMovies = new ArrayList<>();
                }};

                return FactGenerator.generateFacts(openTDBResponse, emptyDataMuseResponseArray, emptyOMDBResponse);
            } else {
                System.out.println("DISTR * 3 = " + distr * 3);
            }

            openTDBDistr = distr;
            dataMuseDistr = distr;
            openMovieDistr = distr;

            // If total is less than count; then count - total, and give it to opentdb
            if (total < count) {
                openTDBDistr = openTDBDistr + (count - total);
                System.out.println("openTDBDistr = " + openTDBDistr);
            }


            // For datamuse random character word:
            Random r = new Random();
            char c = (char) (r.nextInt(26) + 'a');

            // Fetch responses
            OpenTriviaDBResponse openTDBResponse = getOpenTDBQuestion(openTDBDistr, "medium", "multiple");
            DataMuseResponse[] dataMuseResponseArray = getDataMuse(String.valueOf(c), dataMuseDistr);
            OMDBResponse omdbResponse = getOMDB(OMDBParameterGenerator.pickTitleKeyword(), OMDBParameterGenerator.generateYear(), openMovieDistr);

            return FactGenerator.generateFacts(openTDBResponse, dataMuseResponseArray, omdbResponse);
        }
        catch (Exception e)
        {
            System.out.println("ERROR:\n" + (e.getMessage()));
            return new ArrayList<>() {{add(new Fact() {{
                _fact = " TOO MANY REQUESTS. Please wait a few moments before asking for facts again.";
            }});}};
        }
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
        try
        {
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
