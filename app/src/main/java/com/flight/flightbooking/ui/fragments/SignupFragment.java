package com.flight.flightbooking.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.flight.flightbooking.R;
import com.flight.flightbooking.bus.Signup;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    @BindView(R.id.name_editText)
    EditText nameEditText;
    @BindView(R.id.passwd_editText)
    EditText passwdEditText;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.signup_btn)
    public void signup() {
        EventBus.getDefault().post(new Signup(nameEditText.getText().toString(), passwdEditText.getText().toString()));
    }

}
