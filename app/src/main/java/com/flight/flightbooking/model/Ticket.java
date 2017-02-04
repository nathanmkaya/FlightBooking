package com.flight.flightbooking.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

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

    public static void deleteTicket(final Ticket ticket) {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Ticket> ticketRealmResults = realm.where(Ticket.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Ticket ticket1 = ticketRealmResults.where()
                        .equalTo("airline", ticket.airline)
                        .equalTo("departure", ticket.departure)
                        .equalTo("departureDate", ticket.departureDate)
                        .equalTo("departureTime", ticket.departureTime)
                        .equalTo("destination", ticket.destination).findFirst();
                ticket1.deleteFromRealm();
            }
        });
    }
}
