package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tn.esprit.autoloc.domain.enums.ModePaiement;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaiementDTO {
    private Long id;
    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être strictement positif")
    private BigDecimal montant;
    private LocalDate datePaiement;
    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement modePaiement;
    @NotNull(message = "Le contrat est obligatoire")
    private Long contratId;
}