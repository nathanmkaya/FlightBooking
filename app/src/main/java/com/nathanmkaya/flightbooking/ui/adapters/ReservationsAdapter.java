package com.nathanmkaya.flightbooking.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nathanmkaya.flightbooking.R;
import com.nathanmkaya.flightbooking.model.Ticket;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by nathan on 1/18/17.
 */

public class ReservationsAdapter extends RealmRecyclerViewAdapter<Ticket, ReservationsAdapter.ViewHolder> {

    public ReservationsAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Ticket> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.reserveation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ticket ticket = getItem(position);
        holder.airlineView.setText(ticket.airline);
        holder.departureView.setText(ticket.departure);
        holder.destinationView.setText(ticket.destination);
        holder.dateTimeView.setText(ticket.departureDate + " " + ticket.departureTime);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        @BindView(R.id.airline_view)
        TextView airlineView;
        @BindView(R.id.departure_view)
        TextView departureView;
        @BindView(R.id.destination_view)
        TextView destinationView;
        @BindView(R.id.date_view)
        TextView dateTimeView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
