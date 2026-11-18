package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.esprit.autoloc.domain.enums.RoleEmploye;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeDTO {
    private Long id;
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    @NotNull(message = "Le rôle est obligatoire")
    private RoleEmploye role;
    private Long agenceId;
}