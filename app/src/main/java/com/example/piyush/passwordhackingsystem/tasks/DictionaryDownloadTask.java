package com.example.piyush.passwordhackingsystem.tasks;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Piyush on 06-10-2016.
 */

public class DictionaryDownloadTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {

        try {
            URL url = new URL("https://raw.githubusercontent.com/piyush0/PasswordHackingSystem/master/passwords.txt");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                File f = new File(Environment.getExternalStorageDirectory(), "passwords.txt");
                f.createNewFile();
                FileOutputStream fStream = new FileOutputStream(f, true);
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String buffer = br.readLine();


                while (buffer != null) {
                    fStream.write(buffer.getBytes());
                    fStream.write("\n".getBytes());
                    buffer = br.readLine();

                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
