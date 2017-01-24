package com.nathanmkaya.flightbooking.ui.widgets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nathanmkaya.flightbooking.R;

import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;


@SuppressLint({"NewApi", "LocalSuppress"})
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalTime localTime = LocalTime.of(hourOfDay, minute);
        ((TextView) getActivity().findViewById(R.id.time_view)).setText(localTime.format(DateTimeFormatter.ofPattern("hh:mma")));
    }
}
