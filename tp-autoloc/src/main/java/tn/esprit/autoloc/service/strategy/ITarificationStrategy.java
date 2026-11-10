package tn.esprit.autoloc.service.strategy;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public interface ITarificationStrategy {

    BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation);

    String getCode(); // sert à sélectionner dynamiquement la stratégie

    default long nombreJours(Reservation reservation) {
        if (reservation.getDateDebut() == null || reservation.getDateFin() == null) {
            throw new IllegalArgumentException("Les dates de la réservation sont obligatoires");
        }
        return Math.max(1, ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin()));
    }
}