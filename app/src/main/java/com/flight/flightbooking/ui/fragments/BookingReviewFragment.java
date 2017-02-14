package com.flight.flightbooking.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flight.flightbooking.R;
import com.flight.flightbooking.model.Ticket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingReviewFragment extends Fragment {

    @BindView(R.id.destination_txt)
    TextInputEditText destinationTxt;
    @BindView(R.id.currentlocal_txt)
    TextInputEditText currentLocationTxt;
    @BindView(R.id.airline_txt)
    TextInputEditText airlineTxt;
    @BindView(R.id.date_view)
    TextView dateView;
    @BindView(R.id.time_view)
    TextView timeView;

    private String destination;
    private String currentLocation;
    private String departureTime;
    private String departureDate;
    private String airline;

    public BookingReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            destination = bundle.getString("destination");
            currentLocation = bundle.getString("currentLocation");
            departureTime = bundle.getString("departureTime");
            departureDate = bundle.getString("departureDate");
            airline = bundle.getString("cost");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_review, container, false);
        ButterKnife.bind(this, view);

        destinationTxt.setText(destination);
        currentLocationTxt.setText(currentLocation);
        airlineTxt.setText(airline);
        dateView.setText(departureDate);
        timeView.setText(departureTime);

        return view;
    }

    @OnClick(R.id.reservation_proceed_btn)
    public void proceed() {
        Ticket ticket = new Ticket(destination, currentLocation, departureDate, departureTime, airline);
        Ticket.saveTicket(ticket);
        Toast.makeText(getContext(), "Reservation Added", Toast.LENGTH_SHORT).show();
    }

}
