package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amit on 11/12/16.
 */

public class RecentPaymentsCardRecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    JSONArray recentPaymentRecords;
    int noOfRecordsToDisplay;





    public RecentPaymentsCardRecyclerViewAdapter(Context context, JSONArray paymentsRecords, int noOfRecordsToDisplay) {
        this.recentPaymentRecords = new JSONArray();
        this.context = context;
        this.noOfRecordsToDisplay = noOfRecordsToDisplay;
        for (int i = paymentsRecords.length()-1; i>=0; i--) {
            try {
                this.recentPaymentRecords.put(paymentsRecords.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecentPaymentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_payment_card_payment,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            RecentPaymentViewHolder recentPaymentViewHolder = (RecentPaymentViewHolder) holder;
            JSONObject payment = recentPaymentRecords.getJSONObject(position);
            recentPaymentViewHolder.recent_payment_amount.setText(String.format(context.getString(R.string.payment_amount),payment.getString("amount_paid")));
            recentPaymentViewHolder.recent_payment_date.setText(payment.getString("date_of_payment"));
            recentPaymentViewHolder.recent_payment_id.setText(String.format(context.getString(R.string.payment_id),payment.getString("payment_id")));
            recentPaymentViewHolder.recent_payment_method.setText(String.format(context.getString(R.string.payment_method),payment.getString("payment_method")));
            recentPaymentViewHolder.recent_payment_type.setText(String.format(context.getString(R.string.payment_type),payment.getString("payment_type")));

            if (payment.getString("confirmed_flag").equals("1")) {
                recentPaymentViewHolder.recent_payment_confirmed_date.setTextColor(Color.GREEN);
                recentPaymentViewHolder.recent_payment_confirmed_date.setText(String.format(context.getString(R.string.payment_confirmation),payment.getString("payment_confirmation_date")));

                recentPaymentViewHolder.recent_payment_view_receipt.setVisibility(TextView.VISIBLE);
                recentPaymentViewHolder.recent_payment_view_receipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
            else {
                recentPaymentViewHolder.recent_payment_confirmed_date.setTextColor(Color.RED);
                recentPaymentViewHolder.recent_payment_confirmed_date.setText(R.string.not_confirmed_yet);
            }

            if(payment.getString("payment_method").equals("Cash")) {
                recentPaymentViewHolder.recent_payment_image_view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cash));
            }
            else if(payment.getString("payment_method").equals("Cheque")) {
                recentPaymentViewHolder.recent_payment_image_view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cheque));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (recentPaymentRecords.length() < noOfRecordsToDisplay) {
            return recentPaymentRecords.length();
        }
        else {
            return noOfRecordsToDisplay;
        }
    }

    public static class RecentPaymentViewHolder extends RecyclerView.ViewHolder {
        ImageView recent_payment_image_view;
        TextView recent_payment_amount, recent_payment_date, recent_payment_confirmed_date, recent_payment_id, recent_payment_method, recent_payment_type, recent_payment_view_receipt;
        public RecentPaymentViewHolder(View itemView) {
            super(itemView);
            recent_payment_image_view = (ImageView) itemView.findViewById(R.id.recent_payment_image_view);
            recent_payment_amount = (TextView) itemView.findViewById(R.id.recent_payment_amount);
            recent_payment_date = (TextView) itemView.findViewById(R.id.recent_payment_date);
            recent_payment_confirmed_date = (TextView) itemView.findViewById(R.id.recent_payment_confirmed_date);
            recent_payment_method = (TextView) itemView.findViewById(R.id.recent_payment_method);
            recent_payment_type = (TextView) itemView.findViewById(R.id.recent_payment_type);
            recent_payment_id = (TextView) itemView.findViewById(R.id.recent_payment_id);
            recent_payment_view_receipt = (TextView) itemView.findViewById(R.id.recent_payment_view_receipt);
        }
    }
}
