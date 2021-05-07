package com.telephone.zhangxin1761200226.Http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Request {

    public static class CharsetNames{
        public static String UTF8 = "UTF-8";
        public static String GBK = "GBK";
    }

    public static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36";

    public static final String lan_head = "http://172.16.13.22";
    public static final String wan_head = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe";

    public static final String lan_lib_head = "http://202.193.70.139";
    public static final String wan_lib_head = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a2a713d276693b1e2958c7fdcb0c";

    public static final String lan_cxcy_head = "http://172.16.64.212";
    public static final String wan_cxcy_head = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626062a46dbfdca";

    public static final String lan_lib_position_head = "http://202.193.70.139";
    public static final String wan_lib_position_head = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a2a713d276693b1e2958c7fdcb02";

    private static String lwan(@NonNull String url, boolean isLan){
        return isLan?
                url.replaceFirst(wan_head, lan_head).replaceFirst(wan_lib_head, lan_lib_head).replaceFirst(wan_lib_position_head, lan_lib_position_head).replaceFirst(wan_cxcy_head, lan_cxcy_head):
                url.replaceFirst(lan_head, wan_head).replaceFirst(lan_lib_head, wan_lib_head).replaceFirst(lan_lib_position_head, wan_lib_position_head).replaceFirst(lan_cxcy_head, wan_cxcy_head);
    }

    public static String get_lwan(@NonNull String url, boolean isLan){
        return lwan(url, isLan);
    }



    private final boolean isLAN;

    public Request(boolean isLANOrIsHttp) {
        this.isLAN = isLANOrIsHttp;
    }

    public HttpConnectionAndCode get(
            @NonNull String u,
            @Nullable final String[] parms,
            @NonNull final String user_agent,
            @NonNull String referer,
            @Nullable final String cookie,
            @Nullable final String cookie_delimiter,
            @Nullable final String success_resp_text,
            @Nullable final String[] accept_encodings,
            @Nullable final Boolean redirect,
            @Nullable String read_charset_name,
            @Nullable Boolean quick_test
    ){
        u = lwan(u, isLAN);
        referer = lwan(referer, isLAN);
        return isLAN?
                Get.get(
                        u, parms, user_agent, referer, cookie, cookie_delimiter, success_resp_text, accept_encodings, redirect, read_charset_name, quick_test
                ):
                Get_https.get(
                        u, parms, user_agent, referer, cookie, cookie_delimiter, success_resp_text, accept_encodings, redirect, read_charset_name, quick_test
                );
    }

    public HttpConnectionAndCode post(
            @NonNull String u,
            @Nullable final String[] parms,
            @NonNull final String user_agent,
            @NonNull String referer,
            @Nullable final String data,
            @Nullable final String cookie,
            @Nullable final String cookie_delimiter,
            @Nullable final String success_resp_text,
            @Nullable final String[] accept_encodings,
            @Nullable final String content_type,
            @Nullable final Boolean redirect,
            @Nullable String write_charset_name,
            @Nullable String read_charset_name,
            @Nullable Boolean quick_test
    ){
        u = lwan(u, isLAN);
        referer = lwan(referer, isLAN);
        return isLAN?
                Post.post(
                        u, parms, user_agent, referer, data, cookie, cookie_delimiter, success_resp_text, accept_encodings, content_type, redirect, read_charset_name, write_charset_name, quick_test
                ):
                Post_https.post(
                        u, parms, user_agent, referer, data, cookie, cookie_delimiter, success_resp_text, accept_encodings, content_type, redirect, read_charset_name, write_charset_name, quick_test
                );
    }

    public HttpConnectionAndCode bitmap(
            @NonNull String u,
            @Nullable final String[] parms,
            @NonNull final String user_agent,
            @NonNull String referer,
            @Nullable final String cookie,
            @Nullable final String cookie_delimiter,
            @Nullable Boolean quick_test
    ){
        u = lwan(u, isLAN);
        referer = lwan(referer, isLAN);
        return isLAN?
                GetBitmap.get(
                        u, parms, user_agent, referer, cookie, cookie_delimiter, quick_test
                ):
                GetBitmap_https.get(
                        u, parms, user_agent, referer, cookie, cookie_delimiter, quick_test
                );
    }
}
