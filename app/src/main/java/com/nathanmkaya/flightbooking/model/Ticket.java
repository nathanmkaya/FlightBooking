package com.nathanmkaya.flightbooking.model;

import io.realm.Realm;
import io.realm.RealmObject;

public class Ticket extends RealmObject {

    public String destination;
    public String departure;
    public String departureDate;
    public String departureTime;
    public String airline;

    public Ticket() {
    }

    public Ticket(String destination, String departure, String departureDate, String departureTime, String airline) {
        this.destination = destination;
        this.departure = departure;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.airline = airline;
    }

    public static void saveTicket(final Ticket ticket) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Ticket ticket1 = realm.copyToRealm(ticket);
            }
        });
    }

    public static void deleteTicket() {
    }
}
