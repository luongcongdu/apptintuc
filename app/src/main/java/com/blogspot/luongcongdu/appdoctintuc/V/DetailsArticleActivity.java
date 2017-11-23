package com.blogspot.luongcongdu.appdoctintuc.V;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.blogspot.luongcongdu.appdoctintuc.R;

public class DetailsArticleActivity extends AppCompatActivity {

    Intent iArticle;
    String linkDetailArticle;
    WebView wbArticle;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        //lấy link trang new News bên MainActivity
        iArticle = getIntent();
        linkDetailArticle = iArticle.getStringExtra("LINK_ARTICLE");
        Log.d("LINK_ARTICLE", linkDetailArticle);

        wbArticle = (WebView) findViewById(R.id.wb_detail_article);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail_article);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (linkDetailArticle != null) {
            //chạy dialog
            dialog = new ProgressDialog(this);
            dialog.setMessage("Đang tải...");
            dialog.setCancelable(false);
            dialog.show();

            wbArticle.setWebViewClient(onWebViewLoaded);
            wbArticle.loadUrl(linkDetailArticle);
        } else {
            Toast.makeText(this, "Page 404 not found", Toast.LENGTH_SHORT).show();
        }
    }

    private WebViewClient onWebViewLoaded = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            dialog.dismiss();
            Toast.makeText(DetailsArticleActivity.this, "Sorry! Some error was detected!", Toast.LENGTH_SHORT).show();
        }


    };
}
