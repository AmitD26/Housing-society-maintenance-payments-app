package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by amit on 17/12/16.
 */

public class GetNoticeDetails extends AsyncTask<String,String,JSONObject> {

    private Context context;
    public String notice_id;

    public GetNoticeDetails() {
        super();
    }

    public GetNoticeDetails(Context context, String notice_id) {
        this.context = context;
        this.notice_id = notice_id;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(context.getResources().getString(R.string.notice_main_body_PHP));
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            String data = URLEncoder.encode("notice_id", "UTF-8") + "=" + URLEncoder.encode(notice_id, "UTF-8");
            wr.write(data);
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
