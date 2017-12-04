package com.blogspot.luongcongdu.appdoctintuc.V;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.blogspot.luongcongdu.appdoctintuc.R;

public class DetailsArticleSavedActivity extends AppCompatActivity {

    Context context;
    Intent iArticleSaved;
    String content;
    WebView wbArticleSaved;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article_saved);


        //lấy link trang new News bên MainActivity
        iArticleSaved = getIntent();
        content = iArticleSaved.getStringExtra("CONTENT_SAVED");


        wbArticleSaved = (WebView) findViewById(R.id.wb_detail_article_saved);
        WebSettings settings = wbArticleSaved.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail_article_saved);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (content != null) {
            //chạy dialog
            wbArticleSaved.loadData(content, "text/html; charset=utf-8",null);
        } else {
            Toast.makeText(this, "Page 404 not found", Toast.LENGTH_SHORT).show();
        }
    }
}
