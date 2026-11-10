package tn.esprit.autoloc.service.factory;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.strategy.ITarificationStrategy;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Sélectionne dynamiquement la stratégie de tarification adaptée à une réservation.
 * Toutes les implémentations de ITarificationStrategy sont injectées par Spring.
 */
@Component
public class TarificationStrategyFactory {

    private static final long SEUIL_LONGUE_DUREE = 7;
    private static final long DUREE_MAX_WEEKEND = 3;

    private final Map<String, ITarificationStrategy> strategies;

    public TarificationStrategyFactory(List<ITarificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(ITarificationStrategy::getCode, Function.identity()));
    }

    public ITarificationStrategy resoudre(Reservation reservation) {
        if (reservation.getDateDebut() == null || reservation.getDateFin() == null) {
            throw new IllegalArgumentException("Les dates de la réservation sont obligatoires");
        }
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());

        String code;
        if (jours >= SEUIL_LONGUE_DUREE) {
            code = "LONGUE_DUREE";
        } else if (estWeekend(reservation)) {
            code = "WEEKEND";
        } else {
            code = "STANDARD";
        }
        return strategies.get(code);
    }

    private boolean estWeekend(Reservation reservation) {
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        return jours <= DUREE_MAX_WEEKEND
                && (estJourDeWeekend(reservation.getDateDebut().getDayOfWeek())
                || estJourDeWeekend(reservation.getDateFin().getDayOfWeek()));
    }

    private boolean estJourDeWeekend(DayOfWeek jour) {
        return jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY;
    }
}