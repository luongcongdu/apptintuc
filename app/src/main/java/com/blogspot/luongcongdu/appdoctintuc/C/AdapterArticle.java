package com.blogspot.luongcongdu.appdoctintuc.C;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.luongcongdu.appdoctintuc.M.Article;
import com.blogspot.luongcongdu.appdoctintuc.M.Topic;
import com.blogspot.luongcongdu.appdoctintuc.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 11/22/2017.
 */

public class AdapterArticle extends BaseAdapter {
    Context context;
    int myLayout;
    List<Article> listArticle;

    public AdapterArticle(Context context, int myLayout, List<Article> listArticle) {
        this.context = context;
        this.myLayout = myLayout;
        this.listArticle = listArticle;
    }

    @Override
    public int getCount() {
        return listArticle.size();
    }

    @Override
    public Object getItem(int position) {
        return listArticle.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);

        //anh xa va gan gia tri
        TextView txtArticle = convertView.findViewById(R.id.txt_article);
        txtArticle.setText(listArticle.get(position).getTitle());


        ImageView imgArticle = convertView.findViewById(R.id.img_article);
        Picasso.with(context).load(listArticle.get(position).getImg()).into(imgArticle);
        imgArticle.setScaleType(ImageView.ScaleType.FIT_XY);

        return convertView;
    }
}
