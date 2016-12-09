package com.example.amit.dhareshwar_maintenance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amit on 10/12/16.
 */

public class NoticeRecyclerViewAdapter extends RecyclerView.Adapter {

    JSONObject noticesJSON;

    public NoticeRecyclerViewAdapter(JSONObject noticesJSON) {
        this.noticesJSON = noticesJSON;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notice,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Integer noticeID_integer = noticesJSON.length() - position;
        String noticeID = noticeID_integer.toString();
        NoticeViewHolder noticeViewHolder = (NoticeViewHolder) holder;
        noticeViewHolder.noticeID.setText(noticeID);
        try {
            noticeViewHolder.noticeDate.setText(noticesJSON.getString("notice_date"));
            noticeViewHolder.noticeSubject.setText(noticesJSON.getString("notice_subject"));
            noticeViewHolder.noticeSender.setText(noticesJSON.getString("notice_sender"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return noticesJSON.length();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView noticeID, noticeDate, noticeSubject, noticeSender;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            noticeID = (TextView) itemView.findViewById(R.id.noticeID_textView);
            noticeDate = (TextView) itemView.findViewById(R.id.notice_date_textView);
            noticeSubject = (TextView) itemView.findViewById(R.id.notice_subject_textView);
            noticeSender = (TextView) itemView.findViewById(R.id.notice_sender_textView);
        }
    }
}
