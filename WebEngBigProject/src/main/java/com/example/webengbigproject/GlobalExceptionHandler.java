package com.example.webengbigproject;

/**
 * @author Himanshu Bohra
 * This is an exception handler for all un-mapped api calls. Displays a help page.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleEverythingElse(NoHandlerFoundException e) {
        // Custom page

        String customResponse = "<div id = \"main\">" +
                "<b><i>You are seeing this page because you are not using the API call properly." +
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
        // Return the custom response with a 404 Not Found status
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, customResponse, "ERROR");
        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        //return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Submission Failed: Internal Server Error");
        return customResponse;
    }
}



class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    // Additional constructor for single error message
    public ApiError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }
}
