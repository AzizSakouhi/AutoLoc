package tn.esprit.autoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.web.dto.EquipementDTO;

@Mapper(componentModel = "spring")
public interface EquipementMapper {

    @Mapping(target = "id", source = "idEquipement")
    EquipementDTO toDto(Equipement equipement);

    @Mapping(target = "idEquipement", source = "id")
    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDTO dto);
}