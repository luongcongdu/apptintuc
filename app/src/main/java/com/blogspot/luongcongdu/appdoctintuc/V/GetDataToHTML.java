package com.blogspot.luongcongdu.appdoctintuc.V;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.blogspot.luongcongdu.appdoctintuc.M.Article;
import com.blogspot.luongcongdu.appdoctintuc.M.ArticleSaved;

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
    String title;

    public GetDataToHTML(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        //get title
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        title = prefs.getString("title_article", "No title"); //no title: default value

        String link = params[0];

        URL url;
        String content = "";
        try {
            url = new URL(link);

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            while (line != null) {
                content += line;
                line = bufferedReader.readLine();
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RESULT", content);
        return content;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        Log.d("RESULT", aVoid);

        //put data to realm
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Number currentIdNum = realm.where(ArticleSaved.class).max("id");
        int nextId;
        if (currentIdNum == null) {
            nextId = 1;
            Log.d("ID", String.valueOf(nextId));
            Log.d("ID Current", String.valueOf(currentIdNum));
        } else {
            nextId = currentIdNum.intValue() + 1;
            Log.d("ID", String.valueOf(nextId));
        }


        ArticleSaved article = new ArticleSaved();
        article.setId(nextId);
        article.setContent(aVoid);
        article.setTitle(title);

        realm.copyToRealmOrUpdate(article);
        realm.commitTransaction();

        super.onPostExecute(aVoid);
    }
}
