package com.telephone.zhangxin1761200226;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity2ViewPagerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager2_fragment, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TabLayout tabLayout2 = view.findViewById(R.id.tab_layout2);
        ViewPager2 vp2 = view.findViewById(R.id.vp2);

        vp2.setOffscreenPageLimit(4);

        vp2.setAdapter(new MainActivity2ViewPagerAdapter(this));

        new TabLayoutMediator(tabLayout2, vp2,
                (tab, position) -> {
                    String[] tab_texts = new String[]{
                            "新闻",
                            "视频"
                    };
                    int[] tab_icon_ids = new int[]{
                        R.drawable.news,
                        R.drawable.video
                    };
                    tab.setCustomView(null);
                    tab.setCustomView(new TabIcon(getLayoutInflater(), tab_icon_ids[position], tab_texts[position], false, null).getView());
                }
        ).attach();

        tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String[] tab_texts = new String[]{
                        "新闻",
                        "视频"
                };
                int[] tab_icon_ids = new int[]{
                        R.drawable.news_select,
                        R.drawable.video_select
                };
                int position = tab.getPosition();
                tab.setCustomView(null);
                tab.setCustomView(new TabIcon(getLayoutInflater(), tab_icon_ids[position], tab_texts[position], true, null).getView());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                String[] tab_texts = new String[]{
                        "新闻",
                        "视频"
                };
                int[] tab_icon_ids = new int[]{
                        R.drawable.news,
                        R.drawable.video
                };
                int position = tab.getPosition();
                tab.setCustomView(null);
                tab.setCustomView(new TabIcon(getLayoutInflater(), tab_icon_ids[position], tab_texts[position], false, null).getView());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp2.setCurrentItem(1);
        vp2.setCurrentItem(0);
    }
}
