package com.telephone.zhangxin1761200226.Http;

import androidx.annotation.Nullable;

public class Methods {
    public static String getEmptyStringFromNull(String s){
        return (s == null)?(""):(s);
    }
    public static boolean notEmpty(@Nullable String s){
        return s != null && !s.isEmpty();
    }

    public static boolean isEmpty(@Nullable String s){
        return s == null || s.isEmpty();
    }
}
