package com.example.webengbigproject;

/**
 * @author Himanshu Bohra
 * Testing class for Integration testing: API end points expected response
 */

import net.minidev.json.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class IntegrationTests
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSubmitApiEndpoint() throws Exception
    {
        // we will test it directly to see if submission successful string was obtained or not
        // if not, then we will fail the test: isOk() false or Submission Successful string not there.
        mockMvc.perform(MockMvcRequestBuilders.get("/submit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Submission Successful"));
    }


    @Test
    public void testQuestionsApiEndpoint() throws Exception
    {

        // Mvc result can hold the result from the api call
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/questions?amount=5")).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseString = response.getContentAsString();


        // first we gotta turn this response into a json array because thats the response format
        JSONArray questionsArray = new JSONArray(responseString);

        // now just check through assert
        Assertions.assertEquals(5, questionsArray.length());




    }
}
