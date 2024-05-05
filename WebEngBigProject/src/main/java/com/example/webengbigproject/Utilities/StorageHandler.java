package com.example.webengbigproject.Utilities;

import com.example.webengbigproject.Dashboard.ResultJSON;
import com.example.webengbigproject.Dashboard.ScoreResponseJSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;





public class StorageHandler
{

    private static final String SCORE_FILE_NAME = "scores.json";
    private static final String SCORE_FILE_PATH = Paths.get(System.getProperty("user.dir"), SCORE_FILE_NAME).toString();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public ArrayList<ScoreResponseJSON> readScores() throws IOException
    {
        return objectMapper.readValue(new File(SCORE_FILE_PATH), new TypeReference<ArrayList<ScoreResponseJSON>>(){});
    }

    public void updateScores(String mode, String user, int score) throws IOException
    {
        List<ScoreResponseJSON> scores = readScores();
        Optional<ScoreResponseJSON> modeScoresOptional = scores.stream()
                .filter(m -> m._mode.equals(mode))
                .findFirst();

        if (modeScoresOptional.isPresent()) {
            ScoreResponseJSON modeScores = modeScoresOptional.get();
            Optional<ResultJSON> userResultOptional = modeScores._results.stream()
                    .filter(r -> r._user.equals(user))
                    .findFirst();

            if (userResultOptional.isPresent()) {
                // User exists, update the score
                userResultOptional.get()._score = score;
            } else {
                // User doesn't exist, add a new result
                ResultJSON newResult = new ResultJSON();
                newResult._user = user;
                newResult._score = score;
                modeScores._results.add(newResult);
            }
        } else {
            // Mode doesn't exist, create a new mode with the result
            ScoreResponseJSON newModeScores = new ScoreResponseJSON();
            newModeScores._mode = mode;
            ResultJSON newResult = new ResultJSON();
            newResult._user = user;
            newResult._score = score;
            newModeScores._results.add(newResult);
            scores.add(newModeScores);
        }

        writeScores(scores);
    }

    public void writeScores(List<ScoreResponseJSON> scores) throws IOException
    {
        objectMapper.writeValue(new File(SCORE_FILE_PATH), scores);
    }


}
