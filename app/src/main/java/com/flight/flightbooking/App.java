package com.flight.flightbooking;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Realm Initial Configuration
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("flight")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);

        // Android JSR310 Initialization
        AndroidThreeTen.init(this);

        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            Data.populate();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }*/
        Data.populate();
    }
}
