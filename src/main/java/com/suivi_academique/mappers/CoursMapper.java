package com.suivi_academique.mappers;


import com.suivi_academique.dto.CoursDTO;
import com.suivi_academique.entities.Cours;
import com.suivi_academique.entities.Programmation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CoursMapper {

    public CoursDTO toDTO(Cours cours){
        return new CoursDTO(cours.getCodeCours(), cours.getLabelCours(), cours.getDescCours(), cours.getNbHeureCours(),
                cours.getNbCreditCours()
        );
    }

    public Cours toEntity(CoursDTO coursDTO){
        List<Programmation> list = new ArrayList<>();
        return new Cours(coursDTO.getCodeCours(), coursDTO.getLabelCours(), coursDTO.getDescCours(), coursDTO.getNbHeureCours(),
                coursDTO.getNbCreditCours(), list
        );
    }

}
