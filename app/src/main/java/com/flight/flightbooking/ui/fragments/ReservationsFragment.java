package com.flight.flightbooking.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flight.flightbooking.R;
import com.flight.flightbooking.bus.Delete;
import com.flight.flightbooking.model.Ticket;
import com.flight.flightbooking.ui.adapters.ReservationsAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationsFragment extends Fragment {

    @BindView(R.id.reservations_list)
    RecyclerView reservationList;
    ReservationsAdapter adapter;

    public ReservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);
        ButterKnife.bind(this, view);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Ticket> tickets = realm.where(Ticket.class).findAll();
        adapter = new ReservationsAdapter(getContext(), tickets, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reservationList.setLayoutManager(layoutManager);
        reservationList.setAdapter(adapter);
        adapter.setOnItemLongClickListener(new ReservationsAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                Ticket ticket = adapter.getItem(position);
                EventBus.getDefault().post(new Delete(ticket));
                return true;
            }
        });

        return view;
    }

}
