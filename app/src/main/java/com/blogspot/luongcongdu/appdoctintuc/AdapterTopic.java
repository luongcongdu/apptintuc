package com.blogspot.luongcongdu.appdoctintuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 11/20/2017.
 */

public class AdapterTopic extends BaseAdapter {
    Context context;
    int myLayout;
    List<Topic> listTopic;

    public AdapterTopic(Context context, int myLayout, List<Topic> listTopic) {
        this.context = context;
        this.myLayout = myLayout;
        this.listTopic = listTopic;
    }

    @Override
    public int getCount() {

        return listTopic.size();
    }

    @Override
    public Object getItem(int position) {
        return listTopic.get(position);
    }

    @Override
    public long getItemId(int position) {
        Long id = Long.parseLong(listTopic.get(position).getId());
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);

        //anh xa va gan gia tri
        TextView txtTopic = convertView.findViewById(R.id.txt_topic);
        txtTopic.setText(listTopic.get(position).getName());

        ImageView imgTopic = convertView.findViewById(R.id.image_topic);
        imgTopic.setImageResource(listTopic.get(position).getImage());

        return convertView;
    }

}
