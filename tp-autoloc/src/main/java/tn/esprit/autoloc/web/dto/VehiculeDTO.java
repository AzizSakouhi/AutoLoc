package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import tn.esprit.autoloc.domain.enums.CategorieVehicule;
import tn.esprit.autoloc.domain.enums.StatutVehicule;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VehiculeDTO {
    private Long id;
    @NotBlank(message = "L'immatriculation est obligatoire")
    private String immatriculation;
    @NotBlank(message = "La marque est obligatoire")
    private String marque;
    @NotBlank(message = "Le modèle est obligatoire")
    private String modele;
    @NotNull(message = "La catégorie est obligatoire")
    private CategorieVehicule categorie;
    @NotNull(message = "Le tarif journalier est obligatoire")
    @Positive(message = "Le tarif journalier doit être strictement positif")
    private BigDecimal tarifJournalier;
    private StatutVehicule statut;
    @NotNull(message = "L'agence est obligatoire")
    private Long agenceId;
}