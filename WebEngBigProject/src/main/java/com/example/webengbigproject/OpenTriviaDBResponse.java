package com.example.webengbigproject;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
    @author Himanshu Bohra
    This class contains the json response for OpenTDB API query.
    Example: https://opentdb.com/api.php?amount=1&difficulty=medium&type=multiple&encode=url3986
 */
public class OpenTriviaDBResponse
{
    @JsonProperty("type")
    private String type;

    @JsonProperty("difficulty")
    private String difficulty;

    @JsonProperty("category")
    private String category;

    @JsonProperty("question")
    private String question;

    @JsonProperty("correct_answer")
    private String correctAnswer;

    @JsonProperty("incorrect_answers")
    private ArrayList<String> incorrectAnswers;

    @JsonProperty("response_code")
    private String responseCode;


    // Getters and setters
    public String getDate()
    {
        return type;
    }

    public String getdifficulty()
    {
        return difficulty;
    }

    public String getCategory()
    {
        return category;
    }

    public String getQuestion()
    {
        return question;
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public String getIncorrectAnswers()
    {
        return incorrectAnswers;
    }

    public String responseCode()
    {
        return responseCode;
    }

}
