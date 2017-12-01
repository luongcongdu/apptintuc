package com.blogspot.luongcongdu.appdoctintuc.V;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import io.realm.Realm;

/**
 * Created by Admin on 11/29/2017.
 */

public class GetDataToHTML extends AsyncTask<String, Void, String> {
    Context context;
    @Override
    protected String doInBackground(String... params) {
        String link = params[0];

        URL url;
        String content="";
        try {
            url = new URL(link);

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            while(line!=null){
                content += line;
                line = bufferedReader.readLine();
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RESULT",content);
        return content;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        Log.d("RESULT",aVoid);

        //put data to realm
        //Realm myRealm = Realm.getInstance(Realm.getDefaultConfiguration());

        super.onPostExecute(aVoid);
    }
}
