package com.example.piyush.passwordhackingsystem.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.piyush.passwordhackingsystem.CheckerPOJO;
import com.example.piyush.passwordhackingsystem.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Piyush on 04-10-2016.
 */

public class BruteForceTask extends AsyncTask<CheckerPOJO, BigInteger, Pair> {


    public static final String TAG = "BruteTask";
    BigInteger count;
    String totalPermutations;

    public BruteForceTask(String totalPermutation) {
        count = new BigInteger("0");
        this.totalPermutations = totalPermutation;
    }



    @Override
    protected Pair doInBackground(CheckerPOJO... params) {

        Log.d(TAG, "doInBackground: ");
        CheckerPOJO checkerPOJO = params[0];
        int minRange = checkerPOJO.getMinR();
        int maxRange = checkerPOJO.getMaxR();
        boolean containsLower = checkerPOJO.isContainsLower();
        boolean containsUpper = checkerPOJO.isContainsUpper();
        boolean containsNumber = checkerPOJO.isContainsNumber();
        boolean containsSpecial = checkerPOJO.isContainsSpecial();
        String startsFrom = checkerPOJO.getStartsFrom();
        String actualPassword = checkerPOJO.getActualPassword();

        int withoutStarts = minRange - startsFrom.length();
        int withoutStartsUpperLimit = maxRange - startsFrom.length();

        StringBuilder toBePassedinMatcher= new StringBuilder(actualPassword);
        toBePassedinMatcher.delete(0,startsFrom.length());

        Pair retVal = new Pair();
        retVal.checker = false;
        retVal.password = "NULL";
//        Log.d(TAG, "doInBackground: " + toBePassedinMatcher);

        for(int i = withoutStarts ; i<= withoutStartsUpperLimit ; i++) {
            Pair answer = help(dataset(containsUpper,containsLower,containsNumber,containsSpecial), "" ,
                    toBePassedinMatcher.toString(),i);
//            publishProgress(count);
            if(answer.checker) {
                retVal.checker = true;
                retVal.password = startsFrom;
                retVal.password += answer.password;
                return retVal;
            }
        }

        Log.d(TAG, "doInBackground: total pers " + totalPermutations);

        Log.d(TAG, "doInBackground: count" + count.toString());
        return retVal;
    }

    public Pair help(ArrayList<Character> dataset, String osf, String password, int length) {


        if(osf.length() == length) {
            count = count.add(new BigInteger("1"));



            BigDecimal decimalCount = new BigDecimal(count);
            BigDecimal decimalTotalPers = new BigDecimal(totalPermutations);

//                        BigDecimal perc = decimalCount.divideToIntegralValue(decimalTotalPers);
            BigDecimal perc = decimalCount.divide(decimalTotalPers,2, RoundingMode.HALF_UP);
            perc = perc.multiply(new BigDecimal("100"));

            int prog = Integer.valueOf(perc.toBigInteger().toString());

            if(prog%5 == 0 ) {
                publishProgress(count, new BigInteger(String.valueOf(prog)));
            }


            if(osf.equals(password)) {
                Pair base = new Pair();
                base.checker = true;
                base.password=osf;
                publishProgress(count, new BigInteger(String.valueOf("100")));
                return base;
            }
            else{
                Pair base = new Pair();
                base.checker = false;
                base.password="NULL";
                return base;
            }

        }


        Pair retVal = new Pair();

        retVal.checker = false;
        retVal.password = "";

        for(int i = 0;  i<dataset.size(); i++) {

            Pair prev = help(dataset, osf + dataset.get(i) , password, length);

            if(prev.checker) {
                retVal.checker = true;
                retVal.password = prev.password;
                break;
            }

        }

        return retVal;

    }

    public ArrayList<Character> dataset(boolean Upper, boolean lower, boolean numbers, boolean special) {

        ArrayList<Character> retVal = new ArrayList<>();

        if(Upper) {
            for(int i = 65; i<91; i++) {
                char toBeAdded = (char)i;
                retVal.add(toBeAdded);
            }
        }

        if(lower) {
            for(int i = 97; i<123; i++) {
                char toBeAdded = (char)i;
                retVal.add(toBeAdded);
            }
        }

        if(numbers) {
            for(int i = 48; i<58; i++) {
                char toBeAdded = (char)i;
                retVal.add(toBeAdded);
            }
        }

        if(special) {
            for(int i = 33; i<41; i++) {
                char toBeAdded = (char)i;
                retVal.add(toBeAdded);
            }

            retVal.add('@');
            retVal.add('_');
        }



        return retVal;
    }



}
