package com.example.amit.dhareshwar_maintenance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by amit on 9/12/16.
 */

public class RVAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notices,parent,false);
        NoticeViewHolder nvh = new NoticeViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 24;
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView hello, hello1;
        public NoticeViewHolder(View itemView) {
            super(itemView);
            hello = (TextView) itemView.findViewById(R.id.card);
            hello1 = (TextView) itemView.findViewById(R.id.card1);
        }
    }
}
