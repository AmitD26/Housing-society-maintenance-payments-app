package com.example.amit.dhareshwar_maintenance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amit on 10/12/16.
 */

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter {

    public static final int STATUS_CARD = 1;
    public static final int DUES_CARD = 2;
    public static final int RECENT_PAYMENTS_CARD = 3;
    public static final int RECENT_RECEIPTS_CARD = 4;

    public JSONObject residentInfo, dues;
    public JSONArray recent_payments, recent_receipts;
    public Context context;

    public DashboardRecyclerViewAdapter(Context context, JSONObject residentInfo, JSONObject dues, JSONArray recent_payments, JSONArray recent_receipts) {
        this.context = context;
        this.recent_payments = recent_payments;
        this.residentInfo = residentInfo;
        this.dues = dues;
        this.recent_receipts = recent_receipts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == STATUS_CARD) {
            return new StatusCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.status_card, parent, false));
        }
        else if (viewType == DUES_CARD) {
            return new DuesCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dues_card, parent, false));
        }
        else if (viewType == RECENT_PAYMENTS_CARD) {
            return new RecentPaymentsCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_payments_card, parent, false));
        }
        else if (viewType == RECENT_RECEIPTS_CARD) {
            return new RecentReceiptsCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_receipts_card, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof StatusCardViewHolder) {
                if (residentInfo.getString("status").equals("Tenant living")) {
                    ((StatusCardViewHolder) holder).owner_name.setVisibility(TextView.VISIBLE);
                    ((StatusCardViewHolder) holder).owner_name.setText(residentInfo.getString("owner_name"));
                    ((StatusCardViewHolder) holder).resident_name.setText(residentInfo.getString("tenant_name"));
                }
                else {
                    ((StatusCardViewHolder) holder).resident_name.setText(residentInfo.getString("owner_name"));
                }
                ((StatusCardViewHolder) holder).flat_no.setText(String.format(context.getString(R.string.flat_no), residentInfo.getString("flat_no")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return STATUS_CARD;
        }
        else if (position == 1) {
            return DUES_CARD;
        }
        else if (position == 2) {
            return RECENT_PAYMENTS_CARD;
        }
        else if (position == 3) {
            return RECENT_RECEIPTS_CARD;
        }

        return super.getItemViewType(position);
    }

    public static class StatusCardViewHolder extends RecyclerView.ViewHolder {
        ImageView status_tenant_image_view;
        TextView flat_no, resident_name, owner_name;
        public StatusCardViewHolder(View itemView) {
            super(itemView);
            status_tenant_image_view = (ImageView) itemView.findViewById(R.id.status_tenant_image_view);
            flat_no = (TextView) itemView.findViewById(R.id.flat_no);
            resident_name = (TextView) itemView.findViewById(R.id.resident_name);
            owner_name = (TextView) itemView.findViewById(R.id.owner_name);

        }
    }

    public static class DuesCardViewHolder extends RecyclerView.ViewHolder {
        TextView dues_left, dues_left_for;
        public DuesCardViewHolder(View itemView) {
            super(itemView);
            dues_left = (TextView) itemView.findViewById(R.id.dues_left);
            dues_left_for = (TextView) itemView.findViewById(R.id.dues_left_for);
        }
    }

    public static class RecentPaymentsCardViewHolder extends RecyclerView.ViewHolder {
        ImageView payment_image_view;
        TextView payment_amount;
        TextView payment_date;
        TextView payment_confirmed_date;
        TextView payment_id;
        TextView payment_method;
        TextView payment_type;
        public RecentPaymentsCardViewHolder(View itemView) {
            super(itemView);
            payment_image_view = (ImageView) itemView.findViewById(R.id.payment_image_view);
            payment_amount = (TextView) itemView.findViewById(R.id.payment_amount);
            payment_date = (TextView) itemView.findViewById(R.id.payment_date);
            payment_confirmed_date = (TextView) itemView.findViewById(R.id.payment_confirmed_date);
            payment_id = (TextView) itemView.findViewById(R.id.payment_id);
            payment_method = (TextView) itemView.findViewById(R.id.payment_method);
            payment_type = (TextView) itemView.findViewById(R.id.payment_type);
        }
    }

    public static class RecentReceiptsCardViewHolder extends RecyclerView.ViewHolder {
        public RecentReceiptsCardViewHolder(View itemView) {
            super(itemView);
        }
    }
}
