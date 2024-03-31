package com.example.webengbigproject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*") // Allow all? (at least gets rid of CORS)
public class HomeController
{
    @GetMapping(value = {"/", "/form", "/usage"})
    public String index()
    {
        return "index"; // This will serve index.html located in src/main/resources/templates/
    }
}

