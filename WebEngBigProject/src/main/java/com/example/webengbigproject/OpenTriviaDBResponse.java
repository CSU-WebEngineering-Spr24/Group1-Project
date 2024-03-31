package com.example.webengbigproject;
import com.fasterxml.jackson.annotation.JsonProperty;
public class OpenTriviaDBResponse
{
    @JsonProperty("date")
    private String date;

    @JsonProperty("explanation")
    private String explanation;

    @JsonProperty("hdurl")
    private String hdurl;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("service_version")
    private String serviceVersion;

    @JsonProperty("title")
    private String title;

    @JsonProperty("url")
    private String url;


    // Getters and setters
    public String getDate()
    {
        return date;
    }

    public String getExplanation()
    {
        return explanation;
    }

    public String getHdurl()
    {
        return hdurl;
    }

    public String getMediaType()
    {
        return mediaType;
    }

    public String getServiceVersion()
    {
        return serviceVersion;
    }

    public String getTitle()
    {
        return title;
    }

    public String getUrl()
    {
        return url;
    }

}
