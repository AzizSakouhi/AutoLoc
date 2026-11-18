package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MaintenanceDTO {
    private Long id;
    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
    @NotNull(message = "Le véhicule est obligatoire")
    private Long vehiculeId;
}