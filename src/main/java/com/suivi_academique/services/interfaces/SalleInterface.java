package com.suivi_academique.services.interfaces;

import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.utils.SalleStatus;

import java.util.List;

public interface SalleInterface {

    SalleDTO save(SalleDTO salleDTO);

    List<SalleDTO> getAll();

    SalleDTO getById(String codeSalle);

    SalleDTO update(String codeSalle ,SalleDTO salleDTO);

    void delete (String  codeSalle);
    SalleDTO findSallesOccupe(SalleStatus salleStatus);
}
