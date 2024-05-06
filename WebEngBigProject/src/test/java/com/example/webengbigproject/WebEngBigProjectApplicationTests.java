package com.example.webengbigproject;

/**
 * @author Himanshu Bohra
 * Testing class for Controller and Service testing: Home controller exists (bundle.js) + OpenTDBService success code.
 */

import com.example.webengbigproject.OpenTDB.OpenTDBService;
import com.example.webengbigproject.OpenTDB.OpenTriviaDBResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
class WebEngBigProjectApplicationTests
{
    @Autowired
    private OpenTDBService openTDBService;

    @Autowired
    private HomeController controller;

    @Test
    void homeControllerTest() throws Exception
    {
        // Simple check to see the home controller is there for the bundle.js
        assertThat(controller).isNotNull();
    }
    @Test
    void openTDBServiceTest()
    {
        // Open TDB response code '0' is actually success. They did not comply with HTTP success codes.
        OpenTriviaDBResponse openTriviaDBResponse = openTDBService.getOpenTDBObject(1, "medium", "multiple");
        Assertions.assertEquals("0", openTriviaDBResponse._responseCode);
        System.out.println("Test successful");
    }

}
