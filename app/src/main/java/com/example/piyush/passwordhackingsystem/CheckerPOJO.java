package com.example.piyush.passwordhackingsystem;

/**
 * Created by Piyush on 04-10-2016.
 */

public class CheckerPOJO {

    int minR;
    int maxR;
    String startsFrom;
    boolean containsNumber;
    boolean containsUpper;
    boolean containsLower;
    boolean containsSpecial;
    String actualPassword;

    public CheckerPOJO(String actualPassword, int minR, int maxR, String startsFrom, boolean containsNumber, boolean containsUpper, boolean containsLower, boolean containsSpecial) {
        this.minR = minR;
        this.maxR = maxR;
        this.startsFrom = startsFrom;
        this.containsNumber = containsNumber;
        this.containsUpper = containsUpper;
        this.containsLower = containsLower;
        this.containsSpecial = containsSpecial;
        this.actualPassword = actualPassword;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public int getMinR() {
        return minR;
    }

    public int getMaxR() {
        return maxR;
    }

    public String getStartsFrom() {
        return startsFrom;
    }

    public boolean isContainsNumber() {
        return containsNumber;
    }

    public boolean isContainsUpper() {
        return containsUpper;
    }

    public boolean isContainsLower() {
        return containsLower;
    }

    public boolean isContainsSpecial() {
        return containsSpecial;
    }
}
