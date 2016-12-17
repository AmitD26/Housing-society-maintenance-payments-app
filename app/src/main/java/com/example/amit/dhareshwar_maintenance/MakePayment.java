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

public class MakePayment extends AsyncTask<String, String, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... strings) {
        String username = strings[0];
        String link = strings[1];
        String payment_method = strings[2];
        String payment_type = strings[3];
        String amount = strings[4];
        String date_of_payment = strings[5];

        try {
            URLConnection conn = new URL(link).openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            String data = URLEncoder.encode("flat_no", "UTF-8") + "=" + URLEncoder.encode(username.substring(6), "UTF-8") + "&" + URLEncoder.encode("payment_method", "UTF-8") + "=" + URLEncoder.encode(payment_method, "UTF-8") + "&" + URLEncoder.encode("payment_type", "UTF-8") + "=" + URLEncoder.encode(payment_type, "UTF-8") + "&" + URLEncoder.encode("date_of_payment", "UTF-8") + "=" + URLEncoder.encode(date_of_payment, "UTF-8") + "&" + URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(amount, "UTF-8");
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
