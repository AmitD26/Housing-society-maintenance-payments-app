package com.example.amit.dhareshwar_maintenance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class NoticeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);

        TextView notice_subject = (TextView) findViewById(R.id.notice_details_subject);
        TextView notice_main_body = (TextView) findViewById(R.id.notice_details_main_body);
        Button notice_back_button = (Button) findViewById(R.id.notice_details_back_button);

        String notice_id = getIntent().getStringExtra("notice_id");
        Log.d("df",notice_id);
        try {
            JSONObject notice_details = new GetNoticeDetails(this, notice_id).execute().get();
            notice_subject.setText(notice_details.getString("notice_subject"));
            notice_main_body.setText(notice_details.getString("main_body"));
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        notice_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
