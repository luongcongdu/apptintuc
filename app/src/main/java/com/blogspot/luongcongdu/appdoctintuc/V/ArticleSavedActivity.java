package com.blogspot.luongcongdu.appdoctintuc.V;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.blogspot.luongcongdu.appdoctintuc.C.AdapterArticleSaved;
import com.blogspot.luongcongdu.appdoctintuc.M.ArticleSaved;
import com.blogspot.luongcongdu.appdoctintuc.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ArticleSavedActivity extends AppCompatActivity {
    private ListView lvArticleSaved;
    Realm realm;
    ArrayList<ArticleSaved> listArticleSaved;
    private AdapterArticleSaved adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_saved);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article_saved);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Back");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lvArticleSaved = (ListView) findViewById(R.id.lv_article_saved);
        realm = Realm.getDefaultInstance();
        listArticleSaved = new ArrayList<>();

        RealmResults<ArticleSaved> articleSaveds = realm.where(ArticleSaved.class).findAll();
        for (ArticleSaved articleSaved : articleSaveds) {
            Log.d("REALM", String.valueOf(articleSaved.getTitle()));
            ArticleSaved saved = new ArticleSaved();
            saved.setId(articleSaved.getId());
            saved.setContent(articleSaved.getContent());
            saved.setTitle(articleSaved.getTitle());

            listArticleSaved.add(saved);
        }

        adapter = new AdapterArticleSaved(ArticleSavedActivity.this, R.layout.item_article_saved, listArticleSaved);
        lvArticleSaved.setAdapter(adapter);

        lvArticleSaved.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ArticleSavedActivity.this,
                        DetailsArticleSavedActivity.class).putExtra("CONTENT_SAVED", listArticleSaved.get(position).getContent());
                startActivity(intent);
            }
        });
    }
}
