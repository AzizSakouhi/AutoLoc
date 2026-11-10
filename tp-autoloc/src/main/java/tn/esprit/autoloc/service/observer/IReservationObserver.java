package tn.esprit.autoloc.service.observer;

import tn.esprit.autoloc.domain.Reservation;

public interface IReservationObserver {
    void onReservationConfirmee(Reservation reservation);
    void onReservationAnnulee(Reservation reservation);
}