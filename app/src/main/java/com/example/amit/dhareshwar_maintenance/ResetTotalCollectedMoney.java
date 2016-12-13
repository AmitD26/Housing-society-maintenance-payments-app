package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by amit on 12/12/16.
 */

/**
 * Created by amit on 12/12/16.
 */

public class ResetTotalCollectedMoney extends AsyncTask<String,String,String> {
    private Context context;

    public ResetTotalCollectedMoney() {
        super();
    }

    public ResetTotalCollectedMoney(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(context.getResources().getString(R.string.reset_total_collected_money_PHP));
            URLConnection conn = url.openConnection();
            conn.getInputStream();

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;    }




}
