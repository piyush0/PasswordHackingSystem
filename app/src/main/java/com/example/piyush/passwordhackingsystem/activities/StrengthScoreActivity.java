package com.example.piyush.passwordhackingsystem.activities;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyush.passwordhackingsystem.Pair;
import com.example.piyush.passwordhackingsystem.R;
import com.example.piyush.passwordhackingsystem.tasks.DictionaryDownloadTask;
import com.example.piyush.passwordhackingsystem.tasks.DictionaryReadTask;
import com.example.piyush.passwordhackingsystem.ui.CustomProgressBar;
import com.example.piyush.passwordhackingsystem.utils.FontsOverride;

import java.io.File;

import static com.example.piyush.passwordhackingsystem.activities.StrengthActivity.PERM_REQ_CODE_STRENGTH_ACT;

public class StrengthScoreActivity extends AppCompatActivity {

    CustomProgressBar customProgressBar;
    TextView tv,tv_common;
    Button button;
    String actualPassword;
    float perc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_score);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA,getWindow());
        tv_common= (TextView) findViewById(R.id.tv_common);
        customProgressBar = (CustomProgressBar) findViewById(R.id.customProgressBar);
        tv = (TextView) findViewById(R.id.activity_strength_score_tv_SCORE);
        button = (Button) findViewById(R.id.activity_strength_score_btn_details);
        perc = getIntent().getFloatExtra("score",100);
        actualPassword=getIntent().getStringExtra("actualPassword");

        checkCommon();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StrengthScoreActivity.this,StrengthActivity.class);
                i.putExtra("actualPassword",getIntent().getStringExtra("actualPassword"));
                i.putExtra("strength", getIntent().getStringExtra("strength"));
                i.putExtra("weakness", getIntent().getStringExtra("weakness"));
                startActivity(i);
                finish();
            }
        });





    }

    private void checkCommon(){
        File f = new File(Environment.getExternalStorageDirectory(), "passwords.txt");

        int permResult = ContextCompat.checkSelfPermission(StrengthScoreActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        if (permResult == PackageManager.PERMISSION_GRANTED) {

            if (!f.exists()) {

                if(checkInternet()) {
                    DictionaryDownloadTask dictionaryDownloadTask = new DictionaryDownloadTask();
                    dictionaryDownloadTask.execute();

                    new DictionaryReadTask() {
                        @Override
                        protected void onPostExecute(Pair pair) {
                            super.onPostExecute(pair);
                            if (pair.checker) {
                                tv_common.setVisibility(View.VISIBLE);
                                tv_common.setText("Your Password Is Too Common!");
                            }
                        }
                    }.execute(actualPassword);}
                else {
                    Toast.makeText(StrengthScoreActivity.this, "Connect to the Internet", Toast.LENGTH_SHORT).show();
                }
            } else {

                new DictionaryReadTask() {
                    @Override
                    protected void onPostExecute(Pair pair) {
                        super.onPostExecute(pair);
                        if (pair.checker) {

                            tv_common.setVisibility(View.VISIBLE);
                            tv_common.setText("Your Password Is Too Common!");
                        }
                    }
                }.execute(actualPassword);
            }

        } else {

            ActivityCompat.requestPermissions(StrengthScoreActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERM_REQ_CODE_STRENGTH_ACT);

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

    @Override
    protected void onResume() {
        super.onResume();
        animate();
    }

    public void animate() {

        animateAngle();
        animateColor();
        animateText();

    }

    int getTrafficlightColor(double value){
        return android.graphics.Color.HSVToColor(new float[]{(float)value*120f,1f,1f});
    }

    public void animateAngle() {
        float endAngle = (perc/100)*360;
        ObjectAnimator animator = ObjectAnimator.ofFloat(customProgressBar,"sweepAngle",0f,endAngle);
        animator.setDuration(2500);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();

    }

    public void animateColor() {
        final float[] from = new float[3],
                to =   new float[3];

        Color.colorToHSV(Color.parseColor("#FF00FF00"), from);
        double d = perc/100;

        Color.colorToHSV(getTrafficlightColor(1-d), to);

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.setDuration(2500);
        anim.setInterpolator(new BounceInterpolator());
        final float[] hsv  = new float[3];
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override public void onAnimationUpdate(ValueAnimator animation) {

                hsv[0] = from[0] + (to[0] - from[0])*animation.getAnimatedFraction();
                hsv[1] = from[1] + (to[1] - from[1])*animation.getAnimatedFraction();
                hsv[2] = from[2] + (to[2] - from[2])*animation.getAnimatedFraction();

                customProgressBar.setColor(Color.HSVToColor(hsv));
            }
        });

        anim.start();
    }

    public void animateText() {
        final ValueAnimator valAn = ValueAnimator.ofInt(0,(int)perc);

        valAn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                tv.setText(String.valueOf(valAn.getAnimatedValue()));
            }

        });


        valAn.setDuration(2500);
        valAn.setInterpolator(new BounceInterpolator());
        valAn.start();
    }

}
