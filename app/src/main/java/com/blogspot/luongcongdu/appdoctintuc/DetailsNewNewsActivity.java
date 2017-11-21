package com.blogspot.luongcongdu.appdoctintuc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DetailsNewNewsActivity extends AppCompatActivity {

    Intent iNewNews;
    String linkNewNews;
    WebView wbNewNews;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_new_news);

        //lấy link trang new News bên MainActivity
        iNewNews = getIntent();
        linkNewNews = iNewNews.getStringExtra("LINK_NEW_NEWS");
        Log.d("LINK_NEWS", linkNewNews);

        wbNewNews = (WebView) findViewById(R.id.wb_new_news);

        if (linkNewNews != null) {
            //chạy dialog
            dialog = new ProgressDialog(this);
            dialog.setMessage("Đang tải...");
            dialog.setCancelable(false);
            dialog.show();

            wbNewNews.setWebViewClient(onWebViewLoaded);
            wbNewNews.loadUrl(linkNewNews);
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
            Toast.makeText(DetailsNewNewsActivity.this, "Sorry! Some error was detected!", Toast.LENGTH_SHORT).show();
        }


    };
}
