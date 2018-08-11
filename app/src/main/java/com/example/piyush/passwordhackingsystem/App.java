package com.example.piyush.passwordhackingsystem;

import android.app.Application;

import com.example.piyush.passwordhackingsystem.utils.FontsOverride;

/**
 * Created by piyush0 on 06/01/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/" + FontsOverride.FONT_PROXIMA_NOVA);
    }
}
