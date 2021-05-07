package com.telephone.zhangxin1761200226;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Page1Fragment extends Fragment {

    public static final int TYPE_NEWS = 0;
    public static final int TYPE_VIDEO = 1;

    private int type;

    public Page1Fragment(int type){
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page1, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView listView = view.findViewById(R.id.page1_list_view);
        switch (type){
            case TYPE_NEWS:
                new Thread(()->{
                    List<NewsAdapter.NewsData> newsDataList = fetchData();
                    view.post(()->{
                        listView.setAdapter(new NewsAdapter(newsDataList));
                    });
                }).start();
                break;
            case TYPE_VIDEO:
                new Thread(()->{
                    List<VideoAdapter.VideoData> videoDataList = fetchData();
                    view.post(()->{
                        listView.setAdapter(new VideoAdapter(videoDataList));
                    });
                }).start();
                break;
            default:
                break;
        }
    }

    private List fetchData(){
        List res = new LinkedList();
        switch (type){
            case TYPE_NEWS:
                List<news_json> news_jsonList = Arrays.asList(new Gson().fromJson(getString(R.string.news_json_string), news_json[].class));
                ImagePicker picker = new ImagePicker(new int[]{
                        R.drawable.news_image1,
                        R.drawable.news_image2,
                        R.drawable.news_image3,
                        R.drawable.news_image4,
                        R.drawable.news_image5,
                        R.drawable.news_image6,
                        R.drawable.news_image7,
                        R.drawable.news_image8,
                        R.drawable.news_image9,
                        R.drawable.news_image10
                });
                for (news_json nj : news_jsonList){
                    res.add(new NewsAdapter.NewsData(picker.next(), nj.getTitle(), nj.getAuthor() + " " + nj.getCommentNumber()));
                }
                return res;
            case TYPE_VIDEO:
                List<video_json> video_jsonList = Arrays.asList(new Gson().fromJson(getString(R.string.video_json_string), video_json[].class));
                picker = new ImagePicker(new int[]{
                        R.drawable.video1
                });
                for (video_json vj : video_jsonList){
                    res.add(new VideoAdapter.VideoData(picker.next(), vj.getTitle(), vj.getPlayTimes(), vj.getTotalTime()));
                }
                return res;
            default:
                return res;
        }
    }
}
