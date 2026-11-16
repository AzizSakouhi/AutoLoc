package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.PaiementDTO;

import java.util.List;

public interface IPaiementService {
    PaiementDTO findById(Long id);
    List<PaiementDTO> findAll();
}