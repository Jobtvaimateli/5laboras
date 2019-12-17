package com.example.a5laboras;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataLoader extends AsyncTask<String, Void, List<String>> {

    List<String> info;
    InputStream inputStream;

    private final AsyncListener mListener;

    public DataLoader(AsyncListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected List<String> doInBackground(String... urls) {
        try {
            inputStream = downloadUrl(urls[0]);
            info = XmlParser.getRateFromECB(inputStream);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream downloadUrl(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            return conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(List<String> list) {
        if (mListener != null) {
            mListener.onCompleted(list);
        }
    }
}

