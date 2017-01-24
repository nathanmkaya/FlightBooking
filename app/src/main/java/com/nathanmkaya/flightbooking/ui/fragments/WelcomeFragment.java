package com.nathanmkaya.flightbooking.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nathanmkaya.flightbooking.R;
import com.nathanmkaya.flightbooking.bus.Welcome;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.signup_btn)
    Button signupBtn;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @OnClick(R.id.login_btn)
    void loginEvent() {
        EventBus.getDefault().post(new Welcome.Login());
    }

    @OnClick(R.id.signup_btn)
    void signupEvent() {
        EventBus.getDefault().post(new Welcome.Signup());
    }

}
