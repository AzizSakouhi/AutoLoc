package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EquipementDTO {
    private Long id;
    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;
}