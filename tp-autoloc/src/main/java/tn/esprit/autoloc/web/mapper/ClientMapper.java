package tn.esprit.autoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.web.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", source = "idClient")
    ClientDTO toDto(Client client);

    @Mapping(target = "idClient", source = "id")
    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDTO dto);
}