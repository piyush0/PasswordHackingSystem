package com.example.piyush.passwordhackingsystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.piyush.passwordhackingsystem.R;
import com.example.piyush.passwordhackingsystem.models.Strength;
import com.example.piyush.passwordhackingsystem.models.Weakness;
import com.example.piyush.passwordhackingsystem.utils.FontsOverride;
import com.example.piyush.passwordhackingsystem.utils.ScoreCalculator;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG="LoginActivity";
    EditText passwordText;
    Button strengthBtn;
    Button hackingBtn;
    CheckBox showBox;

    public static final int INPUT_TYPE_PASSWORD = 129;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());

        passwordText = (EditText) findViewById(R.id.editText);
        strengthBtn = (Button) findViewById(R.id.btn_strength);
        hackingBtn = (Button) findViewById(R.id.btn_hacking);
        showBox = (CheckBox) findViewById(R.id.checkBox);

        showBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    passwordText.setInputType(INPUT_TYPE_PASSWORD);
                } else {

                    passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        hackingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SecondActivity.class);
                i.putExtra("actualPassword", passwordText.getText().toString());
                startActivity(i);
            }
        });

        strengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, StrengthScoreActivity.class);


                String actualPassword = passwordText.getText().toString();


                int numOfChars = numberOfCharacters(actualPassword);
                int upperCaseLetters = upperCaseLetters(actualPassword);
                int lowerCaseLetters = lowerCaseLetters(actualPassword);
                int numbers = numbers(actualPassword);
                int specialCharacters = specialChars(actualPassword);
                int lettersOnly = lettersOnly(actualPassword);
                int numbersOnly = numbersOnly(actualPassword);
                int midNumOrSymbols = midNumOrSymbols(actualPassword);
                int repeatCharacters = repeatChars(actualPassword);
                int consecutiveUpperCase = consecutiveUpperCaseLetters(actualPassword);
                int consecutiveLowerCase = consecutiveLowerCaseLetters(actualPassword);
                int consecutiveNumbers = consecutiveNumbers(actualPassword);
                int sequentialNumbers = sequentialNumbers(actualPassword);
                int sequentialLetters = sequentialLetters(actualPassword);

                Strength strength = new Strength(numOfChars,
                        upperCaseLetters,lowerCaseLetters,
                        numbers,specialCharacters,midNumOrSymbols);

                Weakness weakness = new Weakness(lettersOnly,numbersOnly,
                        repeatCharacters,consecutiveUpperCase,consecutiveLowerCase,
                        consecutiveNumbers,sequentialLetters,sequentialNumbers);

                Float score = ScoreCalculator.getScore(strength, weakness);

                i.putExtra("score",score);
                i.putExtra("strength", Strength.getJson(strength));
                i.putExtra("weakness", Weakness.getJson(weakness));
                i.putExtra("actualPassword", actualPassword);

                startActivity(i);
            }
        });

    }

    public int numberOfCharacters(String str) {
        return str.length();
    }

    public int upperCaseLetters(String str) {
        int retVal = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) >= 65 && str.charAt(i) <= 90) {
                retVal++;
            }
        }

        return retVal;

    }

    public int lowerCaseLetters(String str) {
        int retVal = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) >= 97 && str.charAt(i) <= 122) {
                retVal++;
            }
        }

        return retVal;

    }

    public int numbers(String str) {
        int retVal = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                retVal++;
            }
        }

        return retVal;

    }

    public int specialChars(String str) {
        int retVal = 0;
        for (int i = 0; i < str.length(); ++i) {
            if ((str.charAt(i) >= 33 && str.charAt(i) <= 40) || str.charAt(i) == 64 || str.charAt(i) == 95) {
                retVal++;
            }
        }

        return retVal;

    }

    public int lettersOnly(String str) {
        int retVal = 0;
        if (str.length() == lowerCaseLetters(str) + upperCaseLetters(str)) {
            retVal = str.length();

        }

        return retVal;

    }

    public int numbersOnly(String str) {
        int retVal = 0;
        if (str.length() == numbers(str)) {
            retVal = str.length();

        }

        return retVal;

    }

    public int midNumOrSymbols(String str) {
        int retVal = 0;
        for (int i = 1; i < str.length() - 1; ++i) {
            if ((str.charAt(i) >= 48 && str.charAt(i) <= 57)
                    || (((str.charAt(i) >= 33 && str.charAt(i) <= 40) || str.charAt(i) == 64 || str.charAt(i) == 95))) {
                retVal++;
            }
        }
        return retVal;

    }

    public int repeatChars(String str) {
        int retVal = 0;
        char[] array = new char[256];
        for (int i = 0; i < str.length(); ++i) {
            ++array[str.charAt(i)];
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] > 1) {
                retVal++;

            }
        }
        return retVal;
    }

    public int consecutiveNumbers(String str) {
        return consecutives(str)[2];
    }

    public int consecutiveUpperCaseLetters(String str) {
        return consecutives(str)[0];
    }

    public int consecutiveLowerCaseLetters(String str) {
        return consecutives(str)[1];
    }

    public int[] consecutives(String s) {

        int i = 0;

        int u = 0;
        int l = 0;
        int n = 0;

        while (i < s.length() - 1) {
            int tempu = 0;
            int templ = 0;
            int tempn = 0;
            while (Character.isUpperCase(s.charAt(i)) && Character.isUpperCase(s.charAt(i + 1))) {
                i++;
                tempu++;

                if (i >= s.length() - 1)
                    break;
            }
            while (Character.isLowerCase(s.charAt(i)) && Character.isLowerCase(s.charAt(i + 1))) {
                i++;
                templ++;

                if (i >= s.length() - 1)
                    break;
            }
            while (Character.isDigit(s.charAt(i)) && Character.isDigit(s.charAt(i + 1))) {
                i++;
                tempn++;

                if (i >= s.length() - 1)
                    break;
            }

            if (tempu != 0) {
                tempu++;
            }
            if (templ != 0) {
                templ++;
            }
            if (tempn != 0) {
                tempn++;
            }

            u += tempu;
            l += templ;
            n += tempn;

            i++;

        }

        int[] retVal = {u, l, n};

        return retVal;
    }

    public int sequentialNumbers(String str) {


        int retVal = 0;
        for (int i = 0; i < str.length(); i++) {

            int thisResult = 0;
            while (i < str.length() - 1 && str.charAt(i + 1) - str.charAt(i) == 1 && str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                thisResult++;
                i++;
            }
            thisResult++;

            if (thisResult >= 3) {
                retVal += thisResult;
            }

        }

        return retVal;


    }

    public int sequentialLetters(String str) {


        int retVal = 0;
        for (int i = 0; i < str.length(); i++) {

            int thisResult = 0;
            while (i < str.length() - 1 && str.charAt(i + 1) - str.charAt(i) == 1 && ((str.charAt(i) <= 90 && str.charAt(i) >= 65) || (str.charAt(i) >= 97 && str.charAt(i) <= 122))) {
                thisResult++;
                i++;
            }
            thisResult++;

            if (thisResult >= 3) {
                retVal += thisResult;
            }

        }

        return retVal;


    }
}
