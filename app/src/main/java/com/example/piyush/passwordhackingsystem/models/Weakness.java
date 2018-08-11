package com.example.piyush.passwordhackingsystem.models;

import com.google.gson.Gson;

/**
 * Created by piyush0 on 06/01/17.
 */

public class Weakness {

    Integer numCharsOnly, numNumsOnly, numRepChars, numConsecutiveUpper, numConsecutiveLower,
            numConsecutiveNums, numSequentialLetters, numSequentialNums;


    public Weakness(Integer numCharsOnly,
                    Integer numNumsOnly, Integer numRepChars, Integer numConsecutiveUpper,
                    Integer numConsecutiveLower, Integer numConsecutiveNums, Integer numSequentialLetters,
                    Integer numSequentialNums) {
        this.numCharsOnly = numCharsOnly;
        this.numNumsOnly = numNumsOnly;
        this.numRepChars = numRepChars;
        this.numConsecutiveUpper = numConsecutiveUpper;
        this.numConsecutiveLower = numConsecutiveLower;
        this.numConsecutiveNums = numConsecutiveNums;
        this.numSequentialLetters = numSequentialLetters;
        this.numSequentialNums = numSequentialNums;
    }

    public static String getJson(Weakness weakness){
        Gson gson = new Gson();
        return gson.toJson(weakness);
    }

    public static Weakness getWeakness(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Weakness.class);
    }

    public Integer getNumCharsOnly() {
        return numCharsOnly;
    }

    public Integer getNumNumsOnly() {
        return numNumsOnly;
    }

    public Integer getNumRepChars() {
        return numRepChars;
    }

    public Integer getNumConsecutiveUpper() {
        return numConsecutiveUpper;
    }

    public Integer getNumConsecutiveLower() {
        return numConsecutiveLower;
    }

    public Integer getNumConsecutiveNums() {
        return numConsecutiveNums;
    }

    public Integer getNumSequentialLetters() {
        return numSequentialLetters;
    }

    public Integer getNumSequentialNums() {
        return numSequentialNums;
    }
}
