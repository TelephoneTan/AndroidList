package com.telephone.zhangxin1761200226;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TabIcon {
    private final View view;
    public TabIcon(@NonNull LayoutInflater inflater, int image_id, String text, boolean selected, @Nullable ViewGroup parent){
        view = inflater.inflate(R.layout.tab_icon, parent);
        ((ImageView)view.findViewById(R.id.icon_image)).setImageResource(image_id);
        ((TextView)view.findViewById(R.id.icon_text)).setText(text);
        ((TextView)view.findViewById(R.id.icon_text)).setTextColor(selected ? Color.GREEN : Color.BLACK);
    }

    public View getView() {
        return view;
    }
}
