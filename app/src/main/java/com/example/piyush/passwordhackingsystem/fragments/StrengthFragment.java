package com.example.piyush.passwordhackingsystem.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piyush.passwordhackingsystem.R;
import com.example.piyush.passwordhackingsystem.models.Strength;

/**
 * Created by abhishekyadav on 05/01/17.
 */

public class StrengthFragment extends Fragment {

    Strength strength;
    TextView tv_strengths_numberofcharacters, tv_strengths_uppercaseletters, tv_strengths_lowercaseletters,
            tv_strengths_numbers, tv_strengths_symbols, tv_strengths_midnumorsymbol;

    public static StrengthFragment newInstance(@NonNull Strength strength) {
        Bundle args = new Bundle();
        args.putString("strength", Strength.getJson(strength));
        StrengthFragment strengthFragment = new StrengthFragment();
        strengthFragment.setArguments(args);
        return strengthFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.strength = Strength.getStrength(getArguments().getString("strength"));

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_strength, container, false);

        setTexts(rootView);

        return rootView;

    }

    private void setTexts(ViewGroup rootView) {

        tv_strengths_numberofcharacters = (TextView) rootView.findViewById(R.id.tv_strengths_numberofcharacters);
        tv_strengths_uppercaseletters = (TextView) rootView.findViewById(R.id.tv_strengths_uppercaseletters);
        tv_strengths_lowercaseletters = (TextView) rootView.findViewById(R.id.tv_strengths_lowercaseletters);
        tv_strengths_numbers = (TextView) rootView.findViewById(R.id.tv_strengths_numbers);
        tv_strengths_symbols = (TextView) rootView.findViewById(R.id.tv_strengths_symbols);
        tv_strengths_midnumorsymbol = (TextView) rootView.findViewById(R.id.tv_strengths_midnumorsymbol);

        tv_strengths_numberofcharacters.setText(String.valueOf(this.strength.getNumChars()));

        tv_strengths_uppercaseletters.setText(String.valueOf(this.strength.getNumUpperChars()));

        tv_strengths_lowercaseletters.setText(String.valueOf(this.strength.getNumLowerChars()));

        tv_strengths_numbers.setText(String.valueOf(this.strength.getNumNums()));

        tv_strengths_symbols.setText(String.valueOf(this.strength.getNumSymbols()));

        tv_strengths_midnumorsymbol.setText(String.valueOf(this.strength.getNumMidNumorSymbols()));
    }
}
