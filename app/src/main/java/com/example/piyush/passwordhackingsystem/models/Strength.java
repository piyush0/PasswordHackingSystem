package com.example.piyush.passwordhackingsystem.models;

import com.google.gson.Gson;

/**
 * Created by piyush0 on 06/01/17.
 */

public class Strength {
    Integer numChars, numUpperChars, numLowerChars, numNums, numSymbols, numMidNumorSymbols;

    public Strength(Integer numChars, Integer numUpperChars, Integer numLowerChars, Integer numNums, Integer numSymbols, Integer numMidNumorSymbols) {
        this.numChars = numChars;
        this.numUpperChars = numUpperChars;
        this.numLowerChars = numLowerChars;
        this.numNums = numNums;
        this.numSymbols = numSymbols;
        this.numMidNumorSymbols = numMidNumorSymbols;
    }

    public static String getJson(Strength strength){
        Gson gson = new Gson();
        return gson.toJson(strength);
    }

    public static Strength getStrength(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Strength.class);
    }

    public Integer getNumChars() {
        return numChars;
    }

    public Integer getNumUpperChars() {
        return numUpperChars;
    }

    public Integer getNumLowerChars() {
        return numLowerChars;
    }

    public Integer getNumNums() {
        return numNums;
    }

    public Integer getNumSymbols() {
        return numSymbols;
    }

    public Integer getNumMidNumorSymbols() {
        return numMidNumorSymbols;
    }
}
