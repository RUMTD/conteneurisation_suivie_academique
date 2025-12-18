package com.suivi_academique.services.interfaces;

import com.suivi_academique.dto.AffectationDTO;
import com.suivi_academique.dto.SalleDTO;
import com.suivi_academique.entities.AffectationId;

import java.util.List;

public interface AffectationInterface {


    AffectationDTO save(AffectationDTO affectationDTO);

    List<AffectationDTO> getAll();

    AffectationDTO getById(AffectationId affectationId);

    AffectationDTO update(AffectationId affectationId, AffectationDTO affectationDTO);

    void delete(String codeCours, String codePersonnel);
}
