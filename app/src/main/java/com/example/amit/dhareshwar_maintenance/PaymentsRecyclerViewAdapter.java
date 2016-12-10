package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.graphics.Color;
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

    public PaymentsRecyclerViewAdapter(JSONArray paymentRecords, Context context) {
        this.paymentRecords = new JSONArray();
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
                paymentViewHolder.paymentRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
            else {
                paymentViewHolder.confirmed_date.setTextColor(Color.RED);
                paymentViewHolder.confirmed_date.setText(R.string.not_confirmed_yet);
            }

            paymentViewHolder.payment_image_view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_name));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.paymentRecords.length();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView amount, payment_date, confirmed_date, payment_method, payment_type, payment_id;
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
        }
    }
}
