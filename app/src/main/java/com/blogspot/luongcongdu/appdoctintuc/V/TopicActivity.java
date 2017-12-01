package com.blogspot.luongcongdu.appdoctintuc.V;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.luongcongdu.appdoctintuc.C.AdapterArticle;
import com.blogspot.luongcongdu.appdoctintuc.M.Article;
import com.blogspot.luongcongdu.appdoctintuc.M.NewNews;
import com.blogspot.luongcongdu.appdoctintuc.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {

    Intent iTopic;
    String linkTopic;
    ProgressDialog dialog;
    ListView lvTopic;
    AdapterArticle adapterArticle;
    ArrayList<Article> listArticle;
    String titleTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_pic);

        lvTopic = (ListView) findViewById(R.id.lv_topic);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_topic);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //chạy dialog
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang tải...");
        dialog.setCancelable(false);
        dialog.show();

        iTopic = getIntent();
        linkTopic = iTopic.getStringExtra("LINK_TOPIC");
        titleTopic = iTopic.getStringExtra("TITLE");
        getSupportActionBar().setTitle(titleTopic);
        Log.d("LINK TOPIC", linkTopic);

        new DownloadTask().execute(linkTopic);

        lvTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent iArticle = new Intent(TopicActivity.this,
                        DetailsArticleActivity.class).putExtra("LINK_ARTICLE", listArticle.get(position).getLink());
                Log.d("ARTICLE", listArticle.get(position).getLink());
                startActivity(iArticle);

            }
        });

        lvTopic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TopicActivity.this, "Long", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    //class thực hiện lấy dữ liệu
    private class DownloadTask extends AsyncTask<String, Void, ArrayList<Article>> {


        @Override
        protected ArrayList<Article> doInBackground(String... params) {

            String url = params[0];
            listArticle = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("item");
                for (Element item : elements) {
                    String title = item.select("title").text();
                    String link = item.select("link").text();
                    String description = item.select("description").text();
                    Log.d("DESCRIP", description);
                    String img = "";

                    if (title.equals("")) {
                        continue;
                    }
                    if (link.equals("")) {
                        continue;
                    }
                    if (description.equals("No Description")) {
                        Log.d("CONTENT", "Unavailable");
                        continue;
                    } else {
                        if (description.indexOf("img") != 0) {
                            if (description.contains("src") == true) {
                                Document docImage = Jsoup.parse(description);
                                img = docImage.select("img").get(0).attr("src");
                                if (img.equals("")) {
                                    continue;
                                }
                            }
                        } else {
                            continue;
                        }


                        Log.d("IMG", img);
                    }


                    if (link.equals("") || title.equals("") || description.equals("") || img.equals("")) {
                        Log.d("CONTENT", "Unavailable");
                    } else {
                        listArticle.add(new Article(title, img, link));
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return listArticle;
        }

        @Override
        protected void onPostExecute(final ArrayList<Article> listArticle) {
            super.onPostExecute(listArticle);
            //tắt dialog
            dialog.dismiss();

            adapterArticle = new AdapterArticle(TopicActivity.this, R.layout.item_article_layout, listArticle);
            lvTopic.setAdapter(adapterArticle);
        }
    }

}


