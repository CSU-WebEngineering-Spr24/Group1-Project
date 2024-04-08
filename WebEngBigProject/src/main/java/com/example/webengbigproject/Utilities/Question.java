package com.example.webengbigproject.Utilities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * A class that represents a question in the form of a JSON Object. Has the question and the answers.
 * @author Himanshu Bohra
 */
public class Question
{
    @JsonProperty("question")
    public String _question;

    @JsonProperty("correct_answer")
    public String _correctAnswer;

    @JsonProperty("incorrect_answers")
    public ArrayList<String> _incorrectAnswers;
}
