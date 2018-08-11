package com.example.piyush.passwordhackingsystem.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.piyush.passwordhackingsystem.fragments.StrengthFragment;
import com.example.piyush.passwordhackingsystem.fragments.WeaknessFragment;
import com.example.piyush.passwordhackingsystem.models.Strength;
import com.example.piyush.passwordhackingsystem.models.Weakness;

/**
 * Created by abhishekyadav on 05/01/17.
 */

public class StrengthActivityAdapter extends FragmentStatePagerAdapter {

    Strength strength;
    Weakness weakness;
    public static final String TAG = "Adapter";

    public StrengthActivityAdapter(FragmentManager fm, Strength strength, Weakness weakness) {
        super(fm);
        this.strength = strength;
        this.weakness = weakness;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return StrengthFragment.newInstance(strength);
        } else {
            WeaknessFragment w = WeaknessFragment.newInstance(weakness);
            Log.d(TAG, "getItem: " + w);
            return w;
        }


    }

    @Override
    public CharSequence getPageTitle(int position) {
     if (position == 0) {
            return "Strengths";
        } else {
            return "Weaknesses";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
