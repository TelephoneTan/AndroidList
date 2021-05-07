package com.telephone.zhangxin1761200226;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainActivity2ViewPagerAdapter extends FragmentStateAdapter {
    public MainActivity2ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new Page1Fragment(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
