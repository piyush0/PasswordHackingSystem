package com.example.piyush.passwordhackingsystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyush.passwordhackingsystem.R;
import com.example.piyush.passwordhackingsystem.utils.FontsOverride;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SecondActivity extends AppCompatActivity {

    int minRange, maxRange;
    String startsFrom;
    boolean containsLowerCase, containsUpperCase, containsNumber, containsSpecialCharacter;

    EditText minR, maxR, passwordStartFrom;
    CheckBox lowerCase, upperCase, number, specialCharacter;
    Button submit;
    String actualPassword;


    BigInteger totalPermutations;
    BigDecimal ETA;

    Boolean invalidInput;

    public static final double TOTAL_ITERATIONS = 1.79769313486231570E+6;
    public static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());

        Intent i = getIntent();
        actualPassword = i.getStringExtra("actualPassword");

        this.minR = (EditText) findViewById(R.id.et1);
        this.maxR = (EditText) findViewById(R.id.et2);
        this.passwordStartFrom = (EditText) findViewById(R.id.et3);
        this.lowerCase = (CheckBox) findViewById(R.id.second_activity_cb_lowerCase);
        this.upperCase = (CheckBox) findViewById(R.id.second_activity_cb_upperCase);
        this.number = (CheckBox) findViewById(R.id.second_activity_cb_number);
        this.specialCharacter = (CheckBox) findViewById(R.id.second_activity_cb_specialCharacter);
        this.submit = (Button) findViewById(R.id.second_activity_btn_submit);

        this.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                invalidInput = false;

                if (minR.getText().toString().equals("") || maxR.getText().toString().equals("")) {
                    invalidInput = true;
                } else {
                    minRange = (Integer.valueOf(minR.getText().toString()));
                    maxRange = (Integer.valueOf(maxR.getText().toString()));
                }
                startsFrom = passwordStartFrom.getText().toString();
                containsLowerCase = lowerCase.isChecked();
                containsUpperCase = upperCase.isChecked();
                containsNumber = number.isChecked();
                containsSpecialCharacter = specialCharacter.isChecked();

                totalPermutations = getTotalPermutations();
                ETA = getETA();

//                tv = (TextView) findViewById(R.id.test);
//                tv.setText(etaBeautiful(ETA));





                if (minRange > maxRange) {
                    invalidInput = true;
                } else if (minRange < startsFrom.length()) {
                    invalidInput = true;
                } else if (startsFrom.length() > maxRange) {
                    invalidInput = true;
                }


                if (invalidInput) {
                    Toast.makeText(SecondActivity.this, "Invalid Parameters", Toast.LENGTH_LONG).show();
                } else {

                    Intent i = new Intent(getApplicationContext(), PasswordGuessingActivity.class);
                    i.putExtra("ETA", ETA.toBigInteger().toString());
                    i.putExtra("ETAbeauty", etaBeautiful(ETA));
                    i.putExtra("minRange", minRange);
                    i.putExtra("maxRange", maxRange);
                    i.putExtra("startsFrom", startsFrom);
                    i.putExtra("containsLowerCase", containsLowerCase);
                    i.putExtra("containsUpperCase", containsUpperCase);
                    i.putExtra("containsNumber", containsNumber);
                    i.putExtra("containsSpecialCharacter", containsSpecialCharacter);
                    i.putExtra("actualPassword", actualPassword);
//                    i.putExtra("actualPassword", removeLater.getText().toString());
                    i.putExtra("totalPermutations", totalPermutations.toString());
                    Log.d(TAG, "onClick: Eta beautiful" + etaBeautiful(ETA));

                    startActivity(i);
                }
            }
        });


    }

    public BigInteger getTotalPermutations() {


        BigInteger retVal = new BigInteger("0");
        Integer totalPossibilities = 0;

        if (containsLowerCase) {
            totalPossibilities += 26;
        }

        if (containsUpperCase) {
            totalPossibilities += 26;
        }

        if (containsNumber) {
            totalPossibilities += 10;
        }

        if (containsSpecialCharacter) {
            totalPossibilities += 10;
        }

        int lengthOfStartsFrom = this.startsFrom.length();
        int temp = this.minRange;
        BigInteger total = new BigInteger(totalPossibilities.toString());
        while (temp <= this.maxRange) {

            Integer spots = temp - lengthOfStartsFrom;

            if (spots > 0) {

                Integer tempForSpots = spots;
                BigInteger toBeAdded = new BigInteger("1");
                while (tempForSpots > 0) {

                    toBeAdded = toBeAdded.multiply(total);

                    tempForSpots--;
                }

                retVal = retVal.add(toBeAdded);

            }
            temp++;
        }

        return retVal;

    }


    public BigDecimal getETA() {

        BigDecimal totalPers = new BigDecimal(totalPermutations);

        double startTime = System.currentTimeMillis();

        for (double i = 0; i < TOTAL_ITERATIONS; i++) {
            if ("abcdqewadvavfdsbffbdfgnhfjgdntyutdcbtfynbsv".equals("vndalisvdavdadbfddsfbadfdvnvlfa")) {
                //Random Comparison between two strings.
            }
        }

        double endTime = System.currentTimeMillis();

        Double sampleTimeElapsed = endTime - startTime;

        BigDecimal retVal = new BigDecimal("1");

        BigDecimal ste = new BigDecimal(sampleTimeElapsed.toString());


        ste = ste.divide(new BigDecimal(String.valueOf(TOTAL_ITERATIONS)), 1000, RoundingMode.HALF_UP);
        retVal = totalPers.multiply(ste).multiply(new BigDecimal("10000")); //our hypothesis. (Error checking mechanism)
        return retVal;

    }

    public static String etaBeautiful(BigDecimal eta) {
        BigDecimal conversion;
        String retval = "";
        BigDecimal milliVal = new BigDecimal(("1"));
        BigDecimal secondsVal = new BigDecimal("1000");
        BigDecimal minuteVal = new BigDecimal("60000");
        BigDecimal hourVal = new BigDecimal("3.6e+6");
        BigDecimal dayVal = new BigDecimal("8.64e+7");
        BigDecimal yearVal = new BigDecimal("3.154e+10");
        BigDecimal monthVal = new BigDecimal("2.628e+9");
        if (eta.compareTo(yearVal) == 1) {
            conversion = eta.divide(yearVal, 2, RoundingMode.HALF_UP);

            retval = conversion.toString() + " Years";
        } else if (eta.compareTo(monthVal) == 1) {
            conversion = eta.divide(monthVal, 2, RoundingMode.HALF_UP);

            retval = conversion.toString() + " Months";
        } else if (eta.compareTo(dayVal) == 1) {
            conversion = eta.divide(dayVal, 2, RoundingMode.HALF_UP);

            retval = conversion.toString() + " Days";
        } else if (eta.compareTo(hourVal) == 1) {
            conversion = eta.divide(hourVal, 2, RoundingMode.HALF_UP);

            retval = conversion.toString() + " Hours";
        } else if (eta.compareTo(minuteVal) == 1) {
            conversion = eta.divide(minuteVal, 2, RoundingMode.HALF_UP);

            retval = conversion.toString() + " Minutes";
        } else if (eta.compareTo(secondsVal) == 1) {
            conversion = eta.divide(secondsVal, 2, RoundingMode.HALF_UP);

            retval = conversion.toString() + " Seconds";
        } else {
            conversion = eta.divide(milliVal, 2, RoundingMode.HALF_UP);
            retval = conversion.toString() + " Milliseconds";
        }

        return retval;

    }

}
