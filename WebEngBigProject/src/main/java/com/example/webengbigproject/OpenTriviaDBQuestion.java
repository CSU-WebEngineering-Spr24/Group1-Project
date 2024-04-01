package com.example.webengbigproject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class OpenTriviaDBQuestion
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




    // Getters and setters
    public String getType()
    {
        return type;
    }

    public String getDifficulty()
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

    public ArrayList<String> getIncorrectAnswers()
    {
        return incorrectAnswers;
    }

}
