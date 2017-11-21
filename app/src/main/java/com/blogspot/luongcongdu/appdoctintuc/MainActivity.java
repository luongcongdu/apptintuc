package com.blogspot.luongcongdu.appdoctintuc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String MY_URL = "https://vnexpress.net/rss/tin-moi-nhat.rss";
    ViewFlipper flipperNewNews;
    GridView gridTopic;
    ArrayList<Topic> listTopic;
    AdapterTopic adapter;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //chạy dialog
        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang tải...");
        dialog.setCancelable(false);
        dialog.show();

        //lấy tin mới
        new DownloadTask().execute(MY_URL);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        flipperNewNews = (ViewFlipper) findViewById(R.id.flip_new_news);


        gridTopic = (GridView) findViewById(R.id.grid_topic);
        listTopic = new ArrayList<>();

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addDataToArray();
                        adapter = new AdapterTopic(MainActivity.this, R.layout.item_grid_topic, listTopic);
                        gridTopic.setAdapter(adapter);
                    }
                });
            }
        });
        th.start();

        gridTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, listTopic.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addDataToArray() {
        listTopic.add(new Topic("1", "Thời sự", R.drawable.news));
        listTopic.add(new Topic("2", "Thế giới", R.drawable.world));
        listTopic.add(new Topic("3", "Kinh doanh", R.drawable.business));
        listTopic.add(new Topic("4", "Giải trí", R.drawable.entertement));
        listTopic.add(new Topic("5", "Thể thao", R.drawable.sport));
        listTopic.add(new Topic("6", "Pháp luật", R.drawable.raw));
        listTopic.add(new Topic("7", "Giáo dục", R.drawable.education));
        listTopic.add(new Topic("8", "Sức khỏe", R.drawable.health));
        listTopic.add(new Topic("9", "Khoa học", R.drawable.creative));
        listTopic.add(new Topic("10", "Startup", R.drawable.startup));
        listTopic.add(new Topic("11", "Du lịch", R.drawable.travel));
        listTopic.add(new Topic("12", "Số hóa", R.drawable.tech));
        listTopic.add(new Topic("13", "Xe", R.drawable.vehical));
        listTopic.add(new Topic("14", "Cộng đồng", R.drawable.community));
        listTopic.add(new Topic("15", "Cười", R.drawable.funny));
    }

    //class thực hiện lấy dữ liệu
    private class DownloadTask extends AsyncTask<String, Void, ArrayList<NewNews>> {


        @Override
        protected ArrayList<NewNews> doInBackground(String... params) {

            String url = params[0];
            ArrayList<NewNews> listNews = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("item");
                for (Element item : elements) {
                    String title = item.select("title").text();
                    String link = item.select("link").text();
                    String description = item.select("description").text();

                    Document docImage = Jsoup.parse(description);
                    String img = docImage.select("img").get(0).attr("src");
                    Log.d("IMG", img);

                    listNews.add(new NewNews(title, img, link));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return listNews;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewNews> listNews) {
            super.onPostExecute(listNews);
            //tắt dialog
            dialog.dismiss();
            flipperNewNews.removeAllViews();
            for (int i = 0; i < listNews.size(); i++) {
                Log.d("LOG", i + "");
                Log.d("IMG", listNews.get(i).getImg());

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.item_new_news, null);


                ImageView imageView = view.findViewById(R.id.img_new_news);
                Picasso.with(getApplicationContext()).load(listNews.get(i).getImg()).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                TextView textView = view.findViewById(R.id.txt_new_news);
                textView.setText(listNews.get(i).getTitle());
                flipperNewNews.addView(view);
            }


            flipperNewNews.setFlipInterval(5000);
            flipperNewNews.setAutoStart(true);
            flipperNewNews.startFlipping();//
            //hay lam
            //sao o cái hàm sáng nay không có start mà vẫn chạy đc nhỉ
            Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
            Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
            flipperNewNews.setInAnimation(slide_in);
            flipperNewNews.setOutAnimation(slide_out);


            flipperNewNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int focus = flipperNewNews.getDisplayedChild();
                    NewNews newNews = listNews.get(focus);

                    Intent iNewNews = new Intent(MainActivity.this,
                            DetailsNewNewsActivity.class).putExtra("LINK_NEW_NEWS",newNews.getLink());
                    startActivity(iNewNews);
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
