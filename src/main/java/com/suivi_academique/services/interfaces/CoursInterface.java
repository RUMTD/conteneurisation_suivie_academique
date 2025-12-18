package com.suivi_academique.services.interfaces;

import com.suivi_academique.dto.CoursDTO;

import java.util.List;

public interface CoursInterface {

    CoursDTO save(CoursDTO coursDTO);

    List<CoursDTO> getAll();

    CoursDTO getById(String codeCours);

    CoursDTO update(String codeCours ,CoursDTO coursDTO);

    void delete (String  codeCours);


}
