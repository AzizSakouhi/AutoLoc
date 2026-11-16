package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.AgenceDTO;
import java.util.List;

public interface IAgenceService {
    AgenceDTO create(AgenceDTO dto);
    AgenceDTO findById(Long id);
    List<AgenceDTO> findAll();
    AgenceDTO update(Long id, AgenceDTO dto);
    void deleteById(Long id);
}