package com.nathanmkaya.flightbooking.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nathanmkaya.flightbooking.R;
import com.nathanmkaya.flightbooking.ui.widgets.DatePickerFragment;
import com.nathanmkaya.flightbooking.ui.widgets.TimePickerFragment;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    @BindView(R.id.destination_txt)
    TextInputEditText destination;
    @BindView(R.id.currentlocal_txt)
    TextInputEditText currentLocation;
    @BindView(R.id.airline_txt)
    TextInputEditText airline;
    @BindView(R.id.date_view)
    TextView dateView;
    @BindView(R.id.time_view)
    TextView timeView;
    @BindView(R.id.reservation_proceed_btn)
    Button proceed;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        ButterKnife.bind(this, view);

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        DateTimeFormatter dateFormart = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");

        String dateNow = date.format(dateFormart);
        String timeNow = time.format(timeFormat);

        dateView.setText(dateNow);
        timeView.setText(timeNow);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
            }
        });

        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "TimePicker");
            }
        });

        return view;
    }

    @OnClick(R.id.reservation_proceed_btn)
    public void proceed() {
        Bundle bundle = new Bundle();
        bundle.putString("destination", destination.getText().toString());
        bundle.putString("currentLocation", currentLocation.getText().toString());
        bundle.putString("departureTime", timeView.getText().toString());
        bundle.putString("departureDate", dateView.getText().toString());
        bundle.putString("airline", airline.getText().toString());
        Fragment fragment = new BookingReviewFragment();
        fragment.setArguments(bundle);
        FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment);
        manager.commit();
    }
}
