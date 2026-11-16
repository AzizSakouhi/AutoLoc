package tn.esprit.autoloc.web.dto;

import lombok.*;
import tn.esprit.autoloc.domain.enums.StatutReservation;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationResponseDTO {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
    private Long clientId;
    private String clientNomComplet;
    private Long vehiculeId;
    private String vehiculeImmatriculation;
    private BigDecimal tarifEstime;
}