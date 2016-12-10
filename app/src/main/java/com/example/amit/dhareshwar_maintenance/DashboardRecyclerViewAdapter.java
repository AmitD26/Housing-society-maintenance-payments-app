package com.example.amit.dhareshwar_maintenance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by amit on 10/12/16.
 */

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter {

    public static final int STATUS_CARD = 1;
    public static final int NAME_CARD = 2;
    public static final int DUES_CARD = 3;
    public static final int RECENT_PAYMENTS_CARD = 4;
    public static final int RECENT_RECEIPTS_CARD = 5;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == STATUS_CARD) {
            return new StatusCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.status_card, parent, false));
        }
        else if (viewType == NAME_CARD) {
            return new NameCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.name_card, parent, false));
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
        return new NameCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.status_card, parent, false));
//        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return STATUS_CARD;
        }
        else if (position == 1) {
            return NAME_CARD;
        }
        else if (position == 2) {
            return DUES_CARD;
        }
        else if (position == 3) {
            return RECENT_PAYMENTS_CARD;
        }
        else if (position == 4) {
            return RECENT_RECEIPTS_CARD;
        }

        return super.getItemViewType(position);
    }

    public static class StatusCardViewHolder extends RecyclerView.ViewHolder {
        public StatusCardViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class NameCardViewHolder extends RecyclerView.ViewHolder {
        public NameCardViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class DuesCardViewHolder extends RecyclerView.ViewHolder {
        public DuesCardViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class RecentPaymentsCardViewHolder extends RecyclerView.ViewHolder {
        public RecentPaymentsCardViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class RecentReceiptsCardViewHolder extends RecyclerView.ViewHolder {
        public RecentReceiptsCardViewHolder(View itemView) {
            super(itemView);
        }
    }
}
