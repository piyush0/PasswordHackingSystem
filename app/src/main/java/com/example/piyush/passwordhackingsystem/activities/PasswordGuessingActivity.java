package com.example.piyush.passwordhackingsystem.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyush.passwordhackingsystem.tasks.BruteForceTask;
import com.example.piyush.passwordhackingsystem.CheckerPOJO;
import com.example.piyush.passwordhackingsystem.tasks.DictionaryDownloadTask;
import com.example.piyush.passwordhackingsystem.tasks.DictionaryReadTask;
import com.example.piyush.passwordhackingsystem.Pair;
import com.example.piyush.passwordhackingsystem.R;
import com.example.piyush.passwordhackingsystem.utils.FontsOverride;

import java.io.File;
import java.math.BigInteger;

public class PasswordGuessingActivity extends AppCompatActivity {

    public static final String TAG = "GuessingActivity";

    Button cancel;
    Button btn_brute;
    Button btn_dictionary;
    String ETA;
    TextView eta;
    int minR;
    int maxR;
    String startsFrom;
    boolean containsNumber;
    boolean containsUpper;
    boolean containsLower;
    boolean containsSpecial;
    String actualPassword;
    TextView toShowPassword;
    String totalPermutations;
    ProgressBar progressBar;

    public static final int PERM_REQ_CODE_GUESSING_ACT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_guessing);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.GONE);

        progressBar.setBackgroundColor(Color.parseColor("#40ff60"));
        progressBar.setDrawingCacheBackgroundColor(Color.parseColor("#40ff60"));


        Intent i = getIntent();
        ETA = i.getStringExtra("ETA");
        minR = i.getIntExtra("minRange", 0);
        maxR = i.getIntExtra("maxRange", 0);
        startsFrom = i.getStringExtra("startsFrom");
        containsUpper = i.getBooleanExtra("containsUpperCase", false);
        containsLower = i.getBooleanExtra("containsLowerCase", false);
        containsNumber = i.getBooleanExtra("containsNumber", false);
        containsSpecial = i.getBooleanExtra("containsSpecialCharacter", false);
        actualPassword = i.getStringExtra("actualPassword");
        totalPermutations = i.getStringExtra("totalPermutations");
        final BigInteger totalPers = new BigInteger(totalPermutations);
        eta = (TextView) findViewById(R.id.activity_password_guessing_tv_eta);
        eta.setText(i.getStringExtra("ETAbeauty"));
        toShowPassword = (TextView) findViewById(R.id.activity_password_guessing_tv_password);

        btn_brute = (Button) findViewById(R.id.activity_password_guessing_btn_bruteFroce);
        btn_dictionary = (Button) findViewById(R.id.activity_password_guessing_btn_dictionary);
        cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setVisibility(View.GONE);
        BigInteger etaBig = new BigInteger(ETA);

        if (etaBig.compareTo(new BigInteger("600000")) == 1) {
            btn_brute.setEnabled(false);

        } else {

            btn_brute.setEnabled(true);
        }


        btn_brute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);

                final long startTime = System.currentTimeMillis();

                CheckerPOJO checkerPOJO = new CheckerPOJO(actualPassword, minR, maxR, startsFrom, containsNumber,
                        containsUpper, containsLower, containsSpecial);

                if (!actualPassword.startsWith(startsFrom)) {
                    toShowPassword.setText("Parameters Were Wrong");
                } else {

                    final AnonymousClass anonymousClass = new AnonymousClass(totalPermutations);

                    anonymousClass.execute(checkerPOJO);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            anonymousClass.cancel(true);
                        }
                    });

                }

            }
        });

        btn_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File f = new File(Environment.getExternalStorageDirectory(), "passwords.txt");

                int permResult = ContextCompat.checkSelfPermission(PasswordGuessingActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);


                if (permResult == PackageManager.PERMISSION_GRANTED) {

                    if (!f.exists()) {
                        if (checkInternet()) {
                            DictionaryDownloadTask dictionaryDownloadTask = new DictionaryDownloadTask();
                            dictionaryDownloadTask.execute();

                            new DictionaryReadTask() {
                                @Override
                                protected void onPostExecute(Pair pair) {
                                    super.onPostExecute(pair);
                                    if (pair.checker) {

                                        toShowPassword.setText(pair.password);
                                    } else {
                                        toShowPassword.setText("Not in dictionary");
                                    }
                                }
                            }.execute(actualPassword);
                        } else {
                            Toast.makeText(PasswordGuessingActivity.this, "Connect to the Internet", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        new DictionaryReadTask() {
                            @Override
                            protected void onPostExecute(Pair pair) {
                                super.onPostExecute(pair);
                                if (pair.checker) {

                                    toShowPassword.setText(pair.password);
                                } else {
                                    toShowPassword.setText("Not in dictionary");
                                }
                            }
                        }.execute(actualPassword);
                    }

                } else {

                    ActivityCompat.requestPermissions(PasswordGuessingActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERM_REQ_CODE_GUESSING_ACT);

                }


            }
        });

    }

    public class AnonymousClass extends BruteForceTask {

        public AnonymousClass(String totalPermutation) {
            super(totalPermutation);
        }


        @Override
        protected void onPostExecute(Pair pair) {
            long endTime;

            Log.d(TAG, "onPostExecute: ");
            super.onPostExecute(pair);
            if (pair.checker) {

                toShowPassword.setText(pair.password);
            } else {
                toShowPassword.setText("Parameters Were Wrong");
            }
            endTime = System.currentTimeMillis();
//            Log.d(TAG, "onPostExecute: Time taken" + String.valueOf(endTime - startTime));
        }

        @Override
        protected void onProgressUpdate(BigInteger... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(Integer.valueOf(values[1].toString()));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERM_REQ_CODE_GUESSING_ACT) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                File f = new File(Environment.getExternalStorageDirectory(), "passwords.txt");

                if (!f.exists()) {
                    if (checkInternet()) {
                        DictionaryDownloadTask dictionaryDownloadTask = new DictionaryDownloadTask();
                        dictionaryDownloadTask.execute();

                        new DictionaryReadTask() {
                            @Override
                            protected void onPostExecute(Pair pair) {
                                super.onPostExecute(pair);
                                if (pair.checker) {

                                    toShowPassword.setText(pair.password);
                                } else {
                                    toShowPassword.setText("Not in dictionary");
                                }
                            }
                        }.execute(actualPassword);
                    } else {
                        Toast.makeText(PasswordGuessingActivity.this, "Connect to the Internet", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    new DictionaryReadTask() {
                        @Override
                        protected void onPostExecute(Pair pair) {
                            super.onPostExecute(pair);
                            if (pair.checker) {

                                toShowPassword.setText(pair.password);
                            } else {
                                toShowPassword.setText("Not in dictionary");
                            }
                        }
                    }.execute(actualPassword);
                }
            } else {
                Toast.makeText(PasswordGuessingActivity.this,
                        "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean checkInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        }
//        Log.d(TAG, "checkInternet: typeName" + ni.getTypeName());
//        Log.d(TAG, "checkInternet: state(toString)" + ni.getState().toString());
//        Log.d(TAG, "checkInternet: extraInfo" + ni.getExtraInfo());
//        Log.d(TAG, "checkInternet: subtypeName" + ni.getSubtypeName());
        return false;
    }
}
