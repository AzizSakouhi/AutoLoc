package tn.esprit.autoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.web.dto.AgenceDTO;

@Mapper(componentModel = "spring")
public interface AgenceMapper {

    @Mapping(target = "id", source = "idAgence")
    AgenceDTO toDto(Agence agence);

    @Mapping(target = "idAgence", source = "id")
    @Mapping(target = "employes", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    Agence toEntity(AgenceDTO dto);
}