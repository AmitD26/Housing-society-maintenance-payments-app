package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by amit on 15/12/16.
 */

public class SendNoticeToServer extends AsyncTask<String, String, JSONObject> {
    Context context;

    public SendNoticeToServer(Context context) {
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String username = strings[0];
        String notice_subject = strings[1];
        String notice_main_body = strings[2];
        String notice_date = strings[3];

        try {
            URL url = new URL(context.getString(R.string.send_notice_PHP));
            URLConnection urlConnection = url.openConnection();

            urlConnection.setDoOutput(true);
            String data = URLEncoder.encode("sender_flat_no", "UTF-8") + "=" + URLEncoder.encode(username.substring(6), "UTF-8") + "&" + URLEncoder.encode("notice_subject", "UTF-8") + "=" + URLEncoder.encode(notice_subject, "UTF-8") + "&" + URLEncoder.encode("notice_main_body", "UTF-8") + "=" + URLEncoder.encode(notice_main_body, "UTF-8") + "&" + URLEncoder.encode("notice_date", "UTF-8") + "=" + URLEncoder.encode(notice_date, "UTF-8");
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
