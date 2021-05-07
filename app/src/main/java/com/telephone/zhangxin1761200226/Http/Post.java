package com.telephone.zhangxin1761200226.Http;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;


public class Post {
    /**
     * @return - 0 GET success
     * - -1 cannot open url
     * - -2 cannot close input stream
     * - -3 can not get output stream
     * - -4 POST send body fail
     * - -5 cannot get response
     * - -6 response check fail
     * - -7 302
     * @non-ui
     * @clear
     */
    static HttpConnectionAndCode post(@NonNull final String u,
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
                                      @Nullable String read_charset_name,
                                      @Nullable String write_charset_name,
                                      @Nullable Boolean quick_test
    ) {
        URL url = null;
        HttpURLConnection cnt = null;
        DataOutputStream dos = null;
        InputStreamReader in = null;
        String response = null;
        if (write_charset_name == null) write_charset_name = Request.CharsetNames.UTF8;
        if (read_charset_name == null) read_charset_name = Request.CharsetNames.UTF8;
        Charset write_charset = StandardCharsets.UTF_8;
        Charset read_charset = StandardCharsets.UTF_8;
        try {
            write_charset = Charset.forName(write_charset_name);
        }catch (Exception ignored){}
        try {
            read_charset = Charset.forName(read_charset_name);
        }catch (Exception ignored){}
        int resp_code = 0;
        try {
            StringBuilder u_bulider = new StringBuilder();
            u_bulider.append(u);
            if (parms != null && parms.length > 0) {
                u_bulider.append("?").append(TextUtils.join("&", parms));
            }
            String url_str = UrlProcess.process(u_bulider.toString());
//            url_str = Proxy.replace(url_str);
//            referer = Proxy.replace(referer);
            url = new URL(url_str);
            cnt = (HttpURLConnection) url.openConnection();
//            cnt = (HttpURLConnection) url.openConnection();
            cnt.setDoOutput(true);
            cnt.setDoInput(true);
            cnt.setRequestProperty("User-Agent", user_agent);
            if (accept_encodings != null && accept_encodings.length > 0) {
                List<String> encodings = Arrays.asList(accept_encodings);
                if (!encodings.contains("gzip")) {
                    encodings.add("gzip");
                }
                cnt.setRequestProperty("Accept-Encoding", TextUtils.join(", ", encodings));
            } else {
                cnt.setRequestProperty("Accept-Encoding", "gzip");
            }
            cnt.setRequestProperty("Referer", UrlProcess.process(referer));
            if (data != null) {
                cnt.setRequestProperty("Content-Length", String.valueOf(data.getBytes(write_charset).length));
            }
            if (content_type != null) {
                cnt.setRequestProperty("Content-Type", content_type);
            }
            if (cookie != null) {
                cnt.setRequestProperty("Cookie", cookie);
            }
            cnt.setRequestMethod("POST");
            if (redirect == null) {
                cnt.setInstanceFollowRedirects(true);
            } else {
                cnt.setInstanceFollowRedirects(redirect);
            }
            if (quick_test == null) quick_test = false;
            cnt.setConnectTimeout(quick_test? 500 :2000);
            cnt.setReadTimeout(quick_test? 500 :20000);
            cnt.connect();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpConnectionAndCode(-1);
        }
        String body = "";
        if (data != null) {
            body += data;
        }
        try {
            dos = new DataOutputStream(cnt.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpConnectionAndCode(-3).setC(cnt);
        }
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(cnt.getOutputStream(), write_charset));
            bw.write(body);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpConnectionAndCode(-4).setC(cnt);
        }
        try {
            resp_code = cnt.getResponseCode();
            if (redirect != null && !redirect && resp_code == 302) {
                return new HttpConnectionAndCode(-7).setC(cnt).setResp_code(resp_code);
            }
            List<String> encodings = cnt.getHeaderFields().get("content-encoding");
            if (encodings != null && encodings.contains("gzip")) {
                in = new InputStreamReader(new GZIPInputStream(cnt.getInputStream()), read_charset);
            } else {
                in = new InputStreamReader(cnt.getInputStream(), read_charset);
            }
            int resp_length = cnt.getHeaderFieldInt("Content-Length", -1);
            StringBuilder response_builder = new StringBuilder();
            char read_char;
            while ( (resp_length < 0 || resp_length-- > 0) && (read_char = (char) in.read()) != (char) -1) {
                response_builder.append(read_char);
            }
            response = response_builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpConnectionAndCode(-5).setC(cnt);
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return new HttpConnectionAndCode(-2).setC(cnt).setResp_code(resp_code).setComment(response);
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
                for (HttpCookie httpCookie : cookieList) {
                    String str = httpCookie.getName() + "=" + httpCookie.getValue();
                    cookieStringList.add(str);
                }
                String cookie_join = TextUtils.join(cookie_delimiter, cookieStringList);
                cookie_builder.append(cookie_join);
            }
            set_cookie = cookie_builder.toString();
        }

        //do not disconnect, keep alive
        if (success_resp_text != null) {
            if (!response.contains(success_resp_text)) {
                //if cookie_delimiter != null but no server cookie, set_cookie = ""
                //if no response, response = ""
                return new HttpConnectionAndCode(-6).setAll(cnt, resp_code, response, Methods.getEmptyStringFromNull(set_cookie), null);
            }
        }

        //do not disconnect, keep alive
        //if cookie_delimiter != null but no server cookie, set_cookie = ""
        //if no response, response = ""
        return new HttpConnectionAndCode(0).setAll(cnt, resp_code, response, Methods.getEmptyStringFromNull(set_cookie), null);
    }
}
