package com.flight.flightbooking;

import com.flight.flightbooking.model.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by nathan on 2/13/17.
 */

public class Data {
    public static List<String> places = new ArrayList<>(Arrays.asList("Nairobi", "Mombasa", "Kisumu", "Arusha", "Dar es Salaam", "Mwanza", "Dodoma"));
    static Realm realm = Realm.getDefaultInstance();

    public static void populate() {
        for (String place : places) {
            for (String place2 : places) {
                if (place.equals(place2)) {
                    continue;
                } else {
                    Route route = new Route(place, place2);
                    Route.saveRoute(route);
                    System.out.println(place + " : " + place2);
                }
            }
        }

        final Random random = new Random();

        RealmResults<Route> routes = realm.where(Route.class).findAll();
        for (final Route route : routes) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    route.cost = random.nextInt(800 - 300 + 1) + 300;
                    Route route1 = realm.copyToRealmOrUpdate(route);
                }
            });
        }
        RealmResults<Route> routes1 = realm.where(Route.class).findAll();
        for (Route route : routes1) {
            System.out.println(route.departure + " : " + route.destination + " : " + route.cost);
        }

    }

    private static void deleteAllRoutes() {
        final RealmResults<Route> routes = realm.where(Route.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                routes.deleteAllFromRealm();
            }
        });
    }

    public static void setPrices() {
        final Random random = new Random();

        RealmResults<Route> routes = realm.where(Route.class).findAll();
        for (final Route route : routes) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    route.cost = random.nextInt(800 - 300 + 1) + 300;
                    Route route1 = realm.copyToRealmOrUpdate(route);
                }
            });
        }
    }
}
