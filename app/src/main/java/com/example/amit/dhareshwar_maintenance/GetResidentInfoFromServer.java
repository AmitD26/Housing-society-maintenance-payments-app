package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by amit on 11/12/16.
 */

public class GetResidentInfoFromServer extends AsyncTask<String,String,JSONObject> {
    Context context;

    public GetResidentInfoFromServer(Context context) {
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String link = context.getString(R.string.resident_info_PHP);
        String username = context.getSharedPreferences(MainActivity.LOGIN_INFO_SHARED_PREFS,Context.MODE_PRIVATE).getString("username",null);
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(username);
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
