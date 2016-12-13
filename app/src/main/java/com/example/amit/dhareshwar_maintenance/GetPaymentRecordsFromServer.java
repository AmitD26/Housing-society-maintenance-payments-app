package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
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
 * Created by amit on 10/12/16.
 */

public class GetPaymentRecordsFromServer extends AsyncTask<String,String,JSONArray> {

    Context context;

    public GetPaymentRecordsFromServer(Context context) {
        this.context = context;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        String username = strings[0];
        String link = context.getString(R.string.user_payments_PHP);

        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            wr.write(data);
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return new JSONArray(sb.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
