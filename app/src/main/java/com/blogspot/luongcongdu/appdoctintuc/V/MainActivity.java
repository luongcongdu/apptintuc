package com.blogspot.luongcongdu.appdoctintuc.V;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.blogspot.luongcongdu.appdoctintuc.C.AdapterTopic;
import com.blogspot.luongcongdu.appdoctintuc.M.NewNews;
import com.blogspot.luongcongdu.appdoctintuc.M.Topic;
import com.blogspot.luongcongdu.appdoctintuc.R;
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
    Button btnReload;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        isOnline();
        if (isOnline()) {
            Log.d("ONLINE", "ONLINE");
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
                    Intent iTopic = new Intent(MainActivity.this,
                            TopicActivity.class).putExtra("LINK_TOPIC", listTopic.get(position).getLink());

                    iTopic.putExtra("TITLE", listTopic.get(position).getName());
                    startActivity(iTopic);
                }
            });
        } else {
            //nếu không có mạng, hiển thị màn hình thông báo và thông báo
            setContentView(R.layout.activity_error_internet);
            btnReload = (Button) findViewById(R.id.btn_reload);
            btnReload.setOnClickListener(onReloadClick);
            Toast.makeText(this, "Ứng dụng cần truy cập mạng. Hãy kiểm tra kết nối mạng của thiết bị!", Toast.LENGTH_LONG).show();
        }

    }

    private View.OnClickListener onReloadClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isOnline();

            if (isOnline()) {
                //nếu có mạng, set layout mặc định
                setContentView(R.layout.activity_main);

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                //chạy dialog
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Đang tải...");
                dialog.setCancelable(false);
                dialog.show();

                //lấy tin mới
                new DownloadTask().execute(MY_URL);


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
                toggle.syncState();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(MainActivity.this);

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
                        Intent iTopic = new Intent(MainActivity.this,
                                TopicActivity.class).putExtra("LINK_TOPIC", listTopic.get(position).getLink());
                        startActivity(iTopic);
                    }
                });
            } else {
                //nếu không có mạng, hiển thị màn hình thông báo và thông báo
                setContentView(R.layout.activity_error_internet);
                btnReload = (Button) findViewById(R.id.btn_reload);
                btnReload.setOnClickListener(onReloadClick);
                Toast.makeText(MainActivity.this, "Ứng dụng cần truy cập mạng. Hãy kiểm tra kết nối mạng của thiết bị!", Toast.LENGTH_LONG).show();
            }
        }
    };

    private void addDataToArray() {
        listTopic.add(new Topic("1", "Thời sự", R.drawable.news, "https://vnexpress.net/rss/thoi-su.rss"));
        listTopic.add(new Topic("2", "Thế giới", R.drawable.world, "https://vnexpress.net/rss/the-gioi.rss"));
        listTopic.add(new Topic("3", "Kinh doanh", R.drawable.business, "https://vnexpress.net/rss/kinh-doanh.rss"));
        listTopic.add(new Topic("4", "Giải trí", R.drawable.entertement, "https://vnexpress.net/rss/giai-tri.rss"));
        listTopic.add(new Topic("5", "Thể thao", R.drawable.sport, "https://vnexpress.net/rss/the-thao.rss"));
        listTopic.add(new Topic("6", "Pháp luật", R.drawable.raw, "https://vnexpress.net/rss/phap-luat.rss"));
        listTopic.add(new Topic("7", "Giáo dục", R.drawable.education, "https://vnexpress.net/rss/giao-duc.rss"));
        listTopic.add(new Topic("8", "Sức khỏe", R.drawable.health, "https://vnexpress.net/rss/suc-khoe.rss"));
        listTopic.add(new Topic("9", "Khoa học", R.drawable.creative, "https://vnexpress.net/rss/khoa-hoc.rss"));
        listTopic.add(new Topic("10", "Startup", R.drawable.startup, "https://vnexpress.net/rss/startup.rss"));
        listTopic.add(new Topic("11", "Du lịch", R.drawable.travel, "https://vnexpress.net/rss/du-lich.rss"));
        listTopic.add(new Topic("12", "Số hóa", R.drawable.tech, "https://vnexpress.net/rss/so-hoa.rss"));
        listTopic.add(new Topic("13", "Xe", R.drawable.vehical, "https://vnexpress.net/rss/oto-xe-may.rss\n"));
        listTopic.add(new Topic("14", "Cộng đồng", R.drawable.community, "https://vnexpress.net/rss/cong-dong.rss"));
        listTopic.add(new Topic("15", "Cười", R.drawable.funny, "https://vnexpress.net/rss/cuoi.rss"));
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
                    Log.d("DESCRIP", description);
                    String img = "";

                    if (description.equals("No Description")) {
                        Log.d("CONTENT", "Unavailable");
                        continue;
                    } else {
                        Document docImage = Jsoup.parse(description);
                        img = docImage.select("img").get(0).attr("src");
                        Log.d("IMG", img);
                    }


                    if (link.equals("") || title.equals("") || description.equals("") || img.equals("")) {
                        Log.d("CONTENT", "Unavailable");
                    } else {
                        listNews.add(new NewNews(title, img, link));
                    }
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
                            DetailsNewNewsActivity.class).putExtra("LINK_NEW_NEWS", newNews.getLink());
                    startActivity(iNewNews);
                }
            });
        }
    }

    //phương thức kiểm tra mạng
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

//    @Override
//    public void onBackPressed() {
//
//        if (isOnline() == false) {
//            super.onBackPressed();
//        } else if (isOnline() == true) {
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START);
//            } else {
//                super.onBackPressed();
//            }
//        }
//
//    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ấn một lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            this.setTheme(R.style.AppThemeDark);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_share) {
//            // Handle the camera action
//            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
//        }
        if (id == R.id.nav_contact) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            String[] emailTo = new String[]{"congdu.it@gmail.com"};
            String subject = ("Phản hồi từ người sử dụng H2D News");
            intent.putExtra(Intent.EXTRA_EMAIL, emailTo);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, "Nhập nội dung...");


            startActivity(Intent.createChooser(intent, "Send Email"));
        } else if (id == R.id.nav_info) {
            Intent iInfo = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(iInfo);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
