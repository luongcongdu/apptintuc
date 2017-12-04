package com.blogspot.luongcongdu.appdoctintuc.C;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.luongcongdu.appdoctintuc.M.Article;
import com.blogspot.luongcongdu.appdoctintuc.M.ArticleSaved;
import com.blogspot.luongcongdu.appdoctintuc.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 12/1/2017.
 */

public class AdapterArticleSaved extends BaseAdapter {
    Context context;
    int myLayout;
    List<ArticleSaved> list;

    public AdapterArticleSaved(Context context, int myLayout, List<ArticleSaved> list) {
        this.context = context;
        this.myLayout = myLayout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
        TextView txtId = convertView.findViewById(R.id.txt_id_article_saved);
        String id = String.valueOf(list.get(position).getId());
        txtId.setText(id+". ");

        TextView txtTitle = convertView.findViewById(R.id.txt_title_article_saved);
        txtTitle.setText(list.get(position).getTitle());

        // TODO: 12/1/2017  

        return convertView;
    }
}
