package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceDTO;
import tn.esprit.autoloc.web.mapper.AgenceMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceDTO create(AgenceDTO dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("Une nouvelle agence ne doit pas avoir d'identifiant");
        }
        Agence saved = agenceRepository.save(agenceMapper.toEntity(dto));
        return agenceMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceDTO findById(Long id) {
        return agenceMapper.toDto(chercher(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> findAll() {
        return agenceRepository.findAll().stream().map(agenceMapper::toDto).toList();
    }

    @Override
    public AgenceDTO update(Long id, AgenceDTO dto) {
        Agence existant = chercher(id);
        existant.setNom(dto.getNom());
        existant.setVille(dto.getVille());
        existant.setAdresse(dto.getAdresse());
        existant.setTelephone(dto.getTelephone());
        return agenceMapper.toDto(agenceRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private Agence chercher(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}