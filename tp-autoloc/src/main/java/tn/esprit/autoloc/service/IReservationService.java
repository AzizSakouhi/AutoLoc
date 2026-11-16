package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationRequestDTO;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;

import java.util.List;

public interface IReservationService {
    ReservationResponseDTO create(ReservationRequestDTO dto);
    ReservationResponseDTO findById(Long id);
    List<ReservationResponseDTO> findAll();
    ReservationResponseDTO update(Long id, ReservationRequestDTO dto);
    void deleteById(Long id);

    // Atelier 6 (Observer)
    Reservation creerReservation(Client client);
    Reservation annulerReservation(Reservation reservation);
}