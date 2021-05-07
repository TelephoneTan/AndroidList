package com.telephone.zhangxin1761200226;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    public static class NewsData {
        int image_id;
        String title;
        String author;

        public NewsData(int image_id, String title, String author) {
            this.image_id = image_id;
            this.title = title;
            this.author = author;
        }
    }

    private List<NewsData> data;

    public NewsAdapter(@NonNull List<NewsData> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.news_card, parent, false);
        }
        NewsData newsData = (NewsData) getItem(position);
        ((ImageView)convertView.findViewById(R.id.news_image)).setImageResource(newsData.image_id);
        ((TextView)convertView.findViewById(R.id.news_title)).setText(newsData.title);
        ((TextView)convertView.findViewById(R.id.news_author)).setText(newsData.author);
        return convertView;
    }
}
