package com.flight.flightbooking.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.flight.flightbooking.Data;
import com.flight.flightbooking.R;
import com.flight.flightbooking.model.Route;
import com.flight.flightbooking.ui.widgets.DatePickerFragment;
import com.flight.flightbooking.ui.widgets.TimePickerFragment;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    @BindView(R.id.destination_txt)
    AutoCompleteTextView destination;
    @BindView(R.id.currentlocal_txt)
    AutoCompleteTextView currentLocation;
    @BindView(R.id.route_cost)
    TextView cost;
    @BindView(R.id.date_view)
    TextView dateView;
    @BindView(R.id.time_view)
    TextView timeView;
    @BindView(R.id.reservation_proceed_btn)
    Button proceed;

    ArrayAdapter<String> adapter;
    List<String> places = Data.places;

    boolean next = false;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        ButterKnife.bind(this, view);

        init();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");

        String dateNow = date.format(dateFormat);
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
        if (next) {
            Bundle bundle = new Bundle();
            bundle.putString("destination", destination.getText().toString());
            bundle.putString("currentLocation", currentLocation.getText().toString());
            bundle.putString("departureTime", timeView.getText().toString());
            bundle.putString("departureDate", dateView.getText().toString());
            bundle.putString("cost", cost.getText().toString());
            Fragment fragment = new BookingReviewFragment();
            fragment.setArguments(bundle);
            FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment);
            manager.commit();
        } else {
            if (!currentLocation.getText().toString().equals("") && !destination.getText().toString().equals("")) {
                Realm realm = Realm.getDefaultInstance();
                RealmResults<Route> routes1 = realm.where(Route.class).findAllSorted("destination");
                Route route = routes1.where().equalTo("destination", destination.getText().toString()).equalTo("departure", currentLocation.getText().toString()).findFirst();
                if (route.cost != 0.0f) {
                    cost.setText(String.format("%.00f", route.cost));
                }
                next = true;
                buttonState();
            }
        }
    }

    public void init() {

        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, places);

        currentLocation.setThreshold(1);
        destination.setThreshold(1);
        currentLocation.setAdapter(adapter);
        destination.setAdapter(adapter);


        if (!currentLocation.getText().toString().equals("")) {
            Data.places.remove(currentLocation.getText().toString());
            adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, places);
            destination.setAdapter(adapter);
        } else if (!destination.getText().toString().equals("")) {
            Data.places.remove(destination.getText().toString());
            adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, places);
            currentLocation.setAdapter(adapter);
        } else {
            places = Data.places;
        }

        buttonState();
    }

    public void buttonState() {
        if (next) {
            proceed.setText("Proceed");
        } else {
            proceed.setText("Calculate Price");
        }
    }
}
