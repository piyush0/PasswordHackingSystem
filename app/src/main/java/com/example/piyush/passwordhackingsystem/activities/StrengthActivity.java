package com.example.piyush.passwordhackingsystem.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.piyush.passwordhackingsystem.adapters.StrengthActivityAdapter;
import com.example.piyush.passwordhackingsystem.models.Strength;
import com.example.piyush.passwordhackingsystem.models.Weakness;
import com.example.piyush.passwordhackingsystem.tasks.DictionaryDownloadTask;
import com.example.piyush.passwordhackingsystem.tasks.DictionaryReadTask;
import com.example.piyush.passwordhackingsystem.Pair;
import com.example.piyush.passwordhackingsystem.R;
import com.example.piyush.passwordhackingsystem.utils.FontsOverride;

import java.io.File;

public class StrengthActivity extends AppCompatActivity {
    TextView  tv_weakness_lettersonly,
            tv_weakness_numbersonly, tv_weakness_repeatchars, tv_consecutiveupper, tv_consecutivelower,
            tv_consecutivenumbers, tv_sequentialletters, tv_sequentialnumbers, tv_strentghperc, tv_common;
    Button btn_commonpass;

    String actualPassword;
    ViewPager pager;
    private PagerAdapter pagerAdapter;
    private Strength strength;
    private Weakness weakness;
    public static final String TAG="StrengthActivity";

    public static final int PERM_REQ_CODE_STRENGTH_ACT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_viewpager);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());

        initViews();

        Intent i = getIntent();
        actualPassword = i.getStringExtra("actualPassword");
        strength = Strength.getStrength(i.getStringExtra("strength"));
        weakness = Weakness.getWeakness(i.getStringExtra("weakness"));


//
//
//        tv_weakness_lettersonly.setText(String.valueOf(lettersOnly));
//
//        tv_weakness_numbersonly.setText(String.valueOf(numbersOnly));
//
//        tv_weakness_repeatchars.setText(String.valueOf(repeatCharacters));
//
//        tv_consecutiveupper.setText(String.valueOf(consecutiveUpperCase));
//
//        tv_consecutivelower.setText(String.valueOf(consecutiveLowerCase));
//
//        tv_consecutivenumbers.setText(String.valueOf(consecutiveNumbers));
//
//        tv_sequentialnumbers.setText(String.valueOf(sequentialNumbers));
//
//        tv_sequentialletters.setText(String.valueOf(sequentialLetters));


        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter=new StrengthActivityAdapter(getSupportFragmentManager(), strength, weakness);
        pager.setAdapter(pagerAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);


//        btn_commonpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//            }
//        });


//        tv_strentghperc.setText(String.valueOf(percentage));


    }

    public void initViews(){

        tv_weakness_lettersonly = (TextView) findViewById(R.id.tv_weakness_lettersonly);
        tv_weakness_numbersonly = (TextView) findViewById(R.id.tv_weakness_numbersonly);
        tv_weakness_repeatchars = (TextView) findViewById(R.id.tv_weakness_repeatchars);
        tv_consecutiveupper = (TextView) findViewById(R.id.tv_consecutiveupper);
        tv_consecutivelower = (TextView) findViewById(R.id.tv_consecutivelower);
        tv_consecutivenumbers = (TextView) findViewById(R.id.tv_consecutivenumbers);
        tv_sequentialletters = (TextView) findViewById(R.id.tv_sequentialletters);
        tv_sequentialnumbers = (TextView) findViewById(R.id.tv_sequentialnumbers);
        tv_strentghperc = (TextView) findViewById(R.id.tv_strentghperc);
        btn_commonpass = (Button) findViewById(R.id.btn_commonpass);
        tv_common = (TextView) findViewById(R.id.tv_common);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERM_REQ_CODE_STRENGTH_ACT) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                    && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                File f = new File(Environment.getExternalStorageDirectory(), "passwords.txt");
                if (!f.exists()) {
                    if(checkInternet()) {
                        DictionaryDownloadTask dictionaryDownloadTask = new DictionaryDownloadTask();
                        dictionaryDownloadTask.execute();

                        new DictionaryReadTask() {
                            @Override
                            protected void onPostExecute(Pair pair) {
                                super.onPostExecute(pair);
                                if (pair.checker) {

                                    tv_common.setText("YES");
                                } else {
                                    tv_common.setText("NO");
                                }
                            }
                        }.execute(actualPassword);}
                    else {
                        Toast.makeText(StrengthActivity.this, "Connect to the Internet", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    new DictionaryReadTask() {
                        @Override
                        protected void onPostExecute(Pair pair) {
                            super.onPostExecute(pair);
                            if (pair.checker) {

                                tv_common.setText("YES");
                            } else {
                                tv_common.setText("NO");
                            }
                        }
                    }.execute(actualPassword);
                }
            } else {
                Toast.makeText(StrengthActivity.this,
                        "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkInternet () {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        }
        return false;
    }
}