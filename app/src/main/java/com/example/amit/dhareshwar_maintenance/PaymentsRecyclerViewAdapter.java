package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amit on 10/12/16.
 */

public class PaymentsRecyclerViewAdapter extends RecyclerView.Adapter {

    JSONArray paymentRecords;
    Context context;
    String single_or_all;

    public PaymentsRecyclerViewAdapter(JSONArray paymentRecords, Context context, String single_or_all) {
        this.paymentRecords = new JSONArray();
        this.single_or_all = single_or_all;
        for (int i = paymentRecords.length()-1; i>=0; i--) {
            try {
                this.paymentRecords.put(paymentRecords.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PaymentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.payment,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            JSONObject payment = this.paymentRecords.getJSONObject(position);
            PaymentViewHolder paymentViewHolder = (PaymentViewHolder) holder;
            paymentViewHolder.amount.setText(String.format(context.getString(R.string.payment_amount),payment.getString("amount_paid")));
            paymentViewHolder.payment_date.setText(payment.getString("date_of_payment"));
            paymentViewHolder.payment_id.setText(String.format(context.getString(R.string.payment_id),payment.getString("payment_id")));
            paymentViewHolder.payment_method.setText(String.format(context.getString(R.string.payment_method),payment.getString("payment_method")));
            paymentViewHolder.payment_type.setText(String.format(context.getString(R.string.payment_type),payment.getString("payment_type")));
            
            if (payment.getString("confirmed_flag").equals("1")) {
                paymentViewHolder.confirmed_date.setTextColor(Color.GREEN);
                paymentViewHolder.confirmed_date.setText(String.format(context.getString(R.string.payment_confirmation),payment.getString("payment_confirmation_date")));

                paymentViewHolder.view_receipt.setVisibility(TextView.VISIBLE);
                if (payment.getString("receipt_received").equals("1")) {
                    paymentViewHolder.receipt_given_date.setVisibility(TextView.VISIBLE);
                    paymentViewHolder.receipt_received_date.setVisibility(TextView.VISIBLE);
                    paymentViewHolder.view_receipt.setText(R.string.receipt_received);
                    paymentViewHolder.receipt_received_date.setText(String.format(context.getString(R.string.receipt_received_on), payment.getString("receipt_received_date")));
                    paymentViewHolder.receipt_given_date.setText(String.format(context.getString(R.string.receipt_given_on), payment.getString("receipt_received_date")));
                }
                else if (payment.getString("receipt_given").equals("1")) {
                    paymentViewHolder.receipt_given_date.setVisibility(TextView.VISIBLE);
                    paymentViewHolder.view_receipt.setText(R.string.receipt_given);
                    paymentViewHolder.receipt_given_date.setText(String.format(context.getString(R.string.receipt_given_on), payment.getString("receipt_given_date")));
                }
                else {
                    paymentViewHolder.view_receipt.setText(R.string.receipt_not_given);
                }

            }
            else {
                paymentViewHolder.confirmed_date.setTextColor(Color.RED);
                paymentViewHolder.confirmed_date.setText(R.string.not_confirmed_yet);
            }

            if(payment.getString("payment_method").equals("Cash")) {
                paymentViewHolder.payment_image_view.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.cash));
            }
            else if(payment.getString("payment_method").equals("Cheque")) {
                paymentViewHolder.payment_image_view.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.cheque));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.paymentRecords.length();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView amount, payment_date, confirmed_date, payment_method, payment_type, payment_id, view_receipt, receipt_given_date, receipt_received_date;
        RelativeLayout paymentRelativeLayout;
        ImageView payment_image_view;
        public PaymentViewHolder(View itemView) {
            super(itemView);
            amount = (TextView) itemView.findViewById(R.id.payment_amount);
            confirmed_date = (TextView) itemView.findViewById(R.id.payment_confirmed_date);
            payment_method = (TextView) itemView.findViewById(R.id.payment_method);
            payment_type = (TextView) itemView.findViewById(R.id.payment_type);
            payment_id = (TextView) itemView.findViewById(R.id.payment_id);
            payment_date = (TextView) itemView.findViewById(R.id.payment_date);
            paymentRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.payment_relative_layout);
            payment_image_view = (ImageView) itemView.findViewById(R.id.payment_image_view);
            view_receipt = (TextView) itemView.findViewById(R.id.view_receipt);
            receipt_given_date = (TextView) itemView.findViewById(R.id.receipt_given_date);
            receipt_received_date = (TextView) itemView.findViewById(R.id.receipt_received_date);
        }
    }
}
