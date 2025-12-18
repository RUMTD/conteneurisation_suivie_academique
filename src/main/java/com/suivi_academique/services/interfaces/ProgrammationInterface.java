package com.suivi_academique.services.interfaces;

import com.suivi_academique.dto.ProgrammationDTO;
import com.suivi_academique.dto.SalleDTO;

import java.util.List;

public interface ProgrammationInterface {

    ProgrammationDTO save(ProgrammationDTO programmationDTO);

    List<ProgrammationDTO> getAll();

    ProgrammationDTO getById(int id);

    ProgrammationDTO update(int id ,ProgrammationDTO programmationDTO);

    void delete (int  id);
}
