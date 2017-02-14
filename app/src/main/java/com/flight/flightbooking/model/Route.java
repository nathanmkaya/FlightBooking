package com.flight.flightbooking.model;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nathan on 2/13/17.
 */

public class Route extends RealmObject {
    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public String destination;
    public String departure;
    public float cost;

    public Route() {
    }

    public Route(String destination, String departure, String airline, float cost) {
        this.destination = destination;
        this.departure = departure;
        this.cost = cost;
    }

    public Route(String destination, String departure) {
        this.destination = destination;
        this.departure = departure;
    }

    public static void saveRoute(final Route route) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Route route1 = realm.copyToRealmOrUpdate(route);
            }
        });
    }
}
