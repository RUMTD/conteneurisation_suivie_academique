package com.suivi_academique.services.interfaces;

import com.suivi_academique.dto.PersonnelDTO;
import com.suivi_academique.dto.SalleDTO;

import java.util.List;

public interface Personnelnterface {

    PersonnelDTO save(PersonnelDTO personnelDTO);

    List<PersonnelDTO> getAll();

    PersonnelDTO getById(String codePersonnel);

    PersonnelDTO getByRole(String roleString);

    PersonnelDTO update(String codePersonnel ,PersonnelDTO personnelDTO);

    void delete (String  codePersonnel);
}
