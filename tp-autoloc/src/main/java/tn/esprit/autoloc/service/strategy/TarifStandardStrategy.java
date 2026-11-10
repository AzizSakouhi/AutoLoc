package tn.esprit.autoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TarifStandardStrategy implements ITarificationStrategy {

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        return vehicule.getTarifJournalier()
                .multiply(BigDecimal.valueOf(nombreJours(reservation)))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getCode() {
        return "STANDARD";
    }
}