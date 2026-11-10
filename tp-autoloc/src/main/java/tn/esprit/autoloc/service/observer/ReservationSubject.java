package tn.esprit.autoloc.service.observer;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujet observable : maintient la liste des observateurs et les notifie.
 * Tous les beans IReservationObserver sont injectés par Spring.
 */
@Component
public class ReservationSubject {

    private final List<IReservationObserver> observers;

    public ReservationSubject(List<IReservationObserver> observers) {
        this.observers = new ArrayList<>(observers);
    }

    public void notifierConfirmation(Reservation reservation) {
        observers.forEach(o -> o.onReservationConfirmee(reservation));
    }

    public void notifierAnnulation(Reservation reservation) {
        observers.forEach(o -> o.onReservationAnnulee(reservation));
    }
}