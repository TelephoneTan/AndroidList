package com.telephone.zhangxin1761200226;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class VideoAdapter extends BaseAdapter {
    public static class VideoData {
        int image_id;
        String title;
        String times;
        String length;

        public VideoData(int image_id, String title, String times, String length) {
            this.image_id = image_id;
            this.title = title;
            this.times = times;
            this.length = length;
        }
    }

    private List<VideoData> data;

    public VideoAdapter(@NonNull List<VideoData> data){
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
            convertView = inflater.inflate(R.layout.video_card, parent, false);
        }
        VideoData videoData = (VideoData) getItem(position);
        ((ConstraintLayout)convertView.findViewById(R.id.video_background)).setBackgroundResource(videoData.image_id);
        ((TextView)convertView.findViewById(R.id.video_title)).setText(videoData.title);
        ((TextView)convertView.findViewById(R.id.video_play_times)).setText(videoData.times);
        ((TextView)convertView.findViewById(R.id.video_length)).setText(videoData.length);
        return convertView;
    }
}
