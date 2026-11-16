package tn.esprit.autoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "id", source = "idReservation")
    @Mapping(target = "clientId", source = "client.idClient")
    @Mapping(target = "clientNomComplet", source = "client", qualifiedByName = "nomComplet")
    @Mapping(target = "vehiculeId", source = "vehicule.idVehicule")
    @Mapping(target = "vehiculeImmatriculation", source = "vehicule.immatriculation")
    @Mapping(target = "tarifEstime", ignore = true)
    ReservationResponseDTO toDto(Reservation reservation);

    @Named("nomComplet")
    default String nomComplet(Client client) {
        return client == null ? null : client.getPrenom() + " " + client.getNom();
    }
}