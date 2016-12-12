package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.os.AsyncTask;

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

public class GetTotalCollectedMoneyFromServer extends AsyncTask<String,String,JSONObject> {
    private Context context;

    public GetTotalCollectedMoneyFromServer() {
        super();
    }

    public GetTotalCollectedMoneyFromServer(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(context.getResources().getString(R.string.total_collected_money_PHP));
            URLConnection conn = url.openConnection();

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
