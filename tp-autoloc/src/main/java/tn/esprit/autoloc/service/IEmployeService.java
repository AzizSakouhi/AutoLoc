package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.EmployeDTO;

import java.util.List;

public interface IEmployeService {
    EmployeDTO create(EmployeDTO dto);
    EmployeDTO findById(Long id);
    List<EmployeDTO> findAll();
    EmployeDTO update(Long id, EmployeDTO dto);
    void deleteById(Long id);
    void affecterEmployeAAgence(Long employeId, Long agenceId);
    void desaffecterEmployeDeAgence(Long employeId);
}