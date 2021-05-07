package com.telephone.zhangxin1761200226.Http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;


public class GetBitmap_https {
    /**
     * @non-ui
     * @return
     * - 0 GET success
     * - -1 cannot open url
     * - -5 cannot get response
     * - -6 response check fail
     * - -7 302
     * @clear
     */
    static HttpConnectionAndCode get(@NonNull final String u,
                                            @Nullable final String[] parms,
                                            @NonNull final String user_agent,
                                            @NonNull final String referer,
                                            @Nullable final String cookie,
                                            @Nullable final String cookie_delimiter,
                                            @Nullable Boolean quick_test
    ){
        URL url = null;
        HttpsURLConnection cnt = null;
        String response = null;
        Bitmap bmp = null;
        int resp_code = 0;
        try {
            StringBuilder u_bulider = new StringBuilder();
            u_bulider.append(u);
            if (parms != null && parms.length > 0) {
                u_bulider.append("?").append(TextUtils.join("&", parms));
            }
            String url_str = UrlProcess.process(u_bulider.toString());
            url = new URL(url_str);
            cnt = (HttpsURLConnection) url.openConnection();
            cnt.setDoOutput(true);
            cnt.setDoInput(true);
            cnt.setRequestProperty("User-Agent", user_agent);
            cnt.setRequestProperty("Referer", UrlProcess.process(referer));
            if (cookie != null){
                cnt.setRequestProperty("Cookie", cookie);
            }
            cnt.setRequestMethod("GET");
            cnt.setInstanceFollowRedirects(false);
            cnt.setRequestProperty("Connection", "keep-alive");
            if (quick_test == null) quick_test = false;
            cnt.setConnectTimeout(quick_test? 500 :2000);
            cnt.setReadTimeout(quick_test? 500 :20000);
            cnt.connect();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpConnectionAndCode(-1);
        }
        try {
            resp_code = cnt.getResponseCode();
            if (resp_code == 302){
                return new HttpConnectionAndCode(-7).setC(cnt).setResp_code(resp_code);
            }
            response = "";
            List<String> encodings = cnt.getHeaderFields().get("content-encoding");
            if (encodings != null && encodings.contains("gzip")) {
                bmp = BitmapFactory.decodeStream(new GZIPInputStream(cnt.getInputStream()));
            }else {
                bmp = BitmapFactory.decodeStream(cnt.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpConnectionAndCode(-5).setC(cnt);
        }

        //get cookie from server
        String set_cookie = null;
        if (cookie_delimiter != null) {
            CookieManager cookieman = new CookieManager();
            StringBuilder cookie_builder = new StringBuilder();
            //getHeaderFields() returns the header fields of response
            List<String> cookies = cnt.getHeaderFields().get("Set-Cookie");
            if (cookies != null) {
                for (String cookie_resp : cookies) {
                    cookieman.getCookieStore().add(null, HttpCookie.parse(cookie_resp).get(0));
                }
            }
            if (cookieman.getCookieStore().getCookies().size() > 0) {
                List<HttpCookie> cookieList = cookieman.getCookieStore().getCookies();
                List<String> cookieStringList = new LinkedList<>();
                for (HttpCookie httpCookie : cookieList){
                    String str = httpCookie.getName() + "=" + httpCookie.getValue();
                    cookieStringList.add(str);
                }
                String cookie_join = TextUtils.join(cookie_delimiter, cookieStringList);
                cookie_builder.append(cookie_join);
            }
            set_cookie = cookie_builder.toString();
        }

        //do not disconnect, keep alive

        //do not disconnect, keep alive
        //if cookie_delimiter != null but no server cookie, set_cookie = ""
        //if no response, response = ""
        return new HttpConnectionAndCode(0).setAll(cnt, resp_code, response, Methods.getEmptyStringFromNull(set_cookie), bmp);
    }
}
