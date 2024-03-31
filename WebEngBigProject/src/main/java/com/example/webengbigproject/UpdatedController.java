package com.example.webengbigproject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/*
    TODO: Refactor this code for open trivia DB API (TASK 1 for backend)
 */

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // Allow all? (at least gets rid of CORS)
public class UpdatedController
{
    private APODService _apodService = null;

    public UpdatedController(APODService apodService)
    {
        this._apodService = apodService;
    }


    @GetMapping("/apod")
    public OpenTriviaDBResponse getApod()
    {
        return _apodService.getAPODObject();
    }

    @GetMapping("/apod/getByDate/{date}")
    public OpenTriviaDBResponse getApod(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)
    {
        return _apodService.getAPODObject(date);
    }

    @GetMapping("/apod/getByDate")
    public OpenTriviaDBResponse[] getApod(@RequestParam(value = "start_date", required = false) String start_date,
                                          @RequestParam(value = "end_date", required = false) String end_date)
    {
        return _apodService.getAPODObject(start_date,end_date);
    }

    @GetMapping("/apod/getByCount/{count}")
    public OpenTriviaDBResponse[] getApod(@PathVariable String count)
    {
        return _apodService.getAPODObject(Integer.parseInt(count));
    }


}
