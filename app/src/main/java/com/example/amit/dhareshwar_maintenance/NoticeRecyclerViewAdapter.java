package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amit on 10/12/16.
 */

public class NoticeRecyclerViewAdapter extends RecyclerView.Adapter {

    JSONObject noticesJSON;
    Context context;

    public NoticeRecyclerViewAdapter(JSONObject noticesJSON) {
        this.noticesJSON = noticesJSON;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new NoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notice,parent,false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Integer noticeID_integer = noticesJSON.length() - position;
        final String noticeID = noticeID_integer.toString();
        NoticeViewHolder noticeViewHolder = (NoticeViewHolder) holder;
        noticeViewHolder.noticeID.setText(noticeID);
        try {
            JSONObject thisNotice = (JSONObject) noticesJSON.get(noticeID);
            noticeViewHolder.noticeDate.setText(thisNotice.getString("notice_date"));
            noticeViewHolder.noticeSubject.setText(thisNotice.getString("notice_subject"));
            noticeViewHolder.noticeSender.setText("Sent by:- \nFlat no: " + thisNotice.getString("sender_flat_no") + "\nStatus: " + thisNotice.getString("sender_privilege_level"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        noticeViewHolder.noticeCardRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,NoticeDetailsActivity.class).putExtra("notice_id",noticeID));
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticesJSON.length();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView noticeID, noticeDate, noticeSubject, noticeSender;
        RelativeLayout noticeCardRelativeLayout;

        public NoticeViewHolder(View itemView) {
            super(itemView);
            noticeID = (TextView) itemView.findViewById(R.id.noticeID_textView);
            noticeDate = (TextView) itemView.findViewById(R.id.notice_date_textView);
            noticeSubject = (TextView) itemView.findViewById(R.id.notice_subject_textView);
            noticeSender = (TextView) itemView.findViewById(R.id.notice_sender_textView);
            noticeCardRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.notice_card_relative_layout);
        }
    }
}
