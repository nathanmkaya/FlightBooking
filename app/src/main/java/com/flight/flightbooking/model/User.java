package com.flight.flightbooking.model;

import com.flight.flightbooking.bus.Welcome;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nathan on 1/13/17.
 */

public class User extends RealmObject {
    @PrimaryKey
    public String name;
    public String passwd;

    public User() {
    }

    public User(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public static void addUser(final User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user1 = realm.copyToRealm(user);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                EventBus.getDefault().post(new Welcome.Login());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                System.out.println(error.getMessage());
            }
        });
    }

    public static boolean checkUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> users = realm.where(User.class).findAll();
        for (User user1 : users) {
            if (user.name.equals(user1.name) && user.passwd.equals(user1.passwd)) {
                return true;
            }
        }
        return false;
    }
}
