package com.flight.flightbooking.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flight.flightbooking.R;
import com.flight.flightbooking.model.Ticket;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by nathan on 1/18/17.
 */

public class ReservationsAdapter extends RealmRecyclerViewAdapter<Ticket, ReservationsAdapter.ViewHolder> {

    OnItemClickListener itemClickListener;
    OnItemLongClickListener itemLongClickListener;

    public ReservationsAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Ticket> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.reserveation_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view, viewHolder.getLayoutPosition());
            }
        });*/
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemLongClickListener.onItemLongClick(view, viewHolder.getLayoutPosition());
                return true;
            }
        });
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

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
    }
}
