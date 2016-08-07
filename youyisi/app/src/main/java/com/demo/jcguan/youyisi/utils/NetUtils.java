package com.demo.jcguan.youyisi.utils;

import android.os.AsyncTask;

import com.demo.jcguan.youyisi.bean.Constans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jcguan on 2016/8/7.
 */
public class NetUtils {

    //连接网络获取新闻数据
    public static void getNewsJsonData() {

        new AsyncTask<Void, Void, String>() {
            HttpURLConnection conn = null;

            @Override
            protected String doInBackground(Void... strings) {
                String json = "";
                try {
                    conn = (HttpURLConnection) new URL(Constans.mNewsUrl).openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.connect();

                    int responseCode = conn.getResponseCode();

                    //连接正常
                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        StringBuilder builder = new StringBuilder();

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                        for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
                            builder.append(s);
                        }

                        json = builder.toString();
//                        Log.d(TAG, "doInBackground: " + json);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }.execute();
    }




}
