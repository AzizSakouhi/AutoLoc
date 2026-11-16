package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ContratDTO {
    private Long id;
    private LocalDate dateSignature;
    @PositiveOrZero(message = "Le montant total ne peut pas être négatif")
    private BigDecimal montantTotal;
    private Boolean valide;
    private Long reservationId;
}