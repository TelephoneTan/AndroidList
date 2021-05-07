package com.telephone.zhangxin1761200226.Http;

import androidx.annotation.NonNull;


public class UrlProcess {

    public static final String international_lan_head = "http://172.16.64.232";
    public static final String international_wan_head = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626062a46dbffca";

    public static String process(@NonNull String url){
        if (isInternational()) {
            url = url.replaceFirst(Request.lan_head, international_lan_head);
            url = url.replaceFirst(Request.wan_head, international_wan_head);
        }
        return url;
    }

    public static boolean isInternational(){
        return false;
    }

    public static void set_international(boolean isInternational){
    }

    public static class International {
        public static String expandTermId(@NonNull String term_id){
            if (!isInternational()) return term_id;
            String year_text = term_id.substring(0, 4);
            String batch_code = term_id.substring(4, 5);
            int year = Integer.parseInt(year_text);
            return "" + (year - 1) + "-" + year + "_" + batch_code;
        }
    }
}
