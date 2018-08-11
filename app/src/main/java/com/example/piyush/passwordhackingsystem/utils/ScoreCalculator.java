package com.example.piyush.passwordhackingsystem.utils;

import com.example.piyush.passwordhackingsystem.models.Strength;
import com.example.piyush.passwordhackingsystem.models.Weakness;

/**
 * Created by piyush0 on 06/01/17.
 */

public class ScoreCalculator {

    public static Float getScore(Strength strength, Weakness weakness){

        int score = 0;

        score = score + strength.getNumChars() * 4 + (strength.getNumUpperChars()) * 2 +
                (strength.getNumLowerChars()) * 2 + strength.getNumNums() * 4 + strength.getNumSymbols() * 6 +
                strength.getNumMidNumorSymbols() * 2 - weakness.getNumCharsOnly() - weakness.getNumNumsOnly()
                - weakness.getNumRepChars() - weakness.getNumConsecutiveUpper() * 2 -
                weakness.getNumConsecutiveLower() * 2 - weakness.getNumConsecutiveNums() * 2 -
                weakness.getNumSequentialLetters() * 3 - weakness.getNumSequentialNums() * 3;

        int maxScore = 100;

        float percentage = score * 100;
        percentage = percentage / maxScore;

        if (percentage > 100) {
            percentage = 99;
        }

        return percentage;
    }

}
