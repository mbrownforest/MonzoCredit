package com.example.tescotomonzo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pots {

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    String name;

    @JsonProperty("balance")
    Integer balance;

    @JsonProperty("style")
    public String style;



}
