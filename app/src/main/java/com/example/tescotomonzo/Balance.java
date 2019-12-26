package com.example.tescotomonzo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Balance implements Serializable {

    @JsonProperty("balance")
    private Integer balance;

    @JsonProperty("total_balance")
    private Integer totalBalance;

}
