package com.nathanmkaya.flightbooking.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nathanmkaya.flightbooking.R;
import com.nathanmkaya.flightbooking.bus.Login;
import com.nathanmkaya.flightbooking.bus.Signup;
import com.nathanmkaya.flightbooking.bus.Welcome;
import com.nathanmkaya.flightbooking.model.User;
import com.nathanmkaya.flightbooking.ui.fragments.LoginFragment;
import com.nathanmkaya.flightbooking.ui.fragments.SignupFragment;
import com.nathanmkaya.flightbooking.ui.fragments.WelcomeFragment;
import com.nathanmkaya.flightbooking.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.fullscreen_content)
    View fullscreen;
    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        if (PrefUtils.isLoggedIn(this)) {
            startActivity(new Intent(this, BaseActivity.class));
            finish();
        }

        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        Fragment welcome_fragment = new WelcomeFragment();
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction().replace(R.id.fullscreen_content, welcome_fragment);
        manager.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        fullscreen.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mVisible = false;

    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        fullscreen.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        mVisible = true;

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void showLoginView(Welcome.Login login) {
        Fragment loginFragment = new LoginFragment();
        String name = loginFragment.getClass().getName();
        if (!getSupportFragmentManager().popBackStackImmediate(name, 0)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fullscreen_content, loginFragment).addToBackStack(name).commit();
        }
    }

    @Subscribe
    public void showSignupView(Welcome.Signup signup) {
        Fragment signupFragment = new SignupFragment();
        String name = signupFragment.getClass().getName();
        if (!getSupportFragmentManager().popBackStackImmediate(name, 0)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fullscreen_content, signupFragment).addToBackStack(name).commit();
        }
    }

    @Subscribe
    public void handleLoginEvent(Login event) {
        User user = new User(event.name, event.passwd);
        if (User.checkUser(user)) {
            PrefUtils.setLoggedIn(this);
            startActivity(new Intent(this, BaseActivity.class));
            finish();
        }
    }

    @Subscribe
    public void handleSignupEvent(Signup event) {
        User user = new User(event.name, event.passwd);
        User.addUser(user);
    }
}
