package com.suivi_academique.dto;

import com.suivi_academique.entities.Affectation;
import com.suivi_academique.entities.AffectationId;
import com.suivi_academique.entities.Cours;
import com.suivi_academique.entities.Personnel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AffectationDTO {


    private PersonnelDTO personnel;

    private CoursDTO cours;

    private AffectationId codeAffectation;


}
