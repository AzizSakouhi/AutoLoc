package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.enums.StatutReservation;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.service.factory.NotificationFactory;
import tn.esprit.autoloc.service.factory.TarificationStrategyFactory;
import tn.esprit.autoloc.service.observer.ReservationSubject;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class ReservationServiceTest {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private ReservationSubject reservationSubject;

    @Autowired
    private TarificationStrategyFactory tarificationFactory;

    @Autowired
    private NotificationFactory notificationFactory;

    private Client creerClient() {
        Client client = new Client();
        client.setNom("Ben Salah");
        client.setPrenom("Amine");
        client.setEmail("amine@mail.com");
        client.setTelephone("20111222");
        return client;
    }

    @Test
    void testCreationReservationNotifieEmailEtSms(CapturedOutput output) {
        Reservation reservation = reservationService.creerReservation(creerClient());

        assertEquals(StatutReservation.CONFIRMEE, reservation.getStatut());
        assertTrue(output.getOut().contains("[EMAIL] Confirmation envoyée au client amine@mail.com"));
        assertTrue(output.getOut().contains("[SMS] Confirmation envoyée au 20111222"));
    }

    @Test
    void testAnnulationReservationNotifieEmailEtSms(CapturedOutput output) {
        Reservation reservation = reservationService.creerReservation(creerClient());
        Reservation annulee = reservationService.annulerReservation(reservation);

        assertEquals(StatutReservation.ANNULEE, annulee.getStatut());
        assertTrue(output.getOut().contains("[EMAIL] Annulation notifiée au client amine@mail.com"));
        assertTrue(output.getOut().contains("[SMS] Annulation notifiée au 20111222"));
    }

    @Test
    void testStrategiesDeTarification() {
        Vehicule vehicule = new Vehicule();
        vehicule.setTarifJournalier(new BigDecimal("100.00"));

        // 2 jours, mercredi à vendredi : STANDARD (200.00)
        Reservation standard = new Reservation();
        standard.setDateDebut(LocalDate.of(2026, 11, 4));
        standard.setDateFin(LocalDate.of(2026, 11, 6));
        assertEquals("STANDARD", tarificationFactory.resoudre(standard).getCode());
        assertEquals(0, new BigDecimal("200.00")
                .compareTo(tarificationFactory.resoudre(standard).calculerTarif(vehicule, standard)));

        // 2 jours, samedi à lundi : WEEKEND (200 x 0.90 = 180.00)
        Reservation weekend = new Reservation();
        weekend.setDateDebut(LocalDate.of(2026, 11, 7));
        weekend.setDateFin(LocalDate.of(2026, 11, 9));
        assertEquals("WEEKEND", tarificationFactory.resoudre(weekend).getCode());
        assertEquals(0, new BigDecimal("180.00")
                .compareTo(tarificationFactory.resoudre(weekend).calculerTarif(vehicule, weekend)));

        // 10 jours : LONGUE_DUREE (1000 x 0.80 = 800.00)
        Reservation longue = new Reservation();
        longue.setDateDebut(LocalDate.of(2026, 11, 2));
        longue.setDateFin(LocalDate.of(2026, 11, 12));
        assertEquals("LONGUE_DUREE", tarificationFactory.resoudre(longue).getCode());
        assertEquals(0, new BigDecimal("800.00")
                .compareTo(tarificationFactory.resoudre(longue).calculerTarif(vehicule, longue)));
    }

    @Test
    void testNotificationFactory() {
        assertNotNull(notificationFactory.creerObservateur("EMAIL"));
        assertNotNull(notificationFactory.creerObservateur("SMS"));
        assertThrows(IllegalArgumentException.class, () -> notificationFactory.creerObservateur("FAX"));
    }
}