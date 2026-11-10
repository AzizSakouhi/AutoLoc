package tn.esprit.autoloc.service.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;

@Slf4j
@Component
public class SmsNotificationObserver implements IReservationObserver {

    @Override
    public void onReservationConfirmee(Reservation reservation) {
        log.info("[SMS] Confirmation envoyée au {} pour la réservation #{}",
                reservation.getClient().getTelephone(), reservation.getIdReservation());
    }

    @Override
    public void onReservationAnnulee(Reservation reservation) {
        log.info("[SMS] Annulation notifiée au {} pour la réservation #{}",
                reservation.getClient().getTelephone(), reservation.getIdReservation());
    }
}