package tn.esprit.autoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TarifWeekendStrategy implements ITarificationStrategy {

    private static final BigDecimal REMISE_WEEKEND = new BigDecimal("0.90");

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        return vehicule.getTarifJournalier()
                .multiply(BigDecimal.valueOf(nombreJours(reservation)))
                .multiply(REMISE_WEEKEND)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getCode() {
        return "WEEKEND";
    }
}