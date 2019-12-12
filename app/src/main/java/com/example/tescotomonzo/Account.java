package com.example.tescotomonzo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    @JsonProperty("id")
    public String id;

    @JsonProperty("closed")
    private String closed;

    @JsonProperty("created")
    private String created;

    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    public String type;


}
