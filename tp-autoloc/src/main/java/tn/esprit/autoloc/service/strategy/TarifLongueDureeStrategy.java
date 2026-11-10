package tn.esprit.autoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TarifLongueDureeStrategy implements ITarificationStrategy {

    private static final BigDecimal REMISE_LONGUE_DUREE = new BigDecimal("0.80");

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        return vehicule.getTarifJournalier()
                .multiply(BigDecimal.valueOf(nombreJours(reservation)))
                .multiply(REMISE_LONGUE_DUREE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getCode() {
        return "LONGUE_DUREE";
    }
}