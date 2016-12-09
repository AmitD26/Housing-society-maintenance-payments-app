package com.example.amit.dhareshwar_maintenance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amit on 9/12/16.
 */

public class RVAdapter extends RecyclerView.Adapter {

    private JSONObject j;

    public RVAdapter(JSONObject j) {
        this.j = j;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notices,parent,false);
        NoticeViewHolder nvh = new NoticeViewHolder(v);
//        nvh.rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(parent.getContext(), "Hello", Toast.LENGTH_SHORT).show();
//            }
//        });

        
        return nvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            position = j.length() - position;
            String id = ((Integer)position).toString();
            JSONObject jj = j.getJSONObject(id);
            NoticeViewHolder nvh = (NoticeViewHolder) holder;
            nvh.hello.setText(jj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return j.length();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView hello, hello1;
        RelativeLayout rl;
        public NoticeViewHolder(View itemView) {
            super(itemView);
            hello = (TextView) itemView.findViewById(R.id.card);
            hello1 = (TextView) itemView.findViewById(R.id.card1);
            rl = (RelativeLayout) itemView.findViewById(R.id.noticeRL);
        }
    }
}
