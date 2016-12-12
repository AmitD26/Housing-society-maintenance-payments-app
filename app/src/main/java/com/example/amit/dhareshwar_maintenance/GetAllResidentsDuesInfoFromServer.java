package com.example.amit.dhareshwar_maintenance;

import android.os.AsyncTask;

/**
 * Created by amit on 12/12/16.
 */

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by amit on 12/12/16.
 */

public class GetAllResidentsDuesInfoFromServer extends AsyncTask<String,String,JSONArray> {
    private Context context;

    public GetAllResidentsDuesInfoFromServer() {
        super();
    }

    public GetAllResidentsDuesInfoFromServer(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        try {
            URL url = new URL(context.getResources().getString(R.string.all_residents_dues_info_PHP));
            URLConnection conn = url.openConnection();

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
