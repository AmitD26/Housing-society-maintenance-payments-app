package com.example.amit.dhareshwar_maintenance;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 * Created by amit on 4/12/16.
 */

public class ChangePassword extends AsyncTask<String,String,JSONObject> {

    @Override
    protected JSONObject doInBackground(String... strings) {
        String username = strings[0];
        String password = strings[1];
        String link = strings[2];

        try {
            URLConnection conn = new URL(link).openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            JSONObject jsonData = new JSONObject();
            jsonData.put("username",username);
            jsonData.put("password",password);
            String dataToBeSent = URLEncoder.encode("changed_password","UTF-8") + "=" + URLEncoder.encode(jsonData.toString(),"UTF-8");
            Log.d("pass",dataToBeSent);
            wr.write(dataToBeSent);
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
