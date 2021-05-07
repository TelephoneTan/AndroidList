package com.telephone.zhangxin1761200226;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.telephone.zhangxin1761200226.Http.HttpConnectionAndCode;
import com.telephone.zhangxin1761200226.Http.Request;

public class MainActivity extends AppCompatActivity {

    String[] names = new String[6];
    String[] prices = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(()->{
            HttpConnectionAndCode res = new Request(true).get(
                    "http://81.71.103.3:8080/ssm/api/user/selectCourses",
                    null,
                    "",
                    "",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            meta_json meta_data = new Gson().fromJson(res.comment, meta_json.class);
            for (int i = 0; i < 6; i++){
                names[i] = meta_data.getData().get(i).getName();
                prices[i] = meta_data.getData().get(i).getPrice();
            }
            Log.e("fetch", "end");
            runOnUiThread(()->{
                ((TextView) findViewById(R.id.textView1)).setText(names[0]);
                ((TextView) findViewById(R.id.textView2)).setText(names[1]);
                ((TextView) findViewById(R.id.textView3)).setText(names[2]);
                ((TextView) findViewById(R.id.textView4)).setText(names[3]);
                ((TextView) findViewById(R.id.textView5)).setText(names[4]);
                ((TextView) findViewById(R.id.textView6)).setText(names[5]);

                ((TextView) findViewById(R.id.textView11)).setText(prices[0]);
                ((TextView) findViewById(R.id.textView22)).setText(prices[1]);
                ((TextView) findViewById(R.id.textView33)).setText(prices[2]);
                ((TextView) findViewById(R.id.textView44)).setText(prices[3]);
                ((TextView) findViewById(R.id.textView55)).setText(prices[4]);
                ((TextView) findViewById(R.id.textView66)).setText(prices[5]);
            });
        }).start();
    }
}