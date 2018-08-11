package com.example.piyush.passwordhackingsystem.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piyush.passwordhackingsystem.R;
import com.example.piyush.passwordhackingsystem.models.Weakness;

/**
 * Created by abhishekyadav on 05/01/17.
 */

public class WeaknessFragment extends Fragment {

    Weakness weakness;
    TextView tv_weakness_lettersonly,
            tv_weakness_numbersonly, tv_weakness_repeatchars, tv_consecutiveupper, tv_consecutivelower,
            tv_consecutivenumbers, tv_sequentialletters, tv_sequentialnumbers;
    public static final String TAG="WeaknessFrag";

    public static WeaknessFragment newInstance(@NonNull Weakness weakness){

        Bundle args = new Bundle();
        args.putString("weakness", Weakness.getJson(weakness));
        WeaknessFragment weaknessFragment = new WeaknessFragment();
        weaknessFragment.setArguments(args);
        return weaknessFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: "+weakness);

        this.weakness = Weakness.getWeakness(getArguments().getString("weakness"));

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_weaknesses, container, false);
        setTexts(rootView);

        return rootView;

    }
    private void setTexts(ViewGroup rootView){
        tv_weakness_lettersonly= (TextView) rootView.findViewById(R.id.tv_weakness_lettersonly);

        tv_weakness_numbersonly= (TextView) rootView.findViewById(R.id.tv_weakness_numbersonly);

        tv_weakness_repeatchars= (TextView) rootView.findViewById(R.id.tv_weakness_repeatchars);

        tv_consecutiveupper= (TextView) rootView.findViewById(R.id.tv_consecutiveupper);

        tv_consecutivelower= (TextView) rootView.findViewById(R.id.tv_consecutivelower);

        tv_consecutivenumbers= (TextView) rootView.findViewById(R.id.tv_consecutivenumbers);

        tv_sequentialnumbers= (TextView) rootView.findViewById(R.id.tv_sequentialnumbers);

        tv_sequentialletters= (TextView) rootView.findViewById(R.id.tv_sequentialletters);

        tv_weakness_lettersonly.setText(String.valueOf(this.weakness.getNumCharsOnly()));

        tv_weakness_numbersonly.setText(String.valueOf(this.weakness.getNumNumsOnly()));

        tv_weakness_repeatchars.setText(String.valueOf(this.weakness.getNumRepChars()));

        tv_consecutiveupper.setText(String.valueOf(this.weakness.getNumConsecutiveUpper()));

        tv_consecutivelower.setText(String.valueOf(this.weakness.getNumConsecutiveLower()));

        tv_consecutivenumbers.setText(String.valueOf(this.weakness.getNumConsecutiveNums()));

        tv_sequentialnumbers.setText(String.valueOf(this.weakness.getNumSequentialNums()));

        tv_sequentialletters.setText(String.valueOf(this.weakness.getNumSequentialLetters()));

    }
}
