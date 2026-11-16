package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.enums.StatutReservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.service.factory.TarificationStrategyFactory;
import tn.esprit.autoloc.service.observer.ReservationSubject;
import tn.esprit.autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.mapper.ReservationMapper;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;
    private final TarificationStrategyFactory tarificationFactory;
    private final ReservationSubject reservationSubject;

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        appliquer(reservation, dto);
        return versReponse(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO findById(Long id) {
        return versReponse(chercher(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findAll() {
        return reservationRepository.findAll().stream().map(this::versReponse).toList();
    }

    @Override
    public ReservationResponseDTO update(Long id, ReservationRequestDTO dto) {
        Reservation existant = chercher(id);
        appliquer(existant, dto);
        return versReponse(reservationRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    /** Crée une réservation et notifie tous les observateurs (Atelier 6). */
    @Override
    public Reservation creerReservation(Client client) {
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setDateDebut(LocalDate.now());
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservationSubject.notifierConfirmation(reservation);
        return reservation;
    }

    /** Annule une réservation et notifie tous les observateurs (Atelier 6). */
    @Override
    public Reservation annulerReservation(Reservation reservation) {
        reservation.setStatut(StatutReservation.ANNULEE);
        log.info("Réservation #{} annulée pour le client {}",
                reservation.getIdReservation(), reservation.getClient().getEmail());
        reservationSubject.notifierAnnulation(reservation);
        return reservation;
    }

    private void appliquer(Reservation reservation, ReservationRequestDTO dto) {
        if (!dto.getDateFin().isAfter(dto.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début");
        }
        reservation.setClient(clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", dto.getClientId())));
        reservation.setVehicule(vehiculeRepository.findById(dto.getVehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.getVehiculeId())));
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
    }

    private ReservationResponseDTO versReponse(Reservation reservation) {
        ReservationResponseDTO response = reservationMapper.toDto(reservation);
        if (peutEstimerTarif(reservation)) {
            response.setTarifEstime(tarificationFactory.resoudre(reservation)
                    .calculerTarif(reservation.getVehicule(), reservation));
        }
        return response;
    }

    private boolean peutEstimerTarif(Reservation reservation) {
        return reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getVehicule() != null
                && reservation.getVehicule().getTarifJournalier() != null;
    }

    private Reservation chercher(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }
}